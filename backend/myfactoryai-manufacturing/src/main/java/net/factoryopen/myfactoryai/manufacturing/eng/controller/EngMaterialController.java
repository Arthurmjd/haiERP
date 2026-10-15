package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialBaseType;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.eng.service.*;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWareHouse;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWarePosition;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWareHouseService;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWarePositionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外购料管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/material")
public class EngMaterialController extends BaseController
{
    @Autowired
    private IEngMaterialService engMaterialService;

    @Autowired
    private IEngMaterialInventoryService inventoryService;

    @Autowired
    private IEngMaterialSalesService salesService;

    @Autowired
    private IEngMaterialProductionService productionService;

    @Autowired
    private IEngMaterialPurchasingService purchasingService;

    @Autowired
    private IEngMaterialParametersService parametersService;

    @Autowired
    private IBasUnitService unitService;

    @Autowired
    private IResWareHouseService warehouseService;

    @Autowired
    private IResWarePositionService warepositionService;

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listProduct')")
    @GetMapping("/listProduct")
    public TableDataInfo listProduct(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngProductList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listPureProduct')")
    @GetMapping("/listPureProduct")
    public TableDataInfo listPureProduct(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngPureProductList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listMaterial')")
    @GetMapping("/listMaterial")
    public TableDataInfo listMaterial(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngMaterialList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listPureMaterialOrProduct')")
    @GetMapping("/listPureMaterialOrProduct")
    public TableDataInfo listPureMaterialOrProduct(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngPureMaterialOrProductList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listUtility')")
    @GetMapping("/listUtility")
    public TableDataInfo listUtility(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngUtilityList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 查询外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:listInventory')")
    @GetMapping("/listInventory")
    public TableDataInfo listInventory(EngMaterial engMaterial)
    {
        if(engMaterial!=null && engMaterial.getPageEnable()!=null && engMaterial.getPageEnable()) startPage();
        List<EngMaterial> list = engMaterialService.selectEngInventoryList(engMaterial);
        return getDataTable(list);
    }

    /**
     * 导出外购料管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:export')")
    @Log(title = "外购料管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngMaterial engMaterial)
    {
        List<EngMaterial> list = engMaterialService.selectEngMaterialList(engMaterial);
        ExcelUtil<EngMaterial> util = new ExcelUtil<EngMaterial>(EngMaterial.class);
        util.exportExcel(response, list, "外购料管理数据");
    }

    /**
     * 获取外购料管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engMaterialService.selectEngMaterialById(id));
    }

    /**
     * 获取外购料管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:query')")
    @GetMapping(value = "getNextCode/{prefixCode}")
    public AjaxResult getNextCode(@PathVariable("prefixCode") String prefixCode)
    {
        String materialCode = null;

        List<EngMaterial> materialList = engMaterialService.selectEngMaterialByPrefixCode(prefixCode+".0");
        if (materialList == null || materialList.isEmpty()){
            materialCode = prefixCode + ".00001";
        }else{
            String codeLast = materialList.get(0).getMaterialCode();
            String postfix = codeLast.substring(prefixCode.length()+1,codeLast.length());
            int index = Integer.parseInt(postfix) + 1;

            materialCode = prefixCode+"."+StringUtils.numberTo00String(index,5);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("materialCode", materialCode);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 新增外购料管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:add')")
    @Log(title = "外购料管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngMaterial engMaterial)
    {
        AjaxResult ar = null;
        try{
           int ret = engMaterialService.insertEngMaterial(engMaterial);

            EngMaterialInventory inventory = new EngMaterialInventory();
            inventory.setMaterialId(engMaterial.getId());
            inventoryService.insertEngMaterialInventory(inventory);

            EngMaterialProduction production = new EngMaterialProduction();
            production.setMaterialId(engMaterial.getId());
            productionService.insertEngMaterialProduction(production);

            EngMaterialSales sales = new EngMaterialSales();
            sales.setMaterialId(engMaterial.getId());
            salesService.insertEngMaterialSales(sales);

            EngMaterialPurchasing purchasing = new EngMaterialPurchasing();
            purchasing.setMaterialId(engMaterial.getId());
            purchasingService.insertEngMaterialPurchasing(purchasing);

            EngMaterialParameters parameters = new EngMaterialParameters();
            parameters.setMaterialId(engMaterial.getId());
            parametersService.insertEngMaterialParameters(parameters);

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
     * 修改外购料管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:edit')")
    @Log(title = "外购料管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngMaterial engMaterial)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialService.updateEngMaterial(engMaterial);
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
     * 删除外购料管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:material:remove')")
    @Log(title = "外购料管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engMaterialService.deleteEngMaterialByIds(ids);
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
     * 导入外购料
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:importExcel')")
    @Log(title = "导入外购料", businessType = BusinessType.IMPORT)
    @PutMapping("/importExcel")
    public AjaxResult importExcel(@RequestParam String excelUrl) {
        String absUrl=excelUrl.replace(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        ExcelUtil<EngMaterialExcel> util = new ExcelUtil<EngMaterialExcel>(EngMaterialExcel.class);
        List<String> errProducts =new ArrayList<String>();
        List<String> errUnits = new ArrayList<String>();
        String contractNo=null;
        ChaCustomer customer = null;

        int importNum = 0;
        String msg="";

        try{
            //取Excel数据
            InputStream is = new FileInputStream(absUrl);
            List<EngMaterialExcel> excellist = util.importExcel(is, 0);
            if (excellist != null) {
                for (int i=0; i <excellist.size();i++) {
                    EngMaterialExcel item = excellist.get(i);

                    //跳过空行
                    if (item.getMaterialCode()==null && item.getMaterialName()==null) continue;
                    if (item.getMaterialCode().length()>30 || item.getMaterialName().length()>30) continue;
                    if (item.getMaterialSpec()!=null && item.getMaterialName().length()>50) continue;
                    if (item.getDrawNo()!=null && item.getDrawNo().length()>50) continue;

                    //设置默认值
                    EngMaterial material = new EngMaterial();
                    material.setBaseType(MaterialBaseType.MATERIAL.toString());
                    material.setStatus(BillStatus.BASE_ENABLE);
                    //直取数据
                    material.setMaterialCode(item.getMaterialCode());
                    material.setMaterialName(item.getMaterialName());
                    material.setMaterialSpec(item.getMaterialSpec());
                    material.setDrawNo(item.getDrawNo());
                    material.setPriceLast(item.getPriceLast()==null?new BigDecimal(0):item.getPriceLast());
                    material.setQuantityQualified(item.getQuantityQualified()==null?new BigDecimal(0):item.getQuantityQualified());
                    material.setQuantityScraped(item.getQuantityScraped()==null?new BigDecimal(0):item.getQuantityScraped());

                    String unitDesc = item.getUnitDesc()==null?null:item.getUnitDesc().trim();
                    BasUnit unit = new BasUnit();
                    unit.setUnitName(unitDesc);
                    List<BasUnit> unitList = unitService.selectBasUnitList(unit);
                    if (unitList!=null && unitList.size()>0) material.setUnitId(unitList.get(0).getId());

                    String houseName = item.getHouse();
                    ResWareHouse warehouse = new ResWareHouse();
                    warehouse.setHouseName(houseName==null?null:houseName.trim());
                    List<ResWareHouse> warehouseList = warehouseService.selectResWareHouseList(warehouse);
                    Long houseId=null;
                    if (warehouseList!=null && warehouseList.size()>0){
                        houseId = warehouseList.get(0).getId();
                        material.setHouseId(houseId);
                    }

                    String positionName = item.getPosition();
                    ResWarePosition warePosition = new ResWarePosition();
                    warePosition.setHouseId(houseId);
                    warePosition.setPositionCode(positionName==null?null:positionName.trim());
                    List<ResWarePosition> warePositionList = warepositionService.selectResWarePositionList(warePosition);
                    if (warePositionList!=null && warePositionList.size()>0) material.setPositionId(warePositionList.get(0).getId());

                    //保存
                    material.refreshCreatingStamp();
                    engMaterialService.insertEngMaterial(material);

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
                if (errProducts.size()>0){
                    msg=msg+"产品编码不存在："+ StringUtils.join(errProducts.toArray(), ",")+"；";
                }
                if (errUnits.size()>0){
                    msg=msg+"产品单位不存在："+ StringUtils.join(errUnits.toArray(), ",")+"；";
                }
                DataNotExistException exception =new DataNotExistException(msg);
                throw exception;
            }else{
                msg = "成功导入 "+ importNum +" 条成品！";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        AjaxResult ar = AjaxResult.success();
        ar.put("msg",msg);
        return ar;
    }


}
