package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.priority.PriorityLevel;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.ContractType;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.core.common.type.MaterialMrpCalculatingType;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBomExcel;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItemExcel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品BOMController
 *
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/eng/productBom")
public class EngProductBomController extends BaseController
{
    @Autowired
    private IEngProductBomService engProductBomService;

    /**
     * 查询产品BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductBom engProductBom)
    {
        if(engProductBom!=null && engProductBom.getPageEnable()!=null && engProductBom.getPageEnable()) startPage();
        List<EngProductBom> list = engProductBomService.selectEngProductBomList(engProductBom);
        return getDataTable(list);
    }

    /**
     * 导出产品BOM列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:export')")
    @Log(title = "产品BOM", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductBom engProductBom)
    {
        List<EngProductBom> list = engProductBomService.selectEngProductBomList(engProductBom);
        ExcelUtil<EngProductBom> util = new ExcelUtil<EngProductBom>(EngProductBom.class);
        util.exportExcel(response, list, "产品BOM数据");
    }

    /**
     * 获取产品BOM详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductBomService.selectEngProductBomById(id));
    }

    /**
     * 新增产品BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:add')")
    @Log(title = "产品BOM", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductBom engProductBom)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductBomService.insertEngProductBom(engProductBom);
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
     * 修改产品BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:edit')")
    @Log(title = "产品BOM", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductBom engProductBom)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductBomService.updateEngProductBom(engProductBom);
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
     * 删除产品BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:remove')")
    @Log(title = "产品BOM", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductBomService.deleteEngProductBomByIds(ids);
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
     * 导入BOM
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBom:importExcel4Bom')")
    @Log(title = "导入BOM", businessType = BusinessType.IMPORT)
    @PutMapping("/importExcel4Bom")
    public AjaxResult importExcel4Bom(@RequestParam Long productId, @RequestParam String excelUrl) {
        String absUrl=excelUrl.replace(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        ExcelUtil<EngProductBomExcel> util = new ExcelUtil<EngProductBomExcel>(EngProductBomExcel.class);
        List<String> errProducts =new ArrayList<String>();
        List<String> errUnits = new ArrayList<String>();
        String contractNo=null;
        ChaCustomer customer = null;

        int importNum = 0;
        String msg="";

        try{
            //取Excel数据
            InputStream is = new FileInputStream(absUrl);
            List<EngProductBomExcel> excellist = util.importExcel(is, 1);
            if (excellist != null) {
                //清空BOM
                EngProductBom bom=new EngProductBom();
                bom.setProductId(productId);
                List<EngProductBom> bomlist = engProductBomService.selectEngProductBomList(bom);
                if (bomlist!=null){
                    for(EngProductBom item:bomlist){
                        engProductBomService.deleteEngProductBomById(item.getId());
                    }
                }

                //重新导入BOM
                for (int i=0; i <excellist.size();i++) {
                    EngProductBomExcel item = excellist.get(i);
                    //保存BOM数据
                    EngProductBom bomItem = new EngProductBom();
                    bomItem.setProductId(productId);
                    bomItem.setSeqNo(Integer.valueOf(item.getSeqNo()));
                    bomItem.setMaterialCode(item.getMaterialCode());
                    bomItem.setMaterialName(item.getMaterialName());
                    bomItem.setMaterialSpec(item.getMaterialSpec());
                    bomItem.setDrawNo(item.getDrawNo());
                    bomItem.setStandardQuantity(item.getQuantity());
                    //判断物料基本类型
                    if (item.getMaterialType().equals("标准件")) bomItem.setCalculateType(MaterialMrpCalculatingType.PURCHASING.toString());
                    else if (item.getMaterialType().equals("原材料")) bomItem.setCalculateType(MaterialMrpCalculatingType.PURCHASING.toString());
                    if (item.getMaterialType().equals("半成品")) bomItem.setCalculateType(MaterialMrpCalculatingType.MANUFACTURING.toString());
                    //保存
                    bomItem.refreshCreatingStamp();
                    engProductBomService.insertEngProductBom(bomItem);

                    importNum++;
                }
            }

            //取返回消息
            if (errProducts.size()>0 || errUnits.size()>0){
                if (importNum>0){
                    msg = String.valueOf(excellist.size()-importNum);
                    msg = importNum +" 条物料导入成功， "+ msg +" 条物料导入失败！";
                }
                if (customer==null){
                    msg=msg+"客户不存在或客户名称不正确！";
                }
                if (contractNo==null){
                    msg=msg+"合同号不存在！";
                }
                if (!errProducts.isEmpty()){
                    msg=msg+"产品编码不存在："+ StringUtils.join(errProducts.toArray(), ",")+"；";
                }
                if (!errUnits.isEmpty()){
                    msg=msg+"产品单位不存在："+ StringUtils.join(errUnits.toArray(), ",")+"；";
                }
                DataNotExistException exception =new DataNotExistException(msg);
                throw exception;
            }else{
                msg = "成功导入 "+ importNum +" 条销售明细！";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        AjaxResult ar = AjaxResult.success();
        ar.put("msg",msg);
        return ar;
    }

}
