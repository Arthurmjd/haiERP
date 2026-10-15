package net.factoryopen.core.myfactoryai.manufacturing.pln;

import net.factoryopen.core.common.exception.ObjectInUsingException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.*;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductSopMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductDrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductVideoMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingkipartsMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingtoolMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductRoutingsopMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingdrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductRoutingvideoMapper;

import net.factoryopen.core.common.exception.StatusUnavailableException;

import java.util.List;
import java.math.BigDecimal;

/**
 * MRP工单项ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-07-02
 */
@Service
public class PlnMrpWoAlgorithm
{
    @Autowired(required = false)
    private PlnMrpWoMapper mrpWoMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private PlnWoComputingMapper mpsMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private EngProductBomMapper pbomMapper;

    @Autowired(required = false)
    private EngProductDrawingMapper pdrawingMapper;

    @Autowired(required = false)
    private EngPruductSopMapper psopMapper;

    @Autowired(required = false)
    private EngPruductVideoMapper pvideoMapper;

    @Autowired(required = false)
    private EngProductRoutingMapper proutingMapper;

    @Autowired(required = false)
    private EngProductRoutingkipartsMapper prpartsMapper;

    @Autowired(required = false)
    private EngProductRoutingtoolMapper prtoolMapper;

    @Autowired(required = false)
    private EngPruductRoutingsopMapper prsopMapper;

    @Autowired(required = false)
    private EngProductRoutingdrawingMapper prdrawingMapper;

    @Autowired(required = false)
    private EngPruductRoutingvideoMapper prvideoMapper;

    @Autowired(required = false)
    private PlnWoMaterialMapper wmaterialMapper;

    @Autowired(required = false)
    private PlnWoDrawingMapper wdrawingMapper;

    @Autowired(required = false)
    private PlnWoSopMapper wsopMapper;

    @Autowired(required = false)
    private PlnWoVideoMapper wvideoMapper;

    @Autowired(required = false)
    private PlnWoRoutingMapper wroutingMapper;

    @Autowired(required = false)
    private PlnWoRoutingkipartsMapper wrpartsMapper;

    @Autowired(required = false)
    private PlnWoRoutingtoolMapper wrtoolMapper;

    @Autowired(required = false)
    private PlnWoRoutingsopMapper wrsopMapper;

    @Autowired(required = false)
    private PlnWoRoutingdrawingMapper wrdrawingMapper;

    @Autowired(required = false)
    private PlnWoRoutingvideoMapper wrvideoMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 转工单
     *
     * @param ids id集合
     * @return 结果
     */
    public int toWo(Long[] ids) throws Exception{
        // 判异，排除主计划状态的錯配
        for (int i = 0; i < ids.length; i++) {
            PlnMrpWo mrpWo = mrpWoMapper.selectPlnMrpWoById(ids[i]);
            if (mrpWo.getStatus().intValue() ==BillStatus.FINISH) {
                  String msg = "产品[" + mrpWo.getProductName() + "]已转过工单";
                  throw new ObjectInUsingException(msg);
            }

            PlnWoComputing mps = mpsMapper.selectPlnWoComputingById(mrpWo.getMpsId());
            if (mps.getStatus().intValue() != BillStatus.BASE_ENABLE && mps.getStatus().intValue() != BillStatus.ACTIVE_PLANNING) {
                  String msg = "该产线生产计划项对应的主计划[" + mps.getBillNo() + "]的状态不是[4-投用]或[7-计划中]";
                  throw new StatusUnavailableException(msg);
            }
        }

        // 判异，排除主计划状态的錯配
        for (int i = 0; i < ids.length; i++) {
            PlnMrpWo mrpWo = mrpWoMapper.selectPlnMrpWoById(ids[i]);
            PlnWoComputing mps = mpsMapper.selectPlnWoComputingById(mrpWo.getMpsId());

            // 取得并更新单据规则
            String billNo = billcodeManager.getNextBillno("WO");
            billcodeManager.updateBillSeq("WO");

            // 创建工单（即生产订单Work Order）
            PlnWo wo = new PlnWo();
            wo.setBillNo(billNo);
            wo.setStatus(BillStatus.BASE_AUDIT_PASS);//工单的生效状态
            //设置关键字段
            wo.setMpsId(mps.getId());
            wo.setProductId(mrpWo.getProductId());
            wo.setQuantityPlanned(mrpWo.getQuantity());
            wo.setNeedDay(mrpWo.getNeedDay());
            wo.setPriority(mrpWo.getPriority());
            wo.setLineId(mrpWo.getLineId());
            wo.setWoType(mrpWo.getWoType());
            //复制产品信息
            wo.setProductCode(mrpWo.getProductCode());
            wo.setProductName(mrpWo.getProductName());
            wo.setProductSpec(mrpWo.getProductSpec());
            wo.setDrawNo(mrpWo.getDrawNo());
            wo.setCustomerProductNo(mrpWo.getCustomerProductNo());
            wo.setAbroadProductNo(mrpWo.getAbroadProductNo());
            wo.setUnitId(mrpWo.getUnitId());
            wo.setOrderDay(mrpWo.getOrderDay());
            wo.setPromiseDay(mrpWo.getPromiseDay());
            wo.setProjectId(mrpWo.getProjectId());
            wo.setSoId(mrpWo.getSoId());
            wo.setSoItemId(mrpWo.getSoItemId());
            //保存工单
            wo.refreshCreatingStamp();
            woMapper.insertPlnWo(wo);

            //更改产线计划的状态
            mrpWo.setStatus(BillStatus.FINISH);//设为“结案”状态，表面已转单
            mrpWo.refreshUpdatingStamp();
            mrpWoMapper.updatePlnMrpWo(mrpWo);

            //创建工单的工艺子表项集
            this.createWoMaterial(wo);
            this.createWoRouting(wo);
            this.createWoSop(wo);
            this.createWoDrawing(wo);
            this.createWoVideo(wo);
        }

        return 1;
    }

    private void createWoMaterial(PlnWo wo) {
        List<EngProductBom> bomList = pbomMapper.selectEngProductBomListByProductId(wo.getProductId());
        for (int i = 0; bomList!=null && i < bomList.size(); i++) {
            //取bomItem
            EngProductBom bomItem = bomList.get(i);
            //取bomItem的物料信息
            EngMaterial material = materialMapper.selectEngMaterialById(bomItem.getMaterialId());
            //计算物料量
            double quantity=(bomItem.getStandardQuantity().doubleValue()+bomItem.getLossRate().doubleValue()/100)*wo.getQuantityPlanned().doubleValue();

            //创建工单备料项
            PlnWoMaterial wmaterial = new PlnWoMaterial();
            wmaterial.setWoId(wo.getId());
            wmaterial.setSeqNo(bomItem.getSeqNo());
            wmaterial.setMaterialId(bomItem.getMaterialId());
            wmaterial.setMaterialCode(bomItem.getMaterialCode());
            wmaterial.setMaterialName(bomItem.getMaterialName());
            wmaterial.setMaterialSpec(bomItem.getMaterialSpec());
            wmaterial.setUnitId(material.getUnitId());
            wmaterial.setQuantity(new BigDecimal(quantity).setScale(4, BigDecimal.ROUND_HALF_UP));
            wmaterial.setConsumeType(bomItem.getConsumeType()==null?null:Integer.valueOf(bomItem.getConsumeType()));
            wmaterial.setEnableMust(bomItem.getEnableMust());
            wmaterial.setEnableReplace(bomItem.getEnableReplace());
            wmaterial.setRemark(null);
            //创建戳
            wmaterial.setCreateBy(SecurityUtils.getUsername());
            wmaterial.setCreateTime(DateUtils.getNowDate());
            wmaterial.setUpdateBy(SecurityUtils.getUsername());
            wmaterial.setUpdateTime(DateUtils.getNowDate());
            wmaterialMapper.insertPlnWoMaterial(wmaterial);
        }
    }

    private void createWoRouting(PlnWo wo) {
        List<EngProductRouting> proutingList = proutingMapper.selectEngProductRoutingListByProductId(wo.getProductId());
        for (int i = 0; proutingList!=null && i < proutingList.size(); i++) {
            //取工艺路线项
            EngProductRouting prouting = proutingList.get(i);

            //创建工单的工艺路线项
            PlnWoRouting wrouting = new PlnWoRouting();
            wrouting.setWoId(wo.getId());
            wrouting.setLineId(prouting.getLineId());
            wrouting.setSeqNo(prouting.getSeqNo());
            wrouting.setProcessId(prouting.getProcessId());
            wrouting.setLastSeqNo(prouting.getLastSeqNo());
            wrouting.setNextSeqNo(prouting.getNextSeqNo());
            wrouting.setSecondsFixed(prouting.getSecondsFixed());
            wrouting.setSecondsVariable(prouting.getSecondsVariable());
            wrouting.setSecondsRm(prouting.getSecondsRm());
            wrouting.setSecondsSetup(prouting.getSecondsSetup());
            wrouting.setSecondsStacking(prouting.getSecondsStacking());
            wrouting.setSecondsTransfer(prouting.getSecondsTransfer());
            wrouting.setSecondsBuffer(prouting.getSecondsBuffer());
            wrouting.setLineType(prouting.getLineType());
            wrouting.setProcessType(prouting.getProcessType());
            wrouting.setRemark(null);
            //创建戳
            wrouting.setCreateBy(SecurityUtils.getUsername());
            wrouting.setCreateTime(DateUtils.getNowDate());
            wrouting.setUpdateBy(SecurityUtils.getUsername());
            wrouting.setUpdateTime(DateUtils.getNowDate());
            wroutingMapper.insertPlnWoRouting(wrouting);

            //创建工单工艺路线的多个子表项集
            this.createWoRoutingKiparts(prouting, wrouting);
            this.createWoRoutingTool(prouting, wrouting);
            this.createWoRoutingSop(prouting, wrouting);
            this.createWoRoutingDrawing(prouting, wrouting);
            this.createWoRoutingVideo(prouting, wrouting);
        }
    }

    private void createWoSop(PlnWo wo) {
        List<EngPruductSop> psopList = psopMapper.selectEngPruductSopListByProductId(wo.getProductId());
        for (int i = 0; psopList!=null && i < psopList.size(); i++) {
            //取sopItem
            EngPruductSop psop = psopList.get(i);

            //创建工单SOP项
            PlnWoSop wsop = new PlnWoSop();
            wsop.setWoId(wo.getId());
            wsop.setStepSeq(psop.getStepSeq());
            wsop.setStepTitle(psop.getStepTitle());
            wsop.setDescription(psop.getDescription());
            wsop.setSopUrl(psop.getSopUrl());
            wsop.setRemark(null);
            //创建戳
            wsop.setCreateBy(SecurityUtils.getUsername());
            wsop.setCreateTime(DateUtils.getNowDate());
            wsop.setUpdateBy(SecurityUtils.getUsername());
            wsop.setUpdateTime(DateUtils.getNowDate());
            wsopMapper.insertPlnWoSop(wsop);
        }

    }

    private void createWoDrawing(PlnWo wo) {
        List<EngProductDrawing> pdrawingList = pdrawingMapper.selectEngProductDrawingListByProductId(wo.getProductId());
        for (int i = 0; pdrawingList!=null && i < pdrawingList.size(); i++) {
            //取设计图纸m
            EngProductDrawing pdrawing = pdrawingList.get(i);

            //创建工单的设计图纸项
            PlnWoDrawing wdrawing = new PlnWoDrawing();
            wdrawing.setWoId(wo.getId());
            wdrawing.setSeqNo(10*(i+1));
            wdrawing.setDescription(pdrawing.getDescription());
            wdrawing.setDrawingUrl(pdrawing.getDrawingUrl());
            wdrawing.setRemark(null);
            //创建戳
            wdrawing.setCreateBy(SecurityUtils.getUsername());
            wdrawing.setCreateTime(DateUtils.getNowDate());
            wdrawing.setUpdateBy(SecurityUtils.getUsername());
            wdrawing.setUpdateTime(DateUtils.getNowDate());
            wdrawingMapper.insertPlnWoDrawing(wdrawing);
        }
    }

    private void createWoVideo(PlnWo wo) {
        List<EngPruductVideo> pvideoList = pvideoMapper.selectEngPruductVideoListByProductId(wo.getProductId());
        for (int i = 0; pvideoList!=null && i < pvideoList.size(); i++) {
            //取video
            EngPruductVideo pvideo = pvideoList.get(i);

            //创建工单Video项
            PlnWoVideo wvideo = new PlnWoVideo();
            wvideo.setWoId(wo.getId());
            wvideo.setStepSeq(pvideo.getStepSeq());
            wvideo.setStepTitle(pvideo.getStepTitle());
            wvideo.setDescription(pvideo.getDescription());
            wvideo.setVideoUrl(pvideo.getVideoUrl());
            wvideo.setRemark(null);
            //创建戳
            wvideo.setCreateBy(SecurityUtils.getUsername());
            wvideo.setCreateTime(DateUtils.getNowDate());
            wvideo.setUpdateBy(SecurityUtils.getUsername());
            wvideo.setUpdateTime(DateUtils.getNowDate());
            wvideoMapper.insertPlnWoVideo(wvideo);
        }
    }

    private void createWoRoutingKiparts(EngProductRouting prouting, PlnWoRouting wrouting) {
        List<EngProductRoutingkiparts> prpartList = prpartsMapper.selectEngProductRoutingkipartsListByRoutingId(prouting.getId());
        for (int i = 0; prpartList!=null && i < prpartList.size(); i++) {
            //取关重物料项
            EngProductRoutingkiparts prpart = prpartList.get(i);

            //创建工单的工艺路线项
            PlnWoRoutingkiparts wrpart = new PlnWoRoutingkiparts();
            wrpart.setRoutingId(wrouting.getId());
            wrpart.setMaterialId(prpart.getMaterialId());
            wrpart.setMaterialName(prpart.getMaterialName());
            wrpart.setMaterialSpec(prpart.getMaterialSpec());
            wrpart.setRemark(null);
            //创建戳
            wrpart.setCreateBy(SecurityUtils.getUsername());
            wrpart.setCreateTime(DateUtils.getNowDate());
            wrpart.setUpdateBy(SecurityUtils.getUsername());
            wrpart.setUpdateTime(DateUtils.getNowDate());
            wrpartsMapper.insertPlnWoRoutingkiparts(wrpart);
        }
    }

    private void createWoRoutingTool(EngProductRouting prouting, PlnWoRouting wrouting) {
        List<EngProductRoutingtool> prtoolList = prtoolMapper.selectEngProductRoutingtoolListByRoutingId(prouting.getId());
        for (int i = 0; prtoolList!=null && i < prtoolList.size(); i++) {
            //取工具项
            EngProductRoutingtool prtool = prtoolList.get(i);

            //创建工单的工具项
            PlnWoRoutingtool wrtool = new PlnWoRoutingtool();
            wrtool.setRoutingId(wrouting.getId());
            wrtool.setSeqNo(prtool.getItemSeq());
            wrtool.setToolId(prtool.getToolId());
            wrtool.setRemark(null);
            //创建戳
            wrtool.setCreateBy(SecurityUtils.getUsername());
            wrtool.setCreateTime(DateUtils.getNowDate());
            wrtool.setUpdateBy(SecurityUtils.getUsername());
            wrtool.setUpdateTime(DateUtils.getNowDate());
            wrtoolMapper.insertPlnWoRoutingtool(wrtool);
        }
    }

    private void createWoRoutingSop(EngProductRouting prouting, PlnWoRouting wrouting) {
        List<EngPruductRoutingsop> prsopList = prsopMapper.selectEngPruductRoutingsopListByRoutingId(prouting.getId());
        for (int i = 0; prsopList!=null && i < prsopList.size(); i++) {
            //取作业指导书项
            EngPruductRoutingsop prsop = prsopList.get(i);

            //创建工单的作业指导书项
            PlnWoRoutingsop wrsop = new PlnWoRoutingsop();
            wrsop.setRoutingId(wrouting.getId());
            wrsop.setStepSeq(prsop.getStepSeq());
            wrsop.setStepTitle(prsop.getStepTitle());
            wrsop.setDescription(prsop.getDescription());
            wrsop.setSopUrl(prsop.getSopUrl());
            wrsop.setRemark(null);
            //创建戳
            wrsop.setCreateBy(SecurityUtils.getUsername());
            wrsop.setCreateTime(DateUtils.getNowDate());
            wrsop.setUpdateBy(SecurityUtils.getUsername());
            wrsop.setUpdateTime(DateUtils.getNowDate());
            wrsopMapper.insertPlnWoRoutingsop(wrsop);
        }
    }

    private void createWoRoutingDrawing(EngProductRouting prouting, PlnWoRouting wrouting) {
        List<EngProductRoutingdrawing> prdrawingList = prdrawingMapper.selectEngProductRoutingdrawingListByRoutingId(prouting.getId());
        for (int i = 0; prdrawingList!=null && i < prdrawingList.size(); i++) {
            //取设计图纸项
            EngProductRoutingdrawing prdrawing = prdrawingList.get(i);

            //创建工单的设计图纸项
            PlnWoRoutingdrawing wrdrawing = new PlnWoRoutingdrawing();
            wrdrawing.setRoutingId(wrouting.getId());
            wrdrawing.setDescription(prdrawing.getDescription());
            wrdrawing.setDrawingUrl(prdrawing.getDrawingUrl());
            wrdrawing.setRemark(null);
            //创建戳
            wrdrawing.setCreateBy(SecurityUtils.getUsername());
            wrdrawing.setCreateTime(DateUtils.getNowDate());
            wrdrawing.setUpdateBy(SecurityUtils.getUsername());
            wrdrawing.setUpdateTime(DateUtils.getNowDate());
            wrdrawingMapper.insertPlnWoRoutingdrawing(wrdrawing);
        }
    }

    private void createWoRoutingVideo(EngProductRouting prouting, PlnWoRouting wrouting) {
        List<EngPruductRoutingvideo> prvideoList = prvideoMapper.selectEngPruductRoutingvideoListByRoutingId(prouting.getId());
        for (int i = 0; prvideoList!=null && i < prvideoList.size(); i++) {
            //取作业视频项
            EngPruductRoutingvideo prvideo = prvideoList.get(i);

            //创建工单的作业视频项
            PlnWoRoutingvideo wrvideo = new PlnWoRoutingvideo();
            wrvideo.setRoutingId(wrouting.getId());
            wrvideo.setStepSeq(prvideo.getStepSeq());
            wrvideo.setStepTitle(prvideo.getStepTitle());
            wrvideo.setDescription(prvideo.getDescription());
            wrvideo.setVideoUrl(prvideo.getVideoUrl());
            wrvideo.setRemark(null);
            //创建戳
            wrvideo.setCreateBy(SecurityUtils.getUsername());
            wrvideo.setCreateTime(DateUtils.getNowDate());
            wrvideo.setUpdateBy(SecurityUtils.getUsername());
            wrvideo.setUpdateTime(DateUtils.getNowDate());
            wrvideoMapper.insertPlnWoRoutingvideo(wrvideo);
        }
    }
}
