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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/pln/wo")
public class PlnWoController extends BaseController
{
    @Autowired
    private IPlnWoService plnWoService;

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoDraftList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoPublishingList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoProcessingList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoFinishingList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoHistoryList(plnWo);
        return getDataTable(list);
    }

    /**
     * 查询工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(PlnWo plnWo)
    {
        startPage();
        List<PlnWo> list = plnWoService.selectPlnWoAllList(plnWo);
        return getDataTable(list);
    }

    /**
     * 导出工单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:export')")
    @Log(title = "工单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWo plnWo)
    {
        List<PlnWo> list = plnWoService.selectPlnWoList(plnWo);
        ExcelUtil<PlnWo> util = new ExcelUtil<PlnWo>(PlnWo.class);
        util.exportExcel(response, list, "工单管理数据");
    }

    /**
     * 获取工单管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoService.selectPlnWoById(id));
    }

    /**
     * 新增工单管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:add')")
    @Log(title = "工单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWo plnWo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoService.insertPlnWo(plnWo);
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
     * 修改工单管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:edit')")
    @Log(title = "工单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWo plnWo)
    {
        AjaxResult ar = null;
        try{
            if (plnWo.getStatus()!= BillStatus.BASE_DRAFT
                    && plnWo.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = plnWoService.updatePlnWo(plnWo);
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
     * 删除工单管理
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:remove')")
    @Log(title = "工单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoService.deletePlnWoByIds(ids);
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
     *  ids 发放 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:wo:enable')")
    @Log(title = "发放", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            PlnWo obj = null;
            if (ids!=null){
                for (Long id:ids){
                    obj = plnWoService.selectPlnWoById(id);
                    if(obj.getStatus()!= BillStatus.BASE_DRAFT && obj.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "工单[" + obj.getBillNo() + "]在当前状态下不能发布！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnWoService.selectPlnWoById(ids[i]);
                plnWoService.enable(obj);
            }
            ar = AjaxResult.success("操作成功：[发放]"+ids.length+"条数据！");
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
    //@PreAuthorize("@ss.hasPermi('pln:wo:changeWo')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeWo")
    public AjaxResult changeWo(@RequestBody PlnWo plnWo)
    {
        AjaxResult ar = null;
        try{
            if (plnWo.getStatus()< BillStatus.BASE_ENABLE
                    || plnWo.getStatus()== BillStatus.LIFE_FROZEN || plnWo.getStatus()==BillStatus.LIFE_DEAD
                    || plnWo.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！工单[" + plnWo.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            plnWoService.changeWo(plnWo);
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
    //@PreAuthorize("@ss.hasPermi('pln:wo:frozeWo')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/frozeWo/{ids}")
    public AjaxResult frozeWo(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            PlnWo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnWoService.selectPlnWoById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnWoService.selectPlnWoById(ids[i]);
                plnWoService.frozeWo(obj);
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
    //@PreAuthorize("@ss.hasPermi('pln:wo:disableWo')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disableWo/{ids}")
    public AjaxResult disableWo(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            PlnWo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnWoService.selectPlnWoById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= plnWoService.selectPlnWoById(ids[i]);
                plnWoService.disableWo(obj);
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
