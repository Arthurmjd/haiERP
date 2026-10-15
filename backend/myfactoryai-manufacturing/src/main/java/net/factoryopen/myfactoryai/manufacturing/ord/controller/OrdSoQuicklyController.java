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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoQuickly;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoQuicklyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 急出订单Controller
 *
 * @author admin
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/ord/soQuickly")
public class OrdSoQuicklyController extends BaseController
{
    @Autowired
    private IOrdSoQuicklyService ordSoQuicklyService;

    /**
     * 查询急出订单列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSoQuickly ordSoQuickly)
    {
        startPage();
        List<OrdSoQuickly> list = ordSoQuicklyService.selectOrdSoQuicklyList(ordSoQuickly);
        return getDataTable(list);
    }

    /**
     * 导出急出订单列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:export')")
    @Log(title = "急出订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSoQuickly ordSoQuickly)
    {
        List<OrdSoQuickly> list = ordSoQuicklyService.selectOrdSoQuicklyList(ordSoQuickly);
        ExcelUtil<OrdSoQuickly> util = new ExcelUtil<OrdSoQuickly>(OrdSoQuickly.class);
        util.exportExcel(response, list, "急出订单数据");
    }

    /**
     * 获取急出订单详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordSoQuicklyService.selectOrdSoQuicklyById(id));
    }

    /**
     * 新增急出订单
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:add')")
    @Log(title = "急出订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdSoQuickly ordSoQuickly)
    {
        AjaxResult ar = null;
        try{
           int ret = ordSoQuicklyService.insertOrdSoQuickly(ordSoQuickly);
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
     * 修改急出订单
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:edit')")
    @Log(title = "急出订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdSoQuickly ordSoQuickly)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoQuicklyService.updateOrdSoQuickly(ordSoQuickly);
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
     * 删除急出订单
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:remove')")
    @Log(title = "急出订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordSoQuicklyService.deleteOrdSoQuicklyByIds(ids);
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
     *  ids 发布 主键
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:enable')")
    @Log(title = "发布", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            OrdSoQuickly obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= ordSoQuicklyService.selectOrdSoQuicklyById(ids[i]);
                ret=ordSoQuicklyService.enable(obj);
            }

            ar = AjaxResult.success("操作成功：[发布]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }


    /**
     *  便捷订单生生单
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:delivery')")
    @Log(title = "转单", businessType = BusinessType.OTHER)
    @PutMapping("/deliveryDirectly/{ids}")
    public AjaxResult deliveryDirectly(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                ordSoQuicklyService.deliveryDirectly(ids);
            ar = AjaxResult.success("操作成功：[发货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }


    /**
     *  快速发货
     */
    //@PreAuthorize("@ss.hasPermi('ord:soQuickly:delivery')")
    @Log(title = "发货", businessType = BusinessType.OTHER)
    @PutMapping("/deliveryQuickly/{ids}")
    public AjaxResult deliveryQuickly(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            ordSoQuicklyService.deliveryQuickly(ids);
            ar = AjaxResult.success("操作成功：[发货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
