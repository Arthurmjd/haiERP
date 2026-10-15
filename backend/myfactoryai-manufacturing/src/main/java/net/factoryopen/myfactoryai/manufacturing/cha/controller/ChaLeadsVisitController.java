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
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisit;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaLeadsVisitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客访登记Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/cha/leadsVisit")
public class ChaLeadsVisitController extends BaseController
{
    @Autowired
    private IChaLeadsVisitService chaLeadsVisitService;

    /**
     * 查询客访登记列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChaLeadsVisit chaLeadsVisit)
    {
        startPage();
        List<ChaLeadsVisit> list = chaLeadsVisitService.selectChaLeadsVisitList(chaLeadsVisit);
        return getDataTable(list);
    }

    /**
     * 导出客访登记列表
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:export')")
    @Log(title = "客访登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChaLeadsVisit chaLeadsVisit)
    {
        List<ChaLeadsVisit> list = chaLeadsVisitService.selectChaLeadsVisitList(chaLeadsVisit);
        ExcelUtil<ChaLeadsVisit> util = new ExcelUtil<ChaLeadsVisit>(ChaLeadsVisit.class);
        util.exportExcel(response, list, "客访登记数据");
    }

    /**
     * 获取客访登记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(chaLeadsVisitService.selectChaLeadsVisitById(id));
    }

    /**
     * 新增客访登记
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:add')")
    @Log(title = "客访登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChaLeadsVisit chaLeadsVisit)
    {
        AjaxResult ar = null;
        try{
           int ret = chaLeadsVisitService.insertChaLeadsVisit(chaLeadsVisit);
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
     * 修改客访登记
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:edit')")
    @Log(title = "客访登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChaLeadsVisit chaLeadsVisit)
    {
        AjaxResult ar = null;
        try{
            int ret = chaLeadsVisitService.updateChaLeadsVisit(chaLeadsVisit);
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
     * 删除客访登记
     */
    //@PreAuthorize("@ss.hasPermi('cha:leadsVisit:remove')")
    @Log(title = "客访登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = chaLeadsVisitService.deleteChaLeadsVisitByIds(ids);
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
