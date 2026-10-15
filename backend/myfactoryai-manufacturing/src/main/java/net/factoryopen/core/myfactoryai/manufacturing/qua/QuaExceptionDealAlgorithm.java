package net.factoryopen.core.myfactoryai.manufacturing.qua;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.InspectionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeWorkSegment;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeWorkSegmentMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionDealMapper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 质量异常判处ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class QuaExceptionDealAlgorithm
{
    @Autowired(required = false)
    private QuaExceptionDealMapper dealMapper;

    @Autowired(required = false)
    private ExeWorkSegmentMapper wsMapper;

    @Autowired(required = false)
    private ExeProcessMovesMapper moveMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soItemMapper;

    @Autowired
    private BillcodeManager billcodeManager;

    public int judge(QuaExceptionDeal quaExceptionDeal) throws Exception {
        quaExceptionDeal.refreshCreatingStamp();
        this.dealMapper.updateQuaExceptionDeal(quaExceptionDeal);

        switch (quaExceptionDeal.getInspectType().intValue()) {
            case InspectionType.CHECK_SELF:
                judgeCheckself(quaExceptionDeal);
                break;
            case InspectionType.CHECK_IN_PROCESS:
                judgeCheckInProcess(quaExceptionDeal);
                break;
        }

        return 1;
    }

    private int judgeCheckself(QuaExceptionDeal exception) throws Exception {
        ExeWorkSegment ws = wsMapper.selectExeWorkSegmentById(exception.getWsId());
        ExeProcessMoves move = moveMapper.selectExeProcessMovesById(ws.getMoveId());
        PlnMl ml = this.mlMapper.selectPlnMlById(exception.getMlId());
        PlnWo wo = this.woMapper.selectPlnWoById(ml.getWoId());

        //更新异常数
        switch (exception.getDealType().intValue()) {
            case 0:
            case 10:
                if(ws!=null) ws.setQuantityScrap(new BigDecimal(ws.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityScrap(new BigDecimal(move.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityScrap(new BigDecimal(ml.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityScrap(new BigDecimal(wo.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 20:
                if(ws!=null) ws.setQuantityRedisp(new BigDecimal(ws.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityRedisp(new BigDecimal(move.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityRedisp(new BigDecimal(ml.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityRedisp(new BigDecimal(wo.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 40:
                if(ws!=null) ws.setQuantityPending(new BigDecimal(ws.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityPending(new BigDecimal(move.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityPending(new BigDecimal(ml.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityPending(new BigDecimal(wo.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 30:
                if(ws!=null) ws.setQuantityRework(new BigDecimal(ws.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityRework(new BigDecimal(move.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityRework(new BigDecimal(ml.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityRework(new BigDecimal(wo.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 50:
                if(ws!=null) ws.setQuantityAod(new BigDecimal(ws.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityAod(new BigDecimal(move.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityAod(new BigDecimal(ml.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityAod(new BigDecimal(wo.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
        }

        //保存异常数
        if(ws!=null){
            ws.refreshUpdatingStamp();
            wsMapper.updateExeWorkSegment(ws);
        }
        if(ws!=null){
            move.refreshUpdatingStamp();
            moveMapper.updateExeProcessMoves(move);
        }
        if(ml!=null){
            ml.refreshUpdatingStamp();
            mlMapper.updatePlnMl(ml);
        }
        if(wo!=null) {
            wo.refreshUpdatingStamp();
            woMapper.updatePlnWo(wo);
        }

        //变更异常单状态
        exception.setStatus(BillStatus.FINISH);
        exception.setDealBy(SecurityUtils.getUsername());
        exception.setDealTime(DateUtils.getNowDate());
        exception.refreshUpdatingStamp();
        dealMapper.updateQuaExceptionDeal(exception);

        return 1;
    }

    private int judgeCheckInProcess(QuaExceptionDeal exception) throws Exception {
        ExeWorkSegment ws = wsMapper.selectExeWorkSegmentById(exception.getWsId());
        ExeProcessMoves move = moveMapper.selectExeProcessMovesById(ws.getMoveId());
        PlnMl ml = this.mlMapper.selectPlnMlById(exception.getMlId());
        PlnWo wo = this.woMapper.selectPlnWoById(ml.getWoId());

        //更新异常数
        switch (exception.getDealType().intValue()) {
            case 0:
            case 10:
                if(ws!=null) ws.setQuantityScrap(new BigDecimal(ws.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityScrap(new BigDecimal(move.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityScrap(new BigDecimal(ml.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityScrap(new BigDecimal(wo.getQuantityScrap().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 20:
                if(ws!=null) ws.setQuantityRedisp(new BigDecimal(ws.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityRedisp(new BigDecimal(move.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityRedisp(new BigDecimal(ml.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityRedisp(new BigDecimal(wo.getQuantityRedisp().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 40:
                if(ws!=null) ws.setQuantityPending(new BigDecimal(ws.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityPending(new BigDecimal(move.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityPending(new BigDecimal(ml.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityPending(new BigDecimal(wo.getQuantityPending().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 30:
                if(ws!=null) ws.setQuantityRework(new BigDecimal(ws.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityRework(new BigDecimal(move.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityRework(new BigDecimal(ml.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityRework(new BigDecimal(wo.getQuantityRework().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
            case 50:
                if(ws!=null) ws.setQuantityAod(new BigDecimal(ws.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(move!=null) move.setQuantityAod(new BigDecimal(move.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(ml!=null) ml.setQuantityAod(new BigDecimal(ml.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                if(wo!=null) wo.setQuantityAod(new BigDecimal(wo.getQuantityAod().doubleValue() + exception.getDealQuantity().doubleValue()));
                break;
        }

        //保存异常数
        if(ws!=null){
            ws.refreshUpdatingStamp();
            wsMapper.updateExeWorkSegment(ws);
        }
        if(ws!=null){
            move.refreshUpdatingStamp();
            moveMapper.updateExeProcessMoves(move);
        }
        if(ml!=null){
            ml.refreshUpdatingStamp();
            mlMapper.updatePlnMl(ml);
        }
        if(wo!=null) {
            wo.refreshUpdatingStamp();
            woMapper.updatePlnWo(wo);
        }

        //变更异常单状态
        exception.setStatus(BillStatus.FINISH);
        exception.setDealBy(SecurityUtils.getUsername());
        exception.setDealTime(DateUtils.getNowDate());
        exception.refreshUpdatingStamp();
        dealMapper.updateQuaExceptionDeal(exception);

        return 1;
    }

}
