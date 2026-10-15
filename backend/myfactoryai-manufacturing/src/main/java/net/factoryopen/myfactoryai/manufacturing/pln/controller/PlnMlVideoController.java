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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlVideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlVideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次作业视频Controller
 *
 * @author admin
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlVideo")
public class PlnMlVideoController extends BaseController
{
    @Autowired
    private IPlnMlVideoService plnMlVideoService;

    /**
     * 查询批次作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlVideo plnMlVideo)
    {
        startPage();
        List<PlnMlVideo> list = plnMlVideoService.selectPlnMlVideoList(plnMlVideo);
        return getDataTable(list);
    }

    /**
     * 导出批次作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:export')")
    @Log(title = "批次作业视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlVideo plnMlVideo)
    {
        List<PlnMlVideo> list = plnMlVideoService.selectPlnMlVideoList(plnMlVideo);
        ExcelUtil<PlnMlVideo> util = new ExcelUtil<PlnMlVideo>(PlnMlVideo.class);
        util.exportExcel(response, list, "批次作业视频数据");
    }

    /**
     * 获取批次作业视频详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlVideoService.selectPlnMlVideoById(id));
    }

    /**
     * 新增批次作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:add')")
    @Log(title = "批次作业视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlVideo plnMlVideo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlVideoService.insertPlnMlVideo(plnMlVideo);
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
     * 修改批次作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:edit')")
    @Log(title = "批次作业视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlVideo plnMlVideo)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlVideoService.updatePlnMlVideo(plnMlVideo);
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
     * 删除批次作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlVideo:remove')")
    @Log(title = "批次作业视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlVideoService.deletePlnMlVideoByIds(ids);
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
