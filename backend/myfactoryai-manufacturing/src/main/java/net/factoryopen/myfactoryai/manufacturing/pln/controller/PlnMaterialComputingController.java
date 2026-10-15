package net.factoryopen.myfactoryai.manufacturing.pln.controller;

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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMaterialComputingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.util.NestedServletException;

/**
 * 物料计算Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/materialComputing")
public class PlnMaterialComputingController extends BaseController
{
    @Autowired
    private IPlnMaterialComputingService plnMaterialComputingService;

    /**
     * 查询物料计算列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMaterialComputing plnMaterialComputing)
    {
        startPage();
        List<PlnMaterialComputing> list = plnMaterialComputingService.selectPlnMaterialComputingList(plnMaterialComputing);
        return getDataTable(list);
    }

    /**
     * 导出物料计算列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:export')")
    @Log(title = "物料计算", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMaterialComputing plnMaterialComputing)
    {
        List<PlnMaterialComputing> list = plnMaterialComputingService.selectPlnMaterialComputingList(plnMaterialComputing);
        ExcelUtil<PlnMaterialComputing> util = new ExcelUtil<PlnMaterialComputing>(PlnMaterialComputing.class);
        util.exportExcel(response, list, "物料计算数据");
    }

    /**
     * 获取物料计算详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMaterialComputingService.selectPlnMaterialComputingById(id));
    }

    /**
     * 新增物料计算
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:add')")
    @Log(title = "物料计算", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMaterialComputing plnMaterialComputing)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMaterialComputingService.insertPlnMaterialComputing(plnMaterialComputing);
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
     * 修改物料计算
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:edit')")
    @Log(title = "物料计算", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMaterialComputing plnMaterialComputing)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMaterialComputingService.updatePlnMaterialComputing(plnMaterialComputing);
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
     * 删除物料计算
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:remove')")
    @Log(title = "物料计算", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMaterialComputingService.deletePlnMaterialComputingByIds(ids);
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
     *  ids 算料 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:materialComputing:runMrp')")
    @Log(title = "算料", businessType = BusinessType.OTHER)
    @PutMapping("/runMrp/{ids}")
    public AjaxResult runMrp(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                plnMaterialComputingService.runMrp(ids);
            ar = AjaxResult.success("操作成功：[算料]"+"执行完成！");
        }catch(NestedServletException e){
            e.printStackTrace();
            ar = AjaxResult.error("计算异常：子件嵌套，算料没完没了！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
