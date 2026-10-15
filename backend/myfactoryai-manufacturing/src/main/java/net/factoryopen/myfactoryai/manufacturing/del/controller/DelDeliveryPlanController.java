package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlan;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发货计划维护Controller
 *
 * @author admin
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/deliveryPlan")
public class DelDeliveryPlanController extends BaseController
{
    @Autowired
    private IDelDeliveryPlanService delDeliveryPlanService;

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanDraftList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanPublishingList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanProcessingList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanFinishingList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanHistoryList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 查询发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(DelDeliveryPlan delDeliveryPlan)
    {
        if(delDeliveryPlan!=null && delDeliveryPlan.getPageEnable()!=null && delDeliveryPlan.getPageEnable()) startPage();
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanAllList(delDeliveryPlan);
        return getDataTable(list);
    }

    /**
     * 导出发货计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:export')")
    @Log(title = "发货计划维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelDeliveryPlan delDeliveryPlan)
    {
        List<DelDeliveryPlan> list = delDeliveryPlanService.selectDelDeliveryPlanList(delDeliveryPlan);
        ExcelUtil<DelDeliveryPlan> util = new ExcelUtil<DelDeliveryPlan>(DelDeliveryPlan.class);
        util.exportExcel(response, list, "发货计划维护数据");
    }

    /**
     * 获取发货计划维护详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delDeliveryPlanService.selectDelDeliveryPlanById(id));
    }

    /**
     * 新增发货计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:add')")
    @Log(title = "发货计划维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelDeliveryPlan delDeliveryPlan)
    {
        AjaxResult ar = null;
        try{
           int ret = delDeliveryPlanService.insertDelDeliveryPlan(delDeliveryPlan);
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
     * 修改发货计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:edit')")
    @Log(title = "发货计划维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelDeliveryPlan delDeliveryPlan)
    {
        AjaxResult ar = null;
        try{
            if (delDeliveryPlan.getStatus()!= BillStatus.BASE_DRAFT
                    && delDeliveryPlan.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delDeliveryPlanService.updateDelDeliveryPlan(delDeliveryPlan);
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
     * 删除发货计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:remove')")
    @Log(title = "发货计划维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delDeliveryPlanService.deleteDelDeliveryPlanByIds(ids);
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
     *  ids 冻结 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            DelDeliveryPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                delDeliveryPlanService.froze(obj);
            }

            ar = AjaxResult.success("操作成功：[冻结]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 解冻 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                delDeliveryPlanService.unfroze(obj);
            }
            ar = AjaxResult.success("操作成功：[解冻]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 停用 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                delDeliveryPlanService.disable(obj);
            }

            ar = AjaxResult.success("操作成功：[停用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 强结 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:finishforcely')")
    @Log(title = "强结", businessType = BusinessType.OTHER)
    @PutMapping("/finishforcely/{ids}")
    public AjaxResult finishforcely(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                if (obj.getStatus()<BillStatus.BASE_ENABLE
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()==BillStatus.FINISH_FORCELY || obj.getStatus()==BillStatus.FINISH_FORCELY){
                    String msg="结案["+obj.getBillNo()+"]非法！状态为冻结、停用、结案及未发布生效的对象不能结案。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryPlanService.selectDelDeliveryPlanById(ids[i]);
                delDeliveryPlanService.finishforcely(obj);
            }
            ar = AjaxResult.success("操作成功：[强结]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }

        return ar;
    }
    /**
     * 转单
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryPlan:billDelivery')")
    @Log(title = "转单", businessType = BusinessType.OTHER)
    @PutMapping("/billDelivery/{ids}")
    public AjaxResult billDelivery(@PathVariable Long[] ids, @RequestBody DelDeliveryPlan delDeliveryPlan)
    {
        AjaxResult ar = null;
        try{
          delDeliveryPlanService.billDelivery(ids, delDeliveryPlan);
          ar = AjaxResult.success("操作成功：[转单]"+"执行完成！");
        }
        catch (Exception e){
           e.printStackTrace();
           ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
      }

}
