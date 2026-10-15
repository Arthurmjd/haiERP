package net.factoryopen.core.myfactoryai.manufacturing.qua;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.InspectionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.ExeInspectSelfMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectSelf;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 自检异常登记ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class ExeInspectSelfAlgorithm
{
    @Autowired(required = false)
    private ExeInspectSelfMapper exeInspectSelfMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 请判
       *
       * @param exeInspectSelf 请判
       * @return 结果
       */
      public int requestDeal(ExeInspectSelf exeInspectSelf) throws Exception{
          //防呆检查
          ExeInspectSelf inspectSelf=exeInspectSelfMapper.selectExeInspectSelfById(exeInspectSelf.getId());
          if (inspectSelf.getStatus()!= BillStatus.BASE_DRAFT){
              String msg = "自检异常[" + inspectSelf.getBillNo() + "]的状态不是[0-底稿]";
              throw new StatusUnavailableException(msg);
          }

          //更新状态
          inspectSelf.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
          inspectSelf.refreshUpdatingStamp();
          exeInspectSelfMapper.updateExeInspectSelf(inspectSelf);

          return 1;
      }

}
