package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryDealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 交货筹划Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/deliveryDeal")
public class DelDeliveryDealController extends BaseController
{
    @Autowired
    private IDelDeliveryDealService delDeliveryDealService;

    @Autowired
    private IOrdSoService soService;
    @Autowired
    private IChaCustomerService customerService;

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealList(delDeliveryDeal);
//        if(list!=null){
//            for (DelDeliveryDeal item:list){
//                OrdSo so = soService.selectOrdSoById(item.getSoId());
//                ChaCustomer customer = customerService.selectChaCustomerById(so.getCustomerId());
//                item.setContractNo(so.getContractNo());
//                item.setCustomerNo(customer!=null?customer.getCustomerCode():null);
//                item.setCustomerName(customer!=null?customer.getCustomerName():null);
//            }
//        }

        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealDraftList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealPublishingList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listPublished')")
    @GetMapping("/listPublished")
    public TableDataInfo listPublished(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealPublishedList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealProcessingList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealFinishingList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealHistoryList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 查询交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(DelDeliveryDeal delDeliveryDeal)
    {
        startPage();
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealAllList(delDeliveryDeal);
        return getDataTable(list);
    }

    /**
     * 导出交货筹划列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:export')")
    @Log(title = "交货筹划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelDeliveryDeal delDeliveryDeal)
    {
        List<DelDeliveryDeal> list = delDeliveryDealService.selectDelDeliveryDealList(delDeliveryDeal);
        ExcelUtil<DelDeliveryDeal> util = new ExcelUtil<DelDeliveryDeal>(DelDeliveryDeal.class);
        util.exportExcel(response, list, "交货数据");
    }

    /**
     * 获取交货筹划详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delDeliveryDealService.selectDelDeliveryDealById(id));
    }

    /**
     * 新增交货筹划
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:add')")
    @Log(title = "交货筹划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
           int ret = delDeliveryDealService.insertDelDeliveryDeal(delDeliveryDeal);
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
     * 修改交货筹划
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:edit')")
    @Log(title = "交货筹划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
            if (delDeliveryDeal.getStatus()!= BillStatus.BASE_DRAFT
                    && delDeliveryDeal.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delDeliveryDealService.updateDelDeliveryDeal(delDeliveryDeal);
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
     * 删除交货筹划
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:remove')")
    @Log(title = "交货筹划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delDeliveryDealService.deleteDelDeliveryDealByIds(ids);
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
     * 发货
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:planDelivery')")
    @Log(title = "发货", businessType = BusinessType.OTHER)
    @PutMapping("/planDelivery")
    public AjaxResult planDelivery(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
            delDeliveryDealService.planDelivery(delDeliveryDeal);
            ar = AjaxResult.success("操作成功：[发货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 投产
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:planMps')")
    @Log(title = "投产", businessType = BusinessType.OTHER)
    @PutMapping("/planMps")
    public AjaxResult planMps(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
            delDeliveryDealService.planMps(delDeliveryDeal);
            ar = AjaxResult.success("操作成功：[投产]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 外协
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryDeal:planOs')")
    @Log(title = "外协", businessType = BusinessType.OTHER)
    @PutMapping("/planOs")
    public AjaxResult planOs(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
            delDeliveryDealService.planOs(delDeliveryDeal);
            ar = AjaxResult.success("操作成功：[转包]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 转正料号
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:normalizeCode')")
    @Log(title = "转改料号", businessType = BusinessType.OTHER)
    @PutMapping("/normalizeCode")
    public AjaxResult normalizeCode(@RequestBody DelDeliveryDeal delDeliveryDeal)
    {
        AjaxResult ar = null;
        try{
            delDeliveryDealService.normalizeCode(delDeliveryDeal);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
