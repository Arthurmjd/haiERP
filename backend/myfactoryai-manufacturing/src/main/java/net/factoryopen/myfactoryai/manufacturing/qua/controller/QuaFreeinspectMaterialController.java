package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectMaterial;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 免检物料Controller
 *
 * @author admin
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/qua/freeinspectMaterial")
public class QuaFreeinspectMaterialController extends BaseController
{
    @Autowired
    private IQuaFreeinspectMaterialService quaFreeinspectMaterialService;

    /**
     * 查询免检物料列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        startPage();
        List<QuaFreeinspectMaterial> list = quaFreeinspectMaterialService.selectQuaFreeinspectMaterialList(quaFreeinspectMaterial);
        return getDataTable(list);
    }

    /**
     * 导出免检物料列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:export')")
    @Log(title = "免检物料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        List<QuaFreeinspectMaterial> list = quaFreeinspectMaterialService.selectQuaFreeinspectMaterialList(quaFreeinspectMaterial);
        ExcelUtil<QuaFreeinspectMaterial> util = new ExcelUtil<QuaFreeinspectMaterial>(QuaFreeinspectMaterial.class);
        util.exportExcel(response, list, "免检物料数据");
    }

    /**
     * 获取免检物料详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaFreeinspectMaterialService.selectQuaFreeinspectMaterialById(id));
    }

    /**
     * 新增免检物料
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:add')")
    @Log(title = "免检物料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        AjaxResult ar = null;
        try{
           int ret = quaFreeinspectMaterialService.insertQuaFreeinspectMaterial(quaFreeinspectMaterial);
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
     * 修改免检物料
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:edit')")
    @Log(title = "免检物料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectMaterialService.updateQuaFreeinspectMaterial(quaFreeinspectMaterial);
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
     * 删除免检物料
     */
    //@PreAuthorize("@ss.hasPermi('qua:freeinspectMaterial:remove')")
    @Log(title = "免检物料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaFreeinspectMaterialService.deleteQuaFreeinspectMaterialByIds(ids);
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
