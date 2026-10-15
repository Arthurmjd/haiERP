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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingtoolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次工具Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlRoutingtool")
public class PlnMlRoutingtoolController extends BaseController
{
    @Autowired
    private IPlnMlRoutingtoolService plnMlRoutingtoolService;

    /**
     * 查询批次工具列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlRoutingtool plnMlRoutingtool)
    {
        startPage();
        List<PlnMlRoutingtool> list = plnMlRoutingtoolService.selectPlnMlRoutingtoolList(plnMlRoutingtool);
        return getDataTable(list);
    }

    /**
     * 导出批次工具列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:export')")
    @Log(title = "批次工具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlRoutingtool plnMlRoutingtool)
    {
        List<PlnMlRoutingtool> list = plnMlRoutingtoolService.selectPlnMlRoutingtoolList(plnMlRoutingtool);
        ExcelUtil<PlnMlRoutingtool> util = new ExcelUtil<PlnMlRoutingtool>(PlnMlRoutingtool.class);
        util.exportExcel(response, list, "批次工具数据");
    }

    /**
     * 获取批次工具详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlRoutingtoolService.selectPlnMlRoutingtoolById(id));
    }

    /**
     * 新增批次工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:add')")
    @Log(title = "批次工具", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlRoutingtool plnMlRoutingtool)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlRoutingtoolService.insertPlnMlRoutingtool(plnMlRoutingtool);
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
     * 修改批次工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:edit')")
    @Log(title = "批次工具", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlRoutingtool plnMlRoutingtool)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingtoolService.updatePlnMlRoutingtool(plnMlRoutingtool);
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
     * 删除批次工具
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingtool:remove')")
    @Log(title = "批次工具", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingtoolService.deletePlnMlRoutingtoolByIds(ids);
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
