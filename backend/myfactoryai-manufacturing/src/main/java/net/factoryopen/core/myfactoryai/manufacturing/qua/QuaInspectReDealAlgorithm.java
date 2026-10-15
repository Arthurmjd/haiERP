package net.factoryopen.core.myfactoryai.manufacturing.qua;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.InspectionType;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectReDealMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 质检请求处理ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-10-06
 */
@Service
public class QuaInspectReDealAlgorithm
{
    @Autowired(required = false)
    private QuaInspectReDealMapper requestMapper;

    @Autowired(required = false)
    private QuaInspectRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockRequestMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper stockRequestItemMapper;

    @Autowired(required = false)
    private SupPoItemMapper poitemMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 收检
       *
       * @param request 质检请求
       * @return 结果
       */
      public int acceptInspection(QuaInspectReDeal request) throws Exception{
          //防呆检查
          if (request.getStatus()!= BillStatus.BASE_ENABLE){
              String msg = "质检请求[" + request.getBillNo() + "]的状态不是[4-生效]";
              throw new StatusUnavailableException(msg);
          }

          //更新请求状态
          request=requestMapper.selectQuaInspectReDealById(request.getId());
          request.setStatus(BillStatus.ACTIVE_STARTED);
          request.refreshUpdatingStamp();
          requestMapper.updateQuaInspectReDeal(request);
          //更新请求明细的状态
          List<QuaInspectRequestItem> reqItems = requestItemMapper.selectQuaInspectRequestItemListByRequestId(request.getId());
          for (QuaInspectRequestItem reqItem : reqItems){
              if (reqItem.getQuantity().doubleValue()>0) {
                  reqItem.setStatus(BillStatus.ACTIVE_STARTED);
                  reqItem.refreshUpdatingStamp();
                  requestItemMapper.updateQuaInspectRequestItem(reqItem);
              }
              //明细的收货量不大于0，则删除之
              else{
                  requestItemMapper.deleteQuaInspectRequestItemById(reqItem.getId());
              }

              //更新前置单据的数量
              SncStockRequestItem stockRequestItem = null;
              SupPoItem poitem = null;
              switch (request.getInspectType()){
                  case InspectionType.RECEIVE_SUBCONTRACT:
                      stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(reqItem.getSourceId());
                      poitem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                      poitem.setQuantityInspected(new BigDecimal(poitem.getQuantityInspected().doubleValue()+reqItem.getQuantity().doubleValue()));
                      poitemMapper.updateSupPoItem(poitem);
                      break;
                  case InspectionType.RECEIVE_BUYING:
                      stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(reqItem.getSourceId());
                      poitem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                      poitem.setQuantityInspected(new BigDecimal(poitem.getQuantityInspected().doubleValue()+reqItem.getQuantity().doubleValue()));
                      poitemMapper.updateSupPoItem(poitem);
                      break;
                  case InspectionType.RECEIVE_OUTERSUPPORT:
                      stockRequestItem = stockRequestItemMapper.selectSncStockRequestItemById(reqItem.getSourceId());
                      poitem = poitemMapper.selectSupPoItemById(stockRequestItem.getSourceId());
                      poitem.setQuantityInspected(new BigDecimal(poitem.getQuantityInspected().doubleValue()+reqItem.getQuantity().doubleValue()));
                      poitemMapper.updateSupPoItem(poitem);
                      break;
                  case InspectionType.CHECK_SELF:
                      break;
                  case InspectionType.CHECK_IN_PROCESS:
                      break;
                  case InspectionType.CHECK_OUTPUT:
                      break;
                  case InspectionType.CHECK_DELIVERY:
                      break;
                  case InspectionType.RECEIVE_DELIVERY_BACK:
                      break;
                  default:
              }

          }

          //更新请求的来源单号（收货单号）和状态（在处理）
          SncStockRequest stockRequest = stockRequestMapper.selectSncStockRequestById(request.getRequestBillId());
          stockRequest.setStatus(BillStatus.ACTIVE_PROCESSING);
          stockRequest.refreshUpdatingStamp();
          stockRequestMapper.updateSncStockRequest(stockRequest);

          return 1;
      }

}
