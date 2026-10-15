package net.factoryopen.myfactoryai.manufacturing.qua.controller;

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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionSolutiontypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购质量异常处理类型Controller
 *
 * @author admin
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/pexceptionSolutiontype")
public class QuaPexceptionSolutiontypeController extends BaseController
{
    @Autowired
    private IQuaPexceptionSolutiontypeService quaPexceptionSolutiontypeService;

    /**
     * 查询采购质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        startPage();
        List<QuaPexceptionSolutiontype> list = quaPexceptionSolutiontypeService.selectQuaPexceptionSolutiontypeList(quaPexceptionSolutiontype);
        return getDataTable(list);
    }

    /**
     * 导出采购质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:export')")
    @Log(title = "采购质量异常处理类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        List<QuaPexceptionSolutiontype> list = quaPexceptionSolutiontypeService.selectQuaPexceptionSolutiontypeList(quaPexceptionSolutiontype);
        ExcelUtil<QuaPexceptionSolutiontype> util = new ExcelUtil<QuaPexceptionSolutiontype>(QuaPexceptionSolutiontype.class);
        util.exportExcel(response, list, "采购质量异常处理类型数据");
    }

    /**
     * 获取采购质量异常处理类型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaPexceptionSolutiontypeService.selectQuaPexceptionSolutiontypeById(id));
    }

    /**
     * 新增采购质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:add')")
    @Log(title = "采购质量异常处理类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaPexceptionSolutiontypeService.insertQuaPexceptionSolutiontype(quaPexceptionSolutiontype);
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
     * 修改采购质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:edit')")
    @Log(title = "采购质量异常处理类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionSolutiontypeService.updateQuaPexceptionSolutiontype(quaPexceptionSolutiontype);
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
     * 删除采购质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:pexceptionSolutiontype:remove')")
    @Log(title = "采购质量异常处理类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaPexceptionSolutiontypeService.deleteQuaPexceptionSolutiontypeByIds(ids);
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
