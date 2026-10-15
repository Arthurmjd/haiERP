package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.ErType;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelMpsService;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBomService;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingService;
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
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngErService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺处理请求Controller
 *
 * @author admin
 * @date 2024-12-12
 */
@RestController
@RequestMapping("/eng/er")
public class EngErController extends BaseController
{
    @Autowired
    private IEngErService engErService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IEngProductBomService bomService;

    @Autowired
    private IEngProductRoutingService routingService;

    @Autowired
    private IDelMpsService mpsService;

    /**
     * 查询工艺处理请求列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngEr engEr)
    {
        startPage();
        List<EngEr> list = engErService.selectEngErList(engEr);
        return getDataTable(list);
    }

    /**
     * 导出工艺处理请求列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:export')")
    @Log(title = "工艺处理请求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngEr engEr)
    {
        List<EngEr> list = engErService.selectEngErList(engEr);
        ExcelUtil<EngEr> util = new ExcelUtil<EngEr>(EngEr.class);
        util.exportExcel(response, list, "工艺处理请求数据");
    }

    /**
     * 获取工艺处理请求详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engErService.selectEngErById(id));
    }

    /**
     * 新增工艺处理请求
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:add')")
    @Log(title = "工艺处理请求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
           int ret = engErService.insertEngEr(engEr);
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
     * 修改工艺处理请求
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:edit')")
    @Log(title = "工艺处理请求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
            int ret = engErService.updateEngEr(engEr);
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
     * 删除工艺处理请求
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:remove')")
    @Log(title = "工艺处理请求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engErService.deleteEngErByIds(ids);
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
     *  ids 完成 主键
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:finish')")
    @Log(title = "完成", businessType = BusinessType.OTHER)
    @PutMapping("/finish/{ids}")
    public AjaxResult finish(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret=-1;
            EngEr obj = null;

            //卡控BOM或Routing无
            for (int i = 0; i < ids.length; i++){
                boolean hasBomOrRouting = true;

                //取产品
                obj= engErService.selectEngErById(ids[i]);
                EngProductBasis product = productService.selectEngProductBasisById(obj.getProductId());

                //卡控临时料
                if(product.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
                    String msg = "自制主计划[" + obj.getMpsNo() + "]使用了临时料号，须转补正式工艺数据！";
                    throw new MaterialUnavailableException(msg);
                }

                //判断有无BOM(暂且弃查该项)
                EngProductBom bom = new EngProductBom();
                bom.setProductId(product.getId());
                List<EngProductBom> bomList = bomService.selectEngProductBomList(bom);
                //hasBomOrRouting = hasBomOrRouting && (bomList!=null && bomList.size()>0);

                //判断有无routing
                EngProductRouting routing = new EngProductRouting();
                routing.setProductId(product.getId());
                List<EngProductRouting> routingList = routingService.selectEngProductRoutingList(routing);
                hasBomOrRouting = hasBomOrRouting && (routingList!=null && routingList.size()>0);
                //卡控无BOM或无Routing
                if (!hasBomOrRouting){
                    String msg = "产成品[" + product.getMaterialCode() + "]的工艺路线不存在，工艺数据不足！";
                    throw new MaterialUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= engErService.selectEngErById(ids[i]);

                switch (obj.getErType()){
                    case ErType.CREATING:
                        DelMps mps = mpsService.selectDelMpsById(obj.getMpsId());
                        mps.setStatus(BillStatus.ACTIVE_PROCESS_REQ);//工艺完
                        mps.refreshUpdatingStamp();
                        mpsService.updateDelMps(mps);
                    case ErType.UPGRADING:
                    case ErType.ROUTING_CHANGE:
                    case ErType.EXCEPTION_DEAL:
                    default:
                }

                ret=engErService.finish(obj);
            }

            ar = AjaxResult.success("操作成功：[完成]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 生成产品料号
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:generateCode')")
    @Log(title = "生成料号", businessType = BusinessType.OTHER)
    @PutMapping("/generateCode")
    public AjaxResult generateCode(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
            engErService.generateCode(engEr);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 维护BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:updateBom')")
    @Log(title = "维护BOM", businessType = BusinessType.OTHER)
    @PutMapping("/updateBom")
    public AjaxResult updateBom(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
            engErService.updateBom(engEr);
            ar = AjaxResult.success("操作成功：[维护BOM]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 维护路线
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:updateRouting')")
    @Log(title = "维护路线", businessType = BusinessType.OTHER)
    @PutMapping("/updateRouting")
    public AjaxResult updateRouting(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
            engErService.updateRouting(engEr);
            ar = AjaxResult.success("操作成功：[维护路线]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     * 维护文档
     */
    //@PreAuthorize("@ss.hasPermi('eng:er:updateDocs')")
    @Log(title = "维护文档", businessType = BusinessType.OTHER)
    @PutMapping("/updateDocs")
    public AjaxResult updateDocs(@RequestBody EngEr engEr)
    {
        AjaxResult ar = null;
        try{
            engErService.updateDocs(engEr);
            ar = AjaxResult.success("操作成功：[维护文档]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

}
