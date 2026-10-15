package net.factoryopen.myfactoryai.manufacturing.res.controller;

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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResLineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产线管理Controller
 *
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/line")
public class ResLineController extends BaseController
{
    @Autowired
    private IResLineService resLineService;

    /**
     * 查询产线管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:line:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResLine resLine)
    {
        if(resLine!=null && resLine.getPageEnable()!=null && resLine.getPageEnable()) startPage();
        List<ResLine> list = resLineService.selectResLineList(resLine);
        return getDataTable(list);
    }

    /**
     * 导出产线管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:line:export')")
    @Log(title = "产线管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResLine resLine)
    {
        List<ResLine> list = resLineService.selectResLineList(resLine);
        ExcelUtil<ResLine> util = new ExcelUtil<ResLine>(ResLine.class);
        util.exportExcel(response, list, "产线管理数据");
    }

    /**
     * 获取产线管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:line:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resLineService.selectResLineById(id));
    }

    /**
     * 新增产线管理
     */
    //@PreAuthorize("@ss.hasPermi('res:line:add')")
    @Log(title = "产线管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResLine resLine)
    {
        AjaxResult ar = null;
        try{
           int ret = resLineService.insertResLine(resLine);
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
     * 修改产线管理
     */
    //@PreAuthorize("@ss.hasPermi('res:line:edit')")
    @Log(title = "产线管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResLine resLine)
    {
        AjaxResult ar = null;
        try{
            int ret = resLineService.updateResLine(resLine);
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
     * 删除产线管理
     */
    //@PreAuthorize("@ss.hasPermi('res:line:remove')")
    @Log(title = "产线管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resLineService.deleteResLineByIds(ids);
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
