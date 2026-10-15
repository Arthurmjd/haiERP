package net.factoryopen.myfactoryai.manufacturing.snc.controller;

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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGr;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncGrService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 来料收货登记Controller
 *
 * @author admin
 * @date 2024-07-26
 */
@RestController
@RequestMapping("/snc/gr")
public class SncGrController extends BaseController
{
    @Autowired
    private ISncGrService sncGrService;

    /**
     * 查询来料收货登记列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncGr sncGr)
    {
        startPage();
        List<SncGr> list = sncGrService.selectSncGrList(sncGr);
        return getDataTable(list);
    }

    /**
     * 导出来料收货登记列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:export')")
    @Log(title = "来料收货登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncGr sncGr)
    {
        List<SncGr> list = sncGrService.selectSncGrList(sncGr);
        ExcelUtil<SncGr> util = new ExcelUtil<SncGr>(SncGr.class);
        util.exportExcel(response, list, "来料收货登记数据");
    }

    /**
     * 获取来料收货登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncGrService.selectSncGrById(id));
    }

    /**
     * 新增来料收货登记
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:add')")
    @Log(title = "来料收货登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncGr sncGr)
    {
        AjaxResult ar = null;
        try{
           int ret = sncGrService.insertSncGr(sncGr);
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
     * 修改来料收货登记
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:edit')")
    @Log(title = "来料收货登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncGr sncGr)
    {
        AjaxResult ar = null;
        try{
            int ret = sncGrService.updateSncGr(sncGr);
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
     * 删除来料收货登记
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:remove')")
    @Log(title = "来料收货登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncGrService.deleteSncGrByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('snc:gr:enable')")
    @Log(title = "发放", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SncGr obj = null;
            if (ids!=null){
                for (Long id:ids){
                    obj = sncGrService.selectSncGrById(id);
                    if(obj.getStatus()!= BillStatus.BASE_DRAFT && obj.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "对象[" + obj.getBillNo() + "]在当前状态下不能发布！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                sncGrService.enable(obj);
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
    //@PreAuthorize("@ss.hasPermi('snc:gr:changeSncGr')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeSncGr")
    public AjaxResult changeSncGr(@RequestBody SncGr sncGr)
    {
        AjaxResult ar = null;
        try{
            if (sncGr.getStatus()< BillStatus.BASE_ENABLE
                    || sncGr.getStatus()== BillStatus.LIFE_FROZEN || sncGr.getStatus()==BillStatus.LIFE_DEAD
                    || sncGr.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！收货单[" + sncGr.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            sncGrService.changeSncGr(sncGr);
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
    //@PreAuthorize("@ss.hasPermi('snc:gr:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            SncGr obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                sncGrService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('snc:gr:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SncGr obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                sncGrService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('snc:gr:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SncGr obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= sncGrService.selectSncGrById(ids[i]);
                sncGrService.disable(obj);
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
     * 入库
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:stockin')")
    @Log(title = "入库", businessType = BusinessType.OTHER)
    @PutMapping("/stockin")
    public AjaxResult stockin(@RequestBody SncGr sncGr)
    {
        AjaxResult ar = null;
        try{
            sncGrService.stockin(sncGr);
            ar = AjaxResult.success("操作成功：[入库]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 请检
     */
    //@PreAuthorize("@ss.hasPermi('snc:gr:requestInspection')")
    @Log(title = "请检", businessType = BusinessType.OTHER)
    @PutMapping("/requestInspection")
    public AjaxResult requestInspection(@RequestBody SncGr sncGr)
    {
        AjaxResult ar = null;
        try{
            sncGrService.requestInspection(sncGr);
            ar = AjaxResult.success("操作成功：[请检]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
