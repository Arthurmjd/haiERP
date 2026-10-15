package net.factoryopen.myfactoryai.manufacturing.cha.controller;

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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisiting;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaLeadsVisitingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 线索拜访Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/leadsVisiting")
public class ChaLeadsVisitingController extends BaseController
{
    @Autowired
    private IChaLeadsVisitingService chaLeadsVisitingService;

    /**
     * 查询线索拜访列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaLeadsVisiting chaLeadsVisiting)
    {
        startPage();
        List<ChaLeadsVisiting> list = chaLeadsVisitingService.selectChaLeadsVisitingList(chaLeadsVisiting);
        return getDataTable(list);
    }

    /**
     * 导出线索拜访列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:export')")
    @Log(title = "线索拜访", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaLeadsVisiting chaLeadsVisiting)
    {
        List<ChaLeadsVisiting> list = chaLeadsVisitingService.selectChaLeadsVisitingList(chaLeadsVisiting);
        ExcelUtil<ChaLeadsVisiting> util = new ExcelUtil<ChaLeadsVisiting>(ChaLeadsVisiting.class);
        util.exportExcel(response, list, "线索拜访数据");
    }

    /**
     * 获取线索拜访详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaLeadsVisitingService.selectChaLeadsVisitingById(id));
    }

    /**
     * 新增线索拜访
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:add')")
    @Log(title = "线索拜访", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaLeadsVisiting chaLeadsVisiting)
    {
        AjaxResult ar = null;
        try{
           int ret = chaLeadsVisitingService.insertChaLeadsVisiting(chaLeadsVisiting);
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
     * 修改线索拜访
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:edit')")
    @Log(title = "线索拜访", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaLeadsVisiting chaLeadsVisiting)
    {
        AjaxResult ar = null;
        try{
            int ret = chaLeadsVisitingService.updateChaLeadsVisiting(chaLeadsVisiting);
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
     * 删除线索拜访
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisiting:remove')")
    @Log(title = "线索拜访", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaLeadsVisitingService.deleteChaLeadsVisitingByIds(ids);
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
