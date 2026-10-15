package net.factoryopen.myfactoryai.manufacturing.snc.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockRequestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出入库请求Controller
 *
 * @author admin
 * @date 2025-05-17
 */
@RestController
@RequestMapping("/snc/stockRequest")
public class SncStockRequestController extends BaseController
{
    @Autowired
    private ISncStockRequestService sncStockRequestService;

    /**
     * 查询出入库请求列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncStockRequest sncStockRequest)
    {
        startPage();
        sncStockRequest.setCreateBy(SecurityUtils.getUsername());
        List<SncStockRequest> list = sncStockRequestService.selectSncStockRequestList(sncStockRequest);
        return getDataTable(list);
    }

    /**
     * 查询出入库请求列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:list')")
    @GetMapping("/listPMaterial")
    public TableDataInfo listPMaterial(SncStockRequest sncStockRequest)
    {
        startPage();
        sncStockRequest.setCreateBy(SecurityUtils.getUsername());
        List<SncStockRequest> list = sncStockRequestService.selectSncStockRequestPMaterialList(sncStockRequest);
        return getDataTable(list);
    }

    /**
     * 查询出入库请求列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:list')")
    @GetMapping("/listFinished")
    public TableDataInfo listFinished(SncStockRequest sncStockRequest)
    {
        startPage();
        List<SncStockRequest> list = sncStockRequestService.selectSncStockRequestFinishedList(sncStockRequest);
        return getDataTable(list);
    }

    /**
     * 导出出入库请求列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:export')")
    @Log(title = "出入库请求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncStockRequest sncStockRequest)
    {
        List<SncStockRequest> list = sncStockRequestService.selectSncStockRequestList(sncStockRequest);
        ExcelUtil<SncStockRequest> util = new ExcelUtil<SncStockRequest>(SncStockRequest.class);
        util.exportExcel(response, list, "出入库请求数据");
    }

    /**
     * 获取出入库请求详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncStockRequestService.selectSncStockRequestById(id));
    }

    /**
     * 新增出入库请求
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:add')")
    @Log(title = "出入库请求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncStockRequest sncStockRequest)
    {
        AjaxResult ar = null;
        try{
            sncStockRequest.refreshCreatingStamp();
           int ret = sncStockRequestService.insertSncStockRequest(sncStockRequest);
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
     * 修改出入库请求
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:edit')")
    @Log(title = "出入库请求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncStockRequest sncStockRequest)
    {
        AjaxResult ar = null;
        try{
            sncStockRequest.refreshUpdatingStamp();
            int ret = sncStockRequestService.updateSncStockRequest(sncStockRequest);
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
     * 删除出入库请求
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:remove')")
    @Log(title = "出入库请求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncStockRequestService.deleteSncStockRequestByIds(ids);
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
     *  ids 启用 主键
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:enable')")
    @Log(title = "启用", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            SncStockRequest obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= sncStockRequestService.selectSncStockRequestById(ids[i]);
                ret=sncStockRequestService.enable(obj);
            }

            ar = AjaxResult.success("操作成功：[启用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 请库 主键
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockRequest:requestStock')")
    @Log(title = "请库", businessType = BusinessType.OTHER)
    @PutMapping("/requestStock/{ids}")
    public AjaxResult requestStock(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                sncStockRequestService.requestStock(ids);
            ar = AjaxResult.success("操作成功：[请库]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
