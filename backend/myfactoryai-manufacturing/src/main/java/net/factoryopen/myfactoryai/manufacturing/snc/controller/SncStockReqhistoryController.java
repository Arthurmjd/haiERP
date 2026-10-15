package net.factoryopen.myfactoryai.manufacturing.snc.controller;

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
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqhistory;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockReqhistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出入库历史Controller
 *
 * @author liqiang
 * @date 2025-05-17
 */
@RestController
@RequestMapping("/snc/stockReqhistory")
public class SncStockReqhistoryController extends BaseController
{
    @Autowired
    private ISncStockReqhistoryService sncStockReqhistoryService;

    /**
     * 查询出入库历史列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncStockReqhistory sncStockReqhistory)
    {
        startPage();
        List<SncStockReqhistory> list = sncStockReqhistoryService.selectSncStockReqhistoryList(sncStockReqhistory);
        return getDataTable(list);
    }

    /**
     * 导出出入库历史列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:export')")
    @Log(title = "出入库历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncStockReqhistory sncStockReqhistory)
    {
        List<SncStockReqhistory> list = sncStockReqhistoryService.selectSncStockReqhistoryList(sncStockReqhistory);
        ExcelUtil<SncStockReqhistory> util = new ExcelUtil<SncStockReqhistory>(SncStockReqhistory.class);
        util.exportExcel(response, list, "出入库历史数据");
    }

    /**
     * 获取出入库历史详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sncStockReqhistoryService.selectSncStockReqhistoryById(id));
    }

    /**
     * 新增出入库历史
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:add')")
    @Log(title = "出入库历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncStockReqhistory sncStockReqhistory)
    {
        AjaxResult ar = null;
        try{
           int ret = sncStockReqhistoryService.insertSncStockReqhistory(sncStockReqhistory);
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
     * 修改出入库历史
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:edit')")
    @Log(title = "出入库历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncStockReqhistory sncStockReqhistory)
    {
        AjaxResult ar = null;
        try{
            int ret = sncStockReqhistoryService.updateSncStockReqhistory(sncStockReqhistory);
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
     * 删除出入库历史
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:remove')")
    @Log(title = "出入库历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = sncStockReqhistoryService.deleteSncStockReqhistoryByIds(ids);
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
     * 打印
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqhistory:printRequest')")
    @Log(title = "打印", businessType = BusinessType.OTHER)
    @PutMapping("/printRequest")
    public AjaxResult printRequest(@RequestBody SncStockReqhistory sncStockReqhistory)
    {
        AjaxResult ar = null;
        try{
            sncStockReqhistoryService.printRequest(sncStockReqhistory);
            ar = AjaxResult.success("操作成功：[打印]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
