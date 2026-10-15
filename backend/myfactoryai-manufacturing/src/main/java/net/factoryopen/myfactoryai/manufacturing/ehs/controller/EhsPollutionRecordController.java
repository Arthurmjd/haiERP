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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionRecord;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 污染物排放登记Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/pollutionRecord")
public class EhsPollutionRecordController extends BaseController
{
    @Autowired
    private IEhsPollutionRecordService ehsPollutionRecordService;

    /**
     * 查询污染物排放登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPollutionRecord ehsPollutionRecord)
    {
        startPage();
        List<EhsPollutionRecord> list = ehsPollutionRecordService.selectEhsPollutionRecordList(ehsPollutionRecord);
        return getDataTable(list);
    }

    /**
     * 导出污染物排放登记列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:export')")
    @Log(title = "污染物排放登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPollutionRecord ehsPollutionRecord)
    {
        List<EhsPollutionRecord> list = ehsPollutionRecordService.selectEhsPollutionRecordList(ehsPollutionRecord);
        ExcelUtil<EhsPollutionRecord> util = new ExcelUtil<EhsPollutionRecord>(EhsPollutionRecord.class);
        util.exportExcel(response, list, "污染物排放登记数据");
    }

    /**
     * 获取污染物排放登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPollutionRecordService.selectEhsPollutionRecordById(id));
    }

    /**
     * 新增污染物排放登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:add')")
    @Log(title = "污染物排放登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPollutionRecord ehsPollutionRecord)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPollutionRecordService.insertEhsPollutionRecord(ehsPollutionRecord);
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
     * 修改污染物排放登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:edit')")
    @Log(title = "污染物排放登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPollutionRecord ehsPollutionRecord)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionRecordService.updateEhsPollutionRecord(ehsPollutionRecord);
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
     * 删除污染物排放登记
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionRecord:remove')")
    @Log(title = "污染物排放登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionRecordService.deleteEhsPollutionRecordByIds(ids);
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
