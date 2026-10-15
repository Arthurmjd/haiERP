package net.factoryopen.core.myfactoryai.manufacturing.pln;

import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.logic.ProductAlgorithm;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoComputingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMrpWoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoComputing;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.ObjectInUsingException;
import org.springframework.web.util.NestedServletException;

/**
 * 计划分解ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-01
 */
@Service
public class PlnWoComputingAlgorithm
{
    @Autowired(required = false)
    private ProductAlgorithm productAlgorithmr;

    @Autowired(required = false)
    private PlnWoComputingMapper mpsMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private PlnMrpWoMapper woMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

    /**
     * 分解
     *
     * @param ids id集合
     * @return 结果
     */
    public int splitMps(Long[] ids) throws Exception{
        try{
            // 判异，排除状态的錯配
            for (int i = 0; i < ids.length; i++) {
                PlnWoComputing mps = mpsMapper.selectPlnWoComputingById(ids[i]);
                if (mps.getStatus() != BillStatus.BASE_ENABLE && mps.getStatus() != BillStatus.ACTIVE_PLANNING) {
                    String msg = "主计划[" + mps.getBillNo() + "]的状态不是[4-投用]或[7-计划中]";
                    throw new StatusUnavailableException(msg);
                }

                List<PlnMrpWo> woList = woMapper.selectPlnMrpWoListByMpsId(mps.getId());
                for (int j = 0; j < woList.size(); j++) {
                    if (woList.get(j).getStatus() ==BillStatus.FINISH) {
                        String msg = "主计划[" + mps.getBillNo() + "]存在已转工单的的产线生产计划";
                        throw new ObjectInUsingException(msg);
                    }
                }
            }

            // 逐一取主计划，建MRP产线生产计划（工单前身）
            for (int i = 0; i < ids.length; i++) {
                PlnWoComputing mps = mpsMapper.selectPlnWoComputingById(ids[i]);
                double quantityMps = mps.getQuantityPlanned().doubleValue();

                //初始化结果HashMap、产品配比、交货日
                Double k =1D;
                Date day = mps.getPromiseDay();
                Integer priority = mps.getPriority();
                HashMap<Long,Object[]> bomTree = new HashMap<Long,Object[]>();
                //对产品启动递归计算
                productAlgorithmr.mergeWoTree(bomTree, null, mps.getProductId(), k, day, priority);

                //删除该主计划既有的MRP产线生产计划
                woMapper.deletePlnMrpWoByMpsId(mps.getId());

                //遍历bom树，建物料需求明细
                for(Long productId: bomTree.keySet()){
                    //取分解来的成品半成品
                    EngMaterial product = materialMapper.selectEngMaterialById(productId);

                    //取分解结果
                    Object[] objArray = bomTree.get(productId);
                    double rateMaterial = ((Double)objArray[0]).doubleValue();//半成品用料比
                    Date dayDelivery  = (Date)objArray[1];//计划交期
                    Integer priorityOrder  = (Integer)objArray[2];//优先级
                    Long lineId =(Long)objArray[3];//计划产线


                    // 创建产线生产计划，设置关键字段
                    PlnMrpWo wo = new PlnMrpWo();
                    wo.setStatus(BillStatus.BASE_ENABLE);//生效
                    wo.setMpsId(mps.getId());
                    wo.setProductId(productId);
                    // 计算需求量交期，并设置之
                    wo.setQuantity(new BigDecimal(quantityMps * rateMaterial).setScale(4, BigDecimal.ROUND_HALF_UP));
                    wo.setNeedDay(dayDelivery);
                    wo.setPriority(priorityOrder);
                    wo.setLineId(lineId);
                    //设置产线计划类型
                    switch (mps.getMpsType()) {
                        case 0: wo.setWoType(0); break; //MPS普通订单-> wo普通
                        case 1: wo.setWoType(2); break; //MPS首制订单-> wo首制
                        case 2: wo.setWoType(4); break; //MPS重制补单-> wo重制
                        case 3: wo.setWoType(0); break; //MPS客户预测-> wo普通
                        case 4: wo.setWoType(0); break; //MPS市场预测-> wo普通
                        case 5: wo.setWoType(0); break; //MPS安全备库-> wo普通
                        case 6: wo.setWoType(3); break; //MPS研发试制-> wo试制
                    }

                    //复制物料信息
                    wo.setProductCode(product.getMaterialCode());
                    wo.setProductName(product.getMaterialName());
                    wo.setProductSpec(product.getMaterialSpec());
                    wo.setDrawNo(product.getDrawNo());
                    wo.setCustomerProductNo(product.getCustomerProductNo());
                    wo.setAbroadProductNo(product.getAbroadProductNo());
                    wo.setUnitId(product.getUnitId());
                    wo.setOrderDay(mps.getOrderDay());
                    wo.setPromiseDay(mps.getPromiseDay());
                    wo.setProjectId(mps.getProjectId());
                    wo.setSoId(mps.getSoId());
                    wo.setSoItemId(mps.getSoItemId());
                    //保存产线计划
                    wo.refreshCreatingStamp();
                    woMapper.insertPlnMrpWo(wo);
                }

                //更新生产主计划状态,更新时间戳
                if (mps.getStatus()==BillStatus.BASE_ENABLE){
                    mps.setStatus(BillStatus.ACTIVE_PLANNING);
                }
                mps.refreshUpdatingStamp();
                mpsMapper.updatePlnWoComputing(mps);
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

        } catch (Exception e) {
            throw e;
        }

        return 1;
    }

}
