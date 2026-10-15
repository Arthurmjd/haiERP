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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoBom;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单BOMController
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woBom")
public class PlnWoBomController extends BaseController
{
    @Autowired
    private IPlnWoBomService plnWoBomService;

    /**
     * 查询工单BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoBom plnWoBom)
    {
        startPage();
        List<PlnWoBom> list = plnWoBomService.selectPlnWoBomList(plnWoBom);
        return getDataTable(list);
    }

    /**
     * 导出工单BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:export')")
    @Log(title = "工单BOM", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoBom plnWoBom)
    {
        List<PlnWoBom> list = plnWoBomService.selectPlnWoBomList(plnWoBom);
        ExcelUtil<PlnWoBom> util = new ExcelUtil<PlnWoBom>(PlnWoBom.class);
        util.exportExcel(response, list, "工单BOM数据");
    }

    /**
     * 获取工单BOM详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoBomService.selectPlnWoBomById(id));
    }

    /**
     * 新增工单BOM
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:add')")
    @Log(title = "工单BOM", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoBom plnWoBom)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoBomService.insertPlnWoBom(plnWoBom);
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
     * 修改工单BOM
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:edit')")
    @Log(title = "工单BOM", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoBom plnWoBom)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoBomService.updatePlnWoBom(plnWoBom);
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
     * 删除工单BOM
     */
    //@PreAuthorize("@ss.hasPermi('pln:woBom:remove')")
    @Log(title = "工单BOM", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoBomService.deletePlnWoBomByIds(ids);
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
