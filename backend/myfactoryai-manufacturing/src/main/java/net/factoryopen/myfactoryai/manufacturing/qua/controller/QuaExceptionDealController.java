package net.factoryopen.myfactoryai.manufacturing.qua.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionDealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量异常判处Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/qua/exceptionDeal")
public class QuaExceptionDealController extends BaseController
{
    @Autowired
    private IQuaExceptionDealService quaExceptionDealService;

    /**
     * 查询质量异常判处列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaExceptionDeal quaExceptionDeal)
    {
        startPage();
        List<QuaExceptionDeal> list = quaExceptionDealService.selectQuaExceptionDealList(quaExceptionDeal);
        return getDataTable(list);
    }

    /**
     * 导出质量异常判处列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:export')")
    @Log(title = "质量异常判处", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaExceptionDeal quaExceptionDeal)
    {
        List<QuaExceptionDeal> list = quaExceptionDealService.selectQuaExceptionDealList(quaExceptionDeal);
        ExcelUtil<QuaExceptionDeal> util = new ExcelUtil<QuaExceptionDeal>(QuaExceptionDeal.class);
        util.exportExcel(response, list, "质量异常判处数据");
    }

    /**
     * 获取质量异常判处详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaExceptionDealService.selectQuaExceptionDealById(id));
    }

    /**
     * 新增质量异常判处
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:add')")
    @Log(title = "质量异常判处", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaExceptionDeal quaExceptionDeal)
    {
        AjaxResult ar = null;
        try{
           int ret = quaExceptionDealService.insertQuaExceptionDeal(quaExceptionDeal);
           if (ret > 0){
               ar = AjaxResult.success("操作成功：[新增]1条数据！");
           }
           else{
               ar = AjaxResult.error(ret, "操作失败：[新增]0条数据！");
           }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 修改质量异常判处
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:edit')")
    @Log(title = "质量异常判处", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaExceptionDeal quaExceptionDeal)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionDealService.updateQuaExceptionDeal(quaExceptionDeal);
            if (ret > 0){
                ar = AjaxResult.success("操作成功：[修改]"+ret+"条数据！");
            }
            else{
                ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 删除质量异常判处
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:remove')")
    @Log(title = "质量异常判处", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionDealService.deleteQuaExceptionDealByIds(ids);
            if (ret > 0){
                ar = AjaxResult.success("操作成功：[删除]"+ret+"条数据！");
            }
            else{
                ar = AjaxResult.error(ret, "操作失败：[删除]0条数据！");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 判处
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDeal:judge')")
    @Log(title = "判处", businessType = BusinessType.OTHER)
    @PutMapping("/judge")
    public AjaxResult judge(@RequestBody QuaExceptionDeal quaExceptionDeal)
    {
        AjaxResult ar = null;
        try{
            quaExceptionDealService.judge(quaExceptionDeal);
            ar = AjaxResult.success("操作成功：[判处]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
