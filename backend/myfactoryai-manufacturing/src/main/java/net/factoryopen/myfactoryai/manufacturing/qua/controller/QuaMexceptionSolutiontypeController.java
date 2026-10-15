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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionSolutiontypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量异常处理类型Controller
 *
 * @author admin
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/qua/mexceptionSolutiontype")
public class QuaMexceptionSolutiontypeController extends BaseController
{
    @Autowired
    private IQuaMexceptionSolutiontypeService quaMexceptionSolutiontypeService;

    /**
     * 查询质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        startPage();
        List<QuaMexceptionSolutiontype> list = quaMexceptionSolutiontypeService.selectQuaMexceptionSolutiontypeList(quaMexceptionSolutiontype);
        return getDataTable(list);
    }

    /**
     * 导出质量异常处理类型列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:export')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        List<QuaMexceptionSolutiontype> list = quaMexceptionSolutiontypeService.selectQuaMexceptionSolutiontypeList(quaMexceptionSolutiontype);
        ExcelUtil<QuaMexceptionSolutiontype> util = new ExcelUtil<QuaMexceptionSolutiontype>(QuaMexceptionSolutiontype.class);
        util.exportExcel(response, list, "质量异常处理类型数据");
    }

    /**
     * 获取质量异常处理类型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaMexceptionSolutiontypeService.selectQuaMexceptionSolutiontypeById(id));
    }

    /**
     * 新增质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:add')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
           int ret = quaMexceptionSolutiontypeService.insertQuaMexceptionSolutiontype(quaMexceptionSolutiontype);
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
     * 修改质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:edit')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionSolutiontypeService.updateQuaMexceptionSolutiontype(quaMexceptionSolutiontype);
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
     * 删除质量异常处理类型
     */
    //@PreAuthorize("@ss.hasPermi('qua:mexceptionSolutiontype:remove')")
    @Log(title = "质量异常处理类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaMexceptionSolutiontypeService.deleteQuaMexceptionSolutiontypeByIds(ids);
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
