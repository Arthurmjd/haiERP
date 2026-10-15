package net.factoryopen.myfactoryai.manufacturing.sup.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购单管理Controller
 *
 * @author admin
 * @date 2024-09-23
 */
@RestController
@RequestMapping("/sup/po")
public class SupPoController extends BaseController
{
    @Autowired
    private ISupPoService supPoService;

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoDraftList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoPublishingList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoProcessingList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoFinishingList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoHistoryList(supPo);
        return getDataTable(list);
    }

    /**
     * 查询采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(SupPo supPo)
    {
        if(supPo!=null && supPo.getPageEnable()!=null && supPo.getPageEnable()) startPage();
        List<SupPo> list = supPoService.selectSupPoAllList(supPo);
        return getDataTable(list);
    }

    /**
     * 导出采购单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:export')")
    @Log(title = "采购单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPo supPo)
    {
        List<SupPo> list = supPoService.selectSupPoList(supPo);
        ExcelUtil<SupPo> util = new ExcelUtil<SupPo>(SupPo.class);
        util.exportExcel(response, list, "采购单管理数据");
    }

    /**
     * 获取采购单管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPoService.selectSupPoById(id));
    }

    /**
     * 新增采购单管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:add')")
    @Log(title = "采购单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPo supPo)
    {
        AjaxResult ar = null;
        try{
           int ret = supPoService.insertSupPo(supPo);
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
     * 修改采购单管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:edit')")
    @Log(title = "采购单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPo supPo)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoService.updateSupPo(supPo);
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
     * 删除采购单管理
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:remove')")
    @Log(title = "采购单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPoService.deleteSupPoByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('sup:po:enable')")
    @Log(title = "发放", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPo obj = null;
            if (ids!=null){
                for (Long id:ids){
                    obj = supPoService.selectSupPoById(id);
                    if(obj.getStatus()!= BillStatus.BASE_DRAFT && obj.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "采购单[" + obj.getBillNo() + "]在当前状态下不能发布！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                supPoService.enable(obj);
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
    //@PreAuthorize("@ss.hasPermi('sup:po:changeSupPo')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeSupPo")
    public AjaxResult changeSupPo(@RequestBody SupPo supPo)
    {
        AjaxResult ar = null;
        try{
            if (supPo.getStatus()< BillStatus.BASE_ENABLE
                    || supPo.getStatus()== BillStatus.LIFE_FROZEN || supPo.getStatus()==BillStatus.LIFE_DEAD
                    || supPo.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！采购单[" + supPo.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            supPoService.changeSupPo(supPo);
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
    //@PreAuthorize("@ss.hasPermi('sup:po:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            SupPo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                supPoService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('sup:po:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                supPoService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('sup:po:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                supPoService.disable(obj);
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
     *  ids 结案 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:finish')")
    @Log(title = "结案", businessType = BusinessType.OTHER)
    @PutMapping("/finish/{ids}")
    public AjaxResult finish(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                if (obj.getStatus()!= BillStatus.FINISH_WAITING){
                    String msg="结案["+obj.getBillNo()+"]非法！非待结案状态的对象不能结案";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPoService.selectSupPoById(ids[i]);
                obj.setStatus(BillStatus.FINISH);
                obj.refreshUpdatingStamp();
                supPoService.updateSupPo(obj);
            }
            ar = AjaxResult.success("操作成功：[结案]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 通知收货
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:noticeReceiving')")
    @Log(title = "通知收货", businessType = BusinessType.OTHER)
    @PutMapping("/noticeReceiving")
    public AjaxResult noticeReceiving(@RequestBody SupPo supPo)
    {
        AjaxResult ar = null;
        try{
            supPoService.noticeReceiving(supPo);
            ar = AjaxResult.success("操作成功：[通知收货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 通知退货
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:noticeReceiving')")
    @Log(title = "通知退货", businessType = BusinessType.OTHER)
    @PutMapping("/noticeReturning")
    public AjaxResult noticeReturning(@RequestBody SupPoItem poitem)
    {
        AjaxResult ar = null;
        try{
            supPoService.noticeReturning(poitem);
            ar = AjaxResult.success("操作成功：[通知退货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 放弃退货
     */
    //@PreAuthorize("@ss.hasPermi('sup:po:ignoreReturning')")
    @Log(title = "通知退货", businessType = BusinessType.OTHER)
    @PutMapping("/ignoreReturning")
    public AjaxResult ignoreReturning(@RequestBody SupPoItem poitem)
    {
        AjaxResult ar = null;
        try{
            supPoService.ignoreReturning(poitem);
            ar = AjaxResult.success("操作成功：[放弃退货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
