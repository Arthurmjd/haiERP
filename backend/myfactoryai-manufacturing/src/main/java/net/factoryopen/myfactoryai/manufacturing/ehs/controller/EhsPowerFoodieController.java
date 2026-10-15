package net.factoryopen.myfactoryai.manufacturing.ehs.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerFoodieService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电耗设备Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/powerFoodie")
public class EhsPowerFoodieController extends BaseController
{
    @Autowired
    private IEhsPowerFoodieService ehsPowerFoodieService;

    /**
     * 查询电耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPowerFoodie ehsPowerFoodie)
    {
        startPage();
        List<EhsPowerFoodie> list = ehsPowerFoodieService.selectEhsPowerFoodieList(ehsPowerFoodie);
        return getDataTable(list);
    }

    /**
     * 导出电耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:export')")
    @Log(title = "电耗设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPowerFoodie ehsPowerFoodie)
    {
        List<EhsPowerFoodie> list = ehsPowerFoodieService.selectEhsPowerFoodieList(ehsPowerFoodie);
        ExcelUtil<EhsPowerFoodie> util = new ExcelUtil<EhsPowerFoodie>(EhsPowerFoodie.class);
        util.exportExcel(response, list, "电耗设备数据");
    }

    /**
     * 获取电耗设备详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPowerFoodieService.selectEhsPowerFoodieById(id));
    }

    /**
     * 新增电耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:add')")
    @Log(title = "电耗设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPowerFoodie ehsPowerFoodie)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPowerFoodieService.insertEhsPowerFoodie(ehsPowerFoodie);
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
     * 修改电耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:edit')")
    @Log(title = "电耗设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPowerFoodie ehsPowerFoodie)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerFoodieService.updateEhsPowerFoodie(ehsPowerFoodie);
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
     * 删除电耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerFoodie:remove')")
    @Log(title = "电耗设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerFoodieService.deleteEhsPowerFoodieByIds(ids);
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
