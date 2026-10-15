package net.factoryopen.myfactoryai.manufacturing.exe.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlService;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupProcess;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupProcessService;
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
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessMovesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序移转记录Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@RestController
@RequestMapping("/exe/processMoves")
public class ExeProcessMovesController extends BaseController
{
    @Autowired
    private IExeProcessMovesService exeProcessMovesService;

    @Autowired
    private IPlnMlService mlService;

    @Autowired
    private IResWorkgroupProcessService groupProcessService;



    /**
     * 查询工序移转记录列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExeProcessMoves exeProcessMoves)
    {
        startPage();
        List<ExeProcessMoves> list = exeProcessMovesService.selectExeProcessMovesList(exeProcessMoves);
        return getDataTable(list);
    }

    /**
     * 导出工序移转记录列表
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:export')")
    @Log(title = "工序移转记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExeProcessMoves exeProcessMoves)
    {
        List<ExeProcessMoves> list = exeProcessMovesService.selectExeProcessMovesList(exeProcessMoves);
        ExcelUtil<ExeProcessMoves> util = new ExcelUtil<ExeProcessMoves>(ExeProcessMoves.class);
        util.exportExcel(response, list, "工序移转记录数据");
    }

    /**
     * 获取工序移转记录详细信息
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(exeProcessMovesService.selectExeProcessMovesById(id));
    }

    /**
     * 新增工序移转记录
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:add')")
    @Log(title = "工序移转记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExeProcessMoves exeProcessMoves)
    {
        AjaxResult ar = null;
        try{
            PlnMl ml = mlService.selectPlnMlById(exeProcessMoves.getMlId());
            exeProcessMoves.setProductId(ml.getProductId());
            exeProcessMoves.setProductName(ml.getProductName());
            exeProcessMoves.setProductSpec(ml.getProductSpec());
            exeProcessMoves.setUnitId(ml.getUnitId());
            exeProcessMoves.refreshCreatingStamp();
           int ret = exeProcessMovesService.insertExeProcessMoves(exeProcessMoves);
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
     * 修改工序移转记录
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:edit')")
    @Log(title = "工序移转记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExeProcessMoves move)
    {
        AjaxResult ar = null;
        try{
            //若为批次当前工序数，则变更当前工序、当前产线
            PlnMl ml = mlService.selectPlnMlById(move.getMlId());
            if (move.getProcessSeq()!=null && move.getProcessSeq().equals(ml.getCurrentProcessSeq())){

                ResWorkgroupProcess groupProcess = new ResWorkgroupProcess();
                groupProcess.setLineId(move.getLineId());
                groupProcess.setProcessId(move.getProcessId());
                List<ResWorkgroupProcess> groupProcessList = groupProcessService.selectResWorkgroupProcessList(groupProcess);

                if (groupProcessList==null || groupProcessList.isEmpty()){
                    ar = AjaxResult.error("操作失败：产线和工序的组合，没有班组的配置！");
//                //同线同工序 可能多个班组
//                }else if (groupProcessList.size()>=2){
//                    ar = AjaxResult.error("操作失败：产线和工序的组合，配置的班组不唯一！");
                }
                else{
                    //修改工序移转的安排
                    int ret = exeProcessMovesService.updateExeProcessMoves(move);

                    //更新批次的当前工序和班组
                    ml.setCurrentProcessId(move.getProcessId());
                    //同线同工序 可能多个班组
                    //ml.setCurrentWorkgroupId(groupProcessList.get(0).getGroupId());
                    ml.refreshUpdatingStamp();
                    mlService.updatePlnMl(ml);

                    if (ret > 0){
                        ar = AjaxResult.success("操作成功：[修改]"+ret+"条数据！");
                    }
                    else{
                        ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
                    }
                }
            }
            //若非批次当前工序数，则直接修改目标移转安排
            else{
                int ret = exeProcessMovesService.updateExeProcessMoves(move);
                if (ret > 0){
                    ar = AjaxResult.success("操作成功：[修改]"+ret+"条数据！");
                }
                else{
                    ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 删除工序移转记录
     */
    //@PreAuthorize("@ss.hasPermi('exe:processMoves:remove')")
    @Log(title = "工序移转记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = exeProcessMovesService.deleteExeProcessMovesByIds(ids);
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
