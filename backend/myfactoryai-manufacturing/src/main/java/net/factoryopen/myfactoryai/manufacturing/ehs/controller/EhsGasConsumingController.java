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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasConsumingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 气耗登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/gasConsuming")
public class EhsGasConsumingController extends BaseController
{
    @Autowired
    private IEhsGasConsumingService ehsGasConsumingService;

    /**
     * 查询气耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsGasConsuming ehsGasConsuming)
    {
        startPage();
        List<EhsGasConsuming> list = ehsGasConsumingService.selectEhsGasConsumingList(ehsGasConsuming);
        return getDataTable(list);
    }

    /**
     * 导出气耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:export')")
    @Log(title = "气耗登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsGasConsuming ehsGasConsuming)
    {
        List<EhsGasConsuming> list = ehsGasConsumingService.selectEhsGasConsumingList(ehsGasConsuming);
        ExcelUtil<EhsGasConsuming> util = new ExcelUtil<EhsGasConsuming>(EhsGasConsuming.class);
        util.exportExcel(response, list, "气耗登记数据");
    }

    /**
     * 获取气耗登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsGasConsumingService.selectEhsGasConsumingById(id));
    }

    /**
     * 新增气耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:add')")
    @Log(title = "气耗登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsGasConsuming ehsGasConsuming)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsGasConsumingService.insertEhsGasConsuming(ehsGasConsuming);
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
     * 修改气耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:edit')")
    @Log(title = "气耗登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsGasConsuming ehsGasConsuming)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasConsumingService.updateEhsGasConsuming(ehsGasConsuming);
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
     * 删除气耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasConsuming:remove')")
    @Log(title = "气耗登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasConsumingService.deleteEhsGasConsumingByIds(ids);
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
