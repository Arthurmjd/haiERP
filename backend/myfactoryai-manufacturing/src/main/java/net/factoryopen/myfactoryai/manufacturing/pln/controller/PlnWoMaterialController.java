package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoMaterial;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单发料Controller
 *
 * @author admin
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/pln/woMaterial")
public class PlnWoMaterialController extends BaseController
{
    @Autowired
    private IPlnWoMaterialService plnWoMaterialService;

    /**
     * 查询工单发料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoMaterial plnWoMaterial)
    {
        startPage();
        List<PlnWoMaterial> list = plnWoMaterialService.selectPlnWoMaterialList(plnWoMaterial);
        return getDataTable(list);
    }

    /**
     * 导出工单发料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:export')")
    @Log(title = "工单发料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoMaterial plnWoMaterial)
    {
        List<PlnWoMaterial> list = plnWoMaterialService.selectPlnWoMaterialList(plnWoMaterial);
        ExcelUtil<PlnWoMaterial> util = new ExcelUtil<PlnWoMaterial>(PlnWoMaterial.class);
        util.exportExcel(response, list, "工单发料数据");
    }

    /**
     * 获取工单发料详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoMaterialService.selectPlnWoMaterialById(id));
    }

    /**
     * 新增工单发料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:add')")
    @Log(title = "工单发料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoMaterial plnWoMaterial)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoMaterialService.insertPlnWoMaterial(plnWoMaterial);
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
     * 修改工单发料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:edit')")
    @Log(title = "工单发料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoMaterial plnWoMaterial)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoMaterialService.updatePlnWoMaterial(plnWoMaterial);
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
     * 删除工单发料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woMaterial:remove')")
    @Log(title = "工单发料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoMaterialService.deletePlnWoMaterialByIds(ids);
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
