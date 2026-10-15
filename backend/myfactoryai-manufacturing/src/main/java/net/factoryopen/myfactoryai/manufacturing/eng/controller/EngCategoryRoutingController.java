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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryRoutingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品类工艺路线Controller
 *
 * @author admin
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/eng/categoryRouting")
public class EngCategoryRoutingController extends BaseController
{
    @Autowired
    private IEngCategoryRoutingService engCategoryRoutingService;

    /**
     * 查询品类工艺路线列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngCategoryRouting engCategoryRouting)
    {
        if(engCategoryRouting!=null && engCategoryRouting.getPageEnable()!=null && engCategoryRouting.getPageEnable()) startPage();
        List<EngCategoryRouting> list = engCategoryRoutingService.selectEngCategoryRoutingList(engCategoryRouting);
        return getDataTable(list);
    }

    /**
     * 导出品类工艺路线列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:export')")
    @Log(title = "品类工艺路线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngCategoryRouting engCategoryRouting)
    {
        List<EngCategoryRouting> list = engCategoryRoutingService.selectEngCategoryRoutingList(engCategoryRouting);
        ExcelUtil<EngCategoryRouting> util = new ExcelUtil<EngCategoryRouting>(EngCategoryRouting.class);
        util.exportExcel(response, list, "品类工艺路线数据");
    }

    /**
     * 获取品类工艺路线详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engCategoryRoutingService.selectEngCategoryRoutingById(id));
    }

    /**
     * 新增品类工艺路线
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:add')")
    @Log(title = "品类工艺路线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngCategoryRouting engCategoryRouting)
    {
        AjaxResult ar = null;
        try{
           int ret = engCategoryRoutingService.insertEngCategoryRouting(engCategoryRouting);
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
     * 修改品类工艺路线
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:edit')")
    @Log(title = "品类工艺路线", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngCategoryRouting engCategoryRouting)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryRoutingService.updateEngCategoryRouting(engCategoryRouting);
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
     * 删除品类工艺路线
     */
    //@PreAuthorize("@ss.hasPermi('eng:categoryRouting:remove')")
    @Log(title = "品类工艺路线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engCategoryRoutingService.deleteEngCategoryRoutingByIds(ids);
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
