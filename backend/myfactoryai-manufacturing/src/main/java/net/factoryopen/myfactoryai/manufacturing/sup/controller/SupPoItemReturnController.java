package net.factoryopen.myfactoryai.manufacturing.sup.controller;

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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoItemReturnService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 退货项跟踪Controller
 *
 * @author admin
 * @date 2024-10-11
 */
@RestController
@RequestMapping("/sup/poItemReturn")
public class SupPoItemReturnController extends BaseController
{
    @Autowired
    private ISupPoItemReturnService supPoItemReturnService;

    /**
     * 查询退货项跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPoItemReturn supPoItemReturn)
    {
        startPage();
        List<SupPoItemReturn> list = supPoItemReturnService.selectSupPoItemReturnList(supPoItemReturn);
        return getDataTable(list);
    }

    /**
     * 导出退货项跟踪列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:export')")
    @Log(title = "退货项跟踪", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPoItemReturn supPoItemReturn)
    {
        List<SupPoItemReturn> list = supPoItemReturnService.selectSupPoItemReturnList(supPoItemReturn);
        ExcelUtil<SupPoItemReturn> util = new ExcelUtil<SupPoItemReturn>(SupPoItemReturn.class);
        util.exportExcel(response, list, "退货项跟踪数据");
    }

    /**
     * 获取退货项跟踪详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPoItemReturnService.selectSupPoItemReturnById(id));
    }

    /**
     * 新增退货项跟踪
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:add')")
    @Log(title = "退货项跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPoItemReturn supPoItemReturn)
    {
        AjaxResult ar = null;
        try{
           int ret = supPoItemReturnService.insertSupPoItemReturn(supPoItemReturn);
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
     * 修改退货项跟踪
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:edit')")
    @Log(title = "退货项跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPoItemReturn supPoItemReturn)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoItemReturnService.updateSupPoItemReturn(supPoItemReturn);
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
     * 删除退货项跟踪
     */
    //@PreAuthorize("@ss.hasPermi('sup:poItemReturn:remove')")
    @Log(title = "退货项跟踪", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoItemReturnService.deleteSupPoItemReturnByIds(ids);
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
