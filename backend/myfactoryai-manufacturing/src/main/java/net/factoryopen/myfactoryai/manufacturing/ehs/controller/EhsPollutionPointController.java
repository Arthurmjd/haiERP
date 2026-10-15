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
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionPoint;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionPointService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 污染物排放点Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ehs/pollutionPoint")
public class EhsPollutionPointController extends BaseController
{
    @Autowired
    private IEhsPollutionPointService ehsPollutionPointService;

    /**
     * 查询污染物排放点列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:list')")
    @GetMapping("/list")
    public TableDataInfo list(EhsPollutionPoint ehsPollutionPoint)
    {
        startPage();
        List<EhsPollutionPoint> list = ehsPollutionPointService.selectEhsPollutionPointList(ehsPollutionPoint);
        return getDataTable(list);
    }

    /**
     * 导出污染物排放点列表
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:export')")
    @Log(title = "污染物排放点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EhsPollutionPoint ehsPollutionPoint)
    {
        List<EhsPollutionPoint> list = ehsPollutionPointService.selectEhsPollutionPointList(ehsPollutionPoint);
        ExcelUtil<EhsPollutionPoint> util = new ExcelUtil<EhsPollutionPoint>(EhsPollutionPoint.class);
        util.exportExcel(response, list, "污染物排放点数据");
    }

    /**
     * 获取污染物排放点详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ehsPollutionPointService.selectEhsPollutionPointById(id));
    }

    /**
     * 新增污染物排放点
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:add')")
    @Log(title = "污染物排放点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EhsPollutionPoint ehsPollutionPoint)
    {
        AjaxResult ar = null;
        try{
           int ret = ehsPollutionPointService.insertEhsPollutionPoint(ehsPollutionPoint);
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
     * 修改污染物排放点
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:edit')")
    @Log(title = "污染物排放点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EhsPollutionPoint ehsPollutionPoint)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionPointService.updateEhsPollutionPoint(ehsPollutionPoint);
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
     * 删除污染物排放点
     */
    //@PreAuthorize("@ss.hasPermi('ehs:pollutionPoint:remove')")
    @Log(title = "污染物排放点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = ehsPollutionPointService.deleteEhsPollutionPointByIds(ids);
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
