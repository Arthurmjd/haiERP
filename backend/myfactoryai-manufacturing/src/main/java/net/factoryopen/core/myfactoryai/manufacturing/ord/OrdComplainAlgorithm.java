package net.factoryopen.core.myfactoryai.manufacturing.ord;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdComplainMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;

import net.factoryopen.core.common.exception.StatusUnavailableException;

import java.math.BigDecimal;

/**
 * 客诉登记ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class OrdComplainAlgorithm
{
    @Autowired(required = false)
    private OrdComplainMapper complainMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockreqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private EngMaterialMapper productMapper;

    @Autowired
    private BillcodeManager billcodeManager;


      /**
       * 请检
       *
       * @param complain 客诉单
       * @return 结果
       */
      public int requestInspect(OrdComplain complain) throws Exception{
          // 判异，排除状态粗配的客诉单
          complain = complainMapper.selectOrdComplainById(complain.getId());
          if (complain.getStatus().intValue() != 4) {
              String msg = "客诉单[" + complain.getBillNo() + "]的状态不是[4-生效]";
              throw new StatusUnavailableException(msg);
          }

          // 取得并更新单据规则
          String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
          billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);
          //取so
          OrdSo so = soMapper.selectOrdSoById(complain.getSoId());
          //创建采购退货请求
          SncStockRequest request= new SncStockRequest();
          request.setBillNo(billNo);
          request.setIoType(InventoryTransactionType.STOCKIN_4_SALE_BACK);
          request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
          request.setRequestBillId(complain.getId());
          request.setRequestBillNo(complain.getBillNo());
          //补充来源信息
          request.setSourceCustomerId(so.getCustomerId());
          request.setSourceLineId(null);
          request.setSourceSupplierId(null);
          request.setProjectId(null);
          request.setRequestorId(SecurityUtils.getUserId());
          request.setRequestTime(DateUtils.getNowDate());
          //保存请求
          request.refreshCreatingStamp();
          stockreqMapper.insertSncStockRequest(request);

          //获取产品信息
          EngMaterial product = productMapper.selectEngMaterialById(complain.getProductId());
          //建仓库交易明细
          SncStockRequestItem requestItem = new SncStockRequestItem();
          requestItem.setSeqNo(1);
          requestItem.setRequestId(request.getId());
          requestItem.setMaterialId(complain.getProductId());
          requestItem.setMaterialCode(product.getMaterialCode());
          requestItem.setMaterialName(complain.getProductName());
          requestItem.setMaterialSpec(complain.getProductSpec());
          requestItem.setDrawNo(product.getDrawNo());
          requestItem.setCategoryId(product.getCategoryId());
          requestItem.setCompoundId(product.getCompoundId());
          requestItem.setCustomerProductNo(product.getCustomerProductNo());
          requestItem.setAbroadProductNo(product.getAbroadProductNo());
          requestItem.setUnitId(complain.getUnitId());
          requestItem.setQuantity(complain.getQuantity());
          requestItem.setPrice(complain.getPrice());
          requestItem.setAmount(complain.getAmount());
          requestItem.setHouseId(product.getHouseId());
          requestItem.setPositionId(product.getPositionId());
          requestItem.setSourceId(complain.getId());
          //创建戳
          requestItem.setCreateBy(SecurityUtils.getUsername());
          requestItem.setCreateTime(DateUtils.getNowDate());
          requestItem.setUpdateBy(SecurityUtils.getUsername());
          requestItem.setUpdateTime(DateUtils.getNowDate());
          requestItemMapper.insertSncStockRequestItem(requestItem);

          // 客诉单状态改为“请处理”
          complain = complainMapper.selectOrdComplainById(complain.getId());
          complain.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
          complain.refreshUpdatingStamp();
          complainMapper.updateOrdComplain(complain);

          return 1;
      }
}
