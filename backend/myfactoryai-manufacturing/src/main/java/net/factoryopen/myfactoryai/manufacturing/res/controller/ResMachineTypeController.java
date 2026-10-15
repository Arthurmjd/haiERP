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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachineType;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResMachineTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机型Controller
 *
 * @author admin
 * @date 2024-08-11
 */
@RestController
@RequestMapping("/res/machineType")
public class ResMachineTypeController extends BaseController
{
    @Autowired
    private IResMachineTypeService resMachineTypeService;

    /**
     * 查询机型列表
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResMachineType resMachineType)
    {
        if(resMachineType!=null && resMachineType.getPageEnable()!=null && resMachineType.getPageEnable()) startPage();
        List<ResMachineType> list = resMachineTypeService.selectResMachineTypeList(resMachineType);
        return getDataTable(list);
    }

    /**
     * 导出机型列表
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:export')")
    @Log(title = "机型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResMachineType resMachineType)
    {
        List<ResMachineType> list = resMachineTypeService.selectResMachineTypeList(resMachineType);
        ExcelUtil<ResMachineType> util = new ExcelUtil<ResMachineType>(ResMachineType.class);
        util.exportExcel(response, list, "机型数据");
    }

    /**
     * 获取机型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resMachineTypeService.selectResMachineTypeById(id));
    }

    /**
     * 新增机型
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:add')")
    @Log(title = "机型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResMachineType resMachineType)
    {
        AjaxResult ar = null;
        try{
           int ret = resMachineTypeService.insertResMachineType(resMachineType);
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
     * 修改机型
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:edit')")
    @Log(title = "机型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResMachineType resMachineType)
    {
        AjaxResult ar = null;
        try{
            int ret = resMachineTypeService.updateResMachineType(resMachineType);
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
     * 删除机型
     */
    //@PreAuthorize("@ss.hasPermi('res:machineType:remove')")
    @Log(title = "机型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resMachineTypeService.deleteResMachineTypeByIds(ids);
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
