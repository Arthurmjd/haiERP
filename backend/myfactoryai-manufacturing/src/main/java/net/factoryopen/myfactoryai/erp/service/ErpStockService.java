package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.mapper.ErpStockMapper;
import org.springframework.stereotype.Service;

@Service
public class ErpStockService {
    private final ErpStockMapper mapper;
    private final ObjectMapper json;

    public ErpStockService(ErpStockMapper mapper, ObjectMapper json) {
        this.mapper = mapper;
        this.json = json;
    }

    public boolean begin(String key, String action, String targetType, Long targetId, Object payload) {
        if (key == null || key.isBlank() || key.length() > 80) throw new ServiceException("requestKey 不合法", ErpErrorCode.INVALID_PARAM.code());
        String hash = hash(payload);
        mapper.reserveAction(key,action,targetType,targetId,hash,SecurityUtils.getUsername());
        Map<String,Object> prior = mapper.action(key);
        if (!action.equals(prior.get("actionType")) || !targetType.equals(prior.get("targetType"))
                || !targetId.equals(((Number) prior.get("targetId")).longValue())
                || !hash.equals(prior.get("payloadHash"))) {
            throw new ServiceException("请求键已用于不同操作或参数", ErpErrorCode.IDEMPOTENCY_CONFLICT.code());
        }
        return ((Number) prior.get("status")).intValue() == 1;
    }

    public void complete(String key, Long targetId) {
        if (mapper.completeAction(key,"{\"targetId\":\"" + targetId + "\"}") != 1) {
            throw new ServiceException("请求状态异常", ErpErrorCode.IDEMPOTENCY_CONFLICT.code());
        }
    }

    public void receipt(Long materialId,String batch,BigDecimal qty,Long poId,Long lineId,
                        String requestKey,LocalDate date) {
        add(1,materialId,batch,qty);
        txn(1,1,1,materialId,batch,qty,"pu_order",poId,lineId,requestKey,date);
    }

    /** 一次扣减实际落在哪个仓、扣了多少，调用方据此写同仓库的流水。 */
    public record Consumed(Long warehouseId,BigDecimal qty) {}

    /** 入默认仓（单仓 MVP，1 号仓）。 */
    public void add(int itemType,Long itemId,String batch,BigDecimal qty) {
        mapper.addStock(itemType,itemId,batch,qty,(long) ErpStockMapper.DEFAULT_WAREHOUSE,SecurityUtils.getUsername());
    }

    /**
     * 按批次扣减结存，自动落到真正有货的仓库。
     *
     * <p>旧实现把 warehouse_id=1 写死在 SQL 里，而历史成品库存分布在 2 号仓，
     * 结果「库存明明够却报成品库存不足」。这里先查出该批号在每个仓库的结存，
     * 再逐仓扣，并把「批号不存在」和「结存不足」报成不同的话。</p>
     *
     * @param itemType 1 物料 / 2 成品 SKU
     * @param label    报错用的对象名称，如「成品」「物料」
     * @return 每个仓库实际扣掉的数量，调用方必须按它写流水，否则结存与流水对不上
     */
    public List<Consumed> deduct(int itemType,Long itemId,String batch,BigDecimal qty,String label) {
        List<Map<String,Object>> rows = mapper.batchStockRows(itemType,itemId,batch);
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String,Object> row : rows) total = total.add(decimal(row.get("qty")));
        if (total.compareTo(qty) < 0) throw new ServiceException(shortageMessage(itemType,itemId,batch,qty,total,label),
                ErpErrorCode.INSUFFICIENT.code());
        BigDecimal remain = qty;
        List<Consumed> consumed = new ArrayList<>();
        for (Map<String,Object> row : rows) {
            if (remain.signum() <= 0) break;
            BigDecimal rowQty = decimal(row.get("qty"));
            BigDecimal take = remain.min(rowQty);
            Long warehouseId = ((Number) row.get("warehouseId")).longValue();
            if (mapper.subtractStock(itemType,itemId,batch,take,warehouseId) != 1) {
                throw new ServiceException("库存已被其他操作占用，请刷新后重试", ErpErrorCode.INSUFFICIENT.code());
            }
            consumed.add(new Consumed(warehouseId,take));
            remain = remain.subtract(take);
        }
        return consumed;
    }

    private String shortageMessage(int itemType,Long itemId,String batch,BigDecimal need,BigDecimal have,String label) {
        String code = itemType == 2 ? mapper.skuCode(itemId) : mapper.materialCode(itemId);
        String name = (code == null ? label + " " + itemId : code);
        if (have.signum() <= 0) {
            StringBuilder batches = new StringBuilder();
            for (Map<String,Object> row : mapper.availableBatches(itemType,itemId)) {
                if (batches.length() > 0) batches.append("、");
                batches.append(row.get("batchNo")).append("(").append(plain(decimal(row.get("qty")))).append(")");
            }
            String hint = batches.length() == 0 ? "当前没有任何可用库存，请先入库" : "当前可用批次：" + batches;
            return name + " 没有批次「" + batch + "」的" + label + "库存；" + hint;
        }
        return name + " 批次「" + batch + "」结存 " + plain(have) + "，本次需要 " + plain(need);
    }

    private static BigDecimal decimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        return value instanceof BigDecimal d ? d : new BigDecimal(value.toString());
    }

    private static String plain(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    public void txn(int direction,int bizType,int itemType,Long itemId,String batch,
                    BigDecimal qty,String sourceType,Long sourceId,Long sourceLineId,
                    String requestKey,LocalDate date) {
        txn(direction,bizType,itemType,itemId,batch,qty,sourceType,sourceId,sourceLineId,requestKey,date,null);
    }

    public void txn(int direction,int bizType,int itemType,Long itemId,String batch,
                    BigDecimal qty,String sourceType,Long sourceId,Long sourceLineId,
                    String requestKey,LocalDate date,String remark) {
        txnAt((long) ErpStockMapper.DEFAULT_WAREHOUSE,direction,bizType,itemType,itemId,batch,qty,
                sourceType,sourceId,sourceLineId,requestKey,date,remark);
    }

    /**
     * 写库存流水，并显式记录发生在哪个仓库。
     *
     * <p>流水表的 warehouse_id 以前走默认值 1，扣减却可能发生在 2 号仓，
     * 导致「结存减了、流水记在另一个仓」，双向对账（self_check 第 3 项）必然失败。</p>
     */
    public void txnAt(Long warehouseId,int direction,int bizType,int itemType,Long itemId,String batch,
                      BigDecimal qty,String sourceType,Long sourceId,Long sourceLineId,
                      String requestKey,LocalDate date,String remark) {
        String no = "TX" + UUID.randomUUID().toString().replace("-", "").substring(0,26).toUpperCase();
        mapper.insertTxn(no,direction,bizType,itemType,itemId,batch,qty,
                warehouseId == null ? (long) ErpStockMapper.DEFAULT_WAREHOUSE : warehouseId,
                sourceType,sourceId,sourceLineId,requestKey,date,remark,SecurityUtils.getUsername());
    }

    private String hash(Object payload) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(json.writeValueAsBytes(payload));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new IllegalStateException("无法计算请求摘要",e);
        }
    }
}
