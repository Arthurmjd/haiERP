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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMrpMpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料需求Controller
 *
 * @author admin
 * @date 2025-04-28
 */
@RestController
@RequestMapping("/pln/mrpMp")
public class PlnMrpMpController extends BaseController
{
    @Autowired
    private IPlnMrpMpService plnMrpMpService;

    /**
     * 查询物料需求列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMrpMp plnMrpMp)
    {
        startPage();
        List<PlnMrpMp> list = plnMrpMpService.selectPlnMrpMpList(plnMrpMp);
        return getDataTable(list);
    }

    /**
     * 导出物料需求列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:export')")
    @Log(title = "物料需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMrpMp plnMrpMp)
    {
        List<PlnMrpMp> list = plnMrpMpService.selectPlnMrpMpList(plnMrpMp);
        ExcelUtil<PlnMrpMp> util = new ExcelUtil<PlnMrpMp>(PlnMrpMp.class);
        util.exportExcel(response, list, "物料需求数据");
    }

    /**
     * 获取物料需求详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMrpMpService.selectPlnMrpMpById(id));
    }

    /**
     * 新增物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:add')")
    @Log(title = "物料需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMrpMp plnMrpMp)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMrpMpService.insertPlnMrpMp(plnMrpMp);
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
     * 修改物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:edit')")
    @Log(title = "物料需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMrpMp plnMrpMp)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpMpService.updatePlnMrpMp(plnMrpMp);
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
     * 删除物料需求
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:remove')")
    @Log(title = "物料需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMrpMpService.deletePlnMrpMpByIds(ids);
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
     *  ids 启用 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:enable')")
    @Log(title = "启用", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            PlnMrpMp obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= plnMrpMpService.selectPlnMrpMpById(ids[i]);
                ret=plnMrpMpService.enable(obj);
            }

            ar = AjaxResult.success("操作成功：[启用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 请购 主键
     */
    //@PreAuthorize("@ss.hasPermi('pln:mrpMp:requestPurchase')")
    @Log(title = "请购", businessType = BusinessType.OTHER)
    @PutMapping("/requestPurchase/{ids}")
    public AjaxResult requestPurchase(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                plnMrpMpService.requestPurchase(ids);
            ar = AjaxResult.success("操作成功：[请购]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
