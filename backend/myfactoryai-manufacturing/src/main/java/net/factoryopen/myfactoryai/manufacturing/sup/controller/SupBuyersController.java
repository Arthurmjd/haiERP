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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupBuyers;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupBuyersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购员配置Controller
 *
 * @author admin
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/sup/buyers")
public class SupBuyersController extends BaseController
{
    @Autowired
    private ISupBuyersService supBuyersService;

    /**
     * 查询采购员配置列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupBuyers supBuyers)
    {
        startPage();
        List<SupBuyers> list = supBuyersService.selectSupBuyersList(supBuyers);
        return getDataTable(list);
    }

    /**
     * 导出采购员配置列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:export')")
    @Log(title = "采购员配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupBuyers supBuyers)
    {
        List<SupBuyers> list = supBuyersService.selectSupBuyersList(supBuyers);
        ExcelUtil<SupBuyers> util = new ExcelUtil<SupBuyers>(SupBuyers.class);
        util.exportExcel(response, list, "采购员配置数据");
    }

    /**
     * 获取采购员配置详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supBuyersService.selectSupBuyersById(id));
    }

    /**
     * 新增采购员配置
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:add')")
    @Log(title = "采购员配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupBuyers supBuyers)
    {
        AjaxResult ar = null;
        try{
           int ret = supBuyersService.insertSupBuyers(supBuyers);
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
     * 修改采购员配置
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:edit')")
    @Log(title = "采购员配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupBuyers supBuyers)
    {
        AjaxResult ar = null;
        try{
            int ret = supBuyersService.updateSupBuyers(supBuyers);
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
     * 删除采购员配置
     */
    //@PreAuthorize("@ss.hasPermi('sup:buyers:remove')")
    @Log(title = "采购员配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supBuyersService.deleteSupBuyersByIds(ids);
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
