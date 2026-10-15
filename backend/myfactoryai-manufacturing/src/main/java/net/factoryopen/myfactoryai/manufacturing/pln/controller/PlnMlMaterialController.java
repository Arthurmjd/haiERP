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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMaterial;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次备料单Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlMaterial")
public class PlnMlMaterialController extends BaseController
{
    @Autowired
    private IPlnMlMaterialService plnMlMaterialService;

    /**
     * 查询批次备料单列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlMaterial plnMlMaterial)
    {
        startPage();
        List<PlnMlMaterial> list = plnMlMaterialService.selectPlnMlMaterialList(plnMlMaterial);
        return getDataTable(list);
    }

    /**
     * 导出批次备料单列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:export')")
    @Log(title = "批次备料单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlMaterial plnMlMaterial)
    {
        List<PlnMlMaterial> list = plnMlMaterialService.selectPlnMlMaterialList(plnMlMaterial);
        ExcelUtil<PlnMlMaterial> util = new ExcelUtil<PlnMlMaterial>(PlnMlMaterial.class);
        util.exportExcel(response, list, "批次备料单数据");
    }

    /**
     * 获取批次备料单详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlMaterialService.selectPlnMlMaterialById(id));
    }

    /**
     * 新增批次备料单
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:add')")
    @Log(title = "批次备料单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlMaterial plnMlMaterial)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlMaterialService.insertPlnMlMaterial(plnMlMaterial);
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
     * 修改批次备料单
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:edit')")
    @Log(title = "批次备料单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlMaterial plnMlMaterial)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlMaterialService.updatePlnMlMaterial(plnMlMaterial);
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
     * 删除批次备料单
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMaterial:remove')")
    @Log(title = "批次备料单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlMaterialService.deletePlnMlMaterialByIds(ids);
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
