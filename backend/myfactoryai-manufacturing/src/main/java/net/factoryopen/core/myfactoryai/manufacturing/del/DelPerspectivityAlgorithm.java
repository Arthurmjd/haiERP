package net.factoryopen.core.myfactoryai.manufacturing.del;

import java.util.ArrayList;
import java.util.List;

import net.factoryopen.core.common.exception.DataNotExistException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPerspectivityMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivity;

/**
 * 订单透视表ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-23
 */
@Service
public class DelPerspectivityAlgorithm
{
    @Autowired(required = false)
    private DelPerspectivityMapper perspectivityMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

    @Autowired(required = false)
    private DelPlanItemMapper dpMapper;

    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 透算
       *
       * @param delPerspectivity 透算
       * @return 结果
       */
      public int calculatePerspectivity(DelPerspectivity delPerspectivity) throws Exception{
          List<DelPerspectivity> perspectivityList =new ArrayList<DelPerspectivity>();

          //清空当前用户数据
          perspectivityMapper.deleteDelPerspectivityByUserId(SecurityUtils.getUserId());

          List<OrdSo> soList = soMapper.selectOrdSoProcessingList(null);
          if (soList!=null && !soList.isEmpty()){
              for (int i=0;i<soList.size();i++){
                  Long soId =soList.get(i).getId();
                  OrdSo so = soMapper.selectOrdSoById(soId);

                  //搜刮订单明细子孙，创建子孙的条目
                  List<OrdSoItem> soItemList = soitemMapper.selectOrdSoItemListBySoId(soId);
                  if (soItemList!=null){
                      for (int j=0;j<soItemList.size();j++){
                          int dpSeq=0;
                          Long soitemId= soItemList.get(j).getId();
                          OrdSoItem soitem = soitemMapper.selectOrdSoItemById(soitemId);

                          //创建自己的条目
                          DelPerspectivity perspectivity = new DelPerspectivity();
                          perspectivity.setUserId(SecurityUtils.getUserId());
                          perspectivity.setCustomerId(so.getCustomerId());
                          perspectivity.setSoId(soId);
                          perspectivity.setSoItemId(soitemId);
                          perspectivity.setSoNo(so.getBillNo());
                          perspectivity.setSoItemSeq(soitem.getSeqNo());
                          perspectivity.setSoItemStatus(soitem.getStatus());
                          perspectivity.setProductId(soitem.getProductId());
                          perspectivity.setProductCode(soitem.getProductCode());
                          perspectivity.setProductName(soitem.getProductName());
                          perspectivity.setProductSpec(soitem.getProductSpec());
                          perspectivity.setDrawNo(soitem.getDrawNo());
                          perspectivity.setCustomerProductNo(soitem.getCustomerProductNo());
                          perspectivity.setDpTypeSeq(dpSeq);
                          perspectivity.setQuantityTask(soitem.getQuantityOrder());
                          perspectivity.setQuantityOutput(soitem.getQuantityDelivered());
                          perspectivity.refreshCreatingStamp();
                          perspectivityList.add(perspectivity);

                          //搜刮直发子孙，创建子孙的条目
                          List<DelPlanItem> dpList = dpMapper.selectDelPlanItemListBySoItemId(soitemId);
                          if (dpList!=null){
                              for (int k=0;k<dpList.size();k++){
                                  dpSeq++;
                                  Long dpId = dpList.get(k).getId();
                                  DelPlanItem dp = dpMapper.selectDelPlanItemById(dpId);

                                  //创建自己的条目
                                  perspectivity = new DelPerspectivity();
                                  perspectivity.setUserId(SecurityUtils.getUserId());
                                  perspectivity.setCustomerId(so.getCustomerId());
                                  perspectivity.setSoId(soId);
                                  perspectivity.setSoItemId(soitemId);
                                  perspectivity.setSoNo(so.getBillNo());
                                  perspectivity.setSoItemSeq(soitem.getSeqNo());
                                  perspectivity.setSoItemStatus(soitem.getStatus());
                                  perspectivity.setProductId(soitem.getProductId());
                                  perspectivity.setProductName(soitem.getProductName());
                                  perspectivity.setDpTypeSeq(dpSeq);
                                  perspectivity.setDpTypeDesc("直发");
                                  perspectivity.setDpBillno(dp.getBillNo());
                                  perspectivity.setDpStatus(dp.getStatus());
                                  perspectivity.setQuantityTask(dp.getQuantityPlanned());
                                  perspectivity.setQuantityOutput(dp.getQuantityReleased());
                                  perspectivity.refreshCreatingStamp();
                                  perspectivityList.add(perspectivity);
                              }
                          }

                          //搜刮转包子孙，创建子孙的条目
                          List<DelOsplan> opList = osplanMapper.selectDelOsplanListBySoItemId(soitemId);
                          if (opList!=null){
                              for (int k=0;k<opList.size();k++){
                                  dpSeq++;
                                  Long opId = opList.get(k).getId();
                                  DelOsplan op = osplanMapper.selectDelOsplanById(opId);

                                  //创建自己的条目
                                  perspectivity = new DelPerspectivity();
                                  perspectivity.setUserId(SecurityUtils.getUserId());
                                  perspectivity.setCustomerId(so.getCustomerId());
                                  perspectivity.setSoId(soId);
                                  perspectivity.setSoItemId(soitemId);
                                  perspectivity.setSoNo(so.getBillNo());
                                  perspectivity.setSoItemSeq(soitem.getSeqNo());
                                  perspectivity.setSoItemStatus(soitem.getStatus());
                                  perspectivity.setProductId(soitem.getProductId());
                                  perspectivity.setProductName(soitem.getProductName());
                                  perspectivity.setDpTypeSeq(dpSeq);
                                  perspectivity.setDpTypeDesc("转包");
                                  perspectivity.setDpBillno(op.getBillNo());
                                  perspectivity.setDpStatus(op.getStatus());
                                  perspectivity.setQuantityTask(op.getQuantityPlanned());
                                  perspectivity.setQuantityOutput(op.getQuantityFinished());
                                  perspectivity.refreshCreatingStamp();
                                  perspectivityList.add(perspectivity);
                              }
                          }

                          //搜刮自制子孙，创建子孙的条目
                          List<DelMps> mpList = mpsMapper.selectDelMpsListBySoItemId(soitemId);
                          if (mpList!=null){
                              for (int k=0;k<mpList.size();k++){
                                  dpSeq++;

                                  int woSeq=0;
                                  Long mpId = mpList.get(k).getId();
                                  DelMps mp = mpsMapper.selectDelMpsById(mpId);

                                  //创建自己的条目
                                  perspectivity = new DelPerspectivity();
                                  perspectivity.setUserId(SecurityUtils.getUserId());
                                  perspectivity.setCustomerId(so.getCustomerId());
                                  perspectivity.setSoId(soId);
                                  perspectivity.setSoItemId(soitemId);
                                  perspectivity.setSoNo(so.getBillNo());
                                  perspectivity.setSoItemSeq(soitem.getSeqNo());
                                  perspectivity.setSoItemStatus(soitem.getStatus());
                                  perspectivity.setProductId(soitem.getProductId());
                                  perspectivity.setProductName(soitem.getProductName());
                                  perspectivity.setDpTypeSeq(dpSeq);
                                  perspectivity.setDpTypeDesc("自制");
                                  perspectivity.setDpBillno(mp.getBillNo());
                                  perspectivity.setDpStatus(mp.getStatus());
                                  perspectivity.setWoSeq(woSeq);
                                  perspectivity.setQuantityTask(mp.getQuantityPlanned());
                                  perspectivity.setQuantityOutput(mp.getQuantityFinished());
                                  perspectivity.refreshCreatingStamp();
                                  perspectivityList.add(perspectivity);

                                  //搜刮子孙，创建子孙的条目
                                  List<PlnWo> woList = woMapper.selectPlnWoListByMpsId(mpId);
                                  if (woList!=null){
                                      for (int l=0;l<woList.size();l++){
                                          woSeq++;

                                          int mlSeq=0;
                                          Long woId=woList.get(l).getId();
                                          PlnWo wo = woMapper.selectPlnWoById(woId);

                                          //创建自己的条目
                                          perspectivity = new DelPerspectivity();
                                          perspectivity.setUserId(SecurityUtils.getUserId());
                                          perspectivity.setCustomerId(so.getCustomerId());
                                          perspectivity.setSoId(soId);
                                          perspectivity.setSoItemId(soitemId);
                                          perspectivity.setSoNo(so.getBillNo());
                                          perspectivity.setSoItemSeq(soitem.getSeqNo());
                                          perspectivity.setSoItemStatus(soitem.getStatus());
                                          perspectivity.setProductId(soitem.getProductId());
                                          perspectivity.setProductName(soitem.getProductName());
                                          perspectivity.setDpTypeSeq(dpSeq);
                                          perspectivity.setDpTypeDesc("自制");
                                          perspectivity.setDpBillno(mp.getBillNo());
                                          perspectivity.setDpStatus(mp.getStatus());
                                          perspectivity.setWoSeq(woSeq);
                                          perspectivity.setWoId(woId);
                                          perspectivity.setWoStatus(wo.getStatus());
                                          perspectivity.setMlSeq(mlSeq);
                                          perspectivity.setQuantityTask(wo.getQuantityPlanned());
                                          perspectivity.setQuantityOutput(wo.getQuantityOutput());
                                          perspectivity.setQuantityScrap(wo.getQuantityScrap());
                                          perspectivity.setQuantityRedisp(wo.getQuantityRedisp());
                                          perspectivity.setQuantityRework(wo.getQuantityRework());
                                          perspectivity.setQuantityPending(wo.getQuantityPending());
                                          perspectivity.setQuantityAod(wo.getQuantityAod());
                                          perspectivity.refreshCreatingStamp();
                                          perspectivityList.add(perspectivity);

                                          //搜刮子孙，创建子孙的条目
                                          List<PlnMl> mlList = mlMapper.selectPlnMlListByWoId(woId);
                                          if (mlList!=null){
                                              for (int m=0; m<mlList.size();m++){
                                                  mlSeq++;

                                                  Long mlId = mlList.get(m).getId();
                                                  PlnMl ml = mlMapper.selectPlnMlById(mlId);

                                                  perspectivity = new DelPerspectivity();
                                                  perspectivity.setUserId(SecurityUtils.getUserId());
                                                  perspectivity.setCustomerId(so.getCustomerId());
                                                  perspectivity.setSoId(soId);
                                                  perspectivity.setSoItemId(soitemId);
                                                  perspectivity.setSoNo(so.getBillNo());
                                                  perspectivity.setSoItemSeq(soitem.getSeqNo());
                                                  perspectivity.setSoItemStatus(soitem.getStatus());
                                                  perspectivity.setProductId(soitem.getProductId());
                                                  perspectivity.setProductName(soitem.getProductName());
                                                  perspectivity.setDpTypeSeq(dpSeq);
                                                  perspectivity.setDpTypeDesc("自制");
                                                  perspectivity.setDpBillno(mp.getBillNo());
                                                  perspectivity.setDpStatus(mp.getStatus());
                                                  perspectivity.setWoId(woId);
                                                  perspectivity.setWoStatus(wo.getStatus());
                                                  perspectivity.setMlSeq(mlSeq);
                                                  perspectivity.setMlId(mlId);
                                                  perspectivity.setMlStatus(ml.getStatus());
                                                  perspectivity.setMlLineId(ml.getLineId());
                                                  perspectivity.setMlProccessId(ml.getCurrentProcessId());
                                                  perspectivity.setMlProcessStatus(ml.getProcessProgress());
                                                  perspectivity.setQuantityTask(ml.getQuantityDispatched());
                                                  perspectivity.setQuantityOutput(ml.getQuantityOutput());
                                                  perspectivity.setQuantityScrap(ml.getQuantityScrap());
                                                  perspectivity.setQuantityRedisp(ml.getQuantityRedisp());
                                                  perspectivity.setQuantityRework(ml.getQuantityRework());
                                                  perspectivity.setQuantityPending(ml.getQuantityPending());
                                                  perspectivity.setQuantityAod(ml.getQuantityAod());
                                                  perspectivity.setQuantitySplit(ml.getQuantitySplit());
                                                  perspectivity.refreshCreatingStamp();
                                                  perspectivityList.add(perspectivity);
                                              }
                                          }
                                      }
                                  }

                              }
                          }
                      }
                  }
              }
          }

          if (!perspectivityList.isEmpty()){
              for (DelPerspectivity item : perspectivityList) perspectivityMapper.insertDelPerspectivity(item);
              perspectivityList.clear();
          }else{
              String msg = "没有销售订单可供透视！";
              throw new DataNotExistException(msg);
          }

          return 1;
      }

}
