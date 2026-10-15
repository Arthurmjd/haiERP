package net.factoryopen.myfactoryai.erp.service;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HexFormat;
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
        if (key == null || key.isBlank() || key.length() > 80) throw new ServiceException("requestKey 不合法",1001);
        String hash = hash(payload);
        mapper.reserveAction(key,action,targetType,targetId,hash,SecurityUtils.getUsername());
        Map<String,Object> prior = mapper.action(key);
        if (!action.equals(prior.get("actionType")) || !targetType.equals(prior.get("targetType"))
                || !targetId.equals(((Number) prior.get("targetId")).longValue())
                || !hash.equals(prior.get("payloadHash"))) {
            throw new ServiceException("请求键已用于不同操作或参数",2007);
        }
        return ((Number) prior.get("status")).intValue() == 1;
    }

    public void complete(String key, Long targetId) {
        if (mapper.completeAction(key,"{\"targetId\":\"" + targetId + "\"}") != 1) {
            throw new ServiceException("请求状态异常",2007);
        }
    }

    public void receipt(Long materialId,String batch,BigDecimal qty,Long poId,Long lineId,
                        String requestKey,LocalDate date) {
        mapper.addStock(1,materialId,batch,qty,SecurityUtils.getUsername());
        txn(1,1,1,materialId,batch,qty,"pu_order",poId,lineId,requestKey,date);
    }

    public void txn(int direction,int bizType,int itemType,Long itemId,String batch,
                    BigDecimal qty,String sourceType,Long sourceId,Long sourceLineId,
                    String requestKey,LocalDate date) {
        txn(direction,bizType,itemType,itemId,batch,qty,sourceType,sourceId,sourceLineId,requestKey,date,null);
    }

    public void txn(int direction,int bizType,int itemType,Long itemId,String batch,
                    BigDecimal qty,String sourceType,Long sourceId,Long sourceLineId,
                    String requestKey,LocalDate date,String remark) {
        String no = "TX" + UUID.randomUUID().toString().replace("-", "").substring(0,26).toUpperCase();
        mapper.insertTxn(no,direction,bizType,itemType,itemId,batch,qty,sourceType,
                sourceId,sourceLineId,requestKey,date,remark,SecurityUtils.getUsername());
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
