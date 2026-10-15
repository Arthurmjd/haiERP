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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoInfo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单信息Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/pln/woInfo")
public class PlnWoInfoController extends BaseController
{
    @Autowired
    private IPlnWoInfoService plnWoInfoService;

    /**
     * 查询工单信息列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoInfo plnWoInfo)
    {
        startPage();
        List<PlnWoInfo> list = plnWoInfoService.selectPlnWoInfoList(plnWoInfo);
        return getDataTable(list);
    }

    /**
     * 导出工单信息列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:export')")
    @Log(title = "工单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoInfo plnWoInfo)
    {
        List<PlnWoInfo> list = plnWoInfoService.selectPlnWoInfoList(plnWoInfo);
        ExcelUtil<PlnWoInfo> util = new ExcelUtil<PlnWoInfo>(PlnWoInfo.class);
        util.exportExcel(response, list, "工单信息数据");
    }

    /**
     * 获取工单信息详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoInfoService.selectPlnWoInfoById(id));
    }

    /**
     * 新增工单信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:add')")
    @Log(title = "工单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoInfo plnWoInfo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoInfoService.insertPlnWoInfo(plnWoInfo);
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
     * 修改工单信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:edit')")
    @Log(title = "工单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoInfo plnWoInfo)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoInfoService.updatePlnWoInfo(plnWoInfo);
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
     * 删除工单信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woInfo:remove')")
    @Log(title = "工单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoInfoService.deletePlnWoInfoByIds(ids);
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
