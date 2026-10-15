package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/ml")
public class PlnMlController extends BaseController
{
    @Autowired
    private IPlnMlService plnMlService;

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlDraftList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlPublishingList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlProcessingList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlFinishingList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlHistoryList(plnMl);
        return getDataTable(list);
    }

    /**
     * 查询批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(PlnMl plnMl)
    {
        startPage();
        List<PlnMl> list = plnMlService.selectPlnMlAllList(plnMl);
        return getDataTable(list);
    }

    /**
     * 导出批次管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:export')")
    @Log(title = "批次管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMl plnMl)
    {
        List<PlnMl> list = plnMlService.selectPlnMlList(plnMl);
        ExcelUtil<PlnMl> util = new ExcelUtil<PlnMl>(PlnMl.class);
        util.exportExcel(response, list, "批次管理数据");
    }

    /**
     * 获取批次管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlService.selectPlnMlById(id));
    }

    /**
     * 新增批次管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:add')")
    @Log(title = "批次管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMl plnMl)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlService.insertPlnMl(plnMl);
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
     * 修改批次管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:edit')")
    @Log(title = "批次管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMl plnMl)
    {
        AjaxResult ar = null;
        try{
            if (plnMl.getStatus()!= BillStatus.BASE_DRAFT
                    && plnMl.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = plnMlService.updatePlnMl(plnMl);
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
     * 删除批次管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:remove')")
    @Log(title = "批次管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlService.deletePlnMlByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('pln:ml:changeMl')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeMl")
    public AjaxResult changeMl(@RequestBody PlnMl plnMl)
    {
        AjaxResult ar = null;
        try{
            if (plnMl.getStatus()< BillStatus.BASE_ENABLE
                    || plnMl.getStatus()== BillStatus.LIFE_FROZEN || plnMl.getStatus()==BillStatus.LIFE_DEAD
                    || plnMl.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！制造批[" + plnMl.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            plnMlService.updatePlnMl(plnMl);
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
    //@PreAuthorize("@ss.hasPermi('pln:ml:frozeMl')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/frozeMl/{ids}")
    public AjaxResult frozeMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            PlnMl obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                plnMlService.frozeMl(obj);
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
     *  ids 停用 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:disableMl')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disableMl/{ids}")
    public AjaxResult disableMl(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            PlnMl obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                plnMlService.disableMl(obj);
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
     * 变更
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:change')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/change")
    public AjaxResult change(@RequestBody PlnMl plnMl)
    {
        AjaxResult ar = null;
        try{
            if (plnMl.getStatus()< BillStatus.BASE_ENABLE
                    || plnMl.getStatus()== BillStatus.LIFE_FROZEN || plnMl.getStatus()==BillStatus.LIFE_DEAD
                    || plnMl.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！制造批[" + plnMl.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            plnMlService.change(plnMl);
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
    //@PreAuthorize("@ss.hasPermi('pln:ml:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            PlnMl obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                plnMlService.froze(obj);
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
     *  ids 停用 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:ml:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            PlnMl obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnMlService.selectPlnMlById(ids[i]);
                plnMlService.disable(obj);
            }
            ar = AjaxResult.success("操作成功：[停用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}
