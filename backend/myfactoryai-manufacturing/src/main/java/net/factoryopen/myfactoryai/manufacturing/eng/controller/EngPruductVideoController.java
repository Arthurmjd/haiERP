package net.factoryopen.myfactoryai.manufacturing.eng.controller;

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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductVideo;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductVideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品作业视频Controller
 *
 * @author admin
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/pruductVideo")
public class EngPruductVideoController extends BaseController
{
    @Autowired
    private IEngPruductVideoService engPruductVideoService;

    /**
     * 查询产品作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngPruductVideo engPruductVideo)
    {
        startPage();
        List<EngPruductVideo> list = engPruductVideoService.selectEngPruductVideoList(engPruductVideo);
        return getDataTable(list);
    }

    /**
     * 导出产品作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:export')")
    @Log(title = "产品作业视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngPruductVideo engPruductVideo)
    {
        List<EngPruductVideo> list = engPruductVideoService.selectEngPruductVideoList(engPruductVideo);
        ExcelUtil<EngPruductVideo> util = new ExcelUtil<EngPruductVideo>(EngPruductVideo.class);
        util.exportExcel(response, list, "产品作业视频数据");
    }

    /**
     * 获取产品作业视频详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engPruductVideoService.selectEngPruductVideoById(id));
    }

    /**
     * 新增产品作业视频
     */
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:add')")
    @Log(title = "产品作业视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngPruductVideo engPruductVideo)
    {
        AjaxResult ar = null;
        try{
           int ret = engPruductVideoService.insertEngPruductVideo(engPruductVideo);
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
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:edit')")
    @Log(title = "产品作业视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngPruductVideo engPruductVideo)
    {
        AjaxResult ar = null;
        try{
            int ret = engPruductVideoService.updateEngPruductVideo(engPruductVideo);
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
    //@PreAuthorize("@ss.hasPermi('eng:pruductVideo:remove')")
    @Log(title = "产品作业视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engPruductVideoService.deleteEngPruductVideoByIds(ids);
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
