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
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReturn;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectReturnService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 退货检异登记Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/qua/inspectReturn")
public class QuaInspectReturnController extends BaseController
{
    @Autowired
    private IQuaInspectReturnService quaInspectReturnService;

    /**
     * 查询退货检异登记列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuaInspectReturn quaInspectReturn)
    {
        startPage();
        List<QuaInspectReturn> list = quaInspectReturnService.selectQuaInspectReturnList(quaInspectReturn);
        return getDataTable(list);
    }

    /**
     * 导出退货检异登记列表
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:export')")
    @Log(title = "退货检异登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuaInspectReturn quaInspectReturn)
    {
        List<QuaInspectReturn> list = quaInspectReturnService.selectQuaInspectReturnList(quaInspectReturn);
        ExcelUtil<QuaInspectReturn> util = new ExcelUtil<QuaInspectReturn>(QuaInspectReturn.class);
        util.exportExcel(response, list, "退货检异登记数据");
    }

    /**
     * 获取退货检异登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(quaInspectReturnService.selectQuaInspectReturnById(id));
    }

    /**
     * 新增退货检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:add')")
    @Log(title = "退货检异登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuaInspectReturn quaInspectReturn)
    {
        AjaxResult ar = null;
        try{
           int ret = quaInspectReturnService.insertQuaInspectReturn(quaInspectReturn);
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
     * 修改退货检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:edit')")
    @Log(title = "退货检异登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuaInspectReturn quaInspectReturn)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectReturnService.updateQuaInspectReturn(quaInspectReturn);
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
     * 删除退货检异登记
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:remove')")
    @Log(title = "退货检异登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = quaInspectReturnService.deleteQuaInspectReturnByIds(ids);
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

    /**
     * 请判
     */
    //@PreAuthorize("@ss.hasPermi('qua:inspectReturn:requestDeal')")
    @Log(title = "请判", businessType = BusinessType.OTHER)
    @PutMapping("/requestDeal")
    public AjaxResult requestDeal(@RequestBody QuaInspectReturn quaInspectReturn)
    {
        AjaxResult ar = null;
        try{
            quaInspectReturnService.requestDeal(quaInspectReturn);
            ar = AjaxResult.success("操作成功：[请判]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
