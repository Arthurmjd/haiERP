package net.factoryopen.myfactoryai.web.controller.system;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.system.domain.SysBillcodeRule;
import net.factoryopen.myfactoryai.system.service.ISysBillcodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 单据规则Controller
 *
 * @author factoryOpen
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/system/billcodeRule")
public class SysBillcodeRuleController extends BaseController {
    @Autowired
    private ISysBillcodeRuleService sysBillcodeRuleService;

    /**
     * 查询单据规则列表
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBillcodeRule sysBillcodeRule) {
        startPage();
        List<SysBillcodeRule> list = sysBillcodeRuleService.selectSysBillcodeRuleList(sysBillcodeRule);
        return getDataTable(list);
    }

    /**
     * 导出单据规则列表
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:export')")
    @Log(title = "单据规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBillcodeRule sysBillcodeRule) {
        List<SysBillcodeRule> list = sysBillcodeRuleService.selectSysBillcodeRuleList(sysBillcodeRule);
        ExcelUtil<SysBillcodeRule> util = new ExcelUtil<SysBillcodeRule>(SysBillcodeRule.class);
        util.exportExcel(response, list, "单据规则数据");
    }

    /**
     * 获取单据规则详细信息
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysBillcodeRuleService.selectSysBillcodeRuleById(id));
    }

    /**
     * 新增单据规则
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:add')")
    @Log(title = "单据规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBillcodeRule sysBillcodeRule) {
        return toAjax(sysBillcodeRuleService.insertSysBillcodeRule(sysBillcodeRule));
    }

    /**
     * 修改单据规则
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:edit')")
    @Log(title = "单据规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBillcodeRule sysBillcodeRule) {
        return toAjax(sysBillcodeRuleService.updateSysBillcodeRule(sysBillcodeRule));
    }

    /**
     * 删除单据规则
     */
//    @PreAuthorize("@ss.hasPermi('sys:billcodeRule:remove')")
    @Log(title = "单据规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids) {
        return toAjax(sysBillcodeRuleService.deleteSysBillcodeRuleByIds(ids));
    }
}
