package net.factoryopen.myfactoryai.manufacturing.del.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelGdItemService;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractItemService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
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
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryOrder;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发货单管理Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/del/deliveryOrder")
public class DelDeliveryOrderController extends BaseController
{
    @Autowired
    private IDelDeliveryOrderService delDeliveryOrderService;

    @Autowired
    private IDelGdItemService delGdItemService;

    @Autowired
    private IChaCustomerService customerService;

    @Autowired
    private IOrdSoService soService;

    @Autowired
    private IOrdSoItemService soitemService;

    @Autowired
    private IOrdContractItemService contractItemService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IBasUnitService unitService;

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderDraftList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderPublishingList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderProcessingList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderFinishingList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderHistoryList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 查询发货单管理列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(DelDeliveryOrder delDeliveryOrder)
    {
        if(delDeliveryOrder!=null && delDeliveryOrder.getPageEnable()!=null && delDeliveryOrder.getPageEnable()) startPage();
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderAllList(delDeliveryOrder);
        return getDataTable(list);
    }

    /**
     * 获取发货单管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(delDeliveryOrderService.selectDelDeliveryOrderById(id));
    }

    /**
     * 新增发货单管理
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:add')")
    @Log(title = "发货单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DelDeliveryOrder delDeliveryOrder)
    {
        AjaxResult ar = null;
        try{
           int ret = delDeliveryOrderService.insertDelDeliveryOrder(delDeliveryOrder);
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
     * 修改发货单管理
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:edit')")
    @Log(title = "发货单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DelDeliveryOrder delDeliveryOrder)
    {
        AjaxResult ar = null;
        try{
            if (delDeliveryOrder.getStatus()!= BillStatus.BASE_DRAFT
                    && delDeliveryOrder.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "修改非法！对象在当前状态下不支持修改";
                throw new StatusUnavailableException(msg);
            }

            int ret = delDeliveryOrderService.updateDelDeliveryOrder(delDeliveryOrder);
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
     * 删除发货单管理
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:remove')")
    @Log(title = "发货单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            int ret = delDeliveryOrderService.deleteDelDeliveryOrderByIds(ids);
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
     *  ids 冻结 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;
        try{
            DelDeliveryOrder obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="冻结["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                delDeliveryOrderService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryOrder obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                if (obj.getStatus()!= BillStatus.LIFE_FROZEN){
                    String msg="解冻["+obj.getBillNo()+"]非法！非冻结状态的对象，不能冻结。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                delDeliveryOrderService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryOrder obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                if (obj.getStatus()==BillStatus.BASE_DRAFT
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()>=BillStatus.FINISH_WAITING){
                    String msg="停用["+obj.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                delDeliveryOrderService.disable(obj);
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
     *  ids 强结 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:finish')")
    @Log(title = "结案", businessType = BusinessType.OTHER)
    @PutMapping("/finish/{ids}")
    public AjaxResult finish(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryOrder obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                if (obj.getStatus()!=BillStatus.FINISH_WAITING){
                    String msg="结案["+obj.getBillNo()+"]非法！状态非为待结案的对象不能结案。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                obj.setStatus(BillStatus.FINISH);
                delDeliveryOrderService.updateDelDeliveryOrder(obj);
            }
            ar = AjaxResult.success("操作成功：[结案]"+ids.length+"条数据！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 强结 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:finishforcely')")
    @Log(title = "强结", businessType = BusinessType.OTHER)
    @PutMapping("/finishforcely/{ids}")
    public AjaxResult finishforcely(@PathVariable("ids") Long[] ids)
    {
        AjaxResult ar = null;

        try{
            DelDeliveryOrder obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                if (obj.getStatus()<BillStatus.BASE_ENABLE
                        || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                        || obj.getStatus()==BillStatus.FINISH_FORCELY || obj.getStatus()==BillStatus.FINISH_FORCELY){
                    String msg="结案["+obj.getBillNo()+"]非法！状态为冻结、停用、结案及未发布生效的对象不能结案。";
                    throw new StatusUnavailableException(msg);
                }
            }

            for (int i = 0; i < ids.length; i++){
                obj= delDeliveryOrderService.selectDelDeliveryOrderById(ids[i]);
                delDeliveryOrderService.finishforcely(obj);
            }
            ar = AjaxResult.success("操作成功：[强结]"+ids.length+"条数据！");
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
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:changeDelDeliveryOrder')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeDelDeliveryOrder")
    public AjaxResult changeDelDeliveryOrder(@RequestBody DelDeliveryOrder delDeliveryOrder)
    {
        AjaxResult ar = null;
        try{
            if (delDeliveryOrder.getStatus()< BillStatus.BASE_ENABLE
                    || delDeliveryOrder.getStatus()== BillStatus.LIFE_FROZEN || delDeliveryOrder.getStatus()==BillStatus.LIFE_DEAD
                    || delDeliveryOrder.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！发货单[" + delDeliveryOrder.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            delDeliveryOrderService.changeDelDeliveryOrder(delDeliveryOrder);
            ar = AjaxResult.success("操作成功：[变更]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }
    /**
     *  ids 发货 主键
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:delivery')")
    @Log(title = "发货", businessType = BusinessType.OTHER)
    @PutMapping("/delivery/{ids}")
    public AjaxResult delivery(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
                delDeliveryOrderService.delivery(ids);
            ar = AjaxResult.success("操作成功：[发货]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 导出发货单列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:export')")
    @Log(title = "发货单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DelDeliveryOrder delDeliveryOrder)
    {
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderList(delDeliveryOrder);
        ExcelUtil<DelDeliveryOrder> util = new ExcelUtil<DelDeliveryOrder>(DelDeliveryOrder.class);
        util.exportExcel(response, list, "返货单数据");
    }

    /**
     * 导出发货单列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:exportFinishing')")
    @Log(title = "待结发货单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportFinishing")
    public void exportFinishing(HttpServletResponse response, DelDeliveryOrder delDeliveryOrder)
    {
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderFinishingList(delDeliveryOrder);
        ExcelUtil<DelDeliveryOrder> util = new ExcelUtil<DelDeliveryOrder>(DelDeliveryOrder.class);
        util.exportExcel(response, list, "返货单数据");
    }

    /**
     * 导出发货单列表
     */
    //@PreAuthorize("@ss.hasPermi('del:deliveryOrder:exportHistory')")
    @Log(title = "历史发货单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportHistory")
    public void exportHistory(HttpServletResponse response, DelDeliveryOrder delDeliveryOrder)
    {
        List<DelDeliveryOrder> list = delDeliveryOrderService.selectDelDeliveryOrderHistoryList(delDeliveryOrder);
        ExcelUtil<DelDeliveryOrder> util = new ExcelUtil<DelDeliveryOrder>(DelDeliveryOrder.class);
        util.exportExcel(response, list, "返货单数据");
    }

    /**
     * 按订单导出装箱单
     */
    //@PreAuthorize("@ss.hasPermi('ord:deliveryOrder:exportSoBox')")
    @Log(title = "导出订单装箱单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSoBox")
    public void exportSoBox(HttpServletResponse response, DelDeliveryOrder doo)
    {
        String sheetName = "订单装箱单";
        String excelUrl = "/profile/template/deliveryorder.xls";

        //单头坐标
        int customerRowIndex = 2;
        int customerColIndex = 0;
        int deliverydayRowIndex = 2;
        int deliverydayColIndex = 8;

        int contactRowIndex = 1;
        int contactColIndex = 11;
        int phoneRowIndex = 2;
        int phoneColIndex = 11;
        int addressRowIndex = 3;
        int addressColIndex = 11;
        //单身受行数
        int bodyRowIndex = 4;

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            doo = delDeliveryOrderService.selectDelDeliveryOrderById(doo.getId());
            ChaCustomer customer = customerService.selectChaCustomerById(doo.getCustomerId());


            DelGdItem gdItem = new DelGdItem();
            gdItem.setGdId(doo.getId());
            List<DelGdItem> itemList = delGdItemService.selectDelGdItemList(gdItem);
            if (itemList!=null && itemList.size()>1){
                sheet.shiftRows(bodyRowIndex+1,bodyRowIndex+4,itemList.size()-1);
            }

            //写入单头
            Cell cell = sheet.getRow(customerRowIndex).getCell(customerColIndex);
            cell.setCellValue("供货单位："+customer.getCustomerName());
            cell = sheet.getRow(deliverydayRowIndex).getCell(deliverydayColIndex);
            cell.setCellValue(dateFormat.format(DateUtils.getNowDate()));

//            cell = sheet.getRow(contactRowIndex).getCell(contactColIndex);
//            cell.setCellValue(customer.getContact());
//            cell = sheet.getRow(phoneRowIndex).getCell(phoneColIndex);
//            cell.setCellValue(customer.getPhone());
//            cell = sheet.getRow(addressRowIndex).getCell(addressColIndex);
//            cell.setCellValue(customer.getAddress());

            //写入单身
            double sumQuantity = 0;
            double sumAmount = 0;
            for (int i=0; itemList!=null && i<itemList.size();i++){
                DelGdItem item = itemList.get(i);

                //小计
                sumQuantity += item.getQuantity().doubleValue();
                sumAmount += item.getAmount().doubleValue();

                //取值
                OrdSo so = soService.selectOrdSoById(item.getSoId());
                OrdSoItem soitem = soitemService.selectOrdSoItemById(item.getSoItemId());
                BasUnit unit = unitService.selectBasUnitById(item.getUnitId());

                //非首行，则创建新行
                if (i>0){
                    Row row = sheet.createRow(bodyRowIndex+i);
                    CellStyle templateRowStyle = sheet.getRow(bodyRowIndex).getRowStyle();
                    if (templateRowStyle!=null) row.setRowStyle(templateRowStyle);

                    for (int j=0;j<11;j++){
                        cell = row.createCell(j);
                        cell.setCellStyle(sheet.getRow(bodyRowIndex).getCell(j).getCellStyle());
                    }
                }

                //给各列赋值
                cell = sheet.getRow(bodyRowIndex+i).getCell(0);
                cell.setCellValue(item.getSeqNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(1);
                cell.setCellValue(soitem.getProductName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(2);
                cell.setCellValue(soitem.getProductSpec());
                cell = sheet.getRow(bodyRowIndex+i).getCell(3);
                cell.setCellValue(soitem.getDrawNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(5);
                cell.setCellValue(unit.getUnitName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(6);
                cell.setCellValue(item.getQuantity().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(7);
                cell.setCellValue(item.getPrice().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(8);
                cell.setCellValue(item.getAmount().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(9);
                cell.setCellValue(so.getContractNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(10);
                cell.setCellValue(item.getRemark());

//                cell = sheet.getRow(bodyRowIndex+i).getCell(6);
//                cell.setCellValue(soitem.getCustomerProductNo());
//                cell = sheet.getRow(bodyRowIndex+i).getCell(2);
//                cell.setCellValue(soitem.getProductCode());
//                cell = sheet.getRow(bodyRowIndex+i).getCell(7);
//                cell.setCellValue(dateFormat.format(soitem.getNeedDay()));
            }

            //设置小计
            int sumquantityRowIndex = bodyRowIndex + itemList.size();
            int sumquantityColIndex = 6;
            int sumamountRowIndex = bodyRowIndex + itemList.size();
            int sumamountColIndex = 8;
//            cell = sheet.getRow(sumquantityRowIndex).getCell(0);
//            cell.setCellValue("合计");
            cell = sheet.getRow(sumquantityRowIndex).getCell(sumquantityColIndex);
            cell.setCellValue(sumQuantity);
            cell = sheet.getRow(sumamountRowIndex).getCell(sumamountColIndex);
            cell.setCellValue(sumAmount);

            //设置页尾
//            int billerRowIndex = bodyRowIndex + itemList.size()+1;
//            int billerColIndex = 1;
//            cell = sheet.getRow(billerRowIndex).getCell(billerColIndex);
//            cell.setCellValue(SecurityUtils.getNickname()+" "+SecurityUtils.getLoginUser().getUser().getPhonenumber());

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

    /**
     * 按合同导出装箱单
     */
    //@PreAuthorize("@ss.hasPermi('ord:deliveryOrder:exportContractBox')")
    @Log(title = "导出合同装箱单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportContractBox")
    public void exportContractBox(HttpServletResponse response, DelDeliveryOrder doo)
    {
        String sheetName = "订单装箱单";
        String excelUrl = "/profile/template/deliveryorder.xls";

        //单头坐标
        int customerRowIndex = 2;
        int customerColIndex = 0;
        int deliverydayRowIndex = 2;
        int deliverydayColIndex = 8;

        int contactRowIndex = 1;
        int contactColIndex = 11;
        int phoneRowIndex = 2;
        int phoneColIndex = 11;
        int addressRowIndex = 3;
        int addressColIndex = 11;
        //单身受行数
        int bodyRowIndex = 4;

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            doo = delDeliveryOrderService.selectDelDeliveryOrderById(doo.getId());
            ChaCustomer customer = customerService.selectChaCustomerById(doo.getCustomerId());


            DelGdItem gdItem = new DelGdItem();
            gdItem.setGdId(doo.getId());
            List<DelGdItem> itemList = delGdItemService.selectDelGdItemList(gdItem);
            if (itemList!=null && itemList.size()>1){
                sheet.shiftRows(bodyRowIndex+1,bodyRowIndex+4,itemList.size()-1);
            }

            //写入单头
            Cell cell = sheet.getRow(customerRowIndex).getCell(customerColIndex);
            cell.setCellValue("供货单位："+customer.getCustomerName());
            cell = sheet.getRow(deliverydayRowIndex).getCell(deliverydayColIndex);
            cell.setCellValue(dateFormat.format(DateUtils.getNowDate()));

//            cell = sheet.getRow(contactRowIndex).getCell(contactColIndex);
//            cell.setCellValue(customer.getContact());
//            cell = sheet.getRow(phoneRowIndex).getCell(phoneColIndex);
//            cell.setCellValue(customer.getPhone());
//            cell = sheet.getRow(addressRowIndex).getCell(addressColIndex);
//            cell.setCellValue(customer.getAddress());

            //写入单身
            double sumQuantity = 0;
            double sumAmount = 0;
            for (int i=0; itemList!=null && i<itemList.size();i++){
                DelGdItem item = itemList.get(i);

                //小计
                sumQuantity += item.getQuantity().doubleValue();
                sumAmount += item.getAmount().doubleValue();

                //取值
                OrdSo so = soService.selectOrdSoById(item.getSoId());
                OrdSoItem soitem = soitemService.selectOrdSoItemById(item.getSoItemId());
                OrdContractItem contractItem = contractItemService.selectOrdContractItemById(soitem.getContractItemId());
                BasUnit unit = unitService.selectBasUnitById(item.getUnitId());

                //非首行，则创建新行
                if (i>0){
                    Row row = sheet.createRow(bodyRowIndex+i);
                    CellStyle templateRowStyle = sheet.getRow(bodyRowIndex).getRowStyle();
                    if (templateRowStyle!=null) row.setRowStyle(templateRowStyle);

                    for (int j=0;j<11;j++){
                        cell = row.createCell(j);
                        cell.setCellStyle(sheet.getRow(bodyRowIndex).getCell(j).getCellStyle());
                    }
                }

                //给各列赋值
                cell = sheet.getRow(bodyRowIndex+i).getCell(0);
                cell.setCellValue(item.getSeqNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(1);
                cell.setCellValue(contractItem.getProductName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(2);
                cell.setCellValue(contractItem.getProductSpec());
                cell = sheet.getRow(bodyRowIndex+i).getCell(3);
                cell.setCellValue(contractItem.getDrawNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(5);
                cell.setCellValue(unit.getUnitName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(6);
                cell.setCellValue(item.getQuantity().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(7);
                cell.setCellValue(item.getPrice().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(8);
                cell.setCellValue(item.getAmount().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(9);
                cell.setCellValue(so.getContractNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(10);
                cell.setCellValue(item.getRemark());

//                cell = sheet.getRow(bodyRowIndex+i).getCell(6);
//                cell.setCellValue(soitem.getCustomerProductNo());
//                cell = sheet.getRow(bodyRowIndex+i).getCell(2);
//                cell.setCellValue(soitem.getProductCode());
//                cell = sheet.getRow(bodyRowIndex+i).getCell(7);
//                cell.setCellValue(dateFormat.format(soitem.getNeedDay()));
            }

            //设置小计
            int sumquantityRowIndex = bodyRowIndex + itemList.size();
            int sumquantityColIndex = 6;
            int sumamountRowIndex = bodyRowIndex + itemList.size();
            int sumamountColIndex = 8;
//            cell = sheet.getRow(sumquantityRowIndex).getCell(0);
//            cell.setCellValue("合计");
            cell = sheet.getRow(sumquantityRowIndex).getCell(sumquantityColIndex);
            cell.setCellValue(sumQuantity);
            cell = sheet.getRow(sumamountRowIndex).getCell(sumamountColIndex);
            cell.setCellValue(sumAmount);

            //设置页尾
//            int billerRowIndex = bodyRowIndex + itemList.size()+1;
//            int billerColIndex = 1;
//            cell = sheet.getRow(billerRowIndex).getCell(billerColIndex);
//            cell.setCellValue(SecurityUtils.getNickname()+" "+SecurityUtils.getLoginUser().getUser().getPhonenumber());

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
