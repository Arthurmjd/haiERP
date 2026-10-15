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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerConsuming;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerConsumingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电耗登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/powerConsuming")
public class EhsPowerConsumingController extends BaseController
{
    @Autowired
    private IEhsPowerConsumingService ehsPowerConsumingService;

    /**
     * 查询电耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPowerConsuming ehsPowerConsuming)
    {
        startPage();
        List<EhsPowerConsuming> list = ehsPowerConsumingService.selectEhsPowerConsumingList(ehsPowerConsuming);
        return getDataTable(list);
    }

    /**
     * 导出电耗登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:export')")
    @Log(title = "电耗登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPowerConsuming ehsPowerConsuming)
    {
        List<EhsPowerConsuming> list = ehsPowerConsumingService.selectEhsPowerConsumingList(ehsPowerConsuming);
        ExcelUtil<EhsPowerConsuming> util = new ExcelUtil<EhsPowerConsuming>(EhsPowerConsuming.class);
        util.exportExcel(response, list, "电耗登记数据");
    }

    /**
     * 获取电耗登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPowerConsumingService.selectEhsPowerConsumingById(id));
    }

    /**
     * 新增电耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:add')")
    @Log(title = "电耗登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPowerConsuming ehsPowerConsuming)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPowerConsumingService.insertEhsPowerConsuming(ehsPowerConsuming);
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
     * 修改电耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:edit')")
    @Log(title = "电耗登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPowerConsuming ehsPowerConsuming)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerConsumingService.updateEhsPowerConsuming(ehsPowerConsuming);
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
     * 删除电耗登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:powerConsuming:remove')")
    @Log(title = "电耗登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPowerConsumingService.deleteEhsPowerConsumingByIds(ids);
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
