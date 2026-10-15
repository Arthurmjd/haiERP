package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBomService;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelMpsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主计划维护Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/mps")
public class DelMpsController extends BaseController
{
    @Autowired
    private IDelMpsService delMpsService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IEngProductBomService productBomService;

    @Autowired
    private IEngProductRoutingService productRoutingService;

    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsList(delMps);
        return getDataTable(list);
    }

    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsDraftList(delMps);
        return getDataTable(list);
    }


    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsPublishingList(delMps);
        return getDataTable(list);
    }


    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsProcessingList(delMps);
        return getDataTable(list);
    }


    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsFinishingList(delMps);
        return getDataTable(list);
    }


    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsHistoryList(delMps);
        return getDataTable(list);
    }


    /**
     * 查询主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(DelMps delMps)
    {
        startPage();
        List<DelMps> list = delMpsService.selectDelMpsAllList(delMps);
        return getDataTable(list);
    }


    /**
     * 导出主计划维护列表
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:export')")
    @Log(title = "主计划维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelMps delMps)
    {
        List<DelMps> list = delMpsService.selectDelMpsList(delMps);
        ExcelUtil<DelMps> util = new ExcelUtil<DelMps>(DelMps.class);
        util.exportExcel(response, list, "主计划维护数据");
    }

    /**
     * 获取主计划维护详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delMpsService.selectDelMpsById(id));
    }

    /**
     * 新增主计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:add')")
    @Log(title = "主计划维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelMps delMps)
    {
        AjaxResult ar = null;
        try{
           int ret = delMpsService.insertDelMps(delMps);
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
     * 修改主计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:edit')")
    @Log(title = "主计划维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelMps delMps)
    {
        AjaxResult ar = null;
        try{
            if (delMps.getStatus()!= BillStatus.BASE_DRAFT
                    && delMps.getStatus()!=BillStatus.BASE_AUDIT_REJECT
                    && delMps.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delMpsService.updateDelMps(delMps);
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
     * 删除主计划维护
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:remove')")
    @Log(title = "主计划维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delMpsService.deleteDelMpsByIds(ids);
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
     *  ids 发布 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:enable')")
    @Log(title = "发布", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    DelMps mps = delMpsService.selectDelMpsById(id);
                    if(mps.getStatus()!= BillStatus.BASE_DRAFT && mps.getStatus()!=BillStatus.BASE_AUDIT_PASS
                            && mps.getStatus()!=BillStatus.ACTIVE_PROCESS_REQ)//ACTIVE_PROCESS_REQ改用为工艺完
                    {
                        String msg = "自制主计划[" + mps.getBillNo() + "]在当前状态下不能发布生效！";
                        throw new StatusUnavailableException(msg);
                    }

                    EngProductBasis product = productService.selectEngProductBasisById(mps.getProductId());
                    if(product.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
                        String msg = "自制主计划[" + mps.getBillNo() + "]使用了临时料号，须转补正式工艺数据！";
                        throw new MaterialUnavailableException(msg);
                    }

                    EngProductRouting prouting=new EngProductRouting();
                    prouting.setProductId(mps.getProductId());
                    List<EngProductRouting> proutingList = productRoutingService.selectEngProductRoutingList(prouting);
                    if (proutingList==null || proutingList.isEmpty()){
                        String msg = "自制主计划[" + mps.getBillNo() + "]所生产的产品，无工艺路线！";
                        throw new DataNotExistException(msg);
                    }

                    //BOM暂不检查
//                    EngProductBom pbom=new EngProductBom();
//                    pbom.setProductId(mps.getProductId());
//                    List<EngProductBom> pbomList = productBomService.selectEngProductBomList(pbom);
//                    if (pbomList==null || pbomList.isEmpty()){
//                        String msg = "自制主计划[" + mps.getBillNo() + "]所生产的产品，无物料清单（BOM）！";
//                        throw new DataNotExistException(msg);
//                    }
                }
            }

            DelMps mps = null;
            for (int i = 0; i < ids.length; i++){
                mps= delMpsService.selectDelMpsById(ids[i]);
                delMpsService.enable(mps);
            }

            ar = AjaxResult.success("操作成功：[发布]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 变更
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:changeDelMps')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeDelMps")
    public AjaxResult changeDelMps(@RequestBody DelMps delMps)
    {
        AjaxResult ar = null;
        try{
            if (delMps.getStatus()< BillStatus.BASE_ENABLE
                    || delMps.getStatus()== BillStatus.LIFE_FROZEN || delMps.getStatus()==BillStatus.LIFE_DEAD
                    || delMps.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！自制主计划[" + delMps.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            delMpsService.changeDelMps(delMps);
            ar = AjaxResult.success("操作成功：[变更]"+"执行完成！");
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
    //@PreAuthorize("@ss.hasPermi('del:mps:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            DelMps obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                delMpsService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:mps:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelMps obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                delMpsService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:mps:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelMps obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delMpsService.selectDelMpsById(ids[i]);
                delMpsService.disable(obj);
            }
            ar = AjaxResult.success("操作成功：[停用]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 通知工艺
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:noticeEngineering')")
    @Log(title = "通知工艺", businessType = BusinessType.OTHER)
    @PutMapping("/noticeEngineering")
    public AjaxResult noticeEngineering(@RequestBody DelMps delMps)
    {
        AjaxResult ar = null;
        try{
            delMpsService.noticeEngineering(delMps);
            ar = AjaxResult.success("操作成功：[通知工艺]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 转正料号
     */
    //@PreAuthorize("@ss.hasPermi('del:mps:normalizeCode')")
    @Log(title = "转正料号", businessType = BusinessType.OTHER)
    @PutMapping("/normalizeCode")
    public AjaxResult normalizeCode(@RequestBody DelMps delMps)
    {
        AjaxResult ar = null;
        try{
            delMpsService.normalizeCode(delMps);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
