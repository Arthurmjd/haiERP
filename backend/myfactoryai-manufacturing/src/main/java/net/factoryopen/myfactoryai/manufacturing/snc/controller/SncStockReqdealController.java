package net.factoryopen.myfactoryai.manufacturing.snc.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.type.InventoryTransactionType;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockRequestItemService;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplier;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierService;
import net.factoryopen.myfactoryai.system.service.ISysDeptService;
import net.factoryopen.myfactoryai.system.service.ISysUserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqdeal;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockReqdealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请出入库处理Controller
 *
 * @author admin
 * @date 2024-09-16
 */
@RestController
@RequestMapping("/snc/stockReqdeal")
public class SncStockReqdealController extends BaseController
{
    @Autowired
    private ISncStockReqdealService stockReqService;

    @Autowired
    private ISncStockRequestItemService stockReqitemService;

    @Autowired
    private ISupSupplierService supplierService;

    @Autowired
    private IChaCustomerService customerService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IBasUnitService unitService;

    /**
     * 查询请出入库处理列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(SncStockReqdeal sncStockReqdeal)
    {
        startPage();
        List<SncStockReqdeal> list = stockReqService.selectSncStockReqdealList(sncStockReqdeal);
        return getDataTable(list);
    }

    /**
     * 获取请出入库处理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(stockReqService.selectSncStockReqdealById(id));
    }

    /**
     * 新增请出入库处理
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:add')")
    @Log(title = "请出入库处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SncStockReqdeal sncStockReqdeal)
    {
        AjaxResult ar = null;
        try{
           int ret = stockReqService.insertSncStockReqdeal(sncStockReqdeal);
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
     * 修改请出入库处理
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:edit')")
    @Log(title = "请出入库处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SncStockReqdeal sncStockReqdeal)
    {
        AjaxResult ar = null;
        try{
            int ret = stockReqService.updateSncStockReqdeal(sncStockReqdeal);
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
     * 删除请出入库处理
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:remove')")
    @Log(title = "请出入库处理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = stockReqService.deleteSncStockReqdealByIds(ids);
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
     * 收货
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:receiveMaterial')")
    @Log(title = "收货", businessType = BusinessType.OTHER)
    @PutMapping("/receiveMaterial")
    public AjaxResult receiveMaterial(@RequestParam Long requestId,
                                      @RequestParam Integer status,
                                      @RequestParam Integer ioType,
                                      @RequestParam String sourceBillNo,
                                      @RequestBody List<SncStockRequestItem> requestItems)
    {
        AjaxResult ar = null;
        try{
            SncStockRequest request = new SncStockRequest();
            request.setId(requestId);
            request.setStatus(status);
            request.setIoType(ioType);
            request.setSourceBillNo(sourceBillNo);
            stockReqService.receiveMaterial(request, requestItems);
            ar = AjaxResult.success("操作成功：[收货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 请检
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:requestInspection')")
    @Log(title = "请检", businessType = BusinessType.OTHER)
    @PutMapping("/requestInspection")
    public AjaxResult requestInspection(@RequestBody SncStockRequest request)
    {
        AjaxResult ar = null;
        try{
            stockReqService.requestInspection(request);
            ar = AjaxResult.success("操作成功：[请检]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 入库
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:stockIn')")
    @Log(title = "入库", businessType = BusinessType.OTHER)
    @PutMapping("/stockIn/{id}")
    public AjaxResult stockIn(@PathVariable Long id, @RequestBody List<SncStockRequestItem> reqItems)
    {
        AjaxResult ar = null;
        try{
            stockReqService.stockIn(id, reqItems);
            ar = AjaxResult.success("操作成功：[入库]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 出库
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:stockout')")
    @Log(title = "出库", businessType = BusinessType.OTHER)
    @PutMapping("/stockout/{id}")
    public AjaxResult stockout(@PathVariable Long id, @RequestBody List<SncStockRequestItem> reqItems)
    {
        AjaxResult ar = null;
        try{
            stockReqService.stockout(id, reqItems);
            ar = AjaxResult.success("操作成功：[出库]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 入库建议
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:gmgwSuggest')")
    @Log(title = "好物好库推荐", businessType = BusinessType.OTHER)
    @PutMapping("/gmgwSuggest")
    public AjaxResult gmgwSuggest(@RequestBody List<SncStockRequestItem> reqItems)
    {
        List<SncStockRequestItem> list = stockReqService.gmgwSuggest(reqItems);
        return AjaxResult.success(list);
    }

    /**
     * 出库建议
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:fifoSuggest')")
    @Log(title = "先进先出推荐", businessType = BusinessType.OTHER)
    @PutMapping("/fifoSuggest")
    public AjaxResult fifoSuggest(@RequestBody List<SncStockRequestItem> reqItems)
    {
        List<SncStockRequestItem> list = stockReqService.fifoSuggest(reqItems);
        return AjaxResult.success(list);
    }

    /**
     * 转正料号
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:normalizeCode')")
    @Log(title = "转正料号", businessType = BusinessType.OTHER)
    @PutMapping("/normalizeCode")
    public AjaxResult normalizeCode(@RequestBody SncStockRequestItem requestItem)
    {
        AjaxResult ar = null;
        try{
            stockReqService.normalizeCode(requestItem);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 生成料号
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:generateMaterialCode')")
    @Log(title = "生成料号", businessType = BusinessType.OTHER)
    @PutMapping("/generateMaterialCode")
    public AjaxResult generateMaterialCode(@RequestBody SncStockRequestItem requestItem)
    {
        AjaxResult ar = null;
        try{
            stockReqService.generateMaterialCode(requestItem);
            ar = AjaxResult.success("操作成功：[生成料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 导出请出入库处理列表
     */
    //@PreAuthorize("@ss.hasPermi('snc:stockReqdeal:export')")
    @Log(title = "请出入库处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SncStockReqdeal stockReq)
    {
        String sheetName = "装箱单";
        String excelUrl = "/profile/download/template/inventoryio.xlsx";

        //单头坐标
        int titleRowIndex = 0;
        int titleColIndex = 0;
        int deptLabelRowIndex = 1;
        int deptLabelColIndex = 0;
        int deptCaptionRowIndex = 1;
        int deptCaptionColIndex = 2;
        int dayRowIndex = 1;
        int dayColIndex = 6;
        int noRowIndex = 1;
        int noColIndex = 11;
        //单身受行数
        int bodyRowIndex = 3;

        //流声明
        InputStream is = null;
        Workbook wb = null;
        Sheet sheet = null;

        try
        {
            //读取Excel模板
            String absUrl=excelUrl.replace(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
            is = new FileInputStream(absUrl);
            wb = WorkbookFactory.create(is);
            sheet = wb.getSheetAt(0);

            //读取待写入数据
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //重取单头
            stockReq = stockReqService.selectSncStockReqdealById(stockReq.getId());
            SupSupplier supplier = supplierService.selectSupSupplierById(stockReq.getSourceSupplierId());
            ChaCustomer customer = customerService.selectChaCustomerById(stockReq.getSourceCustomerId());
            SysUser requestor = userService.selectUserById(stockReq.getRequestorId());
            SysDept dept = deptService.selectDeptById(requestor.getDeptId());
            //取单身
            SncStockRequestItem reqItem =new SncStockRequestItem();
            reqItem.setRequestId(stockReq.getId());
            List<SncStockRequestItem> itemList = stockReqitemService.selectSncStockRequestItemList(reqItem);
            if (itemList!=null && itemList.size()>1){
                sheet.shiftRows(bodyRowIndex+1,bodyRowIndex+2,itemList.size()-1);
            }

            //写入单头的标题、部门/客户/供应商
            Cell cell = null;
            if (stockReq.getIoType() == InventoryTransactionType.STOCKIN_4_BUY){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"采购入库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("供应商：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(supplier==null?null:supplier.getFullName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKIN_4_SUBCONTRACT){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"转包入库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("供应商：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(supplier==null?null:supplier.getFullName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKIN_4_SALE_BACK){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"销退入库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("客户：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(customer==null?null:customer.getCustomerFullname());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKIN_4_PICKBACK){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"退料入库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("部门：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(dept==null?null:dept.getDeptName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKIN_4_OTHERS){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"其他入库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("部门：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(dept==null?null:dept.getDeptName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKOUT_4_SALE){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"销售出库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("客户：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(customer==null?null:customer.getCustomerFullname());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKOUT_4_PICK){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"领料出库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("部门：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(dept==null?null:dept.getDeptName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKOUT_4_BUYBACK){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"购退出库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("供应商：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(supplier==null?null:supplier.getFullName());
            }else if (stockReq.getIoType() == InventoryTransactionType.STOCKOUT_4_OTHERS){
                cell = sheet.getRow(titleRowIndex).getCell(titleColIndex);
                cell.setCellValue(SecurityUtils.getCompanyFullName()+"其他出库单");
                cell = sheet.getRow(deptLabelRowIndex).getCell(deptLabelColIndex);
                cell.setCellValue("部门：");
                cell = sheet.getRow(deptCaptionRowIndex).getCell(deptCaptionColIndex);
                cell.setCellValue(dept==null?null:dept.getDeptName());
            }

            //设置单身的日期、编号
            cell = sheet.getRow(dayRowIndex).getCell(dayColIndex);
            cell.setCellValue(dateFormat.format(DateUtils.getNowDate()));
            cell = sheet.getRow(noRowIndex).getCell(noColIndex);
            cell.setCellValue(stockReq.getBillNo());

            //写入单身
            double sumQuantity = 0;
            double sumAmount = 0;
            for (int i=0; itemList!=null && i<itemList.size();i++){
                SncStockRequestItem item = itemList.get(i);

                //小计
                sumQuantity += item.getQuantity().doubleValue();
                sumAmount += item.getAmount().doubleValue();

                EngProductBasis product = productService.selectEngProductBasisById(item.getMaterialId());
                BasUnit unit = unitService.selectBasUnitById(item.getUnitId());

                //非首行，则创建新行
                if (i>0){
                    Row row = sheet.createRow(bodyRowIndex+i);
                    row.setRowStyle(sheet.getRow(bodyRowIndex).getRowStyle());

                    for (int j=0;j<13;j++){
                        cell = row.createCell(j);
                        cell.setCellStyle(sheet.getRow(bodyRowIndex).getCell(j).getCellStyle());
                    }
                }

                //给各列赋值
                cell = sheet.getRow(bodyRowIndex+i).getCell(0);
                cell.setCellValue(item.getSeqNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(1);
                cell.setCellValue(product==null?null:product.getMaterialCode());
                cell = sheet.getRow(bodyRowIndex+i).getCell(3);
                cell.setCellValue(item.getMaterialName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(5);
                cell.setCellValue(item.getMaterialSpec());
                cell = sheet.getRow(bodyRowIndex+i).getCell(8);
                cell.setCellValue(unit.getUnitName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(9);
                cell.setCellValue(item.getQuantity().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(10);
                cell.setCellValue(item.getPrice().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(11);
                cell.setCellValue(item.getAmount().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(12);
                cell.setCellValue(item.getRemark());
            }

            //设置小计
            int totalRowIndex = bodyRowIndex + itemList.size();
            int totalColIndex = 2;
            int quantitySumRowIndex = bodyRowIndex + itemList.size();
            int quantitySumColIndex = 9;
            int amountSumRowIndex = bodyRowIndex + itemList.size();
            int amountSumColIndex = 10;
            cell = sheet.getRow(totalRowIndex).getCell(totalColIndex);
            cell.setCellValue(sumAmount);
            cell = sheet.getRow(quantitySumRowIndex).getCell(quantitySumColIndex);
            cell.setCellValue(sumQuantity);
            cell = sheet.getRow(amountSumRowIndex).getCell(amountSumColIndex);
            cell.setCellValue(sumAmount);

            //设置页尾
            int billerRowIndex = bodyRowIndex + itemList.size()+1;
            int billerColIndex = 12;
            cell = sheet.getRow(billerRowIndex).getCell(billerColIndex);
            cell.setCellValue(SecurityUtils.getNickname());

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            wb.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(is);
        }
    }

}
