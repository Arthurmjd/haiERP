package net.factoryopen.myfactoryai.manufacturing.tool.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.manufacturing.tool.domain.BillIdName;
import net.factoryopen.myfactoryai.manufacturing.tool.util.BillManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 订单编号生成
 */
@RestController
@RequestMapping("/tool/billManager")
public class BillManagerController {
    @Autowired
    private BillManager billManager;

    @GetMapping("/{billId}")
    public String getNextBillno(@PathVariable("billId") long billId) {
        String billNo = billManager.getNextBillno(billId);
        return billNo;
    }

    @PutMapping("/{billId}")
    public void updateBillSeq(@PathVariable("billId") long billId) {
        billManager.updateBillSeq(billId);
    }

    @GetMapping("/getBillIdNameList/")
    public AjaxResult getBillIdNameList() {
        List<BillIdName> billIdNameList = billManager.getBillIdNameList();
        return AjaxResult.success(billIdNameList);
    }
}