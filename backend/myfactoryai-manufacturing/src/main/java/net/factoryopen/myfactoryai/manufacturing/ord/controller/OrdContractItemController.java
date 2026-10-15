package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/contractItem")
public class OrdContractItemController extends BaseController {
    @Autowired
    private IOrdContractItemService ordContractItemService;

    @Autowired
    private IOrdContractService contractService;

    /**
     * 查询订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdContractItem ordContractItem) {
        if(ordContractItem!=null && ordContractItem.getPageEnable()!=null && ordContractItem.getPageEnable()) startPage();
        List<OrdContractItem> list = ordContractItemService.selectOrdContractItemList(ordContractItem);
        return getDataTable(list);
    }

    /**
     * 导出合同明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:export')")
    @Log(title = "合同明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdContractItem ordContractItem) {
        List<OrdContractItem> list = ordContractItemService.selectOrdContractItemList(ordContractItem);
        ExcelUtil<OrdContractItem> util = new ExcelUtil<OrdContractItem>(OrdContractItem.class);
        util.exportExcel(response, list, "合同明细数据");
    }

    /**
     * 获取订单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(ordContractItemService.selectOrdContractItemById(id));
    }

    /**
     * 新增订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:add')")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdContractItem ordContractItem) {
        AjaxResult ar = null;
        try {
            int ret = ordContractItemService.insertOrdContractItem(ordContractItem);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[新增]1条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[新增]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

    /**
     * 修改订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:edit')")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdContractItem ordContractItem) {
        AjaxResult ar = null;
        try {
            OrdContract so = contractService.selectOrdContractById(ordContractItem.getContractId());
            if(so.getStatus()!= BillStatus.BASE_DRAFT && so.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "合同[" + so.getContractNo() + "]在当前状态下不能修改！";
                throw new StatusUnavailableException(msg);
            }

            int ret = ordContractItemService.updateOrdContractItem(ordContractItem);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[修改]" + ret + "条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

    /**
     * 删除订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:remove')")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        AjaxResult ar = null;
        try {
            if (ids!=null){
                for (Long id:ids){
                    OrdContractItem item  = ordContractItemService.selectOrdContractItemById(id);
                    OrdContract contract = contractService.selectOrdContractById(item.getContractId());
                    if(contract.getStatus()!= BillStatus.BASE_DRAFT && contract.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                        String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能删除！]";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret = ordContractItemService.deleteOrdContractItemByIds(ids);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[删除]" + ret + "条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[删除]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

    /**
     * 请求临时料号
     */
    //@PreAuthorize("@ss.hasPermi('ord:contractItem:requestTempProduct')")
    @Log(title = "请求临时料号", businessType = BusinessType.OTHER)
    @PutMapping("/requestTempProduct/{ids}")
    public AjaxResult requestTempProduct(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            ordContractItemService.requestTempProduct(ids);
            ar = AjaxResult.success("操作成功：[请求临时料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
