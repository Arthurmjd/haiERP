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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoVideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoVideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工单作业视频Controller
 *
 * @author admin
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/pln/woVideo")
public class PlnWoVideoController extends BaseController
{
    @Autowired
    private IPlnWoVideoService plnWoVideoService;

    /**
     * 查询工单作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnWoVideo plnWoVideo)
    {
        startPage();
        List<PlnWoVideo> list = plnWoVideoService.selectPlnWoVideoList(plnWoVideo);
        return getDataTable(list);
    }

    /**
     * 导出工单作业视频列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:export')")
    @Log(title = "工单作业视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnWoVideo plnWoVideo)
    {
        List<PlnWoVideo> list = plnWoVideoService.selectPlnWoVideoList(plnWoVideo);
        ExcelUtil<PlnWoVideo> util = new ExcelUtil<PlnWoVideo>(PlnWoVideo.class);
        util.exportExcel(response, list, "工单作业视频数据");
    }

    /**
     * 获取工单作业视频详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnWoVideoService.selectPlnWoVideoById(id));
    }

    /**
     * 新增工单作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:add')")
    @Log(title = "工单作业视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnWoVideo plnWoVideo)
    {
        AjaxResult ar = null;
        try{
           int ret = plnWoVideoService.insertPlnWoVideo(plnWoVideo);
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
     * 修改工单作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:edit')")
    @Log(title = "工单作业视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnWoVideo plnWoVideo)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoVideoService.updatePlnWoVideo(plnWoVideo);
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
     * 删除工单作业视频
     */
    //@PreAuthorize("@ss.hasPermi('pln:woVideo:remove')")
    @Log(title = "工单作业视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnWoVideoService.deletePlnWoVideoByIds(ids);
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
