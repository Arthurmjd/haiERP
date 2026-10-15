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
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWareHouse;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWareHouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-08-11
 */
@RestController
@RequestMapping("/res/wareHouse")
public class ResWareHouseController extends BaseController
{
    @Autowired
    private IResWareHouseService resWareHouseService;

    /**
     * 查询仓库管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResWareHouse resWareHouse)
    {
        if(resWareHouse!=null && resWareHouse.getPageEnable()!=null && resWareHouse.getPageEnable()) startPage();
        List<ResWareHouse> list = resWareHouseService.selectResWareHouseList(resWareHouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResWareHouse resWareHouse)
    {
        List<ResWareHouse> list = resWareHouseService.selectResWareHouseList(resWareHouse);
        ExcelUtil<ResWareHouse> util = new ExcelUtil<ResWareHouse>(ResWareHouse.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resWareHouseService.selectResWareHouseById(id));
    }

    /**
     * 新增仓库管理
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResWareHouse resWareHouse)
    {
        AjaxResult ar = null;
        try{
           int ret = resWareHouseService.insertResWareHouse(resWareHouse);
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
     * 修改仓库管理
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResWareHouse resWareHouse)
    {
        AjaxResult ar = null;
        try{
            int ret = resWareHouseService.updateResWareHouse(resWareHouse);
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
     * 删除仓库管理
     */
    //@PreAuthorize("@ss.hasPermi('res:wareHouse:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = resWareHouseService.deleteResWareHouseByIds(ids);
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
