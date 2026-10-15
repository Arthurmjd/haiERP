package net.factoryopen.myfactoryai.manufacturing.sup.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequest;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseRequestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购申请Controller
 *
 * @author admin
 * @date 2024-09-16
 */
@RestController
@RequestMapping("/sup/purchaseRequest")
public class SupPurchaseRequestController extends BaseController
{
    @Autowired
    private ISupPurchaseRequestService supPurchaseRequestService;

    /**
     * 查询采购申请列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPurchaseRequest supPurchaseRequest)
    {
        startPage();
        supPurchaseRequest.setCreateBy(SecurityUtils.getUsername());
        List<SupPurchaseRequest> list = supPurchaseRequestService.selectSupPurchaseRequestList(supPurchaseRequest);
        return getDataTable(list);
    }

    /**
     * 查询采购申请列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:list4fk')")
    @GetMapping("/list4fk")
    public TableDataInfo list4fk(SupPurchaseRequest supPurchaseRequest)
    {
        List<SupPurchaseRequest> list = supPurchaseRequestService.selectSupPurchaseRequestList4fk(supPurchaseRequest);
        return getDataTable(list);
    }

    /**
     * 导出采购申请列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:export')")
    @Log(title = "采购申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPurchaseRequest supPurchaseRequest)
    {
        List<SupPurchaseRequest> list = supPurchaseRequestService.selectSupPurchaseRequestList(supPurchaseRequest);
        ExcelUtil<SupPurchaseRequest> util = new ExcelUtil<SupPurchaseRequest>(SupPurchaseRequest.class);
        util.exportExcel(response, list, "采购申请数据");
    }

    /**
     * 获取采购申请详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPurchaseRequestService.selectSupPurchaseRequestById(id));
    }

    /**
     * 新增采购申请
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:add')")
    @Log(title = "采购申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPurchaseRequest supPurchaseRequest)
    {
        AjaxResult ar = null;
        try{
           int ret = supPurchaseRequestService.insertSupPurchaseRequest(supPurchaseRequest);
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
     * 修改采购申请
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:edit')")
    @Log(title = "采购申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPurchaseRequest supPurchaseRequest)
    {
        AjaxResult ar = null;
        try{
            if (supPurchaseRequest.getStatus()!= BillStatus.BASE_DRAFT
                    && supPurchaseRequest.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = supPurchaseRequestService.updateSupPurchaseRequest(supPurchaseRequest);
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
     * 删除采购申请
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:remove')")
    @Log(title = "采购申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchaseRequestService.deleteSupPurchaseRequestByIds(ids);
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
     *  ids 请审 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequest:auditReq')")
    @Log(title = "请审", businessType = BusinessType.OTHER)
    @PutMapping("/auditReq/{ids}")
    public AjaxResult auditReq(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            SupPurchaseRequest obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchaseRequestService.selectSupPurchaseRequestById(ids[i]);
                ret=supPurchaseRequestService.auditReq(obj);
            }

            ar = AjaxResult.success("操作成功：[请审]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
