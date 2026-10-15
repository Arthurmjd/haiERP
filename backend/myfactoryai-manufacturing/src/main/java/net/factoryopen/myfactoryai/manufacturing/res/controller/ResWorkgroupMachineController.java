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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupMachine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupMachineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班组工序Controller
 *
 * @author admin
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/workgroupMachine")
public class ResWorkgroupMachineController extends BaseController
{
    @Autowired
    private IResWorkgroupMachineService resWorkgroupMachineService;

    /**
     * 查询班组工序列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResWorkgroupMachine resWorkgroupMachine)
    {
        if(resWorkgroupMachine!=null && resWorkgroupMachine.getPageEnable()!=null && resWorkgroupMachine.getPageEnable()) startPage();
        List<ResWorkgroupMachine> list = resWorkgroupMachineService.selectResWorkgroupMachineList(resWorkgroupMachine);
        return getDataTable(list);
    }

    /**
     * 导出班组工序列表
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:export')")
    @Log(title = "班组工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResWorkgroupMachine resWorkgroupMachine)
    {
        List<ResWorkgroupMachine> list = resWorkgroupMachineService.selectResWorkgroupMachineList(resWorkgroupMachine);
        ExcelUtil<ResWorkgroupMachine> util = new ExcelUtil<ResWorkgroupMachine>(ResWorkgroupMachine.class);
        util.exportExcel(response, list, "班组工序数据");
    }

    /**
     * 获取班组工序详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resWorkgroupMachineService.selectResWorkgroupMachineById(id));
    }

    /**
     * 新增班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:add')")
    @Log(title = "班组工序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResWorkgroupMachine resWorkgroupMachine)
    {
        AjaxResult ar = null;
        try{
           int ret = resWorkgroupMachineService.insertResWorkgroupMachine(resWorkgroupMachine);
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
     * 修改班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:edit')")
    @Log(title = "班组工序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResWorkgroupMachine resWorkgroupMachine)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupMachineService.updateResWorkgroupMachine(resWorkgroupMachine);
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
     * 删除班组工序
     */
    //@PreAuthorize("@ss.hasPermi('res:workgroupMachine:remove')")
    @Log(title = "班组工序", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resWorkgroupMachineService.deleteResWorkgroupMachineByIds(ids);
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
