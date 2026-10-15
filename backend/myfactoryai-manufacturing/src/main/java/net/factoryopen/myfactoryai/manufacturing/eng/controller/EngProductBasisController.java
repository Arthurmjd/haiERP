package net.factoryopen.myfactoryai.manufacturing.eng.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

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
 * 产成品管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/eng/productBasis")
public class EngProductBasisController extends BaseController
{
    @Autowired
    private IEngProductBasisService engProductBasisService;

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
     * 查询产成品管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:list')")
    @GetMapping("/list")
    public TableDataInfo list(EngProductBasis engProductBasis)
    {
        if(engProductBasis!=null && engProductBasis.getPageEnable()!=null && engProductBasis.getPageEnable()) startPage();
        List<EngProductBasis> list = engProductBasisService.selectEngProductBasisList(engProductBasis);
        return getDataTable(list);
    }

    /**
     * 导出产成品管理列表
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:export')")
    @Log(title = "产成品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EngProductBasis engProductBasis)
    {
        List<EngProductBasis> list = engProductBasisService.selectEngProductBasisList(engProductBasis);
        ExcelUtil<EngProductBasis> util = new ExcelUtil<EngProductBasis>(EngProductBasis.class);
        util.exportExcel(response, list, "产成品管理数据");
    }

    /**
     * 获取产成品管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(engProductBasisService.selectEngProductBasisById(id));
    }

    /**
     * 新增产成品管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:add')")
    @Log(title = "产成品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EngProductBasis engProductBasis)
    {
        AjaxResult ar = null;
        try{
           int ret = engProductBasisService.insertEngProductBasis(engProductBasis);

            EngMaterialInventory inventory = new EngMaterialInventory();
            inventory.setMaterialId(engProductBasis.getId());
            inventoryService.insertEngMaterialInventory(inventory);

            EngMaterialProduction production = new EngMaterialProduction();
            production.setMaterialId(engProductBasis.getId());
            productionService.insertEngMaterialProduction(production);

            EngMaterialSales sales = new EngMaterialSales();
            sales.setMaterialId(engProductBasis.getId());
            salesService.insertEngMaterialSales(sales);

            EngMaterialPurchasing purchasing = new EngMaterialPurchasing();
            purchasing.setMaterialId(engProductBasis.getId());
            purchasingService.insertEngMaterialPurchasing(purchasing);

            EngMaterialParameters parameters = new EngMaterialParameters();
            parameters.setMaterialId(engProductBasis.getId());
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
     * 修改产成品管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:edit')")
    @Log(title = "产成品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EngProductBasis engProductBasis)
    {
        AjaxResult ar = null;
        try{
            //判断料号是否变化
            EngProductBasis databaseProduct=engProductBasisService.selectEngProductBasisById(engProductBasis.getId());
            if (!engProductBasis.getMaterialCode().equals(databaseProduct.getMaterialCode())){

            }
            int ret = engProductBasisService.updateEngProductBasis(engProductBasis);
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
     * 删除产成品管理
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:remove')")
    @Log(title = "产成品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = engProductBasisService.deleteEngProductBasisByIds(ids);
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
     * 导入成品
     */
    //@PreAuthorize("@ss.hasPermi('eng:productBasis:importExcel')")
    @Log(title = "导入成品", businessType = BusinessType.IMPORT)
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
                    if (item.getMaterialCode()!=null && item.getMaterialCode().length()>30) continue;
                    if (item.getMaterialName()!=null && item.getMaterialName().length()>30) continue;
                    if (item.getMaterialSpec()!=null && item.getMaterialSpec().length()>50) continue;
                    if (item.getDrawNo()!=null && item.getDrawNo().length()>50) continue;

                    //设置默认值
                    EngProductBasis product = new EngProductBasis();
                    product.setBaseType(MaterialBaseType.PRODUCT.toString());
                    product.setStatus(BillStatus.BASE_ENABLE);
                    //直取数据
                    product.setMaterialCode(item.getMaterialCode());
                    product.setMaterialName(item.getMaterialName());
                    product.setMaterialSpec(item.getMaterialSpec());
                    product.setDrawNo(item.getDrawNo());
                    product.setPriceLast(item.getPriceLast()==null?new BigDecimal(0):item.getPriceLast());
                    product.setQuantityQualified(item.getQuantityQualified()==null?new BigDecimal(0):item.getQuantityQualified());
                    product.setQuantityScraped(item.getQuantityScraped()==null?new BigDecimal(0):item.getQuantityScraped());

                    String unitDesc = item.getUnitDesc()==null?null:item.getUnitDesc().trim();
                    BasUnit unit = new BasUnit();
                    unit.setUnitName(unitDesc);
                    List<BasUnit> unitList = unitService.selectBasUnitList(unit);
                    if (unitList!=null && unitList.size()>0) product.setUnitId(unitList.get(0).getId());

                    String houseName = item.getHouse();
                    ResWareHouse warehouse = new ResWareHouse();
                    warehouse.setHouseName(houseName==null?null:houseName.trim());
                    List<ResWareHouse> warehouseList = warehouseService.selectResWareHouseList(warehouse);
                    Long houseId=null;
                    if (warehouseList!=null && warehouseList.size()>0){
                        houseId = warehouseList.get(0).getId();
                        product.setHouseId(houseId);
                    }

                    String positionName = item.getPosition();
                    ResWarePosition warePosition = new ResWarePosition();
                    warePosition.setHouseId(houseId);
                    warePosition.setPositionCode(positionName==null?null:positionName.trim());
                    List<ResWarePosition> warePositionList = warepositionService.selectResWarePositionList(warePosition);
                    if (warePositionList!=null && warePositionList.size()>0) product.setPositionId(warePositionList.get(0).getId());

                    //保存
                    product.refreshCreatingStamp();
                    engProductBasisService.insertEngProductBasis(product);

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
