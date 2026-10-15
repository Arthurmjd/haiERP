package net.factoryopen.myfactoryai.manufacturing.exe.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTrace;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlTraceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 在制批次跟踪Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/mlTrace")
public class ExeMlTraceController extends BaseController
{
    @Autowired
    private IExeMlTraceService exeMlTraceService;

    /**
     * 查询在制批次跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlTrace exeMlTrace)
    {
        startPage();
        List<ExeMlTrace> list = exeMlTraceService.selectExeMlTraceList(exeMlTrace);
        return getDataTable(list);
    }

    /**
     * 导出在制批次跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:export')")
    @Log(title = "在制批次跟踪", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlTrace exeMlTrace)
    {
        List<ExeMlTrace> list = exeMlTraceService.selectExeMlTraceList(exeMlTrace);
        ExcelUtil<ExeMlTrace> util = new ExcelUtil<ExeMlTrace>(ExeMlTrace.class);
        util.exportExcel(response, list, "在制批次跟踪数据");
    }

    /**
     * 获取在制批次跟踪详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlTraceService.selectExeMlTraceById(id));
    }

    /**
     * 新增在制批次跟踪
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:add')")
    @Log(title = "在制批次跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlTrace exeMlTrace)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlTraceService.insertExeMlTrace(exeMlTrace);
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
     * 修改在制批次跟踪
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:edit')")
    @Log(title = "在制批次跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlTrace exeMlTrace)
    {
        AjaxResult ar = null;
        try{
            if (exeMlTrace.getStatus()!= BillStatus.BASE_DRAFT
                    && exeMlTrace.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = exeMlTraceService.updateExeMlTrace(exeMlTrace);
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
     * 删除在制批次跟踪
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlTrace:remove')")
    @Log(title = "在制批次跟踪", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlTraceService.deleteExeMlTraceByIds(ids);
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
