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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDealResult;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionDealResultService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量异常判处结果Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/qua/exceptionDealResult")
public class QuaExceptionDealResultController extends BaseController
{
    @Autowired
    private IQuaExceptionDealResultService quaExceptionDealResultService;

    /**
     * 查询质量异常判处结果列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaExceptionDealResult quaExceptionDealResult)
    {
        startPage();
        List<QuaExceptionDealResult> list = quaExceptionDealResultService.selectQuaExceptionDealResultList(quaExceptionDealResult);
        return getDataTable(list);
    }

    /**
     * 导出质量异常判处结果列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:export')")
    @Log(title = "质量异常判处结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaExceptionDealResult quaExceptionDealResult)
    {
        List<QuaExceptionDealResult> list = quaExceptionDealResultService.selectQuaExceptionDealResultList(quaExceptionDealResult);
        ExcelUtil<QuaExceptionDealResult> util = new ExcelUtil<QuaExceptionDealResult>(QuaExceptionDealResult.class);
        util.exportExcel(response, list, "质量异常判处结果数据");
    }

    /**
     * 获取质量异常判处结果详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaExceptionDealResultService.selectQuaExceptionDealResultById(id));
    }

    /**
     * 新增质量异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:add')")
    @Log(title = "质量异常判处结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaExceptionDealResult quaExceptionDealResult)
    {
        AjaxResult ar = null;
        try{
           int ret = quaExceptionDealResultService.insertQuaExceptionDealResult(quaExceptionDealResult);
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
     * 修改质量异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:edit')")
    @Log(title = "质量异常判处结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaExceptionDealResult quaExceptionDealResult)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionDealResultService.updateQuaExceptionDealResult(quaExceptionDealResult);
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
     * 删除质量异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('qua:exceptionDealResult:remove')")
    @Log(title = "质量异常判处结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaExceptionDealResultService.deleteQuaExceptionDealResultByIds(ids);
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


}
