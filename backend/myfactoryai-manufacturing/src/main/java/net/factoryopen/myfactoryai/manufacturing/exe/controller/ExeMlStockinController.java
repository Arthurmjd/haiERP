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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlStockin;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlStockinService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产成品入库Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/exe/mlStockin")
public class ExeMlStockinController extends BaseController
{
    @Autowired
    private IExeMlStockinService exeMlStockinService;

    /**
     * 查询产成品入库列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeMlStockin exeMlStockin)
    {
        startPage();
        List<ExeMlStockin> list = exeMlStockinService.selectExeMlStockinList(exeMlStockin);
        return getDataTable(list);
    }

    /**
     * 导出产成品入库列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:export')")
    @Log(title = "产成品入库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeMlStockin exeMlStockin)
    {
        List<ExeMlStockin> list = exeMlStockinService.selectExeMlStockinList(exeMlStockin);
        ExcelUtil<ExeMlStockin> util = new ExcelUtil<ExeMlStockin>(ExeMlStockin.class);
        util.exportExcel(response, list, "产成品入库数据");
    }

    /**
     * 获取产成品入库详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeMlStockinService.selectExeMlStockinById(id));
    }

    /**
     * 新增产成品入库
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:add')")
    @Log(title = "产成品入库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeMlStockin exeMlStockin)
    {
        AjaxResult ar = null;
        try{
           int ret = exeMlStockinService.insertExeMlStockin(exeMlStockin);
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
     * 修改产成品入库
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:edit')")
    @Log(title = "产成品入库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeMlStockin exeMlStockin)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlStockinService.updateExeMlStockin(exeMlStockin);
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
     * 删除产成品入库
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:remove')")
    @Log(title = "产成品入库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeMlStockinService.deleteExeMlStockinByIds(ids);
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
     *  ids 入库 主键
     */
    //@PreAuthorize("@ss.hasPermi('exe:mlStockin:productStockin')")
    @Log(title = "入库", businessType = BusinessType.OTHER)
    @PutMapping("/productStockin/{ids}")
    public AjaxResult productStockin(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                exeMlStockinService.productStockin(ids);
            ar = AjaxResult.success("操作成功：[入库]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
