package net.factoryopen.myfactoryai.manufacturing.ehs.controller;

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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardRecord;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsHazardRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全风险登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/hazardRecord")
public class EhsHazardRecordController extends BaseController
{
    @Autowired
    private IEhsHazardRecordService ehsHazardRecordService;

    /**
     * 查询安全风险登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsHazardRecord ehsHazardRecord)
    {
        startPage();
        List<EhsHazardRecord> list = ehsHazardRecordService.selectEhsHazardRecordList(ehsHazardRecord);
        return getDataTable(list);
    }

    /**
     * 导出安全风险登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:export')")
    @Log(title = "安全风险登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsHazardRecord ehsHazardRecord)
    {
        List<EhsHazardRecord> list = ehsHazardRecordService.selectEhsHazardRecordList(ehsHazardRecord);
        ExcelUtil<EhsHazardRecord> util = new ExcelUtil<EhsHazardRecord>(EhsHazardRecord.class);
        util.exportExcel(response, list, "安全风险登记数据");
    }

    /**
     * 获取安全风险登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsHazardRecordService.selectEhsHazardRecordById(id));
    }

    /**
     * 新增安全风险登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:add')")
    @Log(title = "安全风险登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsHazardRecord ehsHazardRecord)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsHazardRecordService.insertEhsHazardRecord(ehsHazardRecord);
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
     * 修改安全风险登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:edit')")
    @Log(title = "安全风险登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsHazardRecord ehsHazardRecord)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsHazardRecordService.updateEhsHazardRecord(ehsHazardRecord);
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
     * 删除安全风险登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:hazardRecord:remove')")
    @Log(title = "安全风险登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsHazardRecordService.deleteEhsHazardRecordByIds(ids);
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


}
