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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseReqAudit;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseReqAuditService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请购审核Controller
 *
 * @author admin
 * @date 2024-09-16
 */
@RestController
@RequestMapping("/sup/purchaseRequestAudit")
public class SupPurchaseReqAuditController extends BaseController
{
    @Autowired
    private ISupPurchaseReqAuditService supPurchaseReqAuditService;

    /**
     * 查询请购审核列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPurchaseReqAudit supPurchaseReqAudit)
    {
        startPage();
        List<SupPurchaseReqAudit> list = supPurchaseReqAuditService.selectSupPurchaseReqAuditList(supPurchaseReqAudit);
        return getDataTable(list);
    }

    /**
     * 导出请购审核列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:export')")
    @Log(title = "请购审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPurchaseReqAudit supPurchaseReqAudit)
    {
        List<SupPurchaseReqAudit> list = supPurchaseReqAuditService.selectSupPurchaseReqAuditList(supPurchaseReqAudit);
        ExcelUtil<SupPurchaseReqAudit> util = new ExcelUtil<SupPurchaseReqAudit>(SupPurchaseReqAudit.class);
        util.exportExcel(response, list, "请购审核数据");
    }

    /**
     * 获取请购审核详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPurchaseReqAuditService.selectSupPurchaseReqAuditById(id));
    }

    /**
     * 新增请购审核
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:add')")
    @Log(title = "请购审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPurchaseReqAudit supPurchaseReqAudit)
    {
        AjaxResult ar = null;
        try{
           int ret = supPurchaseReqAuditService.insertSupPurchaseReqAudit(supPurchaseReqAudit);
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
     * 修改请购审核
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:edit')")
    @Log(title = "请购审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPurchaseReqAudit supPurchaseReqAudit)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchaseReqAuditService.updateSupPurchaseReqAudit(supPurchaseReqAudit);
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
     * 删除请购审核
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:remove')")
    @Log(title = "请购审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchaseReqAuditService.deleteSupPurchaseReqAuditByIds(ids);
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
     *  ids 过审 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:auditPass')")
    @Log(title = "过审", businessType = BusinessType.OTHER)
    @PutMapping("/auditPass/{ids}")
    public AjaxResult auditPass(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            SupPurchaseReqAudit obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchaseReqAuditService.selectSupPurchaseReqAuditById(ids[i]);
                ret=supPurchaseReqAuditService.auditPass(obj);
            }

            ar = AjaxResult.success("操作成功：[过审]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 驳审 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:auditReject')")
    @Log(title = "驳审", businessType = BusinessType.OTHER)
    @PutMapping("/auditReject/{ids}")
    public AjaxResult auditReject(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            SupPurchaseReqAudit obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchaseReqAuditService.selectSupPurchaseReqAuditById(ids[i]);
                ret=supPurchaseReqAuditService.auditReject(obj);
            }

            ar = AjaxResult.success("操作成功：[驳审]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 转计划 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchaseRequestAudit:toPlan')")
    @Log(title = "转计划", businessType = BusinessType.OTHER)
    @PutMapping("/toPlan/{ids}")
    public AjaxResult toPlan(@PathVariable Long[] ids)
    {
        AjaxResult ar=null;

        try{
            supPurchaseReqAuditService.toPlan(ids);
            String msg="操作成功：[转计划]"+"执行完成！";
            ar = AjaxResult.success(msg);
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error(e.getMessage());
        }

        return ar;
    }

}
