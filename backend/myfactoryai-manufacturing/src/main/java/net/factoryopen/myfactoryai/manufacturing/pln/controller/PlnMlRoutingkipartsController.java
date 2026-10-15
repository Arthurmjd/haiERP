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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingkipartsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次关重料Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlRoutingkiparts")
public class PlnMlRoutingkipartsController extends BaseController
{
    @Autowired
    private IPlnMlRoutingkipartsService plnMlRoutingkipartsService;

    /**
     * 查询批次关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        startPage();
        List<PlnMlRoutingkiparts> list = plnMlRoutingkipartsService.selectPlnMlRoutingkipartsList(plnMlRoutingkiparts);
        return getDataTable(list);
    }

    /**
     * 导出批次关重料列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:export')")
    @Log(title = "批次关重料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        List<PlnMlRoutingkiparts> list = plnMlRoutingkipartsService.selectPlnMlRoutingkipartsList(plnMlRoutingkiparts);
        ExcelUtil<PlnMlRoutingkiparts> util = new ExcelUtil<PlnMlRoutingkiparts>(PlnMlRoutingkiparts.class);
        util.exportExcel(response, list, "批次关重料数据");
    }

    /**
     * 获取批次关重料详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlRoutingkipartsService.selectPlnMlRoutingkipartsById(id));
    }

    /**
     * 新增批次关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:add')")
    @Log(title = "批次关重料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlRoutingkipartsService.insertPlnMlRoutingkiparts(plnMlRoutingkiparts);
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
     * 修改批次关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:edit')")
    @Log(title = "批次关重料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingkipartsService.updatePlnMlRoutingkiparts(plnMlRoutingkiparts);
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
     * 删除批次关重料
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingkiparts:remove')")
    @Log(title = "批次关重料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingkipartsService.deletePlnMlRoutingkipartsByIds(ids);
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
