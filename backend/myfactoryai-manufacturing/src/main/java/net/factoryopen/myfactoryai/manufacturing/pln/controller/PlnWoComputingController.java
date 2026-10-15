package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoComputingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.util.NestedServletException;

/**
 * 计划分解Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/woComputing")
public class PlnWoComputingController extends BaseController
{
    @Autowired
    private IPlnWoComputingService plnWoComputingService;

    /**
     * 查询计划分解列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoComputing plnWoComputing)
    {
        startPage();
        List<PlnWoComputing> list = plnWoComputingService.selectPlnWoComputingList(plnWoComputing);
        return getDataTable(list);
    }

    /**
     * 导出计划分解列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:export')")
    @Log(title = "计划分解", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoComputing plnWoComputing)
    {
        List<PlnWoComputing> list = plnWoComputingService.selectPlnWoComputingList(plnWoComputing);
        ExcelUtil<PlnWoComputing> util = new ExcelUtil<PlnWoComputing>(PlnWoComputing.class);
        util.exportExcel(response, list, "计划分解数据");
    }

    /**
     * 获取计划分解详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoComputingService.selectPlnWoComputingById(id));
    }

    /**
     * 新增计划分解
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:add')")
    @Log(title = "计划分解", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoComputing plnWoComputing)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoComputingService.insertPlnWoComputing(plnWoComputing);
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
     * 修改计划分解
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:edit')")
    @Log(title = "计划分解", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoComputing plnWoComputing)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoComputingService.updatePlnWoComputing(plnWoComputing);
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
     * 删除计划分解
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:remove')")
    @Log(title = "计划分解", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoComputingService.deletePlnWoComputingByIds(ids);
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
     *  ids 分解 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:woComputing:splitMps')")
    @Log(title = "分解", businessType = BusinessType.OTHER)
    @PutMapping("/splitMps/{ids}")
    public AjaxResult splitMps(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            plnWoComputingService.splitMps(ids);
            ar = AjaxResult.success("操作成功：[分解]"+"执行完成！");
        }catch(NestedServletException e){
            e.printStackTrace();
            ar = AjaxResult.error("计算异常：子件嵌套，分解没完没了！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
