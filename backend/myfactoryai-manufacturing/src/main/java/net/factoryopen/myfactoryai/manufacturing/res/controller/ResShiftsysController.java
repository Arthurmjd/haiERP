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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShiftsys;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResShiftsysService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班制管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-12-28
 */
@RestController
@RequestMapping("/res/shiftsys")
public class ResShiftsysController extends BaseController
{
    @Autowired
    private IResShiftsysService resShiftsysService;

    /**
     * 查询班制管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResShiftsys resShiftsys)
    {
        if(resShiftsys!=null && resShiftsys.getPageEnable()!=null && resShiftsys.getPageEnable()) startPage();
        List<ResShiftsys> list = resShiftsysService.selectResShiftsysList(resShiftsys);
        return getDataTable(list);
    }

    /**
     * 导出班制管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:export')")
    @Log(title = "班制管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResShiftsys resShiftsys)
    {
        List<ResShiftsys> list = resShiftsysService.selectResShiftsysList(resShiftsys);
        ExcelUtil<ResShiftsys> util = new ExcelUtil<ResShiftsys>(ResShiftsys.class);
        util.exportExcel(response, list, "班制管理数据");
    }

    /**
     * 获取班制管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resShiftsysService.selectResShiftsysById(id));
    }

    /**
     * 新增班制管理
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:add')")
    @Log(title = "班制管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResShiftsys resShiftsys)
    {
        AjaxResult ar = null;
        try{
           int ret = resShiftsysService.insertResShiftsys(resShiftsys);
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
     * 修改班制管理
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:edit')")
    @Log(title = "班制管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResShiftsys resShiftsys)
    {
        AjaxResult ar = null;
        try{
            int ret = resShiftsysService.updateResShiftsys(resShiftsys);
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
     * 删除班制管理
     */
    //@PreAuthorize("@ss.hasPermi('res:shiftsys:remove')")
    @Log(title = "班制管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resShiftsysService.deleteResShiftsysByIds(ids);
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
