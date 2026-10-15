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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResLaborService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工人管理Controller
 *
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/res/labor")
public class ResLaborController extends BaseController
{
    @Autowired
    private IResLaborService resLaborService;

    /**
     * 查询工人管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResLabor resLabor)
    {
        if(resLabor!=null && resLabor.getPageEnable()!=null && resLabor.getPageEnable()) startPage();
        List<ResLabor> list = resLaborService.selectResLaborList(resLabor);
        return getDataTable(list);
    }

    /**
     * 导出工人管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:export')")
    @Log(title = "工人管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResLabor resLabor)
    {
        List<ResLabor> list = resLaborService.selectResLaborList(resLabor);
        ExcelUtil<ResLabor> util = new ExcelUtil<ResLabor>(ResLabor.class);
        util.exportExcel(response, list, "工人管理数据");
    }

    /**
     * 获取工人管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resLaborService.selectResLaborById(id));
    }

    /**
     * 新增工人管理
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:add')")
    @Log(title = "工人管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResLabor resLabor)
    {
        AjaxResult ar = null;
        try{
           int ret = resLaborService.insertResLabor(resLabor);
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
     * 修改工人管理
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:edit')")
    @Log(title = "工人管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResLabor resLabor)
    {
        AjaxResult ar = null;
        try{
            int ret = resLaborService.updateResLabor(resLabor);
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
     * 删除工人管理
     */
    //@PreAuthorize("@ss.hasPermi('res:labor:remove')")
    @Log(title = "工人管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resLaborService.deleteResLaborByIds(ids);
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
