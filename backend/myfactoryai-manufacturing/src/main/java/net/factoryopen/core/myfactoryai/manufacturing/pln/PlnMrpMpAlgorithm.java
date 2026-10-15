package net.factoryopen.core.myfactoryai.manufacturing.pln;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialPurchasingType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMaterialComputingMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpMpMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;

import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;

import net.factoryopen.core.common.exception.StatusUnavailableException;

/**
 * 物料需求ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class PlnMrpMpAlgorithm
{
    @Autowired(required = false)
    private PlnMrpMpMapper mpMapper;

    @Autowired(required = false)
    private SupPurchaseRequestMapper prMapper;

    @Autowired(required = false)
    private SupPurchaseRequestItemMapper prItemMapper;

    @Autowired(required = false)
    private PlnMaterialComputingMapper mpsMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 请购
       *
       * @param ids id集合
       * @return 结果
       */
      public int requestPurchase(Long[] ids) throws Exception{
          // 判异，排除客户和状态的錯配
          for (int i = 0; i < ids.length; i++) {
              PlnMrpMp mp = mpMapper.selectPlnMrpMpById(ids[i]);
              PlnMaterialComputing mps = mpsMapper.selectPlnMaterialComputingById(mp.getMpsId());
              if (mp.getStatus() != BillStatus.BASE_ENABLE) {
                  String msg = "主生产计划为[" + mps.getBillNo() + "] 物料名为["+mp.getMaterialSpec()+"] 的物料计划项状态不是[4-生效]";
                  throw new StatusUnavailableException(msg);
              }
          }

          // 取得并更新单据规则
          String billNo = billcodeManager.getNextBillno("PR");
          billcodeManager.updateBillSeq("PR");

          // 创建采购申请单头
          SupPurchaseRequest pr = new SupPurchaseRequest();
          pr.setBillNo(billNo);
          pr.setStatus(BillStatus.BASE_AUDIT_REQUEST);//设为“待审”状态
          pr.setPurchasingType(MaterialPurchasingType.PRODUCTING);
          pr.setRequestDay(DateUtils.getNowDate());
          pr.setRequestorId(SecurityUtils.getUserId());
          pr.setDeptId(SecurityUtils.getDeptId());
          prMapper.insertSupPurchaseRequest(pr);

          // 逐一取原物料需求，建采购申请明细
          for (int i = 0; i < ids.length; i++) {
              PlnMrpMp mp = mpMapper.selectPlnMrpMpById(ids[i]);

              //创建并保存发货单身
              SupPurchaseRequestItem prItem = new SupPurchaseRequestItem();
              prItem.setSeqNo(i+1);
              prItem.setPrId(pr.getId());
              // 计算需求量，并设置之
              prItem.setQuantity(mp.getQuantity());
              //prItem.setRequireDay(mp.getRequireDay());
              //prItem.setPriority(mp.getPriority());
              //复制物料信息
              prItem.setMaterialId(mp.getMaterialId());
              prItem.setMaterialName(mp.getMaterialName());
              prItem.setMaterialSpec(mp.getMaterialSpec());
              prItem.setUnitId(mp.getUnitId());
              //不为null则赋值
              prItem.setConsumeType(mp.getConsumeType());
              prItem.setEnableMust(mp.getEnableMust());
              prItem.setEnableReplace(mp.getEnableReplace());
              //创建戳
              prItem.setCreateBy(SecurityUtils.getUsername());
              prItem.setCreateTime(DateUtils.getNowDate());
              //保存
              prItemMapper.insertSupPurchaseRequestItem(prItem);

              // 将原物料计划的状态设为“已转”
              mp.setStatus(BillStatus.FINISH);
              mpMapper.updatePlnMrpMp(mp);
          }

          return 1;
      }

}
