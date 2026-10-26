package net.factoryopen.myfactoryai.web.controller.erp;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.erp.domain.FinanceRequest;
import net.factoryopen.myfactoryai.erp.service.ErpFinanceService;
import net.factoryopen.myfactoryai.erp.service.ErpWorkflowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/finance")
public class ErpFinanceController {
    private final ErpFinanceService service;
    private final ErpWorkflowService workflow;

    public ErpFinanceController(ErpFinanceService service, ErpWorkflowService workflow) {
        this.service = service; this.workflow = workflow;
    }

    @PreAuthorize("@ss.hasPermi('fn:account:list')")
    @GetMapping("/accounts")
    public AjaxResult accounts() { return AjaxResult.success(service.accounts()); }

    @PreAuthorize("@ss.hasPermi('fn:account:add')")
    @PostMapping("/accounts")
    public AjaxResult createAccount(@RequestBody FinanceRequest.Account request) {
        return AjaxResult.success(service.saveAccount(request));
    }

    @PreAuthorize("@ss.hasPermi('fn:account:edit')")
    @PutMapping("/accounts/{id}")
    public AjaxResult updateAccount(@PathVariable Long id,@RequestBody FinanceRequest.Account request) {
        return AjaxResult.success(service.saveAccount(new FinanceRequest.Account(id, request.accountNo(),
                request.accountName(), request.accountType(), request.openingBalance(), request.status(), request.remark())));
    }

    @PreAuthorize("@ss.hasPermi('fn:cashTxn:list')")
    @GetMapping("/cash")
    public AjaxResult cash() { return AjaxResult.success(service.cashTxns()); }

    @PreAuthorize("@ss.hasPermi('fn:receivable:list')")
    @GetMapping("/receivables")
    public AjaxResult receivables() { return AjaxResult.success(service.receivables()); }

    @PreAuthorize("@ss.hasPermi('fn:payable:list')")
    @GetMapping("/payables")
    public AjaxResult payables() { return AjaxResult.success(service.payables()); }

    @PreAuthorize("@ss.hasPermi('fn:payment:list')")
    @GetMapping("/payments")
    public AjaxResult payments() { return AjaxResult.success(service.payments()); }

    @PreAuthorize("@ss.hasPermi('fn:payment:list')")
    @GetMapping("/payments/{id}")
    public AjaxResult payment(@PathVariable Long id) { return AjaxResult.success(service.payment(id)); }

    @PreAuthorize("@ss.hasPermi('fn:payment:add')")
    @PostMapping("/payments")
    public AjaxResult createPayment(@RequestBody FinanceRequest.Payment request) {
        return AjaxResult.success(service.createPayment(request));
    }

    @PreAuthorize("@ss.hasPermi('fn:payment:confirm')")
    @PostMapping("/payments/confirm/{id}")
    public AjaxResult confirmPayment(@PathVariable Long id,@RequestBody FinanceRequest.Action request) {
        service.confirmPayment(id, request == null ? null : request.requestKey());
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('fn:account:list')")
    @GetMapping("/overview")
    public AjaxResult overview() { return AjaxResult.success(service.overview()); }

    @PreAuthorize("@ss.hasPermi('fn:period:list')")
    @GetMapping("/periods")
    public AjaxResult periods() { return AjaxResult.success(workflow.periods()); }

    @PreAuthorize("@ss.hasPermi('fn:period:close')")
    @PostMapping("/period/close")
    public AjaxResult closePeriod(@RequestBody FinanceRequest.Period request) {
        workflow.closePeriod(request == null ? null : request.period());
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('fn:period:reopen')")
    @PostMapping("/period/reopen")
    public AjaxResult reopenPeriod(@RequestBody FinanceRequest.Period request) {
        workflow.reopenPeriod(request == null ? null : request.period());
        return AjaxResult.success();
    }
}
