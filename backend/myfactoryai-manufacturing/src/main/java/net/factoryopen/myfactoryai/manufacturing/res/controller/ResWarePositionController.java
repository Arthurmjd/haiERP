package net.factoryopen.myfactoryai.manufacturing.res.controller;

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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWarePosition;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWarePositionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 储位Controller
 *
 * @author jitfactory-generator
 * @date 2024-08-11
 */
@RestController
@RequestMapping("/res/warePosition")
public class ResWarePositionController extends BaseController
{
    @Autowired
    private IResWarePositionService resWarePositionService;

    /**
     * 查询储位列表
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResWarePosition resWarePosition)
    {
        if(resWarePosition!=null && resWarePosition.getPageEnable()!=null && resWarePosition.getPageEnable()) startPage();
        List<ResWarePosition> list = resWarePositionService.selectResWarePositionList(resWarePosition);
        return getDataTable(list);
    }

    /**
     * 导出储位列表
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:export')")
    @Log(title = "储位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResWarePosition resWarePosition)
    {
        List<ResWarePosition> list = resWarePositionService.selectResWarePositionList(resWarePosition);
        ExcelUtil<ResWarePosition> util = new ExcelUtil<ResWarePosition>(ResWarePosition.class);
        util.exportExcel(response, list, "储位数据");
    }

    /**
     * 获取储位详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resWarePositionService.selectResWarePositionById(id));
    }

    /**
     * 新增储位
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:add')")
    @Log(title = "储位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResWarePosition resWarePosition)
    {
        AjaxResult ar = null;
        try{
           int ret = resWarePositionService.insertResWarePosition(resWarePosition);
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
     * 修改储位
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:edit')")
    @Log(title = "储位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResWarePosition resWarePosition)
    {
        AjaxResult ar = null;
        try{
            int ret = resWarePositionService.updateResWarePosition(resWarePosition);
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
     * 删除储位
     */
    //@PreAuthorize("@ss.hasPermi('res:warePosition:remove')")
    @Log(title = "储位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resWarePositionService.deleteResWarePositionByIds(ids);
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
