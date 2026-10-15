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
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMoves;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlMovesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次移转记录Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/pln/mlMoves")
public class PlnMlMovesController extends BaseController
{
    @Autowired
    private IPlnMlMovesService plnMlMovesService;

    /**
     * 查询批次移转记录列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlnMlMoves plnMlMoves)
    {
        startPage();
        List<PlnMlMoves> list = plnMlMovesService.selectPlnMlMovesList(plnMlMoves);
        return getDataTable(list);
    }

    /**
     * 导出批次移转记录列表
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:export')")
    @Log(title = "批次移转记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlnMlMoves plnMlMoves)
    {
        List<PlnMlMoves> list = plnMlMovesService.selectPlnMlMovesList(plnMlMoves);
        ExcelUtil<PlnMlMoves> util = new ExcelUtil<PlnMlMoves>(PlnMlMoves.class);
        util.exportExcel(response, list, "批次移转记录数据");
    }

    /**
     * 获取批次移转记录详细信息
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plnMlMovesService.selectPlnMlMovesById(id));
    }

    /**
     * 新增批次移转记录
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:add')")
    @Log(title = "批次移转记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlnMlMoves plnMlMoves)
    {
        AjaxResult ar = null;
        try{
           int ret = plnMlMovesService.insertPlnMlMoves(plnMlMoves);
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
     * 修改批次移转记录
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:edit')")
    @Log(title = "批次移转记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlnMlMoves plnMlMoves)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlMovesService.updatePlnMlMoves(plnMlMoves);
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
     * 删除批次移转记录
     */
    //@PreAuthorize("@ss.hasPermi('pln:mlMoves:remove')")
    @Log(title = "批次移转记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = plnMlMovesService.deletePlnMlMovesByIds(ids);
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
