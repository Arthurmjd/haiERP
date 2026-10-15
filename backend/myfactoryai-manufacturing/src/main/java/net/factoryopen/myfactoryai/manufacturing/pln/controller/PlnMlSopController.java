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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlSop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlSopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次SOPController
 *
 * @author admin
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlSop")
public class PlnMlSopController extends BaseController
{
    @Autowired
    private IPlnMlSopService plnMlSopService;

    /**
     * 查询批次SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlSop plnMlSop)
    {
        startPage();
        List<PlnMlSop> list = plnMlSopService.selectPlnMlSopList(plnMlSop);
        return getDataTable(list);
    }

    /**
     * 导出批次SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:export')")
    @Log(title = "批次SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlSop plnMlSop)
    {
        List<PlnMlSop> list = plnMlSopService.selectPlnMlSopList(plnMlSop);
        ExcelUtil<PlnMlSop> util = new ExcelUtil<PlnMlSop>(PlnMlSop.class);
        util.exportExcel(response, list, "批次SOP数据");
    }

    /**
     * 获取批次SOP详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlSopService.selectPlnMlSopById(id));
    }

    /**
     * 新增批次SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:add')")
    @Log(title = "批次SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlSop plnMlSop)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlSopService.insertPlnMlSop(plnMlSop);
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
     * 修改批次SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:edit')")
    @Log(title = "批次SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlSop plnMlSop)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlSopService.updatePlnMlSop(plnMlSop);
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
     * 删除批次SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlSop:remove')")
    @Log(title = "批次SOP", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlSopService.deletePlnMlSopByIds(ids);
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
