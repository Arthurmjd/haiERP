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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpMrService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MRP物料需求Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/mrpMr")
public class PlnMrpMrController extends BaseController
{
    @Autowired
    private IPlnMrpMrService plnMrpMrService;

    /**
     * 查询MRP物料需求列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMrpMr plnMrpMr)
    {
        startPage();
        List<PlnMrpMr> list = plnMrpMrService.selectPlnMrpMrList(plnMrpMr);
        return getDataTable(list);
    }

    /**
     * 导出MRP物料需求列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:export')")
    @Log(title = "MRP物料需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMrpMr plnMrpMr)
    {
        List<PlnMrpMr> list = plnMrpMrService.selectPlnMrpMrList(plnMrpMr);
        ExcelUtil<PlnMrpMr> util = new ExcelUtil<PlnMrpMr>(PlnMrpMr.class);
        util.exportExcel(response, list, "MRP物料需求数据");
    }

    /**
     * 获取MRP物料需求详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMrpMrService.selectPlnMrpMrById(id));
    }

    /**
     * 新增MRP物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:add')")
    @Log(title = "MRP物料需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMrpMr plnMrpMr)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMrpMrService.insertPlnMrpMr(plnMrpMr);
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
     * 修改MRP物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:edit')")
    @Log(title = "MRP物料需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMrpMr plnMrpMr)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpMrService.updatePlnMrpMr(plnMrpMr);
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
     * 删除MRP物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:remove')")
    @Log(title = "MRP物料需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpMrService.deletePlnMrpMrByIds(ids);
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
     *  ids 转需求 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMr:toMp')")
    @Log(title = "转需求", businessType = BusinessType.OTHER)
    @PutMapping("/toMp/{ids}")
    public AjaxResult toMp(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            plnMrpMrService.toMp(ids);
            ar = AjaxResult.success("操作成功：[转需求]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}
