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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDispatching;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlDispatchingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 上线派工Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/pln/mlDispatching")
public class PlnMlDispatchingController extends BaseController
{
    @Autowired
    private IPlnMlDispatchingService plnMlDispatchingService;

    /**
     * 查询上线派工列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlDispatching plnMlDispatching)
    {
        startPage();
        List<PlnMlDispatching> list = plnMlDispatchingService.selectPlnMlDispatchingList(plnMlDispatching);
        return getDataTable(list);
    }

    /**
     * 导出上线派工列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:export')")
    @Log(title = "上线派工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlDispatching plnMlDispatching)
    {
        List<PlnMlDispatching> list = plnMlDispatchingService.selectPlnMlDispatchingList(plnMlDispatching);
        ExcelUtil<PlnMlDispatching> util = new ExcelUtil<PlnMlDispatching>(PlnMlDispatching.class);
        util.exportExcel(response, list, "上线派工数据");
    }

    /**
     * 获取上线派工详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlDispatchingService.selectPlnMlDispatchingById(id));
    }

    /**
     * 新增上线派工
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:add')")
    @Log(title = "上线派工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlDispatching plnMlDispatching)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlDispatchingService.insertPlnMlDispatching(plnMlDispatching);
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
     * 修改上线派工
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:edit')")
    @Log(title = "上线派工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlDispatching plnMlDispatching)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlDispatchingService.updatePlnMlDispatching(plnMlDispatching);
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
     * 删除上线派工
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:remove')")
    @Log(title = "上线派工", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlDispatchingService.deletePlnMlDispatchingByIds(ids);
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
     * 批派
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:dispatchMultipleMl')")
    @Log(title = "批派", businessType = BusinessType.OTHER)
    @PutMapping("/dispatchMultipleMl/{ids}")
    public AjaxResult dispatchMultipleMl(@PathVariable Long[] ids, @RequestBody PlnMlDispatching plnMlDispatching)
    {
        AjaxResult ar = null;
        try{
          plnMlDispatchingService.dispatchMultipleMl(ids, plnMlDispatching);
          ar = AjaxResult.success("操作成功：[批派]"+"执行完成！");
        }
        catch (Exception e){
           e.printStackTrace();
           ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
      }
    /**
     * 单派
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlDispatching:dispatchSingleMl')")
    @Log(title = "单派", businessType = BusinessType.OTHER)
    @PutMapping("/dispatchSingleMl")
    public AjaxResult dispatchSingleMl(@RequestBody PlnMlDispatching plnMlDispatching)
    {
        AjaxResult ar = null;
        try{
            plnMlDispatchingService.dispatchSingleMl(plnMlDispatching);
            ar = AjaxResult.success("操作成功：[单派]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
