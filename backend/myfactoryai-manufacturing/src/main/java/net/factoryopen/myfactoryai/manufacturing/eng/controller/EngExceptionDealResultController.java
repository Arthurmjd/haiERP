package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionDealResult;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionDealResultService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺异常判处结果Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/eng/exceptionDealResult")
public class EngExceptionDealResultController extends BaseController
{
    @Autowired
    private IEngExceptionDealResultService engExceptionDealResultService;

    /**
     * 查询工艺异常判处结果列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngExceptionDealResult engExceptionDealResult)
    {
        startPage();
        List<EngExceptionDealResult> list = engExceptionDealResultService.selectEngExceptionDealResultList(engExceptionDealResult);
        return getDataTable(list);
    }

    /**
     * 导出工艺异常判处结果列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:export')")
    @Log(title = "工艺异常判处结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngExceptionDealResult engExceptionDealResult)
    {
        List<EngExceptionDealResult> list = engExceptionDealResultService.selectEngExceptionDealResultList(engExceptionDealResult);
        ExcelUtil<EngExceptionDealResult> util = new ExcelUtil<EngExceptionDealResult>(EngExceptionDealResult.class);
        util.exportExcel(response, list, "工艺异常判处结果数据");
    }

    /**
     * 获取工艺异常判处结果详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engExceptionDealResultService.selectEngExceptionDealResultById(id));
    }

    /**
     * 新增工艺异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:add')")
    @Log(title = "工艺异常判处结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngExceptionDealResult engExceptionDealResult)
    {
        AjaxResult ar = null;
        try{
           int ret = engExceptionDealResultService.insertEngExceptionDealResult(engExceptionDealResult);
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
     * 修改工艺异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:edit')")
    @Log(title = "工艺异常判处结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngExceptionDealResult engExceptionDealResult)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionDealResultService.updateEngExceptionDealResult(engExceptionDealResult);
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
     * 删除工艺异常判处结果
     */
    //@PreAuthorize("@ss.hasPermi('eng:exceptionDealResult:remove')")
    @Log(title = "工艺异常判处结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engExceptionDealResultService.deleteEngExceptionDealResultByIds(ids);
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
