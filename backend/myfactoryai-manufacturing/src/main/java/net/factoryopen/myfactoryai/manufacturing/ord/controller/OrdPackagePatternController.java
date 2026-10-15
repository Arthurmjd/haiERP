package net.factoryopen.myfactoryai.manufacturing.ord.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdPackagePattern;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdPackagePatternService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 包装方式预配Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/packagePattern")
public class OrdPackagePatternController extends BaseController
{
    @Autowired
    private IOrdPackagePatternService ordPackagePatternService;

    /**
     * 查询包装方式预配列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdPackagePattern ordPackagePattern)
    {
        startPage();
        List<OrdPackagePattern> list = ordPackagePatternService.selectOrdPackagePatternList(ordPackagePattern);
        return getDataTable(list);
    }

    /**
     * 导出包装方式预配列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:export')")
    @Log(title = "包装方式预配", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdPackagePattern ordPackagePattern)
    {
        List<OrdPackagePattern> list = ordPackagePatternService.selectOrdPackagePatternList(ordPackagePattern);
        ExcelUtil<OrdPackagePattern> util = new ExcelUtil<OrdPackagePattern>(OrdPackagePattern.class);
        util.exportExcel(response, list, "包装方式预配数据");
    }

    /**
     * 获取包装方式预配详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordPackagePatternService.selectOrdPackagePatternById(id));
    }

    /**
     * 新增包装方式预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:add')")
    @Log(title = "包装方式预配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdPackagePattern ordPackagePattern)
    {
        AjaxResult ar = null;
        try{
           int ret = ordPackagePatternService.insertOrdPackagePattern(ordPackagePattern);
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
     * 修改包装方式预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:edit')")
    @Log(title = "包装方式预配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdPackagePattern ordPackagePattern)
    {
        AjaxResult ar = null;
        try{
            int ret = ordPackagePatternService.updateOrdPackagePattern(ordPackagePattern);
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
     * 删除包装方式预配
     */
    //@PreAuthorize("@ss.hasPermi('ord:packagePattern:remove')")
    @Log(title = "包装方式预配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordPackagePatternService.deleteOrdPackagePatternByIds(ids);
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
