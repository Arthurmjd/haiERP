package net.factoryopen.core.myfactoryai.manufacturing.pln;

import net.factoryopen.core.common.exception.BomUnavailableException;
import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.exception.ObjectInUsingException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.logic.ProductAlgorithm;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.HashMap;
import java.math.BigDecimal;

import net.factoryopen.core.common.exception.StatusUnavailableException;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMaterialComputingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpMrMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;

/**
 * 物料计算ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-30
 */
@Service
public class PlnMaterialComputingAlgorithm
{
    @Autowired(required = false)
    private ProductAlgorithm productAlgorithmr;

    @Autowired(required = false)
    private PlnMaterialComputingMapper plnMaterialComputingMapper;

    @Autowired(required = false)
    private PlnMrpMrMapper mrMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

      /**
       * 算料
       *
       * @param ids id集合
       * @return 结果
       */
      public int runMrp(Long[] ids) throws Exception{
          // 判异，排除状态的錯配
          for (int i = 0; i < ids.length; i++) {
              PlnMaterialComputing mps = plnMaterialComputingMapper.selectPlnMaterialComputingById(ids[i]);
              if (mps.getStatus().intValue() != BillStatus.BASE_ENABLE && mps.getStatus().intValue() != BillStatus.ACTIVE_PLANNING) {
                  String msg = "主计划[" + mps.getBillNo() + "]的状态不是[4-投用]或[7-计划中]";
                  throw new StatusUnavailableException(msg);
              }

              List<PlnMrpMr> mrList = mrMapper.selectPlnMrpMrListByMpsId(mps.getId());
              for (int j = 0; j < mrList.size(); j++) {
                  if (mrList.get(j).getStatus().intValue() ==BillStatus.FINISH) {
                      String msg = "主计划[" + mps.getBillNo() + "]存在已转物料计划的的物料需求";
                      throw new ObjectInUsingException(msg);
                  }
              }
          }

          // 逐一取主计划，建物料需求明细
          for (int i = 0; i < ids.length; i++) {
              PlnMaterialComputing mps = plnMaterialComputingMapper.selectPlnMaterialComputingById(ids[i]);
              double quantityMps = mps.getQuantityPlanned().doubleValue();

              //合并统计成品零部件的bom树的物料总配比
              HashMap<Long,Double> bomTree = new HashMap<Long,Double>();
              productAlgorithmr.mergeBomTree(bomTree, mps.getProductId(), 1);

              //删除该主计划既有的物料需求明细
              mrMapper.deletePlnMrpMrByMpsId(mps.getId());

              //遍历bom树，建物料需求明细
              for(Long materialId : bomTree.keySet()){
                  EngMaterial material = materialMapper.selectEngMaterialById(materialId);
                  if (material==null) {
                      String msg = "BOM中物料[ID:"+materialId+"]不存在！";
                      MaterialUnavailableException ex = new MaterialUnavailableException(msg);
                      throw ex;
                  }
                  double k = bomTree.get(materialId).doubleValue();

                  // 创建物料需求，设置关键字段
                  PlnMrpMr mr = new PlnMrpMr();
                  mr.setMpsId(mps.getId());
                  mr.setStatus(BillStatus.BASE_ENABLE);//设置为“生效”状态
                  // 计算需求量，并设置之
                  mr.setQuantity(new BigDecimal(quantityMps * k).setScale(2, BigDecimal.ROUND_HALF_UP));
                  //复制物料信息
                  mr.setMaterialId(materialId);
                  mr.setMaterialCode(material.getMaterialCode());
                  mr.setMaterialName(material.getMaterialName());
                  mr.setMaterialSpec(material.getMaterialSpec());
                  mr.setUnitId(material.getUnitId());
                  //不为null则赋值
                  if(material.getConsumeType()!=null) mr.setConsumeType(Integer.valueOf(material.getConsumeType()));
                  mr.setEnableMust(null);
                  mr.setEnableReplace(null);
                  //保存物料需求
                  mr.refreshCreatingStamp();
                  mrMapper.insertPlnMrpMr(mr);
              }

              //更新生产主计划状态
              if (mps.getStatus()==BillStatus.BASE_ENABLE){
                  mps.setStatus(BillStatus.ACTIVE_PLANNING);
              }
              mps.refreshUpdatingStamp();
              plnMaterialComputingMapper.updatePlnMaterialComputing(mps);
              //更新订单明细的状态
              OrdSoItem soitem = soitemMapper.selectOrdSoItemById(mps.getSoItemId());
              //TODO 要增加主计划类型的判断
              if (soitem!=null) {
                  if (soitem.getStatus()==BillStatus.ACTIVE_PLANNING){
                      soitem.setStatus(BillStatus.ACTIVE_PROCESSING);
                      soitem.refreshUpdatingStamp();
                      soitemMapper.updateOrdSoItem(soitem);
                  }
                  //更新订单的状态
                  OrdSo so = soMapper.selectOrdSoById(mps.getSoId());
                  if(so.getStatus()==BillStatus.ACTIVE_PLANNING) {
                      so.setStatus(BillStatus.ACTIVE_PROCESSING);
                      so.refreshUpdatingStamp();
                      soMapper.updateOrdSo(so);
                  }
              }
          }

          return 1;
      }

}
