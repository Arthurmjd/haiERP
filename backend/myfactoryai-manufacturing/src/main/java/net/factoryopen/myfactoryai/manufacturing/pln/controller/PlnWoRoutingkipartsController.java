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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingkipartsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单关重料Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woRoutingkiparts")
public class PlnWoRoutingkipartsController extends BaseController
{
    @Autowired
    private IPlnWoRoutingkipartsService plnWoRoutingkipartsService;

    /**
     * 查询工单关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        startPage();
        List<PlnWoRoutingkiparts> list = plnWoRoutingkipartsService.selectPlnWoRoutingkipartsList(plnWoRoutingkiparts);
        return getDataTable(list);
    }

    /**
     * 导出工单关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:export')")
    @Log(title = "工单关重料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        List<PlnWoRoutingkiparts> list = plnWoRoutingkipartsService.selectPlnWoRoutingkipartsList(plnWoRoutingkiparts);
        ExcelUtil<PlnWoRoutingkiparts> util = new ExcelUtil<PlnWoRoutingkiparts>(PlnWoRoutingkiparts.class);
        util.exportExcel(response, list, "工单关重料数据");
    }

    /**
     * 获取工单关重料详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingkipartsService.selectPlnWoRoutingkipartsById(id));
    }

    /**
     * 新增工单关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:add')")
    @Log(title = "工单关重料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingkipartsService.insertPlnWoRoutingkiparts(plnWoRoutingkiparts);
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
     * 修改工单关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:edit')")
    @Log(title = "工单关重料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingkipartsService.updatePlnWoRoutingkiparts(plnWoRoutingkiparts);
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
     * 删除工单关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingkiparts:remove')")
    @Log(title = "工单关重料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingkipartsService.deletePlnWoRoutingkipartsByIds(ids);
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
