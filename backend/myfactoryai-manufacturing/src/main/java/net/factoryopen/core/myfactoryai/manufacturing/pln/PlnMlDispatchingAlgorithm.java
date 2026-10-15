package net.factoryopen.core.myfactoryai.manufacturing.pln;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import net.factoryopen.core.util.InputUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.*;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.factoryopen.core.common.exception.QuantityUnavailableException;

/**
 * 上线派工ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-28
 */
@Service
public class PlnMlDispatchingAlgorithm
{
    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

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

    @Autowired(required = false)
    private PlnMlMaterialMapper mmaterialMapper;

    @Autowired(required = false)
    private PlnMlDrawingMapper mdrawingMapper;

    @Autowired(required = false)
    private PlnMlSopMapper msopMapper;

    @Autowired(required = false)
    private PlnMlVideoMapper mvideoMapper;

    @Autowired(required = false)
    private PlnMlRoutingMapper mroutingMapper;

    @Autowired(required = false)
    private PlnMlRoutingkipartsMapper mrpartsMapper;

    @Autowired(required = false)
    private PlnMlRoutingtoolMapper mrtoolMapper;

    @Autowired(required = false)
    private PlnMlRoutingsopMapper mrsopMapper;

    @Autowired(required = false)
    private PlnMlRoutingdrawingMapper mrdrawingMapper;

    @Autowired(required = false)
    private PlnMlRoutingvideoMapper mrvideoMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockReqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper stockReqitemMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;



    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 批派
       *
       * @param plnMlDispatching 批派
       * @return 结果
       */
    public int dispatchMultipleMl(Long[] ids, PlnMlDispatching plnMlDispatching) throws Exception{
        // 取UI的form变量
        String delieryDay = (String) plnMlDispatching.getParams().get("delieryDay");
        Integer priority = plnMlDispatching.getPriority();

        // 判异，排除排除数量不足的派工
        for (int i = 0; i < ids.length; i++) {
            PlnWo wo = woMapper.selectPlnWoById(ids[i]);

            if (wo.getQuantityPlanned().doubleValue()-wo.getQuantityAssigned().doubleValue()<=0) {
                String woName = wo.getBillNo();
                String msg = "工单[" + woName + "]的可派工数量不大于0";
                throw new QuantityUnavailableException(msg);
            }
        }

        // 逐一取原工单，建制造批次
        for (int i = 0; i < ids.length; i++) {
            PlnWo wo = woMapper.selectPlnWoById(ids[i]);

            // 取得并更新单据规则
            String billNo = billcodeManager.getNextBillno("ML");
            billcodeManager.updateBillSeq("ML");

            //取工艺路线首移转记录
            PlnWoRouting woRoutingFirst = wroutingMapper.selectPlnWoRoutingFirstByWoId(wo.getId());
            double quantityAssigning = wo.getQuantityPlanned().doubleValue()-wo.getQuantityAssigned().doubleValue();

            //创建制造批，并设置特殊值
            PlnMl ml = new PlnMl();
            ml.setBillNo(billNo);
            ml.setStatus(BillStatus.BASE_ENABLE);//设为“生效”
            ml.setProcessProgress(0);//设为“待收”);
            ml.setCurrentProcessSeq(woRoutingFirst.getSeqNo());
            ml.setLineId(woRoutingFirst.getLineId());
            ml.setCurrentProcessId(woRoutingFirst.getProcessId());
            // 设置批次派工量
            ml.setQuantityDispatched(new BigDecimal(quantityAssigning));
            // 若form传参不为空则赋值，否则从工单复制字段值
            if(delieryDay!=null) ml.setDeliveryDay(DateUtils.parseDate(delieryDay));
            else ml.setDeliveryDay(wo.getPromiseDay());
            if(priority!=null) ml.setPriority(priority);
            else ml.setPriority(wo.getPriority());
            //设置制造批次类型
            switch (wo.getWoType()) {
                case 0: ml.setMlType(0); break; //wo普通-> ml普通
                case 1:                  break; //wo委外-> 无
                case 2: ml.setMlType(1); break; //wo首制-> ml首制
                case 3: ml.setMlType(2); break; //wo试制-> ml试制
                case 4: ml.setMlType(3); break; //wo重制-> ml重制
                case 5: ml.setMlType(0); break; //wo拆解-> ml普通
            }
            //从工单里复制批次字段值
            ml.setProductId(wo.getProductId());
            ml.setProductCode(wo.getProductCode());
            ml.setProductName(wo.getProductName());
            ml.setProductSpec(wo.getProductSpec());
            ml.setDrawNo(wo.getDrawNo());
            ml.setUnitId(wo.getUnitId());
            ml.setProjectId(wo.getProjectId());
            ml.setWoId(wo.getId());
            //派工留痕
            ml.setDispatchingBy(SecurityUtils.getUserId());
            ml.setDispatchingTime(DateUtils.getNowDate());
            //保存批次
            ml.refreshCreatingStamp();
            mlMapper.insertPlnMl(ml);

            // 更改原工单的状态和派工量
            if (wo.getStatus()==BillStatus.BASE_ENABLE) wo.setStatus(BillStatus.ACTIVE_PROCESSING);//设为“在制”
            wo.setQuantityAssigned(wo.getQuantityAssigned().add(new BigDecimal(quantityAssigning)));//派工量增加新派量
            wo.refreshUpdatingStamp();
            woMapper.updatePlnWo(wo);

            //创建批次的工艺子表项集
            this.createMlMaterial(ml, wo);
            this.createMlSop(ml);
            this.createMlDrawing(ml);
            this.createMlVideo(ml);
            this.createMlRouting(ml);

            //请求备发料
            this.requestPrepareMaterial(ml);

            //探测刷新主计划的派工量和状态，派工量仅当为最终产品时有效
            DelMps mps = mpsMapper.selectDelMpsById(wo.getMpsId());
            if (mps.getProductId()==ml.getProductId()) {
                mps.setQuantityDispatched(mps.getQuantityDispatched().add(new BigDecimal(quantityAssigning)));
            }
            if (mps.getStatus()==BillStatus.ACTIVE_PLANNING){
                mps.setStatus(BillStatus.ACTIVE_PROCESSING);
            }
            mps.refreshUpdatingStamp();
            mpsMapper.updateDelMps(mps);
        }

        return 1;
    }

    /**
     * 单派
     *
     * @param plnMlDispatching 单派
     * @return 结果
     */
    public int dispatchSingleMl(PlnMlDispatching plnMlDispatching) throws Exception{
        // 取UI的form变量
        double quantityAssigning = InputUtil.extractDouble(plnMlDispatching.getParams().get("quantityAssigning"));
        String delieryDay = (String) plnMlDispatching.getParams().get("deliveryDay");
        Integer priority = plnMlDispatching.getPriority();

        // 判异，排除数量不足的派工
        PlnWo wo = woMapper.selectPlnWoById(plnMlDispatching.getId());
        if (wo.getQuantityPlanned().doubleValue()-wo.getQuantityAssigned().doubleValue()<=0) {
            String woName = wo.getBillNo();
            String msg = "工单[" + woName + "]的可派工数量不大于0";
            throw new QuantityUnavailableException(msg);
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("ML");
        billcodeManager.updateBillSeq("ML");

        //取工艺路线首移转记录
        PlnWoRouting woRoutingFirst = wroutingMapper.selectPlnWoRoutingFirstByWoId(wo.getId());
        if (woRoutingFirst==null) {
            String woName = wo.getBillNo();
            String msg = "工单[" + woName + "]的工艺路线无数据";
            throw new DataNotExistException(msg);
        }

        //创建制造批，并设置特殊值
        PlnMl ml = new PlnMl();
        ml.setBillNo(billNo);
        ml.setStatus(BillStatus.BASE_ENABLE);//设为“生效”
        ml.setProcessProgress(0);//设为“待收”);
        ml.setCurrentProcessSeq(woRoutingFirst.getSeqNo());
        ml.setLineId(woRoutingFirst.getLineId());
        ml.setCurrentProcessId(woRoutingFirst.getProcessId());
        // 设置批次派工量
        ml.setQuantityDispatched(new BigDecimal(quantityAssigning));
        // 若form传参不为空则赋值，否则从工单复制字段值
        if(delieryDay!=null) ml.setDeliveryDay(DateUtils.parseDate(delieryDay));
        else ml.setDeliveryDay(wo.getPromiseDay());
        if(priority!=null) ml.setPriority(priority);
        else ml.setPriority(wo.getPriority());
        //设置制造批次类型
        switch (wo.getWoType()) {
            case 0: ml.setMlType(0); break; //wo普通-> ml普通
            case 1:                  break; //wo委外-> 无
            case 2: ml.setMlType(1); break; //wo首制-> ml首制
            case 3: ml.setMlType(2); break; //wo试制-> ml试制
            case 4: ml.setMlType(3); break; //wo重制-> ml重制
            case 5: ml.setMlType(0); break; //wo拆解-> ml普通
        }
        //从工单里复制批次字段值
        ml.setProductId(wo.getProductId());
        ml.setProductCode(wo.getProductCode());
        ml.setProductName(wo.getProductName());
        ml.setProductSpec(wo.getProductSpec());
        ml.setDrawNo(wo.getDrawNo());
        ml.setUnitId(wo.getUnitId());
        ml.setProjectId(wo.getProjectId());
        ml.setWoId(wo.getId());
        //派工留痕
        ml.setDispatchingBy(SecurityUtils.getUserId());
        ml.setDispatchingTime(DateUtils.getNowDate());
        //保存批次
        ml.refreshCreatingStamp();
        mlMapper.insertPlnMl(ml);

        // 更改原工单的状态和派工量
        if (wo.getStatus()==BillStatus.BASE_ENABLE) wo.setStatus(BillStatus.ACTIVE_PROCESSING);//设为“在制”
        wo.setQuantityAssigned(wo.getQuantityAssigned().add(new BigDecimal(quantityAssigning)));//派工量增加新派量
        wo.refreshUpdatingStamp();
        woMapper.updatePlnWo(wo);

        //创建批次的工艺子表项集
        this.createMlMaterial(ml, wo);
        this.createMlSop(ml);
        this.createMlDrawing(ml);
        this.createMlVideo(ml);
        this.createMlRouting(ml);

        //请求备发料
        this.requestPrepareMaterial(ml);

        //探测刷新主计划的派工量和状态，派工量仅当为最终产品时有效
        DelMps mps = mpsMapper.selectDelMpsById(wo.getMpsId());
        if (mps.getProductId().equals(ml.getProductId())) {
            mps.setQuantityDispatched(mps.getQuantityDispatched().add(new BigDecimal(quantityAssigning)));
        }
        if (mps.getStatus()==BillStatus.ACTIVE_PLANNING){
            mps.setStatus(BillStatus.ACTIVE_PROCESSING);
        }
        mps.refreshUpdatingStamp();
        mpsMapper.updateDelMps(mps);

        return 1;
    }

    private void createMlMaterial(PlnMl ml, PlnWo wo) {
        List<PlnWoMaterial> mmaterialList = wmaterialMapper.selectPlnWoMaterialListByWoId(wo.getId());
        for (int i = 0; mmaterialList!=null && i < mmaterialList.size(); i++) {
            //取物料项
            PlnWoMaterial wmaterial = mmaterialList.get(i);
            //计算物料量
            double quantity=wmaterial.getQuantity().doubleValue()*(ml.getQuantityDispatched().doubleValue()/wo.getQuantityPlanned().doubleValue());

            //创建批次备料项
            PlnMlMaterial mmaterial = new PlnMlMaterial();
            mmaterial.setMlId(ml.getId());
            mmaterial.setSeqNo(wmaterial.getSeqNo());
            mmaterial.setMaterialCode(wmaterial.getMaterialCode());
            mmaterial.setMaterialId(wmaterial.getMaterialId());
            mmaterial.setMaterialName(wmaterial.getMaterialName());
            mmaterial.setMaterialSpec(wmaterial.getMaterialSpec());
            mmaterial.setUnitId(wmaterial.getUnitId());
            mmaterial.setQuantity(new BigDecimal(quantity).setScale(4, BigDecimal.ROUND_HALF_UP));
            mmaterial.setConsumeType(wmaterial.getConsumeType());
            mmaterial.setEnableMust(wmaterial.getEnableMust());
            mmaterial.setEnableReplace(wmaterial.getEnableReplace());
            mmaterial.setRemark(null);
            mmaterial.setCreateBy(SecurityUtils.getUsername());
            mmaterial.setCreateTime(DateUtils.getNowDate());
            mmaterial.setUpdateBy(SecurityUtils.getUsername());
            mmaterial.setUpdateTime(DateUtils.getNowDate());
            mmaterialMapper.insertPlnMlMaterial(mmaterial);
        }
    }

    private void createMlSop(PlnMl ml) {
        List<PlnWoSop> wsopList = wsopMapper.selectPlnWoSopListByWoId(ml.getWoId());
        for (int i = 0; wsopList!=null && i < wsopList.size(); i++) {
            //取sopItem
            PlnWoSop wsop = wsopList.get(i);

            //创建批次SOP项
            PlnMlSop psop= new PlnMlSop();
            psop.setMlId(ml.getId());
            psop.setStepSeq(wsop.getStepSeq());
            psop.setStepTitle(wsop.getStepTitle());
            psop.setDescription(wsop.getDescription());
            psop.setSopUrl(wsop.getSopUrl());
            psop.setRemark(null);
            psop.setCreateBy(SecurityUtils.getUsername());
            psop.setCreateTime(DateUtils.getNowDate());
            psop.setUpdateBy(SecurityUtils.getUsername());
            psop.setUpdateTime(DateUtils.getNowDate());
            msopMapper.insertPlnMlSop(psop);
        }

    }

    private void createMlDrawing(PlnMl ml) {
        List<PlnWoDrawing> wdrawingList = wdrawingMapper.selectPlnWoDrawingListByWoId(ml.getWoId());
        for (int i = 0; wdrawingList!=null && i < wdrawingList.size(); i++) {
            //取设计图纸m
            PlnWoDrawing wdrawing = wdrawingList.get(i);

            //创建批次的设计图纸项
            PlnMlDrawing mdrawing = new PlnMlDrawing();
            mdrawing.setMlId(ml.getId());
            mdrawing.setSeqNo(wdrawing.getSeqNo());
            mdrawing.setDescription(wdrawing.getDescription());
            mdrawing.setDrawingUrl(wdrawing.getDrawingUrl());
            mdrawing.setRemark(null);
            mdrawing.setCreateBy(SecurityUtils.getUsername());
            mdrawing.setCreateTime(DateUtils.getNowDate());
            mdrawing.setUpdateBy(SecurityUtils.getUsername());
            mdrawing.setUpdateTime(DateUtils.getNowDate());
            mdrawingMapper.insertPlnMlDrawing(mdrawing);
        }
    }

    private void createMlVideo(PlnMl ml) {
        List<PlnWoVideo> wvideoList = wvideoMapper.selectPlnWoVideoListByWoId(ml.getWoId());
        for (int i = 0; wvideoList!=null && i < wvideoList.size(); i++) {
            //取video
            PlnWoVideo wvideo = wvideoList.get(i);

            //创建批次Video项
            PlnMlVideo mvideo = new PlnMlVideo();
            mvideo.setMlId(ml.getId());
            mvideo.setStepSeq(wvideo.getStepSeq());
            mvideo.setStepTitle(wvideo.getStepTitle());
            mvideo.setDescription(wvideo.getDescription());
            mvideo.setVideoUrl(wvideo.getVideoUrl());
            mvideo.setRemark(null);
            mvideo.setCreateBy(SecurityUtils.getUsername());
            mvideo.setCreateTime(DateUtils.getNowDate());
            mvideo.setUpdateBy(SecurityUtils.getUsername());
            mvideo.setUpdateTime(DateUtils.getNowDate());
            mvideoMapper.insertPlnMlVideo(mvideo);
        }
    }

    private void createMlRouting(PlnMl ml) {
        List<PlnWoRouting> wroutingList = wroutingMapper.selectPlnWoRoutingListByWoId(ml.getWoId());
        for (int i = 0; wroutingList!=null && i < wroutingList.size(); i++) {
            //取工艺路线项
            PlnWoRouting wrouting = wroutingList.get(i);

            //创建批次的工艺路线项，并设置关键信息
            PlnMlRouting mrouting = new PlnMlRouting();
            mrouting.setMlId(ml.getId());
            mrouting.setStatus(0);//状态为“待收”
            mrouting.setProcessSeq(wrouting.getSeqNo());
            mrouting.setLineId(wrouting.getLineId());
            mrouting.setProcessId(wrouting.getProcessId());
            //复制批次的字段
            mrouting.setProductId(ml.getProductId());
            mrouting.setProductName(ml.getProductName());
            mrouting.setProductSpec(ml.getProductSpec());
            mrouting.setUnitId(ml.getUnitId());
            //复制工单的工艺路线字段
            mrouting.setLastSeqNo(wrouting.getLastSeqNo());
            mrouting.setNextSeqNo(wrouting.getNextSeqNo());
            mrouting.setLineType(wrouting.getLineType());
            mrouting.setProcessType(wrouting.getProcessType());
            mrouting.setSecondsFixed(wrouting.getSecondsFixed());
            mrouting.setSecondsVariable(wrouting.getSecondsVariable());
            mrouting.setSecondsRm(wrouting.getSecondsRm());
            mrouting.setSecondsSetup(wrouting.getSecondsSetup());
            mrouting.setSecondsStacking(wrouting.getSecondsStacking());
            mrouting.setSecondsTransfer(wrouting.getSecondsTransfer());
            mrouting.setSecondsBuffer(wrouting.getSecondsBuffer());
            mrouting.setRemark(null);
            //组织留痕
            mrouting.setDeptId(SecurityUtils.getDeptId());
            mrouting.setGroupId(SecurityUtils.getGroupId());
            //保存
            mrouting.refreshCreatingStamp();
            mroutingMapper.insertPlnMlRouting(mrouting);

            //创建批次工艺路线的多个子表项集
            this.createMlRoutingKiparts(wrouting, mrouting);
            this.createMlRoutingTool(wrouting, mrouting);
            this.createMlRoutingSop(wrouting, mrouting);
            this.createMlRoutingDrawing(wrouting, mrouting);
            this.createMlRoutingVideo(wrouting, mrouting);
        }
    }

    private void createMlRoutingKiparts(PlnWoRouting wrouting, PlnMlRouting mrouting) {
        List<PlnWoRoutingkiparts> wrpartList = wrpartsMapper.selectPlnWoRoutingkipartsListByRoutingId(wrouting.getId());
        for (int i = 0; wrpartList!=null && i < wrpartList.size(); i++) {
            //取关重物料项
            PlnWoRoutingkiparts wrpart = wrpartList.get(i);

            //创建批次的工艺路线项
            PlnMlRoutingkiparts mrpart = new PlnMlRoutingkiparts();
            mrpart.setRoutingId(mrouting.getId());
            mrpart.setMaterialId(wrpart.getMaterialId());
            mrpart.setMaterialName(wrpart.getMaterialName());
            mrpart.setMaterialSpec(wrpart.getMaterialSpec());
            mrpart.setRemark(null);
            mrpart.setCreateBy(SecurityUtils.getUsername());
            mrpart.setCreateTime(DateUtils.getNowDate());
            mrpart.setUpdateBy(SecurityUtils.getUsername());
            mrpart.setUpdateTime(DateUtils.getNowDate());
            mrpartsMapper.insertPlnMlRoutingkiparts(mrpart);
        }
    }

    private void createMlRoutingTool(PlnWoRouting wrouting, PlnMlRouting mrouting) {
        List<PlnWoRoutingtool> wrtooltList = wrtoolMapper.selectPlnWoRoutingtoolListByRoutingId(wrouting.getId());
        for (int i = 0; wrtooltList!=null && i < wrtooltList.size(); i++) {
            //取工具项
            PlnWoRoutingtool wrtool = wrtooltList.get(i);

            //创建批次的工具项
            PlnMlRoutingtool mrtool = new PlnMlRoutingtool();
            mrtool.setRoutingId(mrouting.getId());
            mrtool.setSeqNo(wrtool.getSeqNo());
            mrtool.setToolId(wrtool.getToolId());
            mrtool.setRemark(null);
            mrtool.setCreateBy(SecurityUtils.getUsername());
            mrtool.setCreateTime(DateUtils.getNowDate());
            mrtool.setUpdateBy(SecurityUtils.getUsername());
            mrtool.setUpdateTime(DateUtils.getNowDate());
            mrtoolMapper.insertPlnMlRoutingtool(mrtool);
        }
    }

    private void createMlRoutingSop(PlnWoRouting wrouting, PlnMlRouting mrouting) {
        List<PlnWoRoutingsop> wrsopList = wrsopMapper.selectPlnWoRoutingsopListByRoutingId(wrouting.getId());
        for (int i = 0; wrsopList!=null && i < wrsopList.size(); i++) {
            //取作业指导书项
            PlnWoRoutingsop wrsop = wrsopList.get(i);

            //创建批次的作业指导书项
            PlnMlRoutingsop mrsop = new PlnMlRoutingsop();
            mrsop.setRoutingId(mrouting.getId());
            mrsop.setStepSeq(wrsop.getStepSeq());
            mrsop.setStepTitle(wrsop.getStepTitle());
            mrsop.setDescription(wrsop.getDescription());
            mrsop.setSopUrl(wrsop.getSopUrl());
            mrsop.setRemark(null);
            mrsop.setCreateBy(SecurityUtils.getUsername());
            mrsop.setCreateTime(DateUtils.getNowDate());
            mrsop.setUpdateBy(SecurityUtils.getUsername());
            mrsop.setUpdateTime(DateUtils.getNowDate());
            mrsopMapper.insertPlnMlRoutingsop(mrsop);
        }
    }

    private void createMlRoutingDrawing(PlnWoRouting wrouting, PlnMlRouting mrouting) {
        List<PlnWoRoutingdrawing> wrdrawingList = wrdrawingMapper.selectPlnWoRoutingdrawingListByRoutingId(wrouting.getId());
        for (int i = 0; wrdrawingList!=null && i < wrdrawingList.size(); i++) {
            //取设计图纸项
            PlnWoRoutingdrawing wrdrawing = wrdrawingList.get(i);

            //创建批次的设计图纸项
            PlnMlRoutingdrawing mrdrawing = new PlnMlRoutingdrawing();
            mrdrawing.setRoutingId(mrouting.getId());
            mrdrawing.setDescription(wrdrawing.getDescription());
            mrdrawing.setDrawingUrl(wrdrawing.getDrawingUrl());
            mrdrawing.setRemark(null);
            mrdrawing.setCreateBy(SecurityUtils.getUsername());
            mrdrawing.setCreateTime(DateUtils.getNowDate());
            mrdrawing.setUpdateBy(SecurityUtils.getUsername());
            mrdrawing.setUpdateTime(DateUtils.getNowDate());
            mrdrawingMapper.insertPlnMlRoutingdrawing(mrdrawing);
        }
    }

    private void createMlRoutingVideo(PlnWoRouting wrouting, PlnMlRouting mrouting) {
        List<PlnWoRoutingvideo> wrvideoList = wrvideoMapper.selectPlnWoRoutingvideoListByRoutingId(wrouting.getId());
        for (int i = 0; wrvideoList!=null && i < wrvideoList.size(); i++) {
            //取作业视频项
            PlnWoRoutingvideo wrvideo = wrvideoList.get(i);

            //创建批次的作业视频项
            PlnMlRoutingvideo mrvideo = new PlnMlRoutingvideo();
            mrvideo.setRoutingId(mrouting.getId());
            mrvideo.setStepSeq(wrvideo.getStepSeq());
            mrvideo.setStepTitle(wrvideo.getStepTitle());
            mrvideo.setDescription(wrvideo.getDescription());
            mrvideo.setVideoUrl(wrvideo.getVideoUrl());
            mrvideo.setRemark(null);
            mrvideo.setCreateBy(SecurityUtils.getUsername());
            mrvideo.setCreateTime(DateUtils.getNowDate());
            mrvideo.setUpdateBy(SecurityUtils.getUsername());
            mrvideo.setUpdateTime(DateUtils.getNowDate());
            mrvideoMapper.insertPlnMlRoutingvideo(mrvideo);
        }
    }

    private void requestPrepareMaterial(PlnMl ml) {
        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
        billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);
        //建生产备料请求
        SncStockRequest request= new SncStockRequest();
        //填写关键信息
        request.setBillNo(billNo);
        request.setIoType(InventoryTransactionType.STOCKOUT_4_PICK);
        request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
        request.setRequestBillId(ml.getId());
        request.setRequestBillNo(ml.getBillNo());
        //补充来源信息
        request.setSourceCustomerId(null);
        request.setSourceLineId(ml.getLineId());
        request.setSourceSupplierId(null);
        request.setProjectId(ml.getProjectId());
        request.setRequestorId(SecurityUtils.getUserId());
        request.setRequestTime(DateUtils.getNowDate());
        //保存请求
        request.refreshCreatingStamp();
        stockReqMapper.insertSncStockRequest(request);

        //取物料项
        List<PlnMlMaterial> mlMaterialList = mmaterialMapper.selectPlnMlMaterialListByMlId(ml.getId());
        if (mlMaterialList!=null){
            for (PlnMlMaterial item:mlMaterialList){
                EngMaterial material = materialMapper.selectEngMaterialById(item.getMaterialId());

                SncStockRequestItem requestItem = new SncStockRequestItem();
                requestItem.setSeqNo(item.getSeqNo());
                requestItem.setRequestId(request.getId());
                requestItem.setMaterialId(material.getId());
                requestItem.setMaterialCode(material.getMaterialCode());
                requestItem.setMaterialName(item.getMaterialName());
                requestItem.setMaterialSpec(item.getMaterialSpec());
                requestItem.setDrawNo(material.getDrawNo());
                requestItem.setCategoryId(material.getCategoryId());
                requestItem.setCompoundId(material.getCompoundId());
                requestItem.setCustomerProductNo(material.getCustomerProductNo());
                requestItem.setAbroadProductNo(material.getAbroadProductNo());
                requestItem.setUnitId(item.getUnitId());
                requestItem.setQuantity(item.getQuantity());
                requestItem.setPrice(material.getPriceLast());
                requestItem.setAmount(new BigDecimal(item.getQuantity().doubleValue()*material.getPriceLast().doubleValue()));
                requestItem.setHouseId(material.getHouseId());
                requestItem.setPositionId(material.getPositionId());
                requestItem.setSourceId(item.getId());
                //创建戳
                requestItem.setCreateBy(SecurityUtils.getUsername());
                requestItem.setCreateTime(DateUtils.getNowDate());
                requestItem.setUpdateBy(SecurityUtils.getUsername());
                requestItem.setUpdateTime(DateUtils.getNowDate());
                stockReqitemMapper.insertSncStockRequestItem(requestItem);
            }
        }
    }
}
