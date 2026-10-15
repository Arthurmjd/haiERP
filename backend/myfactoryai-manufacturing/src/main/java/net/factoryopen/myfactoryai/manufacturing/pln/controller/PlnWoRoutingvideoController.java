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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingvideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingvideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品作业视频Controller
 *
 * @author admin
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/pln/woRoutingvideo")
public class PlnWoRoutingvideoController extends BaseController
{
    @Autowired
    private IPlnWoRoutingvideoService plnWoRoutingvideoService;

    /**
     * 查询产品作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoRoutingvideo plnWoRoutingvideo)
    {
        startPage();
        List<PlnWoRoutingvideo> list = plnWoRoutingvideoService.selectPlnWoRoutingvideoList(plnWoRoutingvideo);
        return getDataTable(list);
    }

    /**
     * 导出产品作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:export')")
    @Log(title = "产品作业视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoRoutingvideo plnWoRoutingvideo)
    {
        List<PlnWoRoutingvideo> list = plnWoRoutingvideoService.selectPlnWoRoutingvideoList(plnWoRoutingvideo);
        ExcelUtil<PlnWoRoutingvideo> util = new ExcelUtil<PlnWoRoutingvideo>(PlnWoRoutingvideo.class);
        util.exportExcel(response, list, "产品作业视频数据");
    }

    /**
     * 获取产品作业视频详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoRoutingvideoService.selectPlnWoRoutingvideoById(id));
    }

    /**
     * 新增产品作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:add')")
    @Log(title = "产品作业视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoRoutingvideo plnWoRoutingvideo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoRoutingvideoService.insertPlnWoRoutingvideo(plnWoRoutingvideo);
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
     * 修改产品作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:edit')")
    @Log(title = "产品作业视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoRoutingvideo plnWoRoutingvideo)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingvideoService.updatePlnWoRoutingvideo(plnWoRoutingvideo);
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
     * 删除产品作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woRoutingvideo:remove')")
    @Log(title = "产品作业视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoRoutingvideoService.deletePlnWoRoutingvideoByIds(ids);
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
