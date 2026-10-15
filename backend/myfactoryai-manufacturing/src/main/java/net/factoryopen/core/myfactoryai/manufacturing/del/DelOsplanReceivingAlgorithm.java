package net.factoryopen.core.myfactoryai.manufacturing.del;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.InspectionType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanReceivingMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;

/**
 * 外协收货ServiceImpl业务逻辑实现
 * 
 * @author admin
 * @date 2024-06-28
 */
@Service
public class DelOsplanReceivingAlgorithm
{
    @Autowired(required = false)
    private DelOsplanReceivingMapper receivingMapper;

    @Autowired(required = false)
    private QuaInspectRequestMapper requestMapper;

    @Autowired
    private BillcodeManager billcodeManager;


    /**
     * 请检
     *
     * @param delOsplanReceiving 外协收货单
     * @return 结果
     */
    public int requestInspection(DelOsplanReceiving delOsplanReceiving) throws Exception{
        // 判异，排除状态错配的收货
        DelOsplanReceiving receiving = receivingMapper.selectDelOsplanReceivingById(delOsplanReceiving.getId());
        if (receiving.getStatus().intValue() != 4) {
            String msg = "外协收货单[" + receiving.getBillNo() + "]的状态不是[4-生效]";
            throw new StatusUnavailableException(msg);
        }

        // 取得并更新单据规则
        String billNo = billcodeManager.getNextBillno("QCR");
        billcodeManager.updateBillSeq("QCR");

        //创建外协收货单，并设置特殊值
        QuaInspectRequest request = new QuaInspectRequest();
        request.setBillNo(billNo);
        request.setStatus(BillStatus.BASE_ENABLE);//设为“已生效”
        request.setInspectType(InspectionType.RECEIVE_SUBCONTRACT); //外协收检
        request.setRequestBillId(receiving.getId());
        request.setRequestBillNo(receiving.getBillNo());
        request.setProjectId(receiving.getProjectId());
        request.setSourceSupplierId(receiving.getSupplierId());
        request.setRequestorId(SecurityUtils.getUserId());
        //保存外协收货单
        request.refreshCreatingStamp();
        requestMapper.insertQuaInspectRequest(request);

        // 更改原外协计划单的状态和派工量
        receiving.setStatus(BillStatus.ACTIVE_PROCESSING);//设为“在处理”
        receiving.refreshUpdatingStamp();
        receivingMapper.updateDelOsplanReceiving(receiving);

        return 1;
    }
}
