package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCompound;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCompoundService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 材质数据Controller
 *
 * @author admin
 * @date 2024-11-29
 */
@RestController
@RequestMapping("/eng/compound")
public class EngCompoundController extends BaseController
{
    @Autowired
    private IEngCompoundService engCompoundService;

    /**
     * 查询材质数据列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCompound engCompound)
    {
        if(engCompound!=null && engCompound.getPageEnable()!=null && engCompound.getPageEnable()) startPage();
        List<EngCompound> list = engCompoundService.selectEngCompoundList(engCompound);
        return getDataTable(list);
    }

    /**
     * 导出材质数据列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:export')")
    @Log(title = "材质数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCompound engCompound)
    {
        List<EngCompound> list = engCompoundService.selectEngCompoundList(engCompound);
        ExcelUtil<EngCompound> util = new ExcelUtil<EngCompound>(EngCompound.class);
        util.exportExcel(response, list, "材质数据数据");
    }

    /**
     * 获取材质数据详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCompoundService.selectEngCompoundById(id));
    }

    /**
     * 新增材质数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:add')")
    @Log(title = "材质数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCompound engCompound)
    {
        AjaxResult ar = null;
        try{
           int ret = engCompoundService.insertEngCompound(engCompound);
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
     * 修改材质数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:edit')")
    @Log(title = "材质数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCompound engCompound)
    {
        AjaxResult ar = null;
        try{
            int ret = engCompoundService.updateEngCompound(engCompound);
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
     * 删除材质数据
     */
    //@PreAuthorize("@ss.hasPermi('eng:compound:remove')")
    @Log(title = "材质数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCompoundService.deleteEngCompoundByIds(ids);
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
