package net.factoryopen.core.myfactoryai.manufacturing.pln;

import net.factoryopen.core.common.exception.ObjectInUsingException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;

import net.factoryopen.core.common.exception.StatusUnavailableException;

/**
 * MRP物料需求ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnMrpMrAlgorithm
{
    @Autowired(required = false)
    private PlnMrpMrMapper mrMapper;

    @Autowired(required = false)
    private PlnMrpMpMapper mpMapper;

    @Autowired(required = false)
    private PlnMaterialComputingMapper mpsMapper;

      /**
       * 转需求
       *
       * @param ids id集合
       * @return 结果
       */
      public int toMp(Long[] ids) throws Exception{
          // 判异，排除主计划状态的錯配
          for (int i = 0; i < ids.length; i++) {
              PlnMrpMr mr = mrMapper.selectPlnMrpMrById(ids[i]);
              if (mr.getStatus().intValue() ==BillStatus.FINISH) {
                  String msg = "物料[" + mr.getMaterialName() + "]已转单至采购申请";
                  throw new ObjectInUsingException(msg);
              }

              PlnMaterialComputing mps = mpsMapper.selectPlnMaterialComputingById(mr.getMpsId());
              if (mps.getStatus().intValue() != BillStatus.BASE_ENABLE && mps.getStatus().intValue() != BillStatus.ACTIVE_PLANNING) {
                  String msg = "物料需求对应的主计划[" + mps.getBillNo() + "]的状态不是[4-投用]或[7-计划中]";
                  throw new StatusUnavailableException(msg);
              }
          }

          // 判异，排除主计划状态的錯配
          for (int i = 0; i < ids.length; i++) {
              PlnMrpMr mr = mrMapper.selectPlnMrpMrById(ids[i]);
              PlnMaterialComputing mps = mpsMapper.selectPlnMaterialComputingById(mr.getMpsId());

              // 创建工单（即生产订单Work Order）
              PlnMrpMp mp = new PlnMrpMp();
              //设置关键字段
              mp.setStatus(BillStatus.BASE_ENABLE);//采购申请的生效状态
              mp.setMpsId(mps.getId());
              mp.setMaterialId(mr.getMaterialId());
              mp.setQuantity(mr.getQuantity());
              //复制产品信息
              mp.setMaterialCode(mr.getMaterialCode());
              mp.setMaterialName(mr.getMaterialName());
              mp.setMaterialSpec(mr.getMaterialSpec());
              mp.setUnitId(mr.getUnitId());
              mp.setConsumeType(mr.getConsumeType());
              mp.setEnableMust(mr.getEnableMust());
              mp.setEnableReplace(mr.getEnableReplace());
              //保存工单
              mp.refreshCreatingStamp();
              mpMapper.insertPlnMrpMp(mp);

              //更改产线计划的状态
              mr.setStatus(BillStatus.FINISH);//设为“结案”状态，意为已转单
              mr.refreshUpdatingStamp();
              mrMapper.updatePlnMrpMr(mr);

              //更新主计划时间戳
              mps.refreshUpdatingStamp();
              mpsMapper.updatePlnMaterialComputing(mps);
          }

          return 1;
      }
}
