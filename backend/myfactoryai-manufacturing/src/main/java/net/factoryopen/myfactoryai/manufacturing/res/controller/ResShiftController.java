package net.factoryopen.myfactoryai.manufacturing.res.controller;

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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShift;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResShiftService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班别Controller
 *
 * @author jitfactory-generator
 * @date 2024-12-28
 */
@RestController
@RequestMapping("/res/shift")
public class ResShiftController extends BaseController
{
    @Autowired
    private IResShiftService resShiftService;

    /**
     * 查询班别列表
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResShift resShift)
    {
        if(resShift!=null && resShift.getPageEnable()!=null && resShift.getPageEnable()) startPage();
        List<ResShift> list = resShiftService.selectResShiftList(resShift);
        return getDataTable(list);
    }

    /**
     * 导出班别列表
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:export')")
    @Log(title = "班别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResShift resShift)
    {
        List<ResShift> list = resShiftService.selectResShiftList(resShift);
        ExcelUtil<ResShift> util = new ExcelUtil<ResShift>(ResShift.class);
        util.exportExcel(response, list, "班别数据");
    }

    /**
     * 获取班别详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resShiftService.selectResShiftById(id));
    }

    /**
     * 新增班别
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:add')")
    @Log(title = "班别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResShift resShift)
    {
        AjaxResult ar = null;
        try{
           int ret = resShiftService.insertResShift(resShift);
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
     * 修改班别
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:edit')")
    @Log(title = "班别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResShift resShift)
    {
        AjaxResult ar = null;
        try{
            int ret = resShiftService.updateResShift(resShift);
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
     * 删除班别
     */
    //@PreAuthorize("@ss.hasPermi('res:shift:remove')")
    @Log(title = "班别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resShiftService.deleteResShiftByIds(ids);
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
