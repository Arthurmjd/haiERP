package net.factoryopen.myfactoryai.manufacturing.ord.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdCheckbusi;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdCheckbusiService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单对账服务Controller
 *
 * @author  jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/checkbusi")
public class OrdCheckbusiController extends BaseController
{
    @Autowired
    private IOrdCheckbusiService ordCheckbusiService;

    /**
     * 查询订单对账服务列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdCheckbusi ordCheckbusi)
    {
        startPage();
        List<OrdCheckbusi> list = ordCheckbusiService.selectOrdCheckbusiList(ordCheckbusi);
        return getDataTable(list);
    }

    /**
     * 导出订单对账服务列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:export')")
    @Log(title = "订单对账服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdCheckbusi ordCheckbusi)
    {
        List<OrdCheckbusi> list = ordCheckbusiService.selectOrdCheckbusiList(ordCheckbusi);
        ExcelUtil<OrdCheckbusi> util = new ExcelUtil<OrdCheckbusi>(OrdCheckbusi.class);
        util.exportExcel(response, list, "订单对账服务数据");
    }

    /**
     * 获取订单对账服务详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordCheckbusiService.selectOrdCheckbusiById(id));
    }

    /**
     * 新增订单对账服务
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:add')")
    @Log(title = "订单对账服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdCheckbusi ordCheckbusi)
    {
        AjaxResult ar = null;
        try{
           int ret = ordCheckbusiService.insertOrdCheckbusi(ordCheckbusi);
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
     * 修改订单对账服务
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:edit')")
    @Log(title = "订单对账服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdCheckbusi ordCheckbusi)
    {
        AjaxResult ar = null;
        try{
            int ret = ordCheckbusiService.updateOrdCheckbusi(ordCheckbusi);
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
     * 删除订单对账服务
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:remove')")
    @Log(title = "订单对账服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordCheckbusiService.deleteOrdCheckbusiByIds(ids);
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
     * 对账
     */
    //@PreAuthorize("@ss.hasPermi('ord:checkbusi:checkOrderBusi')")
    @Log(title = "对账", businessType = BusinessType.OTHER)
    @PutMapping("/checkOrderBusi")
    public AjaxResult checkOrderBusi(@RequestBody OrdCheckbusi ordCheckbusi)
    {
        AjaxResult ar = null;
        try{
            ordCheckbusiService.checkOrderBusi(ordCheckbusi);
            ar = AjaxResult.success("操作成功：[对账]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
