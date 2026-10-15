package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
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
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdComplainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客诉登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/complain")
public class OrdComplainController extends BaseController
{
    @Autowired
    private IOrdComplainService ordComplainService;

    /**
     * 查询客诉登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdComplain ordComplain)
    {
        startPage();
        List<OrdComplain> list = ordComplainService.selectOrdComplainList(ordComplain);
        return getDataTable(list);
    }

    /**
     * 导出客诉登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:export')")
    @Log(title = "客诉登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdComplain ordComplain)
    {
        List<OrdComplain> list = ordComplainService.selectOrdComplainList(ordComplain);
        ExcelUtil<OrdComplain> util = new ExcelUtil<OrdComplain>(OrdComplain.class);
        util.exportExcel(response, list, "客诉登记数据");
    }

    /**
     * 获取客诉登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ordComplainService.selectOrdComplainById(id));
    }

    /**
     * 新增客诉登记
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:add')")
    @Log(title = "客诉登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdComplain ordComplain)
    {
        AjaxResult ar = null;
        try{
           int ret = ordComplainService.insertOrdComplain(ordComplain);
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
     * 修改客诉登记
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:edit')")
    @Log(title = "客诉登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdComplain ordComplain)
    {
        AjaxResult ar = null;
        try{
            int ret = ordComplainService.updateOrdComplain(ordComplain);
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
     * 删除客诉登记
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:remove')")
    @Log(title = "客诉登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ordComplainService.deleteOrdComplainByIds(ids);
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
     *  ids 发布 主键
     */
    //@PreAuthorize("@ss.hasPermi('ord:complain:enableOrdComplain')")
    @Log(title = "发布", businessType = BusinessType.OTHER)
    @PutMapping("/enableOrdComplain/{ids}")
    public AjaxResult enableOrdComplain(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            OrdComplain obj = null;
            if (ids!=null){
                for (Long id:ids){
                    obj = ordComplainService.selectOrdComplainById(id);
                    if(obj.getStatus()!= BillStatus.BASE_DRAFT && obj.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "客诉[" + obj.getBillNo() + "]在当前状态下不能发布！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= ordComplainService.selectOrdComplainById(ids[i]);
                ordComplainService.enableOrdComplain(obj);
            }
            ar = AjaxResult.success("操作成功：[发布]"+ids.length+"条数据！");
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
    //@PreAuthorize("@ss.hasPermi('ord:complain:requestInspect')")
    @Log(title = "请检", businessType = BusinessType.OTHER)
    @PutMapping("/requestInspect")
    public AjaxResult requestInspect(@RequestBody OrdComplain ordComplain)
    {
        AjaxResult ar = null;
        try{
            ordComplainService.requestInspect(ordComplain);
            ar = AjaxResult.success("操作成功：[请检]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}
