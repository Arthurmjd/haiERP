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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasFoodieService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 气耗设备Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/gasFoodie")
public class EhsGasFoodieController extends BaseController
{
    @Autowired
    private IEhsGasFoodieService ehsGasFoodieService;

    /**
     * 查询气耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsGasFoodie ehsGasFoodie)
    {
        startPage();
        List<EhsGasFoodie> list = ehsGasFoodieService.selectEhsGasFoodieList(ehsGasFoodie);
        return getDataTable(list);
    }

    /**
     * 导出气耗设备列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:export')")
    @Log(title = "气耗设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsGasFoodie ehsGasFoodie)
    {
        List<EhsGasFoodie> list = ehsGasFoodieService.selectEhsGasFoodieList(ehsGasFoodie);
        ExcelUtil<EhsGasFoodie> util = new ExcelUtil<EhsGasFoodie>(EhsGasFoodie.class);
        util.exportExcel(response, list, "气耗设备数据");
    }

    /**
     * 获取气耗设备详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsGasFoodieService.selectEhsGasFoodieById(id));
    }

    /**
     * 新增气耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:add')")
    @Log(title = "气耗设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsGasFoodie ehsGasFoodie)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsGasFoodieService.insertEhsGasFoodie(ehsGasFoodie);
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
     * 修改气耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:edit')")
    @Log(title = "气耗设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsGasFoodie ehsGasFoodie)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasFoodieService.updateEhsGasFoodie(ehsGasFoodie);
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
     * 删除气耗设备
     */
    //@PreAuthorize("@ss.hasPermi('ehs:gasFoodie:remove')")
    @Log(title = "气耗设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsGasFoodieService.deleteEhsGasFoodieByIds(ids);
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
