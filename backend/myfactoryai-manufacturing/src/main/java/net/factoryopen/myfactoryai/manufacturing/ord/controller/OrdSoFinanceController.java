package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoFinance;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoFinanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/ord/soFinance")
public class OrdSoFinanceController extends BaseController
{
    @Autowired
    private IOrdSoFinanceService soFinanceService;

    /**
     * 查询订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soFinance:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSoFinance soFinance)
    {
        startPage();
        List<OrdSoFinance> list = soFinanceService.selectOrdSoFinanceList(soFinance);
        return getDataTable(list);
    }

    /**
     * 查询订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soFinance:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(OrdSoFinance soFinance)
    {
        startPage();
        List<OrdSoFinance> list = soFinanceService.selectOrdSoFinanceListAll(soFinance);
        return getDataTable(list);
    }

    /**
     * 导出订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soFinance:export')")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSoFinance soFinance)
    {
        List<OrdSoFinance> list = soFinanceService.selectOrdSoFinanceList(soFinance);
        ExcelUtil<OrdSoFinance> util = new ExcelUtil<OrdSoFinance>(OrdSoFinance.class);
        util.exportExcel(response, list, "订单明细数据");
    }

    /**
     * 获取订单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:soFinance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(soFinanceService.selectOrdSoFinanceById(id));
    }
    
    /**
     * 登记开票
     */
    //@PreAuthorize("@ss.hasPermi('ord:soFinance:registerInvoice')")
    @Log(title = "开票登记", businessType = BusinessType.OTHER)
    @PutMapping("/registerInvoice")
    public AjaxResult registerInvoice(@RequestBody OrdSoFinance soFinance)
    {
        AjaxResult ar = null;
        try{
            soFinanceService.registerInvoice(soFinance);
            ar = AjaxResult.success("操作成功：[开票登记]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
