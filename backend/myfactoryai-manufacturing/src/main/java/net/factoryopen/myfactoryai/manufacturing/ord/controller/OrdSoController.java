package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.DateUnavailableException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.priority.PriorityLevel;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasInvoicePatternService;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItemExcel;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单登记发布Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/so")
public class OrdSoController extends BaseController
{
    @Autowired
    private IOrdSoService soService;
    @Autowired
    private IOrdSoItemService soitemService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IBasUnitService unitService;

    @Autowired
    private IChaCustomerService customerService;

    @Autowired
    private IBasInvoicePatternService invoiceService;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoDraftList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoPublishingList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoProcessingList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoFinishingList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoHistoryList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoAllList(ordSo);
        return getDataTable(list);
    }

    /**
     * 查询订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:listAdult')")
    @GetMapping("/listAdult")
    public TableDataInfo listAdult(OrdSo ordSo)
    {
        if(ordSo!=null && ordSo.getPageEnable()!=null && ordSo.getPageEnable()) startPage();
        List<OrdSo> list = soService.selectOrdSoList4Adult(ordSo);
        return getDataTable(list);
    }

    /**
     * 导出订单登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:export')")
    @Log(title = "订单登记发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSo ordSo)
    {
        List<OrdSo> list = soService.selectOrdSoList(ordSo);
        ExcelUtil<OrdSo> util = new ExcelUtil<OrdSo>(OrdSo.class);
        util.exportExcel(response, list, "订单登记发布数据");
    }

    /**
     * 获取订单登记发布详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(soService.selectOrdSoById(id));
    }

    /**
     * 新增订单登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:add')")
    @Log(title = "订单登记发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdSo ordSo)
    {
        AjaxResult ar = null;
        try{
            BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(ordSo.getInvoicePatternId());
            double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();
            ordSo.setAmountTaxed(new BigDecimal(ordSo.getAmountUntaxed().doubleValue()*(1+invoiceRate)));
           int ret = soService.insertOrdSo(ordSo);
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
     * 修改订单登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:edit')")
    @Log(title = "订单登记发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdSo ordSo)
    {
        AjaxResult ar = null;

        try{
            if(ordSo.getStatus()!= BillStatus.BASE_DRAFT && ordSo.getStatus()!=BillStatus.BASE_AUDIT_REJECT && ordSo.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                String msg = "订单[" + ordSo.getBillNo() + "]在当前状态下不能修改！";
                throw new StatusUnavailableException(msg);
            }

            BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(ordSo.getInvoicePatternId());
            double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();
            ordSo.setAmountTaxed(new BigDecimal(ordSo.getAmountUntaxed().doubleValue()*(1+invoiceRate)));
            int ret = soService.updateOrdSo(ordSo);
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
     * 删除订单登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:remove')")
    @Log(title = "订单登记发布", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if(so.getStatus()!= BillStatus.BASE_DRAFT && so.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                        String msg = "订单[" + so.getBillNo() + "]在当前状态下不能删除！";
                        throw new StatusUnavailableException(msg);
                    }

                    List<OrdSoItem> soitemList = soitemService.selectOrdSoItemListBySoId(so.getId());
                    if(soitemList!=null && soitemList.size()>0){
                        String msg = "订单[" + so.getBillNo() + "]存有订单明细，不能删除！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret = soService.deleteOrdSoByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('ord:so:enable')")
    @Log(title = "发布", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if(so.getStatus()!= BillStatus.BASE_DRAFT && so.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "订单[" + so.getBillNo() + "]在当前状态下不能发布生效！";
                        throw new StatusUnavailableException(msg);
                    }

                    List<OrdSoItem> soitemList = this.soitemService.selectOrdSoItemListBySoId(id);
                    if (soitemList != null && soitemList.size() > 0) {
                        for (OrdSoItem soItem : soitemList) {
                            if (soItem.getNeedDay()==null){
                                String msg = "订单明细[" + so.getBillNo() + ","+soItem.getSeqNo()+"]的需求日期不能为空！";
                                throw new DateUnavailableException(msg);
                            }
                        }
                    }

                }
            }

            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                soService.enable(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:so:changeOrdSo')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeOrdSo")
    public AjaxResult changeOrdSo(@RequestBody OrdSo ordSo)
    {
        AjaxResult ar = null;
        try{
            if (ordSo.getStatus()< BillStatus.BASE_ENABLE
                    || ordSo.getStatus()== BillStatus.LIFE_FROZEN || ordSo.getStatus()==BillStatus.LIFE_DEAD
                    || ordSo.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！订单[" + ordSo.getBillNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            soService.changeOrdSo(ordSo);
            ar = AjaxResult.success("操作成功：[变更]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     *  ids 结案 主键
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:finish')")
    @Log(title = "结案", businessType = BusinessType.OTHER)
    @PutMapping("/finish/{ids}")
    public AjaxResult finish(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if (so.getStatus()!=BillStatus.FINISH_WAITING){
                        String msg="结案["+so.getBillNo()+"]非法！状态非为待结案不能冻结。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret=-1;
            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                obj.setStatus(BillStatus.FINISH);
                soService.updateOrdSo(obj);
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
     *  ids 冻结 主键
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if (so.getStatus()==BillStatus.BASE_DRAFT
                            || so.getStatus()== BillStatus.LIFE_FROZEN || so.getStatus()==BillStatus.LIFE_DEAD
                            || so.getStatus()>=BillStatus.FINISH_WAITING){
                        String msg="冻结["+so.getBillNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret=-1;
            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                ret=soService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:so:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if(so.getStatus()!=BillStatus.LIFE_FROZEN){
                        String msg = "订单[" + so.getBillNo() + "]在当前状态下不能解冻！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                soService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:so:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo obj = soService.selectOrdSoById(id);
                    if (obj.getStatus()==BillStatus.BASE_DRAFT
                            || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                            || obj.getStatus()>=BillStatus.FINISH_WAITING){
                        String msg="订单["+obj.getBillNo()+"]停用非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                soService.disable(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:so:finishforcely')")
    @Log(title = "强结", businessType = BusinessType.OTHER)
    @PutMapping("/finishforcely/{ids}")
    public AjaxResult finishforcely(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdSo so = soService.selectOrdSoById(id);
                    if(so.getStatus()>=BillStatus.LIFE_FROZEN || so.getStatus()<BillStatus.BASE_ENABLE){
                        String msg = "订单[" + so.getBillNo() + "]在当前状态下不能强制结案！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdSo obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= soService.selectOrdSoById(ids[i]);
                soService.finishforcely(obj);
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
     * 修改订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:so:import')")
    @Log(title = "导入订单", businessType = BusinessType.IMPORT)
    @PutMapping("/importExcel")
    public AjaxResult importExcel(@RequestParam String excelUrl) {
        String absUrl=excelUrl.replace(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        ExcelUtil<OrdContractItemExcel> util = new ExcelUtil<OrdContractItemExcel>(OrdContractItemExcel.class);
        List<String> errProducts =new ArrayList<String>();
        List<String> errUnits = new ArrayList<String>();
        String contractNo=null;
        ChaCustomer customer = null;

        int importNum = 0;
        String msg="";

        try{
            //取更异常单号
            String billNo = billcodeManager.getNextBillno("SO");
            billcodeManager.updateBillSeq("SO");
            //创建SO
            //OrdSo so = soService.selectOrdSoById(soId);
            OrdSo so = new OrdSo();
            so.setBillNo(billNo);
            so.setInvoicePatternId(8L);
            so.setPayPatternId(2L);
            so.setCurrencyId(1L);
            so.setShipmentTypeId(1L);
            so.setExchangeRate(new BigDecimal(1));
            so.setOrderDay(DateUtils.getNowDate());

            BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(so.getInvoicePatternId());
            double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();
            Long invoiceId = invoice==null?8:invoice.getId();
            //取Excel数据
            InputStream is = new FileInputStream(absUrl);
            List<OrdContractItemExcel> list = util.importExcel(is, 0);
            if (list != null) {
                double amountTotal=0;

                //逐一检查数据合法性
                for (OrdContractItemExcel item : list) {
                    if(importNum==0) {
                        //取合同号,并判异
                        contractNo = item.getContractNo()!=null?item.getContractNo():null;
                        if (contractNo==null) continue;
                        //取客户名,并判异
                        String customerName = item.getCustomerName()!=null?item.getCustomerName():null;
                        if (contractNo==null) continue;
                        else{
                            customer = customerService.selectChaCustomerByName(customerName);
                            if (customer==null) continue;
                        }
                    }
                    //取产品数据，并判异
                    EngProductBasis product = productService.selectEngProductBasisByCode(item.getProductName());
                    if (product==null){
                        errProducts.add(item.getProductName());
                        continue;
                    }
                    //取单位数据，并判异
                    BasUnit unit = unitService.selectBasUnitById(product.getUnitId());
                    if (!unit.getUnitName().equals(item.getUnit())){
                        errUnits.add(item.getProductName());
                        continue;
                    }

                    //保存订单，返回ID
                    if(importNum==0) {
                        so.setContractNo(contractNo);
                        so.setCustomerId(customer.getId());
                        soService.insertOrdSo(so);
                    }

                    //保存明细数据
                    OrdSoItem soItem = new OrdSoItem();
                    soItem.setStatus(BillStatus.BASE_DRAFT);
                    soItem.setSoId(so.getId());
                    soItem.setSeqNo(item.getSeqNo());
                    soItem.setProductId(product.getId());
                    soItem.setProductCode(product.getMaterialCode());
                    soItem.setProductName(product.getMaterialName());
                    soItem.setProductSpec(product.getMaterialSpec());
                    soItem.setDrawNo(product.getDrawNo());
                    soItem.setCustomerProductNo(product.getCustomerProductNo());
                    soItem.setQuantityOrder(item.getQuantity());
                    soItem.setUnitId(product.getUnitId());
                    soItem.setPrice(item.getPrice()!=null?item.getPrice():product.getPriceLast());
                    soItem.setDiscountPrice(soItem.getPrice());
                    soItem.setAmount(new BigDecimal(soItem.getDiscountPrice().doubleValue()*soItem.getQuantityOrder().doubleValue()));
                    soItem.setDiscountRate(new BigDecimal(1));
                    soItem.setFinalAmount(new BigDecimal(soItem.getAmount().doubleValue()*soItem.getDiscountRate().doubleValue()));
                    soItem.setPriority(PriorityLevel.PRIORITY_NORMAL);
                    soItem.setNeedDay(item.getNeedDay());
                    soItem.refreshCreatingStamp();
                    soitemService.insertOrdSoItem(soItem);

                    amountTotal =amountTotal + soItem.getAmount().doubleValue()*soItem.getDiscountRate().doubleValue();
                    importNum++;
                }

                //更新订单金额
                so.setInvoicePatternId(invoiceId);
                so.setAmountUntaxed(new BigDecimal(amountTotal));
                so.setAmountTaxed(new BigDecimal(amountTotal*(1+invoiceRate)));
                soService.updateOrdSo(so);
            }

            //取返回消息
            if (errProducts.size()>0 || errUnits.size()>0){
                if (importNum>0){
                    msg = String.valueOf(list.size()-importNum);
                    msg = importNum +" 条销售明细导入成功， "+ msg +" 条销售明细导入失败！";
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
