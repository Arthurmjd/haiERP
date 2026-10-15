package net.factoryopen.myfactoryai.manufacturing.exe.controller;

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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlSplit;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlSplitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制造批次分拆Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/mlSplit")
public class ExeMlSplitController extends BaseController
{
    @Autowired
    private IExeMlSplitService exeMlSplitService;

    /**
     * 查询制造批次分拆列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlSplit exeMlSplit)
    {
        startPage();
        List<ExeMlSplit> list = exeMlSplitService.selectExeMlSplitList(exeMlSplit);
        return getDataTable(list);
    }

    /**
     * 导出制造批次分拆列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:export')")
    @Log(title = "制造批次分拆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlSplit exeMlSplit)
    {
        List<ExeMlSplit> list = exeMlSplitService.selectExeMlSplitList(exeMlSplit);
        ExcelUtil<ExeMlSplit> util = new ExcelUtil<ExeMlSplit>(ExeMlSplit.class);
        util.exportExcel(response, list, "制造批次分拆数据");
    }

    /**
     * 获取制造批次分拆详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlSplitService.selectExeMlSplitById(id));
    }

    /**
     * 新增制造批次分拆
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:add')")
    @Log(title = "制造批次分拆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlSplit exeMlSplit)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlSplitService.insertExeMlSplit(exeMlSplit);
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
     * 修改制造批次分拆
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:edit')")
    @Log(title = "制造批次分拆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlSplit exeMlSplit)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlSplitService.updateExeMlSplit(exeMlSplit);
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
     * 删除制造批次分拆
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:remove')")
    @Log(title = "制造批次分拆", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlSplitService.deleteExeMlSplitByIds(ids);
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
     * 分批
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlSplit:splitMl')")
    @Log(title = "分批", businessType = BusinessType.OTHER)
    @PutMapping("/splitMl")
    public AjaxResult splitMl(@RequestBody ExeMlSplit exeMlSplit)
    {
        AjaxResult ar = null;
        try{
            exeMlSplitService.splitMl(exeMlSplit);
            ar = AjaxResult.success("操作成功：[分批]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
