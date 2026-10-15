package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingdrawingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单工序图Controller
 *
 * @author admin
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woRoutingdrawing")
public class PlnWoRoutingdrawingController extends BaseController
{
    @Autowired
    private IPlnWoRoutingdrawingService plnWoRoutingdrawingService;

    /**
     * 查询工单工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        startPage();
        List<PlnWoRoutingdrawing> list = plnWoRoutingdrawingService.selectPlnWoRoutingdrawingList(plnWoRoutingdrawing);
        return getDataTable(list);
    }

    /**
     * 导出工单工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:export')")
    @Log(title = "工单工序图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        List<PlnWoRoutingdrawing> list = plnWoRoutingdrawingService.selectPlnWoRoutingdrawingList(plnWoRoutingdrawing);
        ExcelUtil<PlnWoRoutingdrawing> util = new ExcelUtil<PlnWoRoutingdrawing>(PlnWoRoutingdrawing.class);
        util.exportExcel(response, list, "工单工序图数据");
    }

    /**
     * 获取工单工序图详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingdrawingService.selectPlnWoRoutingdrawingById(id));
    }

    /**
     * 新增工单工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:add')")
    @Log(title = "工单工序图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingdrawingService.insertPlnWoRoutingdrawing(plnWoRoutingdrawing);
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
     * 修改工单工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:edit')")
    @Log(title = "工单工序图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRoutingdrawing plnWoRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingdrawingService.updatePlnWoRoutingdrawing(plnWoRoutingdrawing);
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
     * 删除工单工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingdrawing:remove')")
    @Log(title = "工单工序图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingdrawingService.deletePlnWoRoutingdrawingByIds(ids);
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
