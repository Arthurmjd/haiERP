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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingsopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次SOPController
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlRoutingsop")
public class PlnMlRoutingsopController extends BaseController
{
    @Autowired
    private IPlnMlRoutingsopService plnMlRoutingsopService;

    /**
     * 查询批次SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlRoutingsop plnMlRoutingsop)
    {
        startPage();
        List<PlnMlRoutingsop> list = plnMlRoutingsopService.selectPlnMlRoutingsopList(plnMlRoutingsop);
        return getDataTable(list);
    }

    /**
     * 导出批次SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:export')")
    @Log(title = "批次SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlRoutingsop plnMlRoutingsop)
    {
        List<PlnMlRoutingsop> list = plnMlRoutingsopService.selectPlnMlRoutingsopList(plnMlRoutingsop);
        ExcelUtil<PlnMlRoutingsop> util = new ExcelUtil<PlnMlRoutingsop>(PlnMlRoutingsop.class);
        util.exportExcel(response, list, "批次SOP数据");
    }

    /**
     * 获取批次SOP详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlRoutingsopService.selectPlnMlRoutingsopById(id));
    }

    /**
     * 新增批次SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:add')")
    @Log(title = "批次SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlRoutingsop plnMlRoutingsop)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlRoutingsopService.insertPlnMlRoutingsop(plnMlRoutingsop);
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
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:edit')")
    @Log(title = "批次SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlRoutingsop plnMlRoutingsop)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingsopService.updatePlnMlRoutingsop(plnMlRoutingsop);
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
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingsop:remove')")
    @Log(title = "批次SOP", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingsopService.deletePlnMlRoutingsopByIds(ids);
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
