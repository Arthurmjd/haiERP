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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingdrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingdrawingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次工序图Controller
 *
 * @author admin
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlRoutingdrawing")
public class PlnMlRoutingdrawingController extends BaseController
{
    @Autowired
    private IPlnMlRoutingdrawingService plnMlRoutingdrawingService;

    /**
     * 查询批次工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        startPage();
        List<PlnMlRoutingdrawing> list = plnMlRoutingdrawingService.selectPlnMlRoutingdrawingList(plnMlRoutingdrawing);
        return getDataTable(list);
    }

    /**
     * 导出批次工序图列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:export')")
    @Log(title = "批次工序图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        List<PlnMlRoutingdrawing> list = plnMlRoutingdrawingService.selectPlnMlRoutingdrawingList(plnMlRoutingdrawing);
        ExcelUtil<PlnMlRoutingdrawing> util = new ExcelUtil<PlnMlRoutingdrawing>(PlnMlRoutingdrawing.class);
        util.exportExcel(response, list, "批次工序图数据");
    }

    /**
     * 获取批次工序图详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlRoutingdrawingService.selectPlnMlRoutingdrawingById(id));
    }

    /**
     * 新增批次工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:add')")
    @Log(title = "批次工序图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlRoutingdrawingService.insertPlnMlRoutingdrawing(plnMlRoutingdrawing);
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
     * 修改批次工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:edit')")
    @Log(title = "批次工序图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlRoutingdrawing plnMlRoutingdrawing)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingdrawingService.updatePlnMlRoutingdrawing(plnMlRoutingdrawing);
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
     * 删除批次工序图
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlRoutingdrawing:remove')")
    @Log(title = "批次工序图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlRoutingdrawingService.deletePlnMlRoutingdrawingByIds(ids);
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
