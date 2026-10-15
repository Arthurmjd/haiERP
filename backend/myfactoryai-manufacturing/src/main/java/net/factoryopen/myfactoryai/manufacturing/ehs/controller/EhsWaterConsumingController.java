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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsWaterConsumingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 水耗登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/waterConsuming")
public class EhsWaterConsumingController extends BaseController
{
    @Autowired
    private IEhsWaterConsumingService ehsWaterConsumingService;

    /**
     * 查询水耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsWaterConsuming ehsWaterConsuming)
    {
        startPage();
        List<EhsWaterConsuming> list = ehsWaterConsumingService.selectEhsWaterConsumingList(ehsWaterConsuming);
        return getDataTable(list);
    }

    /**
     * 导出水耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:export')")
    @Log(title = "水耗登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsWaterConsuming ehsWaterConsuming)
    {
        List<EhsWaterConsuming> list = ehsWaterConsumingService.selectEhsWaterConsumingList(ehsWaterConsuming);
        ExcelUtil<EhsWaterConsuming> util = new ExcelUtil<EhsWaterConsuming>(EhsWaterConsuming.class);
        util.exportExcel(response, list, "水耗登记数据");
    }

    /**
     * 获取水耗登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsWaterConsumingService.selectEhsWaterConsumingById(id));
    }

    /**
     * 新增水耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:add')")
    @Log(title = "水耗登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsWaterConsuming ehsWaterConsuming)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsWaterConsumingService.insertEhsWaterConsuming(ehsWaterConsuming);
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
     * 修改水耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:edit')")
    @Log(title = "水耗登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsWaterConsuming ehsWaterConsuming)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsWaterConsumingService.updateEhsWaterConsuming(ehsWaterConsuming);
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
     * 删除水耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:waterConsuming:remove')")
    @Log(title = "水耗登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsWaterConsumingService.deleteEhsWaterConsumingByIds(ids);
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
