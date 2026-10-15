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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingsopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单SOPController
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woRoutingsop")
public class PlnWoRoutingsopController extends BaseController
{
    @Autowired
    private IPlnWoRoutingsopService plnWoRoutingsopService;

    /**
     * 查询工单SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRoutingsop plnWoRoutingsop)
    {
        startPage();
        List<PlnWoRoutingsop> list = plnWoRoutingsopService.selectPlnWoRoutingsopList(plnWoRoutingsop);
        return getDataTable(list);
    }

    /**
     * 导出工单SOP列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:export')")
    @Log(title = "工单SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRoutingsop plnWoRoutingsop)
    {
        List<PlnWoRoutingsop> list = plnWoRoutingsopService.selectPlnWoRoutingsopList(plnWoRoutingsop);
        ExcelUtil<PlnWoRoutingsop> util = new ExcelUtil<PlnWoRoutingsop>(PlnWoRoutingsop.class);
        util.exportExcel(response, list, "工单SOP数据");
    }

    /**
     * 获取工单SOP详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingsopService.selectPlnWoRoutingsopById(id));
    }

    /**
     * 新增工单SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:add')")
    @Log(title = "工单SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRoutingsop plnWoRoutingsop)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingsopService.insertPlnWoRoutingsop(plnWoRoutingsop);
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
     * 修改工单SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:edit')")
    @Log(title = "工单SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRoutingsop plnWoRoutingsop)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingsopService.updatePlnWoRoutingsop(plnWoRoutingsop);
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
     * 删除工单SOP
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingsop:remove')")
    @Log(title = "工单SOP", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingsopService.deletePlnWoRoutingsopByIds(ids);
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
