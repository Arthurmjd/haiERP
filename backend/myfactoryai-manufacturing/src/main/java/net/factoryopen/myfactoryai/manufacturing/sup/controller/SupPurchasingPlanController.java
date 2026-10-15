package net.factoryopen.myfactoryai.manufacturing.sup.controller;

import java.math.BigDecimal;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
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
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchasingPlan;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchasingPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购计划Controller
 *
 * @author admin
 * @date 2024-09-18
 */
@RestController
@RequestMapping("/sup/purchasingPlan")
public class SupPurchasingPlanController extends BaseController
{
    @Autowired
    private ISupPurchasingPlanService supPurchasingPlanService;
    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 查询采购计划列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupPurchasingPlan supPurchasingPlan)
    {
        startPage();
        List<SupPurchasingPlan> list = supPurchasingPlanService.selectSupPurchasingPlanList(supPurchasingPlan);
        return getDataTable(list);
    }

    /**
     * 导出采购计划列表
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:export')")
    @Log(title = "采购计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupPurchasingPlan supPurchasingPlan)
    {
        List<SupPurchasingPlan> list = supPurchasingPlanService.selectSupPurchasingPlanList(supPurchasingPlan);
        ExcelUtil<SupPurchasingPlan> util = new ExcelUtil<SupPurchasingPlan>(SupPurchasingPlan.class);
        util.exportExcel(response, list, "采购计划数据");
    }

    /**
     * 获取采购计划详细信息
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(supPurchasingPlanService.selectSupPurchasingPlanById(id));
    }

    /**
     * 新增采购计划
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:add')")
    @Log(title = "采购计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupPurchasingPlan supPurchasingPlan)
    {
        AjaxResult ar = null;
        try{
           int ret = supPurchasingPlanService.insertSupPurchasingPlan(supPurchasingPlan);
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
     * 修改采购计划
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:edit')")
    @Log(title = "采购计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupPurchasingPlan supPurchasingPlan)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchasingPlanService.updateSupPurchasingPlan(supPurchasingPlan);
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
     * 分拆采购计划
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:edit')")
    @Log(title = "分拆采购计划", businessType = BusinessType.UPDATE)
    @PutMapping("/split")
    public AjaxResult split(@RequestBody SupPurchasingPlan supPurchasingPlan)
    {
        AjaxResult ar = null;
        try{
            SupPurchasingPlan dbPP = supPurchasingPlanService.selectSupPurchasingPlanById(supPurchasingPlan.getId());
            if (supPurchasingPlan.getQuantity().compareTo(dbPP.getQuantity()) >= 0){
                return AjaxResult.error("分拆失败！分拆数"+supPurchasingPlan.getQuantity().doubleValue()+"非小于原计划数"+dbPP.getQuantity().doubleValue());
            }

            //取且更单号
            String billNo = billcodeManager.getNextBillno("PP");
            billcodeManager.updateBillSeq("PP");

            SupPurchasingPlan clonePP = supPurchasingPlan.clone();
            clonePP.setBillNo(billNo);
            clonePP.setQuantity(supPurchasingPlan.getQuantity());
            clonePP.setRemark(supPurchasingPlan.getRemark());
            clonePP.refreshCreatingStamp();
            supPurchasingPlanService.insertSupPurchasingPlan(clonePP);

            dbPP.setQuantity(new BigDecimal(dbPP.getQuantity().doubleValue()-supPurchasingPlan.getQuantity().doubleValue()));
            dbPP.refreshUpdatingStamp();
            supPurchasingPlanService.updateSupPurchasingPlan(dbPP);

            ar = AjaxResult.success("分拆成功！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("分拆异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 删除采购计划
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:remove')")
    @Log(title = "采购计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = supPurchasingPlanService.deleteSupPurchasingPlanByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:enable')")
    @Log(title = "启用", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPurchasingPlan obj = null;
            if (ids!=null){
                for (Long id:ids){
                    obj = supPurchasingPlanService.selectSupPurchasingPlanById(id);
                    if(obj.getStatus()!= BillStatus.BASE_DRAFT && obj.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "采购计划[" + obj.getBillNo() + "]在当前状态下不能发布！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                supPurchasingPlanService.enable(obj);
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
     * 转单
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:toPo')")
    @Log(title = "转单", businessType = BusinessType.OTHER)
    @PutMapping("/toPo/{ids}")
    public AjaxResult toPo(@PathVariable Long[] ids, @RequestBody SupPurchasingPlan supPurchasingPlan)
    {
        AjaxResult ar = null;
        try{
          supPurchasingPlanService.toPo(ids, supPurchasingPlan);
          ar = AjaxResult.success("操作成功：[转单]"+"执行完成！");
        }
        catch (Exception e){
           e.printStackTrace();
           ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
      }
    /**
     *  ids 冻结 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            SupPurchasingPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                supPurchasingPlanService.froze(obj);
            }
            ar = AjaxResult.success("操作成功：[冻结]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 解冻 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            SupPurchasingPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                supPurchasingPlanService.unfroze(obj);
            }
            ar = AjaxResult.success("操作成功：[解冻]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 停用 主键
     */
    //@PreAuthorize("@ss.hasPermi('sup:purchasingPlan:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            SupPurchasingPlan obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= supPurchasingPlanService.selectSupPurchasingPlanById(ids[i]);
                supPurchasingPlanService.disable(obj);
            }
            ar = AjaxResult.success("操作成功：[停用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
