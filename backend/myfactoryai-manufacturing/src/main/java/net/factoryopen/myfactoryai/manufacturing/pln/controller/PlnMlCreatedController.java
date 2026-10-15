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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlCreated;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlCreatedService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新派批次Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/mlCreated")
public class PlnMlCreatedController extends BaseController
{
    @Autowired
    private IPlnMlCreatedService plnMlCreatedService;

    /**
     * 查询新派批次列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlCreated plnMlCreated)
    {
        startPage();
        List<PlnMlCreated> list = plnMlCreatedService.selectPlnMlCreatedList(plnMlCreated);
        return getDataTable(list);
    }

    /**
     * 导出新派批次列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:export')")
    @Log(title = "新派批次", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlCreated plnMlCreated)
    {
        List<PlnMlCreated> list = plnMlCreatedService.selectPlnMlCreatedList(plnMlCreated);
        ExcelUtil<PlnMlCreated> util = new ExcelUtil<PlnMlCreated>(PlnMlCreated.class);
        util.exportExcel(response, list, "新派批次数据");
    }

    /**
     * 获取新派批次详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlCreatedService.selectPlnMlCreatedById(id));
    }

    /**
     * 新增新派批次
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:add')")
    @Log(title = "新派批次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlCreated plnMlCreated)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlCreatedService.insertPlnMlCreated(plnMlCreated);
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
     * 修改新派批次
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:edit')")
    @Log(title = "新派批次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlCreated plnMlCreated)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlCreatedService.updatePlnMlCreated(plnMlCreated);
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
     * 删除新派批次
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlCreated:remove')")
    @Log(title = "新派批次", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlCreatedService.deletePlnMlCreatedByIds(ids);
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
