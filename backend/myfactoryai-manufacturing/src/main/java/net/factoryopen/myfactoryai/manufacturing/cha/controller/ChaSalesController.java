package net.factoryopen.myfactoryai.manufacturing.cha.controller;

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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaSales;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaSalesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售员预设Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/cha/sales")
public class ChaSalesController extends BaseController
{
    @Autowired
    private IChaSalesService chaSalesService;

    /**
     * 查询销售员预设列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaSales chaSales)
    {
        startPage();
        List<ChaSales> list = chaSalesService.selectChaSalesList(chaSales);
        return getDataTable(list);
    }

    /**
     * 导出销售员预设列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:export')")
    @Log(title = "销售员预设", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaSales chaSales)
    {
        List<ChaSales> list = chaSalesService.selectChaSalesList(chaSales);
        ExcelUtil<ChaSales> util = new ExcelUtil<ChaSales>(ChaSales.class);
        util.exportExcel(response, list, "销售员预设数据");
    }

    /**
     * 获取销售员预设详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaSalesService.selectChaSalesById(id));
    }

    /**
     * 新增销售员预设
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:add')")
    @Log(title = "销售员预设", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaSales chaSales)
    {
        AjaxResult ar = null;
        try{
           int ret = chaSalesService.insertChaSales(chaSales);
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
     * 修改销售员预设
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:edit')")
    @Log(title = "销售员预设", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaSales chaSales)
    {
        AjaxResult ar = null;
        try{
            int ret = chaSalesService.updateChaSales(chaSales);
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
     * 删除销售员预设
     */
    //@PreAuthorize("@ss.hasPermi('cha:sales:remove')")
    @Log(title = "销售员预设", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaSalesService.deleteChaSalesByIds(ids);
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
