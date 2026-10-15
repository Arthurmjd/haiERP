package net.factoryopen.core.myfactoryai.manufacturing.snc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.*;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.*;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWareHouse;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWareHouseMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.*;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.*;
import net.factoryopen.myfactoryai.manufacturing.sup.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 请出入库处理ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-09-16
 */
@Service
public class SncStockReqdealAlgorithm
{
    @Autowired(required = false)
    private SncStockRequestMapper requestMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private QuaInspectRequestMapper inspectReqMapper;

    @Autowired(required = false)
    private QuaInspectRequestItemMapper inspectReqitemMapper;

    @Autowired(required = false)
    private QuaFreeinspectMapper freeinspectMapper;

    @Autowired(required = false)
    private QuaFreeinspectCategoryMapper freeinspectCategoryMapper;

    @Autowired(required = false)
    private QuaFreeinspectMaterialMapper freeinspectMaterialMapper;

    @Autowired(required = false)
    private SncInventoryIoMapper ioMapper;

    @Autowired(required = false)
    private SncInventoryMapper inventoryMapper;

    @Autowired(required = false)
    private ResWareHouseMapper houseMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private EngMaterialInventoryMapper materialInventoryMapper;

    @Autowired(required = false)
    private SupPoMapper poMapper;

    @Autowired(required = false)
    private SupPoItemMapper poitemMapper;

    @Autowired(required = false)
    private SupPoItemReturnMapper poItemReturnMapper;

    @Autowired(required = false)
    private SupPurchasingPlanMapper buyplanMapper;

    @Autowired(required = false)
    private SupPurchaseRequestItemMapper pritemMapper;

    @Autowired(required = false)
    private QuaInspectRequestItemMapper qritemMapper;

    @Autowired(required = false)
    private QuaExceptionDealMapper qedealMapper;

    @Autowired(required = false)
    private DelDeliveryOrderMapper doMapper;

    @Autowired(required = false)
    private DelGdItemMapper doitemMapper;

    @Autowired(required = false)
    private DelPlanItemMapper delplanMapper;

    @Autowired(required = false)
    private DelOsplanMapper osplanMapper;

    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

    @Autowired(required = false)
    private ExeProcessMovesMapper moveMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soitemMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdContractItemMapper contractItemMapper;

    @Autowired(required = false)
    private OrdContractMapper contractMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 收货
     *
     * @param request 收货请求头
     * @param reqItems 收货请求明细
     * @return 结果
     */
    public int receiveMaterial(SncStockRequest request, List<SncStockRequestItem> reqItems) throws Exception{
        //防呆检查
        if (request.getStatus()!=BillStatus.BASE_ENABLE){
            String msg = "存货交易请求[" + request.getBillNo() + "]的状态不是[4-生效]";
            throw new StatusUnavailableException(msg);
        }

        //更新请求明细的数量和供应商批次
        for (SncStockRequestItem reqItem : reqItems){
            if (reqItem.getQuantity().doubleValue()>0) {
                SncStockRequestItem reqItemDatabase = requestItemMapper.selectSncStockRequestItemById(reqItem.getId());
                reqItemDatabase.setQuantity(reqItem.getQuantity());
                reqItemDatabase.setSourceLotNo(reqItem.getSourceLotNo());
                reqItemDatabase.refreshUpdatingStamp();
                requestItemMapper.updateSncStockRequestItem(reqItemDatabase);

                //更新采购单明细的收货数
                SupPoItem poItem = poitemMapper.selectSupPoItemById(reqItem.getSourceId());
                poItem.setQuantityArrival(new BigDecimal(poItem.getQuantityArrival().doubleValue()+reqItem.getQuantity().doubleValue()));
                poItem.setStatus(BillStatus.ACTIVE_PLANNING);
                poItem.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(poItem);
                //更新采购单状态
                SupPo po = poMapper.selectSupPoById(poItem.getPoId());
                po.setStatus(BillStatus.ACTIVE_PLANNING);
                po.refreshUpdatingStamp();
                poMapper.updateSupPo(po);


                //更新转包计划的状态
                switch (request.getIoType()){
                    case InventoryTransactionType.STOCKIN_4_SUBCONTRACT:
                        DelOsplan osplan = osplanMapper.selectDelOsplanById(poItem.getSourceBillId());
                        osplan.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
                        osplan.refreshUpdatingStamp();
                        osplanMapper.updateDelOsplan(osplan);
                        break;
                    case InventoryTransactionType.STOCKIN_4_BUY:
                        //跳空。自制主计划聚焦生产状态的变化，不看采购状态的变化
                        break;
                }
            }
            //明细的收货量不大于0，则删除之
            else{
                //暂不执行
                //requestItemMapper.deleteSncStockRequestItemById(reqItem.getId());
            }
        }

        //更新请求的来源单号（收货单号）和状态（在处理）
        SncStockRequest reqDatabase = requestMapper.selectSncStockRequestById(request.getId());
        reqDatabase.setStatus(BillStatus.ACTIVE_STARTED);
        reqDatabase.setSourceBillNo(request.getSourceBillNo());
        reqDatabase.refreshUpdatingStamp();
        requestMapper.updateSncStockRequest(reqDatabase);

        return 1;
    }

    /**
     * 请检
     *
     * @param request 存货交易请求
     * @return 结果
     */
    public int requestInspection(SncStockRequest request) throws Exception{
        //非经收货 则报错
        if (request.getStatus()!=BillStatus.ACTIVE_STARTED){
            String msg = "存货交易请求[" + request.getBillNo() + "]的状态不是[5-已收]";
            throw new StatusUnavailableException(msg);
        }

        //采购免检检查
        boolean freeInpectAll = false;
        List<SncStockRequestItem> requestItemList = requestItemMapper.selectSncStockRequestItemListByRequestId(request.getId());
        if (request.getIoType()==InventoryTransactionType.STOCKIN_4_SUBCONTRACT
                || request.getIoType()==InventoryTransactionType.STOCKIN_4_BUY
                || request.getIoType()==InventoryTransactionType.STOCKIN_OUTER_SUPPORT){
            List<QuaFreeinspect> freeinspectList = freeinspectMapper.selectQuaFreeinspectListBySupplierId(request.getSourceSupplierId());
            //若有免检策略，则标记是否全检，非全检则剔除免检项
            if (freeinspectList!=null && freeinspectList.size()>0){
                QuaFreeinspect freeinspectPolicy = freeinspectList.get(0);
                if (freeinspectPolicy.getFreeLevel()==InspectionFreeLevel.SUPPLIER){
                    //设请求明细合格数为收货数
                    for(int i=0; requestItemList!=null && i<requestItemList.size();i++){
                        //更新仓库交易请求明细的数量和状态
                        SncStockRequestItem item = requestItemList.get(i);
                        item.setQuantityQcGood(item.getQuantity());
                        item.refreshUpdatingStamp();
                        requestItemMapper.updateSncStockRequestItem(item);
                    }
                    //仓库交易请求状态设为“待入库”
                    request.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
                    request.refreshUpdatingStamp();
                    requestMapper.updateSncStockRequest(request);

                    //标记为子项全部免检
                    freeInpectAll = true;
                }
                else if (freeinspectPolicy.getFreeLevel()==InspectionFreeLevel.CATEGORY || freeinspectPolicy.getFreeLevel()==InspectionFreeLevel.MATERIAL){
                    for(int i=0; requestItemList!=null && i<requestItemList.size();i++){
                        SncStockRequestItem item = requestItemList.get(i);

                        //取库存请求明细的物料
                        EngMaterial material = materialMapper.selectEngMaterialById(item.getMaterialId());

                        //判断明细项是否品类免检
                        boolean freeCategory = false;
                        QuaFreeinspectCategory freeinspectCategory = new QuaFreeinspectCategory();
                        freeinspectCategory.setPolicyId(freeinspectPolicy.getId());
                        if (material.getCategoryId()!=null){
                            freeinspectCategory.setCategoryId(material.getCategoryId());
                            List<QuaFreeinspectCategory> freeCategoryList = freeinspectCategoryMapper.selectQuaFreeinspectCategoryList(freeinspectCategory);
                            if (freeCategoryList!=null && freeCategoryList.size()>0){freeCategory=true; }
                        }
                        //判断明细项是否物料免检
                        boolean freeMaterial = false;
                        QuaFreeinspectMaterial freeinspectMaterial = new QuaFreeinspectMaterial();
                        freeinspectMaterial.setPolicyId(freeinspectPolicy.getId());
                        freeinspectMaterial.setMaterialId(item.getMaterialId());
                        List<QuaFreeinspectMaterial> freeMaterialList = freeinspectMaterialMapper.selectQuaFreeinspectMaterialList(freeinspectMaterial);
                        if (freeMaterialList!=null && freeMaterialList.size()>0){freeMaterial=true; }

                        //若为品类或物料免检，则设置合格数，统计免检项数
                        if (freeCategory || freeMaterial){
                            item.setQuantityQcGood(item.getQuantity());
                            item.refreshUpdatingStamp();
                            requestItemMapper.updateSncStockRequestItem(item);

                            requestItemList.remove(i);
                            i--;
                        }
                    }

                    //若全部免检，则设置全免检标记、库存请求状态
                    if (requestItemList.size()==0){
                        request.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
                        request.refreshUpdatingStamp();
                        requestMapper.updateSncStockRequest(request);

                        freeInpectAll = true;
                    }
                }
            }
        }

        //非全免检，则请求收检非免检项
        if (!freeInpectAll){
            // 取得并更新单据规则
            String billNo = billcodeManager.getNextBillno("QCR");
            billcodeManager.updateBillSeq("QCR");

            //创建外协收货单，并设置特殊值
            QuaInspectRequest inspectRequest = new QuaInspectRequest();
            inspectRequest.setBillNo(billNo);
            inspectRequest.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
            inspectRequest.setRequestBillId(request.getId());
            inspectRequest.setRequestBillNo(request.getBillNo());
            //设置质检类型
            switch (request.getIoType()){
                case InventoryTransactionType.STOCKIN_4_SUBCONTRACT:
                    inspectRequest.setInspectType(InspectionType.RECEIVE_SUBCONTRACT);
                    break;
                case InventoryTransactionType.STOCKIN_4_BUY:
                    inspectRequest.setInspectType(InspectionType.RECEIVE_BUYING);
                    break;
                case InventoryTransactionType.STOCKIN_OUTER_SUPPORT:
                    inspectRequest.setInspectType(InspectionType.RECEIVE_OUTERSUPPORT);
                    break;
                case InventoryTransactionType.STOCKIN_4_SALE_BACK:
                    inspectRequest.setInspectType(InspectionType.RECEIVE_DELIVERY_BACK);
                    break;
                default:
            }
            //从外协计划里复制字段值
            inspectRequest.setProjectId(request.getProjectId());
            inspectRequest.setSourceSupplierId(request.getSourceSupplierId());
            inspectRequest.setRequestorId(SecurityUtils.getUserId());
            //保存外协收货单
            inspectRequest.refreshCreatingStamp();
            inspectReqMapper.insertQuaInspectRequest(inspectRequest);

            //处理明细项免检后的仓库交易请求项
            for(int i=0; requestItemList!=null && i<requestItemList.size();i++){
                SncStockRequestItem requestItem = requestItemList.get(i);

                QuaInspectRequestItem inspectRequestItem = new QuaInspectRequestItem();
                inspectRequestItem.setRequestId(inspectRequest.getId());
                inspectRequestItem.setSeqNo(i+1);
                inspectRequestItem.setStatus(BillStatus.BASE_ENABLE);
                inspectRequestItem.setMaterialId(requestItemList.get(i).getMaterialId());
                inspectRequestItem.setMaterialCode(requestItemList.get(i).getMaterialCode());
                inspectRequestItem.setMaterialName(requestItemList.get(i).getMaterialName());
                inspectRequestItem.setMaterialSpec(requestItemList.get(i).getMaterialSpec());
                inspectRequestItem.setUnitId(requestItemList.get(i).getUnitId());
                inspectRequestItem.setQuantity(requestItemList.get(i).getQuantity());
                inspectRequestItem.setSourceId(requestItemList.get(i).getId());
                inspectRequestItem.refreshCreatingStamp();
                inspectReqitemMapper.insertQuaInspectRequestItem(inspectRequestItem);

                //更新采购单明细的状态为请检中（意为收货结束、检验开始）
                SupPoItem poItem = poitemMapper.selectSupPoItemById(requestItem.getSourceId());
                poItem.setStatus(BillStatus.ACTIVE_PROCESSING);
                poItem.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(poItem);
                //更新采购单状态为“请检中”
                SupPo po = poMapper.selectSupPoById(poItem.getPoId());
                po.setStatus(BillStatus.ACTIVE_PROCESSING);
                po.refreshUpdatingStamp();
                poMapper.updateSupPo(po);

                //依仓库交易类型来变更来源单据的状态
                switch (request.getIoType()){
                    case InventoryTransactionType.STOCKIN_4_SUBCONTRACT:
                        //状态不变，维持收货后的入库中状态
                        //DelOsplan osplan = osplanMapper.selectDelOsplanById(poItem.getSourceBillId());
                        //osplanMapper.updateDelOsplan(osplan);
                        break;
                    case InventoryTransactionType.STOCKIN_4_BUY:
                        //跳空。自制主计划聚焦生产状态的变化，不看采购状态的变化
                        break;
                }
            }

            //更新请求的来源单号
            SncStockRequest reqDatabase = requestMapper.selectSncStockRequestById(request.getId());
            reqDatabase.setStatus(BillStatus.ACTIVE_PROCESS_REQ);
            reqDatabase.setSourceBillNo(request.getSourceBillNo());
            reqDatabase.refreshUpdatingStamp();
            requestMapper.updateSncStockRequest(reqDatabase);

        }

        return 1;
    }

    /**
     * 入库
     *
     * @param id 入库请求头
     * @param reqItems 入库请求明细
     * @return 结果
     */
    public int stockIn(Long id, List<SncStockRequestItem> reqItems) throws Exception{
        //卡控状态，有检验有异常可或待入库可
        SncStockRequest request = requestMapper.selectSncStockRequestById(id);
        if (request.getStatus()!=BillStatus.ACTIVE_EXCEPTIONAL && request.getStatus()!=BillStatus.ACTIVE_STOCK_ACTION){
            String msg = "存货交易请求[" + request.getBillNo() + "]的状态不是[10-有异常 或 11-待入库]";
            throw new StatusUnavailableException(msg);
        }
        //卡控数量、仓库、临时料号
        for (SncStockRequestItem reqItem : reqItems) {
            if(reqItem.getQuantity().doubleValue()<=0) {
                throw new Exception("入库数不能小于等于0");
            }
            if(reqItem.getHouseId()==null){
                throw new Exception("仓库不能为空");
            }
            //校验临时料号
            EngMaterial material = materialMapper.selectEngMaterialById(reqItem.getMaterialId());
            if (material.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
                String msg = "非法操作！出入库物料[" + reqItem.getMaterialCode() + "]不应是临时料号";
                throw new MaterialUnavailableException(msg);
            }
        }

        for (SncStockRequestItem reqItem : reqItems){
            String billNo = null;
            int expiredDays =0;
            Integer houseType=null;

            //查询仓库类型
            ResWareHouse house =houseMapper.selectResWareHouseById(reqItem.getHouseId());
            houseType = house.getHouseType();
            //查询保质期
            EngMaterialInventory materialInventory = materialInventoryMapper.selectEngMaterialInventoryListByMaterialId(reqItem.getMaterialId());
            expiredDays = materialInventory!=null && materialInventory.getExpiredDays()!=null?materialInventory.getExpiredDays().intValue():0;

            //创建存货、库存批
            SncInventory inventory = new SncInventory();
            //取更单号
            billNo = billcodeManager.getNextBillno(BillType.INVENTORY_LOT);
            billcodeManager.updateBillSeq(BillType.INVENTORY_LOT);
            inventory.setBillNo(billNo);
            //设置必要信息
            inventory.setHouseId(reqItem.getHouseId());
            inventory.setPositionId(reqItem.getPositionId());
            inventory.setMaterialId(reqItem.getMaterialId());
            inventory.setMaterialCode(reqItem.getMaterialCode());
            inventory.setMaterialName(reqItem.getMaterialName());
            inventory.setMaterialSpec(reqItem.getMaterialSpec());
            inventory.setDrawNo(reqItem.getDrawNo());
            inventory.setCustomerProductNo(reqItem.getCustomerProductNo());
            inventory.setAbroadProductNo(reqItem.getAbroadProductNo());
            inventory.setUnitId(reqItem.getUnitId());
            inventory.setQuantity(reqItem.getQuantity());
            inventory.setPrice(reqItem.getPrice());
            inventory.setAmount(reqItem.getAmount());
            //设置库型、保质到期时间
            inventory.setHouseType(houseType);
            inventory.setTimeExpired(expiredDays!=0?DateUtils.addDays(DateUtils.getNowDate(),expiredDays):null);
            //保存
            inventory.refreshCreatingStamp();
            inventoryMapper.insertSncInventory(inventory);

            //创建交易对象
            SncInventoryIo io = new SncInventoryIo();
            //取更单号
            billNo = billcodeManager.getNextBillno(BillType.INVENTORY_TRANSACTION);
            billcodeManager.updateBillSeq(BillType.INVENTORY_TRANSACTION);
            //设置交易基本信息
            io.setBillNo(billNo);
            io.setIoType(request.getIoType());
            io.setMaterialId(reqItem.getMaterialId());
            io.setMaterialCode(reqItem.getMaterialCode());
            io.setMaterialName(reqItem.getMaterialName());
            io.setMaterialSpec(reqItem.getMaterialSpec());
            io.setDrawNo(reqItem.getDrawNo());
            io.setCustomerProductNo(reqItem.getCustomerProductNo());
            io.setAbroadProductNo(reqItem.getAbroadProductNo());
            io.setIlId(inventory.getId());
            io.setIlNo(inventory.getBillNo());
            io.setUnitId(reqItem.getUnitId());
            io.setQuantity(reqItem.getQuantity());
            io.setPrice(reqItem.getPrice());
            io.setAmount(reqItem.getAmount());
            io.setHouseId(reqItem.getHouseId());
            io.setPositionId(reqItem.getPositionId());
            //抄写字段
            io.setRequestId(request.getId());
            io.setRequestItemId(reqItem.getId());
            io.setRemark(reqItem.getRemark());
            //保存
            io.refreshCreatingStamp();
            ioMapper.insertSncInventoryIo(io);
        }

        //请求结案
        request.setStatus(BillStatus.FINISH);
        request.refreshUpdatingStamp();
        requestMapper.updateSncStockRequest(request);

        //同步更新：物料总量、计划量、指令量、订单明细等
        switch (request.getIoType()){
            case InventoryTransactionType.STOCKIN_4_SUBCONTRACT:
                this.updateData4SubcontractStockin(request, reqItems);
                break;
            case InventoryTransactionType.STOCKIN_4_BUY:
                this.updateData4BuyStockin(request, reqItems);
                break;
            case InventoryTransactionType.STOCKIN_OUTER_SUPPORT:
                break;
            case InventoryTransactionType.STOCKIN_4_PICKBACK:
                this.updateData4GeneralStockin(request, reqItems);
                break;
            case InventoryTransactionType.STOCKIN_PRODUCTING:
                this.updateData4GeneralStockin(request, reqItems);
                break;
            case InventoryTransactionType.STOCKIN_4_SALE_BACK:
                this.updateData4GeneralStockin(request, reqItems);
                break;
            case InventoryTransactionType.STOCKIN_4_OTHERS:
                this.updateData4GeneralStockin(request, reqItems);
                break;
            default:
        }

        return 1;
    }

    /**
     * 出库
     *
     * @param id 出库请求头
     * @param reqItems 出库请求明细
     * @return 结果
     */
    public int stockout(Long id, List<SncStockRequestItem> reqItems) throws Exception{
        SncStockRequest request = requestMapper.selectSncStockRequestById(id);
        //卡控状态
        if (request.getStatus()!=BillStatus.BASE_ENABLE){
            String msg = "存货交易请求[" + request.getBillNo() + "]的状态不是[4-生效]";
            throw new StatusUnavailableException(msg);
        }
        //卡控数量、仓库、临时料号
        for (SncStockRequestItem reqItem : reqItems) {
            if(reqItem.getQuantity().doubleValue()<=0) {
                throw new Exception("出库数不能小于等于0");
            }
            if(reqItem.getHouseId()==null){
                throw new Exception("仓库不能为空");
            }
            //校验临时料号
            EngMaterial material = materialMapper.selectEngMaterialById(reqItem.getMaterialId());
            if (material.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
                String msg = "非法操作！出入库物料[" + reqItem.getMaterialCode() + "]不应是临时料号";
                throw new MaterialUnavailableException(msg);
            }
        }

        for (SncStockRequestItem reqItem : reqItems){
            String billNo = null;
            double quantity=0;
            double price=0;
            double amount=0;

            //库存批减量
            SncInventory inventory = inventoryMapper.selectSncInventoryById(reqItem.getIlId());
            if (inventory!=null){
                quantity = inventory.getQuantity().doubleValue() - reqItem.getQuantity().doubleValue();
                amount = inventory.getAmount().doubleValue()-reqItem.getAmount().doubleValue();
                //重设数量和金额
                inventory.setQuantity(new BigDecimal(quantity));
                inventory.setAmount(new BigDecimal(amount));
                //保存
                inventory.refreshUpdatingStamp();
                inventoryMapper.updateSncInventory(inventory);
            }

            //创建交易对象
            SncInventoryIo io = new SncInventoryIo();
            //取更单号
            billNo = billcodeManager.getNextBillno(BillType.INVENTORY_TRANSACTION);
            billcodeManager.updateBillSeq(BillType.INVENTORY_TRANSACTION);
            //设置交易基本信息
            io.setBillNo(billNo);
            io.setIoType(request.getIoType());
            io.setMaterialId(reqItem.getMaterialId());
            io.setMaterialCode(reqItem.getMaterialCode());
            io.setMaterialName(reqItem.getMaterialName());
            io.setMaterialSpec(reqItem.getMaterialSpec());
            io.setDrawNo(reqItem.getDrawNo());
            io.setCustomerProductNo(reqItem.getCustomerProductNo());
            io.setAbroadProductNo(reqItem.getAbroadProductNo());
            io.setIlId(inventory!=null?inventory.getId():null);
            io.setIlNo(inventory!=null?inventory.getBillNo():null);
            io.setUnitId(reqItem.getUnitId());
            io.setQuantity(new BigDecimal(0-reqItem.getQuantity().doubleValue()));
            io.setPrice(reqItem.getPrice());
            io.setAmount(new BigDecimal(0-reqItem.getAmount().doubleValue()));
            io.setHouseId(reqItem.getHouseId());
            io.setPositionId(reqItem.getPositionId());
            //设置价格金额
            io.setPrice(new BigDecimal(price));
            io.setAmount(new BigDecimal(0-amount));
            //抄写字段
            io.setRequestId(request.getId());
            io.setRequestItemId(reqItem.getId());
            io.setRemark(reqItem.getRemark());
            //保存
            io.refreshCreatingStamp();
            ioMapper.insertSncInventoryIo(io);
        }

        //请求结案
        request.setStatus(BillStatus.FINISH);
        request.refreshUpdatingStamp();
        requestMapper.updateSncStockRequest(request);

        //同步更新：物料总量、计划量、指令量、订单明细等
        switch (request.getIoType()){
            case InventoryTransactionType.STOCKOUT_4_SALE:
                this.updateData4SaleStockout(request, reqItems);
                break;
            case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                this.updateData4GeneralBuybackStockout(request, reqItems);
                break;
            case InventoryTransactionType.STOCKOUT_4_PICK:
                this.updateData4GeneralStockout(request, reqItems);
                break;
            case InventoryTransactionType.STOCKOUT_4_OTHERS:
                this.updateData4GeneralStockout(request, reqItems);
                break;
            default:

        }

        return 1;
    }

    /**
     * 好物好库建议
     *
     * @param reqItems 入库请求明细
     * @return 结果
     */
    public List<SncStockRequestItem> gmgwSuggest(List<SncStockRequestItem> reqItems){
        List<SncStockRequestItem> respItemList = new ArrayList<SncStockRequestItem>();

        try{
            for (SncStockRequestItem reqItem : reqItems){
                SncStockRequestItem goodItem = null;
                SncStockRequestItem exceptionalItem = null;
                SncStockRequestItem ngItem = null;
                SncStockRequestItem pendingItem = null;

                double quantity=reqItem.getQuantity().doubleValue();
                double quantityExceptional=reqItem.getQuantityQcExceptional().doubleValue();
                double quantityNg=reqItem.getQuantityQcNg().doubleValue();
                double quatityPending=reqItem.getQuantityQcPending().doubleValue();
                double quantityGood = quantity - quantityNg - quatityPending;

                //取合格品的入库项
                goodItem=(SncStockRequestItem) reqItem.clone();
                goodItem.setQuantity(new BigDecimal(quantityGood));
                goodItem.setMaterialAvailableType(MaterialAvailableType.GOOD);
                respItemList.add(goodItem);

                // 分拆出质量异常有待判处项
                if (quantityExceptional>0){
                    exceptionalItem=(SncStockRequestItem) reqItem.clone();
                    exceptionalItem.setQuantity(new BigDecimal(quantityExceptional));
                    exceptionalItem.setMaterialAvailableType(MaterialAvailableType.EXCEPTIONAL);
                    respItemList.add(exceptionalItem);
                }

                // 分拆出不良品入库项
                if (quantityNg>0){
                    ngItem=(SncStockRequestItem) reqItem.clone();
                    ngItem.setQuantity(new BigDecimal(quantityNg));
                    ngItem.setMaterialAvailableType(MaterialAvailableType.NG);
                    respItemList.add(ngItem);
                }

                // 分拆出待判品入库项
                if (quatityPending>0){
                    pendingItem=(SncStockRequestItem) reqItem.clone();
                    pendingItem.setQuantity(new BigDecimal(quatityPending));
                    pendingItem.setMaterialAvailableType(MaterialAvailableType.PENDING);
                    respItemList.add(pendingItem);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return respItemList;
    }

    /**
     * 先进先出建议
     *
     * @param reqItems 出库请求明细
     * @return 结果
     */
    public List<SncStockRequestItem> fifoSuggest(List<SncStockRequestItem> reqItems){
        List<SncStockRequestItem> respItemList = new ArrayList<SncStockRequestItem>();

        for (SncStockRequestItem reqItem : reqItems){
            //取请求头
            SncStockRequest request = requestMapper.selectSncStockRequestById(reqItem.getRequestId());
            //取物料对象，以便于取默认价格
            EngMaterial material = materialMapper.selectEngMaterialById(reqItem.getMaterialId());
            //补丁：reqitem.materialId错喂，须防错
            //TODO 清理领料数据后，这个if代码可去除
            if (material==null) {
                material = new EngMaterial();
                material.setMaterialCode(reqItem.getMaterialCode());
                List<EngMaterial> materialList=materialMapper.selectAllGoodsList(material);
                material = materialList!=null?materialList.get(0):null;
            }

            //取料的所有库存批
            List<SncInventory> inventoryList = inventoryMapper.selectSncInventoryListByMaterialId(reqItem.getMaterialId());
            //有库存批，则先进先出
            if (inventoryList!=null && inventoryList.size()>0){
                double quantityLeft = reqItem.getQuantity().doubleValue();
                for (int i=0;i<inventoryList.size();i++){
                    //数量非正，则排除之
                    if (inventoryList.get(i).getQuantity().doubleValue()<=0) continue;
                    //非成品库、半成品库、原物料库，则排除之
                    if (inventoryList.get(i).getHouseType().intValue()!=WarehouseType.GOOD_PRODUCT
                            && inventoryList.get(i).getHouseType().intValue()!=WarehouseType.GOOD_SEMI_PRODUCT
                            && inventoryList.get(i).getHouseType().intValue()!=WarehouseType.GOOD_MATERIAL){
                        continue;
                    }

                    //若本批够出，则出完
                    if (quantityLeft<=inventoryList.get(i).getQuantity().doubleValue()){
                        SncStockRequestItem  respItem = new SncStockRequestItem();
                        respItem.setId(reqItem.getId());
                        respItem.setMaterialId(reqItem.getMaterialId());
                        respItem.setMaterialCode(reqItem.getMaterialCode());
                        respItem.setMaterialName(reqItem.getMaterialName());
                        respItem.setMaterialSpec(reqItem.getMaterialSpec());
                        respItem.setCustomerProductNo(reqItem.getCustomerProductNo());
                        respItem.setAbroadProductNo(reqItem.getAbroadProductNo());
                        respItem.setSourceId(reqItem.getSourceId());
                        respItem.setUnitId(reqItem.getUnitId());
                        respItem.setQuantity(new BigDecimal(quantityLeft));
                        respItem.setPrice(inventoryList.get(i).getPrice());
                        respItem.setAmount(new BigDecimal(quantityLeft*inventoryList.get(i).getPrice().doubleValue()));
                        respItem.setHouseId(inventoryList.get(i).getHouseId());
                        respItem.setPositionId(inventoryList.get(i).getPositionId());
                        respItem.setIlId(inventoryList.get(i).getId());
                        //分类设置物料料性
                        switch (request.getIoType()){
                            case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                                respItem.setMaterialAvailableType(MaterialAvailableType.NG);
                                break;
                            default:
                                respItem.setMaterialAvailableType(MaterialAvailableType.EXPIRED);
                        }

                        //存入，且跳出
                        respItemList.add(respItem);
                        break;
                    }
                    //若本批不够出，且为最后库存批，则先出尽该库存批，再从默认库位出
                    else if(i==inventoryList.size()-1 && quantityLeft>inventoryList.get(i).getQuantity().doubleValue()){
                        //取完当前库存批
                        SncStockRequestItem  respItem = new SncStockRequestItem();
                        respItem.setId(reqItem.getId());
                        respItem.setMaterialId(reqItem.getMaterialId());
                        respItem.setMaterialCode(reqItem.getMaterialCode());
                        respItem.setMaterialName(reqItem.getMaterialName());
                        respItem.setMaterialSpec(reqItem.getMaterialSpec());
                        respItem.setCustomerProductNo(reqItem.getCustomerProductNo());
                        respItem.setAbroadProductNo(reqItem.getAbroadProductNo());
                        respItem.setSourceId(reqItem.getSourceId());
                        respItem.setUnitId(reqItem.getUnitId());
                        respItem.setQuantity(inventoryList.get(i).getQuantity());
                        respItem.setPrice(inventoryList.get(i).getPrice());
                        respItem.setAmount(inventoryList.get(i).getAmount());
                        respItem.setHouseId(inventoryList.get(i).getHouseId());
                        respItem.setPositionId(inventoryList.get(i).getPositionId());
                        respItem.setIlId(inventoryList.get(i).getId());
                        //分类设置物料料性
                        switch (request.getIoType()){
                            case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                                respItem.setMaterialAvailableType(MaterialAvailableType.NG);
                                break;
                            default:
                                respItem.setMaterialAvailableType(MaterialAvailableType.EXPIRED);
                        }
                        //存入
                        respItemList.add(respItem);

                        //再从默认仓库取
                        respItem = new SncStockRequestItem();
                        respItem.setId(reqItem.getId());
                        respItem.setMaterialId(reqItem.getMaterialId());
                        respItem.setMaterialCode(reqItem.getMaterialCode());
                        respItem.setMaterialName(reqItem.getMaterialName());
                        respItem.setMaterialSpec(reqItem.getMaterialSpec());
                        respItem.setCustomerProductNo(reqItem.getCustomerProductNo());
                        respItem.setAbroadProductNo(reqItem.getAbroadProductNo());
                        respItem.setSourceId(reqItem.getSourceId());
                        respItem.setUnitId(reqItem.getUnitId());
                        respItem.setQuantity(new BigDecimal(quantityLeft-inventoryList.get(i).getQuantity().doubleValue()));
                        respItem.setPrice(material.getPriceLast());
                        respItem.setAmount(new BigDecimal(reqItem.getQuantity().doubleValue()*material.getPriceLast().doubleValue()).setScale(4, BigDecimal.ROUND_HALF_UP));
                        respItem.setHouseId(material.getHouseId());
                        respItem.setPositionId(material.getPositionId());
                        respItem.setIlId(null);
                        //分类设置物料料性
                        switch (request.getIoType()){
                            case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                                respItem.setMaterialAvailableType(MaterialAvailableType.NG);
                                break;
                            default:
                                respItem.setMaterialAvailableType(MaterialAvailableType.EXPIRED);
                        }
                        //存入
                        respItemList.add(respItem);
                    }
                    //若本批不够出，且非最后库存批，则出尽本批后出下批
                    else{
                        SncStockRequestItem  respItem = new SncStockRequestItem();
                        respItem.setId(reqItem.getId());
                        respItem.setMaterialId(reqItem.getMaterialId());
                        respItem.setMaterialCode(reqItem.getMaterialCode());
                        respItem.setMaterialName(reqItem.getMaterialName());
                        respItem.setMaterialSpec(reqItem.getMaterialSpec());
                        respItem.setCustomerProductNo(reqItem.getCustomerProductNo());
                        respItem.setAbroadProductNo(reqItem.getAbroadProductNo());
                        respItem.setSourceId(reqItem.getSourceId());
                        respItem.setUnitId(reqItem.getUnitId());
                        respItem.setQuantity(inventoryList.get(i).getQuantity());
                        respItem.setPrice(inventoryList.get(i).getPrice());
                        respItem.setAmount(inventoryList.get(i).getAmount());
                        respItem.setHouseId(inventoryList.get(i).getHouseId());
                        respItem.setPositionId(inventoryList.get(i).getPositionId());
                        respItem.setIlId(inventoryList.get(i).getId());
                        //分类设置物料料性
                        switch (request.getIoType()){
                            case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                                respItem.setMaterialAvailableType(MaterialAvailableType.NG);
                                break;
                            default:
                                respItem.setMaterialAvailableType(MaterialAvailableType.EXPIRED);
                        }
                        //存入
                        respItemList.add(respItem);

                        //待扣减的下库存批量
                        quantityLeft-=inventoryList.get(i).getQuantity().doubleValue();
                    }
                }
            }
            //无库存批，则从默认仓库储位出库
            else{
                SncStockRequestItem  respItem = new SncStockRequestItem();
                respItem.setId(reqItem.getId());
                respItem.setMaterialId(reqItem.getMaterialId());
                respItem.setMaterialCode(reqItem.getMaterialCode());
                respItem.setMaterialName(reqItem.getMaterialName());
                respItem.setMaterialSpec(reqItem.getMaterialSpec());
                respItem.setCustomerProductNo(reqItem.getCustomerProductNo());
                respItem.setAbroadProductNo(reqItem.getAbroadProductNo());
                respItem.setSourceId(reqItem.getSourceId());
                respItem.setUnitId(reqItem.getUnitId());
                respItem.setQuantity(reqItem.getQuantity());
                respItem.setPrice(material.getPriceLast());
                respItem.setAmount(new BigDecimal(reqItem.getQuantity().doubleValue()*material.getPriceLast().doubleValue()).setScale(4, BigDecimal.ROUND_HALF_UP));
                respItem.setHouseId(material.getHouseId());
                respItem.setPositionId(material.getPositionId());
                respItem.setIlId(null);
                //分类设置物料料性
                switch (request.getIoType()){
                    case InventoryTransactionType.STOCKOUT_4_BUYBACK:
                        respItem.setMaterialAvailableType(MaterialAvailableType.NG);
                        break;
                    default:
                        respItem.setMaterialAvailableType(MaterialAvailableType.EXPIRED);
                }
                //存入
                respItemList.add(respItem);
            }
        }

        return respItemList;
    }

    /**
     * 转正料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    public int normalizeCode(SncStockRequestItem requestItem) throws Exception{
        //校验料号重复否
        EngMaterial materialTarget = materialMapper.selectEngMaterialById(requestItem.getMaterialId());
        if (materialTarget==null || materialTarget.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
            String msg = "正式料号[" + requestItem.getMaterialCode() + "]不存在！";
            throw new MaterialUnavailableException(msg);
        }
        //校验临时料号
        SncStockRequestItem reqitemDatabase = requestItemMapper.selectSncStockRequestItemById(requestItem.getId());
        EngMaterial materialSource = materialMapper.selectEngMaterialById(reqitemDatabase.getMaterialId());
        if (!materialSource.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
            String msg = "被转料号[" + materialSource.getMaterialCode() + "]非临时料号！";
            throw new MaterialUnavailableException(msg);
        }

        //替代临时料
        this.replaceTemporaryMaterial(reqitemDatabase, materialTarget);

        return 1;
    }

    /**
     * 生成料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    public int generateMaterialCode(SncStockRequestItem requestItem) throws Exception{
        //校验料号重复否
        EngMaterial materialTarget = new EngMaterial();
        materialTarget.setMaterialCode(requestItem.getMaterialCode());
        List<EngMaterial> materialList = materialMapper.selectEngMaterialList(materialTarget);
        if (materialList!=null && materialList.size()>0){
            String msg = "料号[" + requestItem.getMaterialCode() + "]已存在！";
            throw new MaterialUnavailableException(msg);
        }

        //取原RequestItem
        SncStockRequestItem reqitemDatabase = requestItemMapper.selectSncStockRequestItemById(requestItem.getId());

        //创建物料
        materialTarget = new EngMaterial();
        materialTarget.setMaterialCode(requestItem.getMaterialCode());
        materialTarget.setCategoryId(requestItem.getCategoryId());
        materialTarget.setCompoundId(requestItem.getCompoundId());
        materialTarget.setMaterialSpec(requestItem.getMaterialSpec());
        materialTarget.setDrawNo(requestItem.getDrawNo());
        materialTarget.setCustomerProductNo(requestItem.getCustomerProductNo());
        materialTarget.setAbroadProductNo(requestItem.getAbroadProductNo());
        materialTarget.setBaseType(String.valueOf(MaterialBaseType.MATERIAL));
        materialTarget.setMaterialName(requestItem.getMaterialName());
        materialTarget.setUnitId(requestItem.getUnitId());
        materialTarget.refreshUpdatingStamp();
        materialMapper.insertEngMaterial(materialTarget);

        //替代临时料
        this.replaceTemporaryMaterial(reqitemDatabase, materialTarget);

        return 1;
    }

    private void updateData4SubcontractStockin(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //有否不良品入库
        boolean hasNG = false;

        //按明细更新数据
        for (SncStockRequestItem reqItem : reqItems){
            //标记不良品入库
            hasNG = hasNG || reqItem.getMaterialAvailableType()==MaterialAvailableType.NG;

            //更新物料总量
            this.increaseMaterialQuantity(reqItem.getMaterialId(), reqItem.getQuantity());

            //更新采购明细的收货数、状态（已入库）
            SupPoItem poitem = poitemMapper.selectSupPoItemById(reqItem.getSourceId());
            poitem.setQuantityInventory((new BigDecimal(poitem.getQuantityInventory().doubleValue()+reqItem.getQuantity().doubleValue())));
            if (poitem.getQuantityInventory().compareTo(poitem.getQuantityOrder())>=0){
                //不合格的异入库。此处借用状态5标记异入库
                if (reqItem.getMaterialAvailableType()==MaterialAvailableType.NG) poitem.setStatus(BillStatus.ACTIVE_STARTED);
                //正常的已入库
                else poitem.setStatus(BillStatus.ACTIVE_TERMINATED);
            }
            poitem.refreshUpdatingStamp();
            poitemMapper.updateSupPoItem(poitem);

            //更新转包计划的完成量
            DelOsplan osplan = osplanMapper.selectDelOsplanById(poitem.getSourceBillId());
            osplan.setQuantityFinished(new BigDecimal(osplan.getQuantityFinished().doubleValue()+reqItem.getQuantity().doubleValue()));
            //更新转包计划的状态
            if (osplan.getQuantityFinished().compareTo(osplan.getQuantityPlanned())>=0){
                osplan.setStatus(BillStatus.FINISH_WAITING);
            }
            //保存更新
            osplan.refreshUpdatingStamp();
            osplanMapper.updateDelOsplan(osplan);

            //更新订单明细的转包完成量
            OrdSoItem soitem= soitemMapper.selectOrdSoItemById(osplan.getSoItemId());
            soitem.setQuantitySubcontracted(new BigDecimal(soitem.getQuantitySubcontracted().doubleValue()+reqItem.getQuantity().doubleValue()));
            soitem.refreshUpdatingStamp();
            soitemMapper.updateOrdSoItem(soitem);
        }

        //判断PO可否待结案，可则待结案
        boolean areAllPoitemFinished = true;
        List<SupPoItem> poitemList = poitemMapper.selectSupPoItemListByPoId(request.getRequestBillId());
        for (int i=0; poitemList!=null && i<poitemList.size();i++){
            areAllPoitemFinished = areAllPoitemFinished && poitemList.get(i).getQuantityInventory().compareTo(poitemList.get(i).getQuantityOrder())>=0;
            if (!areAllPoitemFinished) break;
        }
        if(areAllPoitemFinished && !hasNG){
            SupPo po = poMapper.selectSupPoById(request.getRequestBillId());
            po.setStatus(BillStatus.FINISH_WAITING);
            po.refreshUpdatingStamp();
            poMapper.updateSupPo(po);
        }
    }

    private void updateData4BuyStockin(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //有否不良品入库
        boolean hasNG = false;

        //按明细更新数据
        for (SncStockRequestItem reqItem : reqItems){
            //标记不良品入库
            hasNG = hasNG || reqItem.getMaterialAvailableType()==MaterialAvailableType.NG;

            //更新采购明细的收货数和状态（已入库）
            SupPoItem poitem = poitemMapper.selectSupPoItemById(reqItem.getSourceId());
            poitem.setQuantityInventory((new BigDecimal(poitem.getQuantityInventory().doubleValue()+reqItem.getQuantity().doubleValue())));
            if (poitem.getQuantityInventory().compareTo(poitem.getQuantityOrder())>=0){
                //不合格的异入库。此处借用状态5标记异入库
                if (reqItem.getMaterialAvailableType()==MaterialAvailableType.NG) poitem.setStatus(BillStatus.ACTIVE_STARTED);
                    //正常的已入库
                else poitem.setStatus(BillStatus.ACTIVE_TERMINATED);
            }
            poitem.refreshUpdatingStamp();
            poitemMapper.updateSupPoItem(poitem);

            //判断PO可否待结案，可则待结案
            if (poitem.getQuantityInventory().compareTo(poitem.getQuantityOrder())>=0){
                boolean areAllPoitemFinished = true;
                List<SupPoItem> poitemList = poitemMapper.selectSupPoItemListByPoId(poitem.getPoId());
                for (int i=0; poitemList!=null && i<poitemList.size();i++){
                    areAllPoitemFinished = areAllPoitemFinished && poitemList.get(i).getQuantityInventory().compareTo(poitemList.get(i).getQuantityOrder())>=0;
                    if (!areAllPoitemFinished) break;
                }
                if(areAllPoitemFinished && !hasNG){
                    SupPo po = poMapper.selectSupPoById(poitem.getPoId());
                    po.setStatus(BillStatus.FINISH_WAITING);
                    po.refreshUpdatingStamp();
                    poMapper.updateSupPo(po);
                }
            }
        }
    }

    private void updateData4GeneralStockin(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //更新物料总量
        for (SncStockRequestItem reqItem : reqItems){
            this.increaseMaterialQuantity(reqItem.getMaterialId(), reqItem.getQuantity());

            PlnMl ml = mlMapper.selectPlnMlById(reqItem.getSourceId());
            ml.setQuantityOutput(reqItem.getQuantity());
            ml.setStatus(BillStatus.FINISH_WAITING);
            ml.setStockinBy(SecurityUtils.getUserId());
            ml.setStockinTime(DateUtils.getNowDate());
            ml.refreshUpdatingStamp();
            mlMapper.updatePlnMl(ml);

            //取移转记录
            ExeProcessMoves param = new ExeProcessMoves();
            param.setMlId(ml.getId());
            param.setProcessSeq(ml.getCurrentProcessSeq());
            ExeProcessMoves move =moveMapper.selectExeProcessMovesList(param).get(0);
            move.setStatus(8);//已转
            move.refreshUpdatingStamp();
            moveMapper.updateExeProcessMoves(move);

            //更新wo的产出数量
            PlnWo wo = woMapper.selectPlnWoById(ml.getWoId());
            wo.setQuantityOutput(new BigDecimal(wo.getQuantityOutput().doubleValue()+reqItem.getQuantity().doubleValue()));
            wo.refreshUpdatingStamp();
            woMapper.updatePlnWo(wo);

            //更新mps的产出数量，当且仅当工单为最终产品时有效
            DelMps mps = mpsMapper.selectDelMpsById(wo.getMpsId());
            if (mps.getProductId().equals(reqItem.getMaterialId())){
                mps.setQuantityFinished(new BigDecimal(mps.getQuantityFinished().doubleValue()+reqItem.getQuantity().doubleValue()));
                mps.refreshUpdatingStamp();
                mpsMapper.updateDelMps(mps);
            }

            //更新订单项(交货项)的产出数，当且仅当工单为最终产品时有效
            OrdSoItem soItem = soitemMapper.selectOrdSoItemById(mps.getSoItemId());
            if (soItem!=null && soItem.getProductId().equals(reqItem.getMaterialId())){
                soItem.setQuantityManufactured(new BigDecimal(soItem.getQuantityManufactured().doubleValue()+reqItem.getQuantity().doubleValue()));
                soItem.refreshUpdatingStamp();
                soitemMapper.updateOrdSoItem(soItem);
            }
        }
    }

    private void updateData4SaleStockout(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //按明细更新数据
        for (SncStockRequestItem reqItem : reqItems){
            //更新物料总量
            this.reduceMaterialQuantity(reqItem.getMaterialId(), reqItem.getQuantity());
            //更新发货单明细的发货数、状态
            DelGdItem doitem = doitemMapper.selectDelGdItemById(reqItem.getSourceId());
            doitem.setQuantityDelivered(new BigDecimal(doitem.getQuantityDelivered().doubleValue()+reqItem.getQuantity().doubleValue()));
            doitem.refreshUpdatingStamp();
            doitemMapper.updateDelGdItem(doitem);
            //更新发货单状态
            DelDeliveryOrder doo = doMapper.selectDelDeliveryOrderById(doitem.getGdId());
            List<DelGdItem> doitemList = doitemMapper.selectDelGdItemListByGdId(doitem.getGdId());
            boolean allDoitemDeliveryed =true;
            if (doitemList!=null){
                for (DelGdItem item : doitemList){
                    allDoitemDeliveryed = allDoitemDeliveryed && item.getQuantityDelivered().doubleValue()>=item.getQuantity().doubleValue();
                    if (!allDoitemDeliveryed) break;
                }
            }
            //发货够数则待结案，否则发货中
            if (allDoitemDeliveryed)  doo.setStatus(BillStatus.FINISH_WAITING);
            else doo.setStatus(BillStatus.ACTIVE_PROCESSING);
            doo.refreshUpdatingStamp();
            doMapper.updateDelDeliveryOrder(doo);

            //更新发货计划的完成量、状态
            DelPlanItem delplan = delplanMapper.selectDelPlanItemById(doitem.getDpId());
            delplan.setQuantityReleased(new BigDecimal(delplan.getQuantityReleased().doubleValue()+reqItem.getQuantity().doubleValue()));
            //DP发完则待结案，否则在发货
            if (delplan.getQuantityReleased().compareTo(delplan.getQuantityPlanned())>=0){
                delplan.setStatus(BillStatus.FINISH_WAITING);
            }else{
                delplan.setStatus(BillStatus.ACTIVE_PROCESSING);
            }
            //保存更新
            delplan.refreshUpdatingStamp();
            delplanMapper.updateDelPlanItem(delplan);

            //减少物料的预约量
            EngMaterial material = materialMapper.selectEngMaterialById(delplan.getProductId());
            material.setQuantityBooked(new BigDecimal(material.getQuantityBooked().doubleValue()-reqItem.getQuantity().doubleValue()));
            material.refreshUpdatingStamp();
            materialMapper.updateEngMaterial(material);

            //更新订单明细的发货量和状态
            OrdSoItem soitem= soitemMapper.selectOrdSoItemById(doitem.getSoItemId());
            soitem.setQuantityDelivered(new BigDecimal(soitem.getQuantityDelivered().doubleValue()+reqItem.getQuantity().doubleValue()));
            //明细发完则待结案，否则在发货
            if (soitem.getQuantityDelivered().doubleValue()>=soitem.getQuantityOrder().doubleValue()) soitem.setStatus(BillStatus.FINISH_WAITING);
            else soitem.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
            soitem.refreshUpdatingStamp();
            soitemMapper.updateOrdSoItem(soitem);
            //更新订单状态
            OrdSo so = soMapper.selectOrdSoById(soitem.getSoId());
            List<OrdSoItem> soItemList = soitemMapper.selectOrdSoItemListBySoId(soitem.getSoId());
            boolean allSoitemDeliveryed =true;
            if (soItemList!=null){
                for (OrdSoItem item : soItemList){
                    allSoitemDeliveryed = allSoitemDeliveryed && item.getQuantityDelivered().doubleValue()>=item.getQuantityOrder().doubleValue();
                    if (!allSoitemDeliveryed) break;
                }
            }
            //发货够数则待结案，否则发货中
            if (allSoitemDeliveryed)  so.setStatus(BillStatus.FINISH_WAITING);
            else so.setStatus(BillStatus.ACTIVE_STOCK_ACTION);
            so.refreshUpdatingStamp();
            soMapper.updateOrdSo(so);
        }

        //判断发货单可否待结案，可则待结
        boolean areAllDoitemFinished = true;
        List<DelGdItem> doitemList = doitemMapper.selectDelGdItemListByGdId(request.getRequestBillId());
        for (int i=0; doitemList!=null && i<doitemList.size();i++){
            areAllDoitemFinished = areAllDoitemFinished && doitemList.get(i).getQuantityDelivered().compareTo(doitemList.get(i).getQuantity())>=0;
            if (!areAllDoitemFinished) break;
        }
        if(areAllDoitemFinished){
            DelDeliveryOrder doo = doMapper.selectDelDeliveryOrderById(request.getRequestBillId());
            doo.setStatus(BillStatus.FINISH_WAITING);
            doo.refreshUpdatingStamp();
            doMapper.updateDelDeliveryOrder(doo);
        }
    }

    private void updateData4GeneralBuybackStockout(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //更新物料总量、采购明细状态
        for (SncStockRequestItem reqItem : reqItems){
            this.reduceMaterialQuantity(reqItem.getMaterialId(), reqItem.getQuantity());

            SupPoItem poitem = poitemMapper.selectSupPoItemById(reqItem.getSourceId());
            poitem.setStatus(BillStatus.FINISH_WAITING);
            poitem.refreshUpdatingStamp();
            poitemMapper.updateSupPoItem(poitem);
        }

        SupPo po = poMapper.selectSupPoById(request.getRequestBillId());
        List<SupPoItem> poitemList = poitemMapper.selectSupPoItemListByPoId(po.getId());
        boolean allPoitemStockouted = true;
        if (poitemList!=null){
            for (SupPoItem item:poitemList){
                allPoitemStockouted = allPoitemStockouted
                        && (item.getStatus()==BillStatus.ACTIVE_TERMINATED || item.getStatus()==BillStatus.FINISH_WAITING);
                if (!allPoitemStockouted) break;
            }
        }
        if (allPoitemStockouted) po.setStatus(BillStatus.FINISH_WAITING);
        po.refreshUpdatingStamp();
        poMapper.updateSupPo(po);
    }

    private void updateData4GeneralStockout(SncStockRequest request, List<SncStockRequestItem> reqItems){
        //更新物料总量
        for (SncStockRequestItem reqItem : reqItems){
            this.reduceMaterialQuantity(reqItem.getMaterialId(), reqItem.getQuantity());
        }
    }

    private void increaseMaterialQuantity(Long materialId, BigDecimal quantity){
        EngMaterial material = materialMapper.selectEngMaterialById(materialId);
        material.setQuantityQualified(new BigDecimal(material.getQuantityQualified().doubleValue()+quantity.doubleValue()));
        material.refreshUpdatingStamp();
        materialMapper.updateEngMaterial(material);
    }

    private void reduceMaterialQuantity(Long materialId, BigDecimal quantity){
        EngMaterial material = materialMapper.selectEngMaterialById(materialId);
        material.setQuantityQualified(new BigDecimal(material.getQuantityQualified().doubleValue()-quantity.doubleValue()));
        material.refreshUpdatingStamp();
        materialMapper.updateEngMaterial(material);
    }

    private int replaceTemporaryMaterial(SncStockRequestItem requestItem, EngMaterial materialTarget){
        //缓存源物料ID
        Long materialIdSource = requestItem.getMaterialId();

        //替代出入库请求临时料
        SncStockRequestItem requestItemTemp=new SncStockRequestItem();
        requestItemTemp.setMaterialId(materialIdSource);
        List<SncStockRequestItem> requestItemList =requestItemMapper.selectSncStockRequestItemList(requestItemTemp);
        if (requestItemList!=null){
            for (SncStockRequestItem item : requestItemList){
                requestItem.setMaterialId(materialTarget.getId());
                requestItem.setMaterialCode(materialTarget.getMaterialCode());
                requestItem.setMaterialName(materialTarget.getMaterialName());
                requestItem.setMaterialSpec(materialTarget.getMaterialSpec());
                requestItem.setDrawNo(materialTarget.getDrawNo());
                requestItem.setCategoryId(materialTarget.getCategoryId());
                requestItem.setCompoundId(materialTarget.getCompoundId());
                requestItem.setCustomerProductNo(materialTarget.getCustomerProductNo());
                requestItem.setAbroadProductNo(materialTarget.getAbroadProductNo());
                requestItem.refreshUpdatingStamp();
                requestItemMapper.updateSncStockRequestItem(requestItem);
            }
        }

        /** 替代直发线的临时料 **/
        //替代直发线的临时料
        DelPlanItem delPlan=new DelPlanItem();
        delPlan.setProductId(materialIdSource);
        List<DelPlanItem> delPlanList =delplanMapper.selectDelPlanItemList(delPlan);
        if (delPlanList!=null){
            for (DelPlanItem item : delPlanList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setProductName(materialTarget.getMaterialName());
                item.setProductSpec(materialTarget.getMaterialSpec());
                item.setDrawNo(materialTarget.getDrawNo());
                item.setCustomerProductNo(materialTarget.getCustomerProductNo());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                delplanMapper.updateDelPlanItem(item);
            }
        }
        //替代发货单的临时料
        DelGdItem doitem=new DelGdItem();
        doitem.setProductId(materialIdSource);
        List<DelGdItem> doitemList =doitemMapper.selectDelGdItemList(doitem);
        if (doitemList!=null){
            for (DelGdItem item : doitemList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setProductName(materialTarget.getMaterialName());
                item.setProductSpec(materialTarget.getMaterialSpec());
                item.setDrawNo(materialTarget.getDrawNo());
                item.setCustomerProductNo(materialTarget.getCustomerProductNo());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                doitemMapper.updateDelGdItem(item);
            }
        }

        /** 替代转包线的临时料 **/
        //替代转包计划的临时料
        DelOsplan osPlan=new DelOsplan();
        osPlan.setProductId(materialIdSource);
        List<DelOsplan> osplanList = osplanMapper.selectDelOsplanList(osPlan);
        if (osplanList!=null){
            for (DelOsplan item : osplanList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setProductName(materialTarget.getMaterialName());
                item.setProductSpec(materialTarget.getMaterialSpec());
                item.setDrawNo(materialTarget.getDrawNo());
                item.setCustomerProductNo(materialTarget.getCustomerProductNo());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                osplanMapper.updateDelOsplan(item);
            }
        }
        //替代请购单的临时料
        SupPurchaseRequestItem pritem=new SupPurchaseRequestItem();
        pritem.setMaterialId(materialIdSource);
        List<SupPurchaseRequestItem> pritemList = pritemMapper.selectSupPurchaseRequestItemList(pritem);
        if (pritemList!=null){
            for (SupPurchaseRequestItem item : pritemList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                pritemMapper.updateSupPurchaseRequestItem(item);
            }
        }
        //替代采购计划的临时料
        SupPurchasingPlan buyPlan=new SupPurchasingPlan();
        buyPlan.setMaterialId(materialIdSource);
        List<SupPurchasingPlan> buyplanList = buyplanMapper.selectSupPurchasingPlanList(buyPlan);
        if (buyplanList!=null){
            for (SupPurchasingPlan item : buyplanList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                buyplanMapper.updateSupPurchasingPlan(item);
            }
        }
        //替代采购单的临时料
        SupPoItem poitem=new SupPoItem();
        poitem.setMaterialId(materialIdSource);
        List<SupPoItem> poitemList = poitemMapper.selectSupPoItemList(poitem);
        if (poitemList!=null){
            for (SupPoItem item : poitemList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                poitemMapper.updateSupPoItem(item);
            }
        }
        //替代质检请求的临时料
        QuaInspectRequestItem qritem=new QuaInspectRequestItem();
        qritem.setMaterialId(materialIdSource);
        List<QuaInspectRequestItem> qritemList = qritemMapper.selectQuaInspectRequestItemList(qritem);
        if (qritemList!=null){
            for (QuaInspectRequestItem item : qritemList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                qritemMapper.updateQuaInspectRequestItem(item);
            }
        }
        //替代质量异常处理的临时料
        QuaExceptionDeal qedeal=new QuaExceptionDeal();
        qedeal.setMaterialId(materialIdSource);
        List<QuaExceptionDeal> qedealList = qedealMapper.selectQuaExceptionDealList(qedeal);
        if (qedealList!=null){
            for (QuaExceptionDeal item : qedealList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                qedealMapper.updateQuaExceptionDeal(item);
            }
        }
        //替代采购退货单的临时料
        SupPoItemReturn poItemReturn=new SupPoItemReturn();
        poItemReturn.setMaterialId(materialIdSource);
        List<SupPoItemReturn> poItemReturnList = poItemReturnMapper.selectSupPoItemReturnList(poItemReturn);
        if (poItemReturnList!=null){
            for (SupPoItemReturn item : poItemReturnList){
                item.setMaterialId(materialTarget.getId());
                item.setMaterialCode(materialTarget.getMaterialCode());
                item.setMaterialName(materialTarget.getMaterialName());
                item.setMaterialSpec(materialTarget.getMaterialSpec());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                poItemReturnMapper.updateSupPoItemReturn(item);
            }
        }

        /** 替代转包线的临时料 **/
        //替代主计划的临时料
        DelMps mps=new DelMps();
        mps.setProductId(materialIdSource);
        List<DelMps> mpsList = mpsMapper.selectDelMpsList(mps);
        if (mpsList!=null){
            for (DelMps item : mpsList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setProductName(materialTarget.getMaterialName());
                item.setProductSpec(materialTarget.getMaterialSpec());
                item.setDrawNo(materialTarget.getDrawNo());
                item.setCustomerProductNo(materialTarget.getCustomerProductNo());
                item.setAbroadProductNo(materialTarget.getAbroadProductNo());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                mpsMapper.updateDelMps(item);
            }
        }

        /** 替代订单合同的临时料 **/
        //替代订单明细的临时料
        OrdSoItem soItem=new OrdSoItem();
        soItem.setProductId(materialIdSource);
        List<OrdSoItem> soItemList = soitemMapper.selectOrdSoItemList(soItem);
        if (soItemList!=null){
            for (OrdSoItem item : soItemList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setProductName(materialTarget.getMaterialName());
                item.setProductSpec(materialTarget.getMaterialSpec());
                item.setDrawNo(materialTarget.getDrawNo());
                item.setCustomerProductNo(materialTarget.getCustomerProductNo());
                item.setUnitId(materialTarget.getUnitId());
                item.refreshUpdatingStamp();
                soitemMapper.updateOrdSoItem(item);
            }
        }
        //替代合同明细的临时料
        OrdContractItem contractItem=new OrdContractItem();
        contractItem.setProductId(materialIdSource);
        List<OrdContractItem> contractItemList = contractItemMapper.selectOrdContractItemList(contractItem);
        if (contractItemList!=null){
            for (OrdContractItem item : contractItemList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                item.setUnitId(materialTarget.getUnitId());
                //保留原合同数据
                //item.setDrawNo(productTarget.getDrawNo());
                //item.setProductName(productTarget.getMaterialName());
                //item.setProductSpec(productTarget.getMaterialSpec());
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                //item.setAbroadProductNo(productTarget.getAbroadProductNo());
                item.refreshUpdatingStamp();
                contractItemMapper.updateOrdContractItem(item);
            }
        }
        //替代总成合同头的临时料
        OrdContract contract = new OrdContract();
        contract.setProductId(materialIdSource);
        List<OrdContract> contractList = contractMapper.selectOrdContractList(contract);
        if (contractList!=null){
            for (OrdContract item : contractList){
                item.setProductId(materialTarget.getId());
                item.setProductCode(materialTarget.getMaterialCode());
                //保留原合同数据
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.refreshUpdatingStamp();
                contractMapper.updateOrdContract(item);
            }
        }

        return 1;
    }

}
