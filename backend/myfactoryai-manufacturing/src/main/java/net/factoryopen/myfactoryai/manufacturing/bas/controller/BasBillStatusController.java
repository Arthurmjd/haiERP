package net.factoryopen.myfactoryai.manufacturing.bas.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasBillStatus;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasBillStatusService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单据状态Controller
 *
 * @author admin
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/bas/billStatus")
public class BasBillStatusController extends BaseController
{
    @Autowired
    private IBasBillStatusService basBillStatusService;

/**
 * 查询单据状态列表
 */
//@PreAuthorize("@ss.hasPermi('bas:billStatus:list')")
@GetMapping("/list")
    public TableDataInfo list(BasBillStatus basBillStatus)
    {
        //startPage();
        List<BasBillStatus> list = basBillStatusService.selectBasBillStatusList(basBillStatus);
        return getDataTable(list);
    }

    /**
     * 导出单据状态列表
     */
//    @PreAuthorize("@ss.hasPermi('bas:billStatus:export')")
    @Log(title = "单据状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasBillStatus basBillStatus)
    {
        List<BasBillStatus> list = basBillStatusService.selectBasBillStatusList(basBillStatus);
        ExcelUtil<BasBillStatus> util = new ExcelUtil<BasBillStatus>(BasBillStatus.class);
        util.exportExcel(response, list, "单据状态数据");
    }

    /**
     * 获取单据状态详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bas:billStatus:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basBillStatusService.selectBasBillStatusById(id));
    }

    /**
     * 新增单据状态
     */
//    @PreAuthorize("@ss.hasPermi('bas:billStatus:add')")
    @Log(title = "单据状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasBillStatus basBillStatus)
    {
        return toAjax(basBillStatusService.insertBasBillStatus(basBillStatus));
    }

    /**
     * 修改单据状态
     */
//    @PreAuthorize("@ss.hasPermi('bas:billStatus:edit')")
    @Log(title = "单据状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasBillStatus basBillStatus) throws Exception
    {
        return toAjax(basBillStatusService.updateBasBillStatus(basBillStatus));
    }

    /**
     * 删除单据状态
     */
//    @PreAuthorize("@ss.hasPermi('bas:billStatus:remove')")
    @Log(title = "单据状态", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(basBillStatusService.deleteBasBillStatusByIds(ids));
    }
}
