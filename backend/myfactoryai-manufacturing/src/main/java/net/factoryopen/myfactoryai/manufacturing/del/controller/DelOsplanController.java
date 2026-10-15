package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelOsplanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外协计划维护Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/osplan")
public class DelOsplanController extends BaseController
{
    @Autowired
    private IDelOsplanService delOsplanService;

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanDraftList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanPublishingList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanProcessingList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanFinishingList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanHistoryList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 查询外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(DelOsplan delOsplan)
    {
        startPage();
        List<DelOsplan> list = delOsplanService.selectDelOsplanAllList(delOsplan);
        return getDataTable(list);
    }

    /**
     * 导出外协计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:export')")
    @Log(title = "外协计划维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelOsplan delOsplan)
    {
        List<DelOsplan> list = delOsplanService.selectDelOsplanList(delOsplan);
        ExcelUtil<DelOsplan> util = new ExcelUtil<DelOsplan>(DelOsplan.class);
        util.exportExcel(response, list, "外协计划维护数据");
    }

    /**
     * 获取外协计划维护详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delOsplanService.selectDelOsplanById(id));
    }

    /**
     * 新增外协计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:add')")
    @Log(title = "外协计划维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelOsplan delOsplan)
    {
        AjaxResult ar = null;
        try{
           int ret = delOsplanService.insertDelOsplan(delOsplan);
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
     * 修改外协计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:edit')")
    @Log(title = "外协计划维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelOsplan delOsplan)
    {
        AjaxResult ar = null;
        try{
            if (delOsplan.getStatus()!= BillStatus.BASE_DRAFT
                    && delOsplan.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delOsplanService.updateDelOsplan(delOsplan);
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
     * 删除外协计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:remove')")
    @Log(title = "外协计划维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delOsplanService.deleteDelOsplanByIds(ids);
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
     * 变更
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:changeDelOsplan')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeDelOsplan")
    public AjaxResult changeDelOsplan(@RequestBody DelOsplan delOsplan)
    {
        AjaxResult ar = null;
        try{
            if (delOsplan.getStatus()< BillStatus.BASE_ENABLE
                    || delOsplan.getStatus()== BillStatus.LIFE_FROZEN || delOsplan.getStatus()==BillStatus.LIFE_DEAD
                    || delOsplan.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！转包计划[" + delOsplan.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            delOsplanService.changeDelOsplan(delOsplan);
            ar = AjaxResult.success("操作成功：[变更]"+"执行完成！");
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
    //@PreAuthorize("@ss.hasPermi('del:osplan:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelOsplan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                delOsplanService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:osplan:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelOsplan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                delOsplanService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:osplan:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelOsplan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                delOsplanService.disable(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:osplan:finishforcely')")
    @Log(title = "强结", businessType = BusinessType.OTHER)
    @PutMapping("/finishforcely/{ids}")
    public AjaxResult finishforcely(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelOsplan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                if (obj.getStatus()<BillStatus.BASE_ENABLE
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()==BillStatus.FINISH_FORCELY || obj.getStatus()==BillStatus.FINISH_FORCELY){
                    String msg="结案["+obj.getBillNo()+"]非法！状态为冻结、停用、结案及未发布生效的对象不能结案。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delOsplanService.selectDelOsplanById(ids[i]);
                delOsplanService.finishforcely(obj);
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
     * 收货
     */
    //@PreAuthorize("@ss.hasPermi('del:osplan:toPuchasing')")
    @Log(title = "转购", businessType = BusinessType.OTHER)
    @PutMapping("/toPr/{ids}")
    public AjaxResult toPr(@PathVariable("ids") Long[] ids)
    {
        String msg=null;

        try{
            delOsplanService.toPr(ids);
            msg="操作成功：[收货]"+"执行完成！";
        }
        catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }

        return AjaxResult.success(msg);
    }

}
