package net.factoryopen.core.myfactoryai.manufacturing.exe;

import java.math.BigDecimal;
import java.util.List;

import net.factoryopen.core.common.exception.NumberNotEnoughException;
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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLaborMapper;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResMachineMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlOperatingMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessWorkMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.RecordNotExistException;

/**
 * 车间班组作业ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class ExeMlOperatingAlgorithm
{
    @Autowired(required = false)
    private ExeMlOperatingMapper mlMapper;

    @Autowired(required = false)
    private ExeProcessMovesMapper moveMapper;

    @Autowired(required = false)
    private ExeProcessWorkMapper wsMapper;

    @Autowired(required = false)
    private SncStockRequestMapper stockreqMapper;

    @Autowired(required = false)
    private SncStockRequestItemMapper requestItemMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private EngMaterialMapper productMapper;

    @Autowired
    private ResLaborMapper laborMapper;

    @Autowired
    private ResMachineMapper machineMapper;

    @Autowired
    private BillcodeManager billcodeManager;

      /**
       * 接收
       *
       * @param ids id集合
       * @return 结果
       */
      public int acceptMl(Long[] ids) throws Exception{
          // 判异，排除状态的錯配
          for (int i = 0; i < ids.length; i++) {
              //取在制批次
              ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);

              //判断制造批次状态合法否
              if (ml.getStatus().intValue() != 4 && ml.getStatus().intValue()!=8) {
                  String msg = "制造批[" + ml.getBillNo() + "]的批次状态不是[4-已派工]或[8-在制]";
                  throw new StatusUnavailableException(msg);
              }
              if (ml.getProcessProgress().intValue() != 0) {
                  String msg = "制造批[" + ml.getBillNo() + "]的工序状态不是[0-待收]";
                  throw new StatusUnavailableException(msg);
              }

              //取移转记录
              ExeProcessMoves param = new ExeProcessMoves();
              param.setMlId(ml.getId());
              param.setProcessSeq(ml.getCurrentProcessSeq());
              List<ExeProcessMoves> moves = moveMapper.selectExeProcessMovesList(param);
              //判断移转记录存在否
              if (moves == null || moves.size()== 0) {
                  String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的记录不存在";
                  throw new RecordNotExistException(msg);
              }
              //判断移转记录状态合法否
              if (moves.get(0).getStatus()!=0) {
                  String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的移转状态不是[0-待接收]";
                  throw new StatusUnavailableException(msg);
              }
          }

          // 取移转记录表，更新移转记录
          for (int i = 0; i < ids.length; i++) {
              //取在制批次
              ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);

              //计算应接收数量
              double quantityAcceptable =ml.getQuantityAccepted().doubleValue();
              if (quantityAcceptable==0){
                  quantityAcceptable = ml.getQuantityDispatched().doubleValue();
              }else{
                  quantityAcceptable = quantityAcceptable - ml.getQuantitySplit().doubleValue()
                          - ml.getQuantityScrap().doubleValue() - ml.getQuantityRedisp().doubleValue() - ml.getQuantityPending().doubleValue();
              }

              //取移转记录
              ExeProcessMoves param = new ExeProcessMoves();
              param.setMlId(ml.getId());
              param.setProcessSeq(ml.getCurrentProcessSeq());
              ExeProcessMoves currentMove =moveMapper.selectExeProcessMovesList(param).get(0);

              //修改接收信息
              currentMove.setStatus(4);//状态设为待开工”
              currentMove.setQuantityReceive(new BigDecimal(quantityAcceptable));
              currentMove.setReceiveBy(SecurityUtils.getUserId());
              currentMove.setReceiveTime(DateUtils.getNowDate());
              //保存
              currentMove.refreshUpdatingStamp();
              moveMapper.updateExeProcessMoves(currentMove);

              //修改上工序状态为“已转”
              ExeProcessMoves lastMove = this.getLastProcessSeq(currentMove);
              if (ml.getCurrentProcessSeq() > lastMove.getProcessSeq())
              {
                  lastMove.setStatus(8);//状态设为“已转”
                  lastMove.refreshUpdatingStamp();
                  moveMapper.updateExeProcessMoves(lastMove);
              }

              //取班组ID
              Long groupId = null;
              List<ResLabor> laborList = laborMapper.selectResLaborListByUserId(SecurityUtils.getUserId());
              if (laborList!=null && laborList.size()>0){
                  groupId = laborList.get(0).getWorkgroupId();
              }

              // 修改在制批次的状态和工序状态
              ml.setQuantityAccepted(ml.getQuantityDispatched());
              ml.setStatus(BillStatus.ACTIVE_PROCESSING);//批次在制
              ml.setProcessProgress(4);//状态设为工序“待开工”
              ml.setCurrentWorkgroupId(groupId);
              ml.setAcceptingTime(DateUtils.getNowDate());
              ml.setAcceptingBy(SecurityUtils.getUserId());
              ml.refreshUpdatingStamp();
              mlMapper.updateExeMlOperating(ml);
          }

          return 1;
      }
      /**
       * 请料
       *
       * @param ids id集合
       * @return 结果
       */
      public int requestMaterial(Long[] ids) throws Exception{
          String msg = "本功能暂无启用！";
          throw new StatusUnavailableException(msg);
          // 判异，排除客户和状态的錯配
          //for (int i = 0; i < ids.length; i++) {
          //    DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

          //    if (!plan.getCustomerId().equals(customerId)) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        ChaCustomer customer = customerMapper.selectChaCustomerById(customerId);
          //        String msg = "发货计划[" + so.getBillNo() + "]的客户不是[" + customer.getCustomerName() + "]";
          //        throw new CustomerUnavailableException(msg);
          //    }
          //    if (plan.getStatus().intValue() != 4) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        String msg = "发货计划[" + so.getBillNo() + "]的状态不是[4-生效]";
          //        throw new StatusUnavailableException(msg);
          //    }
          //    if (DateUtils.parseDate(deliveryDay).before(DateUtils.getNowDate())) {
          //        OrdSo so = soMapper.selectOrdSoById(plan.getSoId());
          //        String msg = "发货计划[" + so.getBillNo() + "]的交期早于现在";
          //        throw new DateUnavailableException(msg);
          //    }
          //}

          // 取得并更新单据规则
          //String billNo = billcodeManager.getNextBillno("SD");
          //billcodeManager.updateBillSeq("SD");

          // 创建发货单头
          //DelDeliveryOrder order = new DelDeliveryOrder();
          //order.setBillNo(billNo);
          //orderMapper.insertDelDeliveryOrder(order);

          // 逐一取原发货计划，建发货单明细
          //for (int i = 0; i < ids.length; i++) {
          //    DelDeliveryPlan plan = planMapper.selectDelDeliveryPlanById(ids[i]);

          //    //创建并保存发货单身
          //    DelGdItem orderItem = new DelGdItem();
          //    orderItem.setGdId(order.getId());
          //    orderItem.setSeqNo(i+1);
          //    orderItemMapper.insertDelGdItem(orderItem);

          //    // 将原发货计划的状态设为“在处理”
          //    plan.setStatus(6);
          //    planMapper.updateDelDeliveryPlan(plan);
          //}

          //return 1;
      }
      /**
       * 转出
       *
       * @param ids id集合
       * @return 结果
       */
      public int postMl(Long[] ids) throws Exception{
          // 判异，排除状态的錯配
          for (int i = 0; i < ids.length; i++) {
              //取在制批次
              ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);

              //判断制造批次状态合法否
              if (ml.getStatus().intValue() != 8) {
                  String msg = "制造批[" + ml.getBillNo() + "]的批次状态不是[8-在制]";
                  throw new StatusUnavailableException(msg);
              }
              if (ml.getProcessProgress().intValue() != 6) {
                  String msg = "制造批[" + ml.getBillNo() + "]的工序状态不是[6-已完工]";
                  throw new StatusUnavailableException(msg);
              }

              //取移转记录
              ExeProcessMoves param = new ExeProcessMoves();
              param.setMlId(ml.getId());
              param.setProcessSeq(ml.getCurrentProcessSeq());
              List<ExeProcessMoves> moves = moveMapper.selectExeProcessMovesList(param);
              //判断移转记录存在否
              if (moves == null || moves.size()== 0) {
                  String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的记录不存在";
                  throw new RecordNotExistException(msg);
              }
              //判断移转记录状态合法否
              ExeProcessMoves move = moves.get(0);
              if (move.getStatus()!=6) {
                  String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的移转状态不是[6-已完工]";
                  throw new StatusUnavailableException(msg);
              }

              //检查转出数量合法否
              double quantityPostAvailable = 0;
              double quantityPostShould = move.getQuantityReceive().doubleValue() - move.getQuantitySplit().doubleValue()
                      - move.getQuantityScrap().doubleValue() - move.getQuantityRedisp().doubleValue() - move.getQuantityPending().doubleValue();
              List<ExeProcessWork> wsList = wsMapper.selectExeProcessWorkListByMoveId(move.getId());
              if (wsList!=null && wsList.size()>0) {
                  for (ExeProcessWork ws : wsList) {
                      quantityPostAvailable = quantityPostAvailable + ws.getQuantityFinish().doubleValue();
                  }
              }
              if (quantityPostAvailable<quantityPostShould) {
                  String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的完工数量不足";
                  throw new NumberNotEnoughException(msg);
              }
          }

          // 取移转记录表，更新移转记录
          for (int i = 0; i < ids.length; i++) {
              //取在制批次
              ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);

              //取移转记录
              ExeProcessMoves param = new ExeProcessMoves();
              param.setMlId(ml.getId());
              param.setProcessSeq(ml.getCurrentProcessSeq());
              ExeProcessMoves move =moveMapper.selectExeProcessMovesList(param).get(0);

              double quantityPost = move.getQuantityReceive().doubleValue() - move.getQuantitySplit().doubleValue()
                      - move.getQuantityScrap().doubleValue() - move.getQuantityRedisp().doubleValue() - move.getQuantityPending().doubleValue();

              //修改接收信息
              move.setStatus(7);//状态设为“在转”
              move.setQuantityPost(new BigDecimal(quantityPost).setScale(4, BigDecimal.ROUND_HALF_UP));
              move.setPostBy(SecurityUtils.getUserId());
              move.setPostTime(DateUtils.getNowDate());
              //保存
              move.refreshUpdatingStamp();
              moveMapper.updateExeProcessMoves(move);

              //取下工序
              ExeProcessMoves nextMove = this.getNextProcessSeq(move);
              //若存在下工序，修改批次的工序状态、当前工序
              if (ml.getCurrentProcessSeq() < nextMove.getProcessSeq())
              {
                  ml.setCurrentProcessSeq(nextMove.getProcessSeq());
                  ml.setCurrentProcessId(nextMove.getProcessId());
                  ml.setLineId(nextMove.getLineId());
                  ml.setCurrentWorkgroupId(null);
                  ml.setProcessProgress(0);//工序状态设为“待收”
              }
              //若已为尾工序
              else if (ml.getCurrentProcessSeq() == nextMove.getProcessSeq()){
                  double quantityOutput = ml.getQuantityAccepted().doubleValue() - ml.getQuantitySplit().doubleValue()
                          - ml.getQuantityScrap().doubleValue() - ml.getQuantityRedisp().doubleValue() - ml.getQuantityPending().doubleValue();
                  ml.setQuantityOutput(new BigDecimal(quantityOutput));
                  ml.setStatus(BillStatus.ACTIVE_PROCESSED);//批次状态为“完制”
                  ml.setFinishBy(SecurityUtils.getUserId());
                  ml.setFinishTime(DateUtils.getNowDate());
                  ml.setProcessProgress(7);//工序状态设为“在转”
              }
              //保存批次的批次状态和当前工序
              ml.refreshUpdatingStamp();
              mlMapper.updateExeMlOperating(ml);
          }

          return 1;
      }
    /**
     * 转出
     *
     * @param ids id集合
     * @return 结果
     */
    public int requestStockin(Long[] ids) throws Exception{
        // 判异，排除状态的錯配
        for (int i = 0; i < ids.length; i++) {
            //取在制批次
            ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);
            //判断制造批次状态合法否
            if (ml.getStatus().intValue() != BillStatus.ACTIVE_PROCESSED) {
                String msg = "制造批[" + ml.getBillNo() + "]的状态不是[9-完制]";
                throw new StatusUnavailableException(msg);
            }
        }

        // 取移转记录表，更新移转记录
        for (int i = 0; i < ids.length; i++) {
            //更新批次的入库请求状态与数据
            ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);
            ml.setStatus(BillStatus.ACTIVE_STOCK_ACTION);//请入库
            ml.setStockreqBy(SecurityUtils.getUserId());
            ml.setStockreqTime(DateUtils.getNowDate());
            ml.refreshUpdatingStamp();
            mlMapper.updateExeMlOperating(ml);

            // 取得并更新单据规则
            String billNo = billcodeManager.getNextBillno(BillType.INVENTORY_REQUEST);
            billcodeManager.updateBillSeq(BillType.INVENTORY_REQUEST);

            //建产成品入库请求头
            SncStockRequest request= new SncStockRequest();
            //填写关键信息
            request.setBillNo(billNo);
            request.setStatus(BillStatus.ACTIVE_STOCK_ACTION);//设为“待入库”
            request.setIoType(InventoryTransactionType.STOCKIN_PRODUCTING);
            request.setRequestBillId(ml.getId());
            request.setRequestBillNo(ml.getBillNo());
            //补充来源信息
            request.setSourceBillId(ml.getId());
            request.setSourceBillNo(ml.getBillNo());
            request.setSourceCustomerId(null);
            request.setSourceLineId(SecurityUtils.getLineId());
            request.setSourceSupplierId(null);
            request.setProjectId(null);
            request.setRequestorId(SecurityUtils.getUserId());
            request.setRequestTime(DateUtils.getNowDate());
            //保存请求
            request.refreshCreatingStamp();
            stockreqMapper.insertSncStockRequest(request);

            //建产成品入库请求明细（仅1条）
            EngMaterial material = productMapper.selectEngMaterialById(ml.getProductId());
            SncStockRequestItem requestItem = new SncStockRequestItem();
            requestItem.setSeqNo(1);
            requestItem.setRequestId(request.getId());
            requestItem.setMaterialId(material.getId());
            requestItem.setMaterialCode(material.getMaterialCode());
            requestItem.setMaterialName(material.getMaterialName());
            requestItem.setMaterialSpec(material.getMaterialSpec());
            requestItem.setDrawNo(material.getDrawNo());
            requestItem.setCategoryId(material.getCategoryId());
            requestItem.setCompoundId(material.getCompoundId());
            requestItem.setCustomerProductNo(material.getCustomerProductNo());
            requestItem.setAbroadProductNo(material.getAbroadProductNo());
            requestItem.setUnitId(material.getUnitId());
            requestItem.setQuantity(ml.getQuantityOutput());
            requestItem.setPrice(material.getPriceLast());
            requestItem.setAmount(new BigDecimal(ml.getQuantityOutput().doubleValue()*material.getPriceLast().doubleValue()).setScale(2,BigDecimal.ROUND_UP));
            requestItem.setHouseId(material.getHouseId());
            requestItem.setPositionId(material.getPositionId());
            requestItem.setSourceId(ml.getId());
            requestItem.setSourceLotNo(ml.getBillNo());
            //创建戳
            requestItem.refreshCreatingStamp();
            requestItemMapper.insertSncStockRequestItem(requestItem);
        }

        return 1;
    }
    /**
     * 批次结案
     *
     * @param ids id集合
     * @return 结果
     */
    public int finishMl(Long[] ids) throws Exception{
        // 判异，排除状态的錯配
        for (int i = 0; i < ids.length; i++) {
            //取在制批次
            ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);
            //判断制造批次状态合法否
            if (ml.getStatus().intValue() != BillStatus.FINISH_WAITING) {
                String msg = "制造批[" + ml.getBillNo() + "]的状态不是[16-待结案]";
                throw new StatusUnavailableException(msg);
            }
        }

        // 取移转记录表，更新移转记录
        for (int i = 0; i < ids.length; i++) {
            //结案制造批
            ExeMlOperating ml=mlMapper.selectExeMlOperatingById(ids[i]);
            ml.setStatus(BillStatus.FINISH);//结案
            ml.refreshUpdatingStamp();
            mlMapper.updateExeMlOperating(ml);

            //尝试结案工单
            PlnWo wo = woMapper.selectPlnWoById(ml.getWoId());
            if (wo.getQuantityPlanned().doubleValue()==wo.getQuantityAssigned().doubleValue()){
                boolean finishedAllMl=true;
                List<ExeMlOperating> mlList = mlMapper.selectExeMlOperatingListByWoId(wo.getId());
                for (ExeMlOperating item:mlList){finishedAllMl = finishedAllMl && item.getStatus()==BillStatus.FINISH;}

                if (finishedAllMl) {
                    wo.setStatus(BillStatus.FINISH);
                    wo.refreshUpdatingStamp();
                    woMapper.updatePlnWo(wo);
                }
            }

            //尝试结案MPS
            DelMps mps = mpsMapper.selectDelMpsById(wo.getMpsId());
            if (mps.getQuantityPlanned().doubleValue()==mps.getQuantityFinished().doubleValue()){
                boolean finishedAllWo = true;
                List<PlnWo> woList = woMapper.selectPlnWoListByMpsId(mps.getId());
                for (PlnWo item:woList){finishedAllWo = finishedAllWo && item.getStatus()==BillStatus.FINISH;}

                if (finishedAllWo){
                    mps.setStatus(BillStatus.FINISH);
                    mps.refreshUpdatingStamp();
                    mpsMapper.updateDelMps(mps);
                }
            }
        }

        return 1;
    }
    /**
     * 开工
     *
     * @param exeMlOperating 开工
     * @return 结果
     */
    public int startWork(ExeMlOperating exeMlOperating) throws Exception{
        // 取UI的form变量
        Object objQuantityStart = exeMlOperating.getParams().get("quantityStart");
        Object objMachineId = exeMlOperating.getParams().get("machineId");
        Object objMoldId = exeMlOperating.getParams().get("moldId");
        Object objToolId = exeMlOperating.getParams().get("toolId");
        Object objGaugeId = exeMlOperating.getParams().get("gaugeId");
        Object objWorkDay = exeMlOperating.getParams().get("workDay");
        Object objShiftId = exeMlOperating.getParams().get("shiftId");

        double quantityStart = objQuantityStart==null?0:InputUtil.extractDouble(objQuantityStart);
        Long machineId = objMachineId==null?null:InputUtil.extractLong(objMachineId);
//        Long moldId = objMoldId==null?null:InputUtil.extractLong(objMoldId);
//        Long toolId = objToolId==null?null:InputUtil.extractLong(objToolId);
//        Long gaugeId = objGaugeId==null?null:InputUtil.extractLong(objGaugeId);
        String workDay = (String) objWorkDay;
        String shiftString = (String) objShiftId;
        Long shiftId = shiftString==null?null:Long.parseLong(shiftString);

        // 判异，排除制造批的状态错配
        ExeMlOperating ml= mlMapper.selectExeMlOperatingById(exeMlOperating.getId());
        if (ml.getStatus().intValue()!=BillStatus.ACTIVE_PROCESSING) {
            String msg = "制造批[" + ml.getBillNo() + "]的批次状态不是[8-在制]";
            throw new StatusUnavailableException(msg);
        }
        if (ml.getProcessProgress().intValue()!=4 && ml.getProcessProgress().intValue()!=6) {
            String msg = "制造批[" + ml.getBillNo() + "]的当前工序状态不能开工！";
            throw new StatusUnavailableException(msg);
        }
        //判异，排出工序的状态错配
        ExeProcessMoves param = new ExeProcessMoves();
        param.setMlId(ml.getId());
        param.setProcessSeq(ml.getCurrentProcessSeq());
        List<ExeProcessMoves> moves = moveMapper.selectExeProcessMovesList(param);
        //判断移转记录存在否
        if (moves == null || moves.size()== 0) {
            String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的记录不存在";
            throw new RecordNotExistException(msg);
        }
        ExeProcessMoves currentMove = moves.get(0);
        if (currentMove.getStatus().intValue()!=4 && currentMove.getStatus().intValue()!=6) {
            String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的移转状态不能开工！";
            throw new StatusUnavailableException(msg);
        }
        //判异机台可用性
        ResMachine machine = null;
        if (machineId!=null){
            machine = machineMapper.selectResMachineById(machineId);
            if (machine!=null && machine.getIsBusy().equals("1")){
                String msg = "机台[" + machine.getMachineName() + "]为在制状态，不能开工！";
                throw new StatusUnavailableException(msg);
            }
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("WS");
        billcodeManager.updateBillSeq("WS");

        //创建制造批，并设置特殊值
        ExeProcessWork ws = new ExeProcessWork();
        ws.setBillNo(billNo);

        ws.setMoveId(currentMove.getId());
        ws.setQuantityStart(new BigDecimal(quantityStart).setScale(2, BigDecimal.ROUND_HALF_UP));
        ws.setMachineId(machineId);
        ws.setMoldId(null);
        ws.setToolId(null);
        ws.setGaugeId(null);
        ws.setWorkDay(workDay==null?DateUtils.getNowDate():DateUtils.parseDate(workDay));
        ws.setWorkShifitId(shiftId);
        //复制字段值
        ws.setMlId(exeMlOperating.getId());
        ws.setProcessSeq(currentMove.getProcessSeq());
        ws.setProcessId(currentMove.getProcessId());
        ws.setProductId(currentMove.getProductId());
        ws.setProductCode(exeMlOperating.getProductCode());
        ws.setLineId(currentMove.getLineId());
        ws.setGroupId(exeMlOperating.getCurrentWorkgroupId());
        ws.setUnitId(currentMove.getUnitId());
        //开工戳
        ws.setDeptId(SecurityUtils.getDeptId());
        ws.setStartBy(SecurityUtils.getUserId());
        ws.setStartTime(DateUtils.getNowDate());
        //记录戳
        ws.refreshCreatingStamp();
        wsMapper.insertExeProcessWork(ws);

        //刷新machine状态
        if (machine!=null){
            machine.setIsBusy("1");
            machine.setWsId(ws.getId());
            machine.setWsNo(ws.getBillNo());
            machine.setMlId(ml.getId());
            machine.setMlNo(ml.getBillNo());
            machine.setProcessSeq(currentMove.getProcessSeq());
            machine.setProcessId(currentMove.getProcessId());
            machine.setIoTime(DateUtils.getNowDate());
            machine.refreshUpdatingStamp();
            machineMapper.updateResMachine(machine);
        }

        // 修改当前移转记录的状态
        currentMove.setStatus(5);//状态设为“待完工”
        currentMove.refreshUpdatingStamp();
        moveMapper.updateExeProcessMoves(currentMove);
        // 修改制造批的工序状态
        ml.setProcessProgress(5);//制造批的工序状态设为“待完工”
        ml.refreshUpdatingStamp();
        mlMapper.updateExeMlOperating(ml);

        return 1;
    }
    /**
     * 完工
     *
     * @param exeMlOperating 完工
     * @return 结果
     */
    public int finishWork(ExeMlOperating exeMlOperating) throws Exception{
        // 判异，排除制造批的状态错配
        ExeMlOperating ml= mlMapper.selectExeMlOperatingById(exeMlOperating.getId());
        if (ml.getStatus().intValue() != 8) {
            String msg = "制造批[" + ml.getBillNo() + "]的批次状态不是[8-在制]";
            throw new StatusUnavailableException(msg);
        }
        if (ml.getProcessProgress().intValue()!=5) {
            String msg = "制造批[" + ml.getBillNo() + "]的工序状态不是[6-待完工]！";
            throw new StatusUnavailableException(msg);
        }
        //判异，排出工序的状态错配
        ExeProcessMoves param = new ExeProcessMoves();
        param.setMlId(ml.getId());
        param.setProcessSeq(ml.getCurrentProcessSeq());
        List<ExeProcessMoves> moves = moveMapper.selectExeProcessMovesList(param);
        //判断移转记录存在否
        if (moves == null || moves.size()== 0) {
            String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的记录不存在";
            throw new RecordNotExistException(msg);
        }
        ExeProcessMoves currentMove = moves.get(0);
        if (currentMove.getStatus().intValue()!=5) {
            String msg = "在制批次[" + ml.getBillNo() + "]工序号为["+ml.getCurrentProcessSeq()+"]的工序状态不是[6-待完工]！";
            throw new StatusUnavailableException(msg);
        }

        // 取UI的form变量，从而取出工单
        Long wsId = null;
        Object obj = exeMlOperating.getParams().get("wsId");
        if (obj.getClass().equals(Long.class)){
            wsId = (Long)obj;
        }else if (obj.getClass().equals(Integer.class)){
            wsId =  ((Integer)obj).longValue();
        }
        ExeProcessWork ws = wsMapper.selectExeProcessWorkById(wsId);

        //修改出工单记录，并保存
        double quantityFinish=ws.getQuantityStart().doubleValue() - ws.getQuantitySplit().doubleValue()
                - ws.getQuantityScrap().doubleValue() - ws.getQuantityRedisp().doubleValue() - ws.getQuantityPending().doubleValue();
        ws.setQuantityFinish(new BigDecimal(quantityFinish).setScale(2, BigDecimal.ROUND_HALF_UP));
        ws.setStatus(BillStatus.FINISH);
        ws.setFinishBy(SecurityUtils.getUserId());
        ws.setFinishTime(DateUtils.getNowDate());
        //保存
        ws.refreshUpdatingStamp();
        wsMapper.updateExeProcessWork(ws);

        //刷新machine状态
        ResMachine machine = machineMapper.selectResMachineById(ws.getMachineId());
        if (machine!=null){
            machine.setIsBusy("0");
            machine.setWsId(null);
            machine.setWsNo(null);
            machine.setMlId(null);
            machine.setMlNo(null);
            machine.setProcessSeq(null);
            machine.setProcessId(null);
            machine.setIoTime(null);
            machine.refreshUpdatingStamp();
            machineMapper.updateResMachine(machine);
        }

        // 判断是否存在其他未完的出工单
        List<ExeProcessWork> wsList = wsMapper.selectExeProcessWorkListByMoveId(currentMove.getId());
        boolean hasOtherWorkingSegment = false;
        for(ExeProcessWork w:wsList)
        {
            if(w.getId()!=ws.getId() && w.getFinishTime()==null)
            {
                hasOtherWorkingSegment=true;
                break;
            }
        }
        //若不存在其他未完的出工单，则修改移转记录和批次当前状态
        if(!hasOtherWorkingSegment)
        {
            //修改保存移转记录的状态
            currentMove.setStatus(6);//状态设为“完工”
            currentMove.refreshUpdatingStamp();
            moveMapper.updateExeProcessMoves(currentMove);

            //修改保存制造批的工序状态
            ml.setProcessProgress(6);//制造批的工序状态设为“完工”
            mlMapper.updateExeMlOperating(ml);
        }

        return 1;
    }

    private ExeProcessMoves getNextProcessSeq(ExeProcessMoves currentMove)
    {
        ExeProcessMoves result = currentMove;

        List<ExeProcessMoves> moves =moveMapper.selectExeProcessMovesListByMlId(currentMove.getMlId());
        for(ExeProcessMoves move:moves)
        {
            if(move.getProcessSeq()>currentMove.getProcessSeq() && result.getProcessSeq() == currentMove.getProcessSeq())
            {
                result = move;
            }
            else if(move.getProcessSeq()>currentMove.getProcessSeq() && result.getProcessSeq()>move.getProcessSeq())
            {
                result = move;
            }
        }

        return result;
    }

    private ExeProcessMoves getLastProcessSeq(ExeProcessMoves currentMove)
    {
        ExeProcessMoves result = currentMove;

        List<ExeProcessMoves> moves =moveMapper.selectExeProcessMovesListByMlId(currentMove.getMlId());
        for(ExeProcessMoves move:moves)
        {
            if(move.getProcessSeq()<currentMove.getProcessSeq() && result.getProcessSeq() == currentMove.getProcessSeq())
            {
                result = move;
            }
            else if(move.getProcessSeq()<currentMove.getProcessSeq() && result.getProcessSeq()<move.getProcessSeq())
            {
                result = move;
            }
        }

        return result;
    }

}
