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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsWaterFoodieService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 水耗设备Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/waterFoodie")
public class EhsWaterFoodieController extends BaseController
{
    @Autowired
    private IEhsWaterFoodieService ehsWaterFoodieService;

    /**
     * 查询水耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsWaterFoodie ehsWaterFoodie)
    {
        startPage();
        List<EhsWaterFoodie> list = ehsWaterFoodieService.selectEhsWaterFoodieList(ehsWaterFoodie);
        return getDataTable(list);
    }

    /**
     * 导出水耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:export')")
    @Log(title = "水耗设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsWaterFoodie ehsWaterFoodie)
    {
        List<EhsWaterFoodie> list = ehsWaterFoodieService.selectEhsWaterFoodieList(ehsWaterFoodie);
        ExcelUtil<EhsWaterFoodie> util = new ExcelUtil<EhsWaterFoodie>(EhsWaterFoodie.class);
        util.exportExcel(response, list, "水耗设备数据");
    }

    /**
     * 获取水耗设备详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsWaterFoodieService.selectEhsWaterFoodieById(id));
    }

    /**
     * 新增水耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:add')")
    @Log(title = "水耗设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsWaterFoodie ehsWaterFoodie)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsWaterFoodieService.insertEhsWaterFoodie(ehsWaterFoodie);
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
     * 修改水耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:edit')")
    @Log(title = "水耗设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsWaterFoodie ehsWaterFoodie)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsWaterFoodieService.updateEhsWaterFoodie(ehsWaterFoodie);
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
     * 删除水耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterFoodie:remove')")
    @Log(title = "水耗设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsWaterFoodieService.deleteEhsWaterFoodieByIds(ids);
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
