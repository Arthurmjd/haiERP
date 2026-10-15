package net.factoryopen.myfactoryai.manufacturing.snc.controller;

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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventoryIo;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventoryIoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库存货流水Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-20
 */
@RestController
@RequestMapping("/snc/inventoryIo")
public class SncInventoryIoController extends BaseController
{
    @Autowired
    private ISncInventoryIoService sncInventoryIoService;

    /**
     * 查询仓库存货流水列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncInventoryIo sncInventoryIo)
    {
        startPage();
        List<SncInventoryIo> list = sncInventoryIoService.selectSncInventoryIoList(sncInventoryIo);
        return getDataTable(list);
    }

    /**
     * 导出仓库存货流水列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:export')")
    @Log(title = "仓库存货流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncInventoryIo sncInventoryIo)
    {
        List<SncInventoryIo> list = sncInventoryIoService.selectSncInventoryIoList(sncInventoryIo);
        ExcelUtil<SncInventoryIo> util = new ExcelUtil<SncInventoryIo>(SncInventoryIo.class);
        util.exportExcel(response, list, "仓库存货流水数据");
    }

    /**
     * 获取仓库存货流水详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncInventoryIoService.selectSncInventoryIoById(id));
    }

    /**
     * 新增仓库存货流水
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:add')")
    @Log(title = "仓库存货流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncInventoryIo sncInventoryIo)
    {
        AjaxResult ar = null;
        try{
           int ret = sncInventoryIoService.insertSncInventoryIo(sncInventoryIo);
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
     * 修改仓库存货流水
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:edit')")
    @Log(title = "仓库存货流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncInventoryIo sncInventoryIo)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventoryIoService.updateSncInventoryIo(sncInventoryIo);
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
     * 删除仓库存货流水
     */
    //@PreAuthorize("@ss.hasPermi('snc:inventoryIo:remove')")
    @Log(title = "仓库存货流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncInventoryIoService.deleteSncInventoryIoByIds(ids);
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
