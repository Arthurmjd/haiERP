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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpWoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MRP工单项Controller
 *
 * @author admin
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/mrpWo")
public class PlnMrpWoController extends BaseController
{
    @Autowired
    private IPlnMrpWoService plnMrpWoService;

    /**
     * 查询MRP工单项列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMrpWo plnMrpWo)
    {
        startPage();
        List<PlnMrpWo> list = plnMrpWoService.selectPlnMrpWoList(plnMrpWo);
        return getDataTable(list);
    }

    /**
     * 导出MRP工单项列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:export')")
    @Log(title = "MRP工单项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMrpWo plnMrpWo)
    {
        List<PlnMrpWo> list = plnMrpWoService.selectPlnMrpWoList(plnMrpWo);
        ExcelUtil<PlnMrpWo> util = new ExcelUtil<PlnMrpWo>(PlnMrpWo.class);
        util.exportExcel(response, list, "MRP工单项数据");
    }

    /**
     * 获取MRP工单项详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMrpWoService.selectPlnMrpWoById(id));
    }

    /**
     * 新增MRP工单项
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:add')")
    @Log(title = "MRP工单项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMrpWo plnMrpWo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMrpWoService.insertPlnMrpWo(plnMrpWo);
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
     * 修改MRP工单项
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:edit')")
    @Log(title = "MRP工单项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMrpWo plnMrpWo)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpWoService.updatePlnMrpWo(plnMrpWo);
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
     * 删除MRP工单项
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:remove')")
    @Log(title = "MRP工单项", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpWoService.deletePlnMrpWoByIds(ids);
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
     *  ids 转工单 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpWo:toWo')")
    @Log(title = "转工单", businessType = BusinessType.OTHER)
    @PutMapping("/toWo/{ids}")
    public AjaxResult toWo(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            plnMrpWoService.toWo(ids);
            ar = AjaxResult.success("操作成功：[转工单]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
}
