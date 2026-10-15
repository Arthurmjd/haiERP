package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.priority.PriorityLevel;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.ContractType;
import net.factoryopen.core.util.FormatUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasInvoicePatternService;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.*;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractItemService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 合同登记发布Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/contract")
public class OrdContractController extends BaseController
{
    @Autowired
    private IOrdContractService contractService;
    @Autowired
    private IOrdContractItemService contractitemService;

    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IBasUnitService unitService;

    @Autowired
    private IChaCustomerService customerService;

    @Autowired
    private IBasInvoicePatternService invoiceService;

    @Autowired
    private IOrdSoService soService;

    @Autowired
    private IOrdSoItemService soitemService;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listDraft')")
    @GetMapping("/listDraft")
    public TableDataInfo listDraft(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractDraftList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listPublishing')")
    @GetMapping("/listPublishing")
    public TableDataInfo listPublishing(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractPublishingList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listProcessing')")
    @GetMapping("/listProcessing")
    public TableDataInfo listProcessing(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractProcessingList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listFinishing')")
    @GetMapping("/listFinishing")
    public TableDataInfo listFinishing(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractFinishingList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listHistory')")
    @GetMapping("/listHistory")
    public TableDataInfo listHistory(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractHistoryList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listAll')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractAllList(ordContract);
        return getDataTable(list);
    }

    /**
     * 查询合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:listAdult')")
    @GetMapping("/listAdult")
    public TableDataInfo listAdult(OrdContract ordContract)
    {
        if(ordContract!=null && ordContract.getPageEnable()!=null && ordContract.getPageEnable()) startPage();
        List<OrdContract> list = contractService.selectOrdContractList4Adult(ordContract);
        return getDataTable(list);
    }

    /**
     * 导出合同登记发布列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:export1')")
    @Log(title = "合同登记发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    public void expor1t(HttpServletResponse response, OrdContract ordContract)
    {
        List<OrdContract> list = contractService.selectOrdContractList(ordContract);
        ExcelUtil<OrdContract> util = new ExcelUtil<OrdContract>(OrdContract.class);
        util.exportExcel(response, list, "合同数据");
    }

    /**
     * 获取合同登记发布详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(contractService.selectOrdContractById(id));
    }

    /**
     * 新增合同登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:add')")
    @Log(title = "合同登记发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdContract ordContract)
    {
        AjaxResult ar = null;
        try{
            BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(ordContract.getInvoicePatternId());
            double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();
            ordContract.setAmountTaxed(new BigDecimal(ordContract.getAmountUntaxed().doubleValue()*(1+invoiceRate)));
           int ret = contractService.insertOrdContract(ordContract);
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
     * 修改合同登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:edit')")
    @Log(title = "合同登记发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdContract ordContract)
    {
        AjaxResult ar = null;

        try{
            if(ordContract.getStatus()!= BillStatus.BASE_DRAFT && ordContract.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                String msg = "合同[" + ordContract.getContractNo() + "]在当前状态下不能修改！";
                throw new StatusUnavailableException(msg);
            }

            BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(ordContract.getInvoicePatternId());
            double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();
            ordContract.setAmountTaxed(new BigDecimal(ordContract.getAmountUntaxed().doubleValue()*(1+invoiceRate)));
            int ret = contractService.updateOrdContract(ordContract);
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
     * 删除合同登记发布
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:remove')")
    @Log(title = "合同登记发布", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;

        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if(contract.getStatus()!= BillStatus.BASE_DRAFT && contract.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                        String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能删除！";
                        throw new StatusUnavailableException(msg);
                    }

                    List<OrdContractItem> contractitemList = contractitemService.selectOrdContractItemListByContractId(contract.getId());
                    if(contractitemList!=null && contractitemList.size()>0){
                        String msg = "合同[" + contract.getContractNo() + "]存有合同明细，不能删除！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret = contractService.deleteOrdContractByIds(ids);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:enable')")
    @Log(title = "发布", businessType = BusinessType.OTHER)
    @PutMapping("/enable/{ids}")
    public AjaxResult enable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if(contract.getStatus()!= BillStatus.BASE_DRAFT && contract.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                        String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能发布生效！";
                        throw new StatusUnavailableException(msg);
                    }

                    //检查下单日期
                    if(contract.getOrderDay()==null){
                        String msg = "合同[" + contract.getContractNo() + "]的合同日期不能为空！";
                        throw new DataNotExistException(msg);
                    }

                    //若为总成合同，则检查总成料号
                    if (contract.getContractType()==ContractType.ASSEMBLY){
                        if (contract.getProductId()==null){
                            String msg = "总成合同[" + contract.getContractNo() + "]的物料代码不存在！";
                            throw new DataNotExistException(msg);
                        }
                    }
                    //若为普通合同，则检查明细项料号
                    else{
                        boolean hasEmptyProduct =false;
                        List<OrdContractItem> itemList = contractitemService.selectOrdContractItemListByContractId(contract.getId());
                        if (itemList!=null){
                            for (OrdContractItem item : itemList){
                                hasEmptyProduct = hasEmptyProduct || item.getProductId()==null;
                            }
                        }
                        if (hasEmptyProduct){
                            String msg = "合同[" + contract.getContractNo() + "]的明细缺少物料代码！";
                            throw new DataNotExistException(msg);
                        }
                    }
                }
            }

            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                contractService.enable(obj);
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
     *  ids 订单ID集合
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:toSo')")
    @Log(title = "转单", businessType = BusinessType.OTHER)
    @PutMapping("/toSo/{ids}")
    public AjaxResult toSo(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                contractService.toSo(ids);
                ar = AjaxResult.success("操作成功：[转单]"+ids.length+"条数据！");
            }
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:changeOrdContract')")
    @Log(title = "变更", businessType = BusinessType.OTHER)
    @PutMapping("/changeOrdContract")
    public AjaxResult changeOrdContract(@RequestBody OrdContract ordContract)
    {
        AjaxResult ar = null;
        try{
            if (ordContract.getStatus()< BillStatus.BASE_ENABLE
                    || ordContract.getStatus()== BillStatus.LIFE_FROZEN || ordContract.getStatus()==BillStatus.LIFE_DEAD
                    || ordContract.getStatus()>=BillStatus.FINISH_WAITING){
                String msg = "变更非法！合同[" + ordContract.getContractNo() + "]在当前状态下不能变更！";
                throw new StatusUnavailableException(msg);
            }

            contractService.changeOrdContract(ordContract);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:finish')")
    @Log(title = "结案", businessType = BusinessType.OTHER)
    @PutMapping("/finish/{ids}")
    public AjaxResult finish(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if (contract.getStatus()!=BillStatus.FINISH_WAITING){
                        String msg="结案["+contract.getContractNo()+"]非法！状态非为待结案不能冻结。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret=-1;
            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                obj.setStatus(BillStatus.FINISH);
                contractService.updateOrdContract(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:froze')")
    @Log(title = "冻结", businessType = BusinessType.OTHER)
    @PutMapping("/froze/{ids}")
    public AjaxResult froze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if (contract.getStatus()==BillStatus.BASE_DRAFT
                            || contract.getStatus()== BillStatus.LIFE_FROZEN || contract.getStatus()==BillStatus.LIFE_DEAD
                            || contract.getStatus()>=BillStatus.FINISH_WAITING){
                        String msg="冻结["+contract.getContractNo()+"]非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能冻结。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret=-1;
            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                ret=contractService.froze(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:unfroze')")
    @Log(title = "解冻", businessType = BusinessType.OTHER)
    @PutMapping("/unfroze/{ids}")
    public AjaxResult unfroze(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if(contract.getStatus()!=BillStatus.LIFE_FROZEN){
                        String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能解冻！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                contractService.unfroze(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:disable')")
    @Log(title = "停用", businessType = BusinessType.OTHER)
    @PutMapping("/disable/{ids}")
    public AjaxResult disable(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract obj = contractService.selectOrdContractById(id);
                    if (obj.getStatus()==BillStatus.BASE_DRAFT
                            || obj.getStatus()== BillStatus.LIFE_FROZEN || obj.getStatus()==BillStatus.LIFE_DEAD
                            || obj.getStatus()>=BillStatus.FINISH_WAITING){
                        String msg="合同["+obj.getContractNo()+"]停用非法！状态为底稿、冻结、停用、待结案、已结案的对象，不能停用。";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                contractService.disable(obj);
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
    //@PreAuthorize("@ss.hasPermi('ord:contract:finishforcely')")
    @Log(title = "强结", businessType = BusinessType.OTHER)
    @PutMapping("/finishforcely/{ids}")
    public AjaxResult finishforcely(@PathVariable Long[] ids)
    {
        AjaxResult ar = null;
        try{
            if (ids!=null){
                for (Long id:ids){
                    OrdContract contract = contractService.selectOrdContractById(id);
                    if(contract.getStatus()>=BillStatus.LIFE_FROZEN || contract.getStatus()<BillStatus.BASE_ENABLE){
                        String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能强制结案！";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            OrdContract obj = null;
            for (int i = 0; i < ids.length; i++){
                obj= contractService.selectOrdContractById(ids[i]);
                contractService.finishforcely(obj);
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
     * 请求临时料号
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:requestTempProduct')")
    @Log(title = "请求临时料号", businessType = BusinessType.OTHER)
    @PutMapping("/requestTempProduct")
    public AjaxResult requestTempProduct(@RequestBody OrdContract ordContract)
    {
        AjaxResult ar = null;
        try{
            contractService.requestTempProduct(ordContract);
            ar = AjaxResult.success("操作成功：[请求临时料号]"+"执行完成！");
        }
        catch (Exception e){
            e.printStackTrace();
            ar = AjaxResult.error("操作异常："+e.getMessage());
        }
        return ar;
    }

    /**
     * 导入宜桐特殊合同
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:importExcel')")
    @Log(title = "导入标准合同", businessType = BusinessType.IMPORT)
    @PutMapping("/importExcel")
    public AjaxResult importExcel(@RequestParam String excelUrl) {
        int customerRowIndex = 2;
        int customerColIndex = 1;
        int contractRowIndex = 1;
        int contractColIndex = 1;
        int orderdayRowIndex = -1;
        int orderdayColIndex = -1;
        int titleRowIndex = 5;
        Class clazz = ExcelImport.class;
        return this.importContractExcel(excelUrl,clazz,0,
                null,customerRowIndex,customerColIndex,null,
                contractRowIndex,contractColIndex, null,
                orderdayRowIndex,orderdayColIndex,null,
                titleRowIndex);
    }

    private AjaxResult importContractExcel(String excelUrl, Class<ExcelImportInteface> clazz, int sheetIndex,
                                          String customerCellCaption,int customerRowindex, int customerColIndex, String customerPrefix,
                                          int contractRowIndex, int contractColIndex, String contractPrefix,
                                          int orderdayRowIndex, int orderdayColIndex, String orderdayPrefix,
                                          int titleRowIndex) {
        String absUrl=excelUrl.replace(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        ExcelUtil<ExcelImportInteface> util = new ExcelUtil<ExcelImportInteface>(clazz);
        List<String> errProducts =new ArrayList<String>();
        List<String> errUnits = new ArrayList<String>();
        String contractNo = null;
        Date orderDay = null;
        String customerName = null;
        ChaCustomer customer = null;

        int importNum = 0;
        String msg="";

        try{
            //取Excel数据
            InputStream is = new FileInputStream(absUrl);
            //取非表格数据
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(sheetIndex);
            Cell cell = sheet.getRow(contractRowIndex).getCell(contractColIndex);
            //取contractNo，清洗contractNo无关字符
            if (cell.getCellType()==CellType.NUMERIC) cell.setCellType(CellType.STRING);
            contractNo = cell.getStringCellValue();
            if (contractNo==null || contractNo.trim().equals("")){
                String errMsg = "没有找到合同号于 ["+(contractRowIndex+1)+"行"+(contractColIndex+1)+"列] 的位置上";
                throw new Exception(errMsg);
            }

            if (contractPrefix!=null) contractNo = contractNo.replace(contractPrefix,"").trim();
            //取orderDay，清洗contractNo无关字符
            if (orderdayRowIndex!=-1 && orderdayColIndex !=-1){
                cell = sheet.getRow(orderdayRowIndex).getCell(orderdayColIndex);
                if (cell.getCellType()==CellType.NUMERIC) cell.setCellType(CellType.STRING);
                String dateString = cell.getStringCellValue();
                if (dateString==null || dateString.trim().equals("")){
                    String errMsg = "没有找到下单日期于 ["+(orderdayRowIndex+1)+"行"+(orderdayColIndex+1)+"列] 的位置上";
                    throw new Exception(errMsg);
                }

                if (orderdayPrefix!=null) dateString = dateString.replace(orderdayPrefix,"").trim();
                orderDay = FormatUtil.string2Date(dateString);
            }
            //取customerName，清洗customerName无关字符
            if (customerCellCaption!=null){
                for (Row row : sheet) {
                    for (Cell c : row) {
                        if (c.toString().equals(customerCellCaption)) {
                            customerRowindex = row.getRowNum();
                            customerColIndex = c.getColumnIndex()+1;
                            break;
                        }
                    }
                }
            }
            cell =sheet.getRow(customerRowindex).getCell(customerColIndex);
            if (cell.getCellType()==CellType.NUMERIC) cell.setCellType(CellType.STRING);
            //若客户标题跨列，则一直给客户名称的列序号+1
            while (customerCellCaption !=null && customerCellCaption.equals(cell.getStringCellValue())) {
                customerColIndex = cell.getColumnIndex()+1;
                cell =sheet.getRow(customerRowindex).getCell(customerColIndex);
            }
            customerName = cell.getStringCellValue();
            if (customerName==null || customerName.trim().equals("")){
                String errMsg = "没有找到客户名于 ["+(customerRowindex+1)+"行"+(customerColIndex+1)+"列] 的位置上";
                throw new Exception(errMsg);
            }

            if (customerPrefix!=null) customerName = customerName.replace(customerPrefix,"").trim();
            customerName = customerName.replaceAll("[0-9a-zA-Z]", "").trim();
            if (customerName.indexOf("公司")>=0 && customerName.indexOf("厂")>=0){
                customerName = customerName.substring(0,customerName.indexOf("厂")+"厂".length());
            }else{
                if (customerName.indexOf("公司")>=0) customerName = customerName.substring(0,customerName.indexOf("公司")+"公司".length());
                if (customerName.indexOf("厂")>=0) customerName = customerName.substring(0,customerName.indexOf("厂")+"厂".length());
            }

            try{
                //取customer实体类
                customer = customerService.selectChaCustomerByName(customerName);
            } catch (TooManyResultsException e){
                e.printStackTrace();
                String errMsg = "客户重复错误！Excel客户名 ["+customerName+"]对应了多个客户资料，请确保客户的唯一性";
                throw new Exception(errMsg);
            }catch (Exception e){
                e.printStackTrace();
                String errMsg = "客户名错误！Excel客户名 ["+customerName+"]可能对应了多个客户账号";
                throw new Exception(errMsg);
            }
            //关闭文件流
            wb.close();
            is.close();

            if (customer==null){
                msg="客户不存在或客户名称不正确！";
            }
            else if (contractNo==null){
                msg=msg+"合同号不存在！";
            }else{
                Long invoiceId = 8L;
                BasInvoicePattern invoice= invoiceService.selectBasInvoicePatternById(invoiceId);
                double invoiceRate = invoice==null?0.13:invoice.getPatternRate().doubleValue();

                OrdContract contract = new OrdContract();
                contract.setContractType(ContractType.STANDARD);
                contract.setContractNo(contractNo);
                contract.setCustomerId(customer==null?null:customer.getId());
                contract.setOrderDay(orderDay);
                contract.setInvoicePatternId(invoiceId);
                contract.setPayPatternId(2L);
                contract.setCurrencyId(1L);
                contract.setShipmentTypeId(1L);
                contract.setExchangeRate(new BigDecimal(1));

                contract.refreshUpdatingStamp();
                contractService.insertOrdContract(contract);

                //转excel表格
                is = new FileInputStream(absUrl);
                List<ExcelImportInteface> list = util.importExcel(sheetIndex, is, titleRowIndex);
                if (list != null) {
                    double amountTotal = 0;

                    //逐一处理
                    boolean hasUnit = false;
                    boolean hasPrice = false;
                    boolean hasNeedDay = false;
                    for (ExcelImportInteface item : list) {
                        //判空行 则跳过
                        if (item.getProductCode()==null && item.getProductName()==null) continue;
                        if (item.getProductName()!=null && item.getProductName().trim().equals("")) continue;
                        if (item.getQuantity()==null || item.getQuantity().doubleValue()==0) continue;
                        if ((item.getUnit()==null || item.getUnit().trim().equals("")) && hasUnit) continue;
                        if (item.getPrice()==null && hasPrice) continue;
                        if (item.getNeedDay()==null && hasNeedDay) continue;

                        //标记非空行有值否
                        if (!hasUnit && item.getUnit()!=null && item.getUnit().trim().length()>0) hasUnit=true;
                        if (!hasPrice && item.getPrice()!=null) hasPrice=true;
                        if (!hasNeedDay && item.getNeedDay()!=null) hasNeedDay=true;

                        //设默认值
                        BigDecimal price = item.getPrice()==null?new BigDecimal(0):item.getPrice();
                        BigDecimal quantityOrder = item.getQuantity()==null?new BigDecimal(0):item.getQuantity();
                        Long unitId = null;

                        //取产品数据，并判异
                        EngProductBasis product = new EngProductBasis();
                        product.setMaterialName(item.getProductName());
                        List<EngProductBasis> productList = productService.selectEngProductBasisList(product);
                        if (productList != null && productList.size()>0) {
                            product = productList.get(0);
                            price = price!=new BigDecimal(0)?price:product.getPriceLast();
                        }else{
                            errProducts.add(item.getProductName());
                        }

                        //取单位数据，并判异
                        BasUnit unit = new BasUnit();
                        unit.setUnitName(item.getUnit());
                        List<BasUnit> unitList = unitService.selectBasUnitList(unit);
                        if (unitList != null && unitList.size() > 0) {
                            unitId = unitList.get(0).getId();
                        } else {
                            errUnits.add(item.getProductName());
                        }

                        //保存明细数据
                        OrdContractItem contractItem = new OrdContractItem();
                        contractItem.setStatus(BillStatus.BASE_DRAFT);
                        contractItem.setContractId(contract.getId());
                        contractItem.setSeqNo(String.valueOf(importNum + 1));
                        contractItem.setProductId(product == null ? null : product.getId());
                        contractItem.setProductCode(item.getProductCode());
                        contractItem.setProductName(item.getProductName());
                        contractItem.setProductSpec(item.getProductSpec());
                        contractItem.setDrawNo(item.getDrawNo());
                        contractItem.setCustomerProductNo(item.getCustomerProductNo());
                        contractItem.setAbroadProductNo(item.getAbroadProductNo());
                        contractItem.setQuantityOrder(quantityOrder);
                        contractItem.setUnitId(unitId);
                        contractItem.setPrice(price);
                        contractItem.setDiscountPrice(price);
                        contractItem.setAmount(new BigDecimal(price.doubleValue() * quantityOrder.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        contractItem.setDiscountRate(new BigDecimal(1));
                        contractItem.setFinalAmount(new BigDecimal(price.doubleValue() * quantityOrder.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        contractItem.setPriority(PriorityLevel.PRIORITY_NORMAL);
                        contractItem.setNeedDay(item.getNeedDay());
                        contractItem.setRemark(item.getRemark());
                        contractItem.refreshCreatingStamp();

                        try{
                            contractitemService.insertOrdContractItem(contractItem);
                        }catch (DataIntegrityViolationException e){
                            String errMsg = "数据错误！该图号的某个字段值太长："+item.getDrawNo();
                            throw new Exception(errMsg);
                        }

                        amountTotal = amountTotal + contractItem.getAmount().doubleValue() * contractItem.getDiscountRate().doubleValue();
                        importNum++;
                    }

                    //更新合同金额
                    contract.setInvoicePatternId(invoiceId);
                    contract.setAmountUntaxed(new BigDecimal(amountTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
                    contract.setAmountTaxed(new BigDecimal(amountTotal * (1 + invoiceRate)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    contractService.updateOrdContract(contract);
                }

                //取返回消息
                if (errProducts.size()>0 || errUnits.size()>0){
                    if (importNum>0){
                        msg = importNum +" 条合同明细导入成功！";
                    }
                    if (errProducts.size()>0){
                        msg=msg+"无产品编码："+ StringUtils.join(errProducts.toArray(), ",")+"。";
                    }
                    if (errUnits.size()>0){
                        msg=msg+"无单位："+ StringUtils.join(errUnits.toArray(), ",")+"。";
                    }
                    DataNotExistException exception =new DataNotExistException(msg);
                    throw exception;
                }else{
                    msg = "成功导入 "+ importNum +" 条合同明细！";
                }
            }

        }catch (Exception e){
            e.printStackTrace();

            AjaxResult ar = AjaxResult.error();
            ar.put("msg",e.getMessage());
            return ar;
        }

        AjaxResult ar = AjaxResult.success();
        ar.put("msg",msg);
        return ar;
    }

    /**
     * 导出合同
     */
    //@PreAuthorize("@ss.hasPermi('ord:contract:export')")
    @Log(title = "导出合同", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdContract contract)
    {
        String sheetName = "合同数据";
        String excelUrl = "/profile/download/template/contract.xlsx";

        //单头坐标
        int customerRowIndex = 1;
        int customerColIndex = 1;
        int contractRowIndex = 2;
        int contractColIndex = 1;
        int orderdayRowIndex = 3;
        int orderdayColIndex = 1;
        int contactRowIndex = 1;
        int contactColIndex = 11;
        int phoneRowIndex = 2;
        int phoneColIndex = 11;
        int addressRowIndex = 3;
        int addressColIndex = 11;
        int billerRowIndex = 5;
        int billerColIndex = 1;
        int billdayRowIndex = 5;
        int billdayColIndex = 3;
        //单身受行数
        int bodyRowIndex = 7;

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
            contract = contractService.selectOrdContractById(contract.getId());
            ChaCustomer customer = customerService.selectChaCustomerById(contract.getCustomerId());
            BasInvoicePattern invoicePattern = invoiceService.selectBasInvoicePatternById(contract.getInvoicePatternId());
            List<OrdContractItem> contractItemList = contractitemService.selectOrdContractItemListByContractId(contract.getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //写入单身
            Cell cell = sheet.getRow(customerRowIndex).getCell(customerColIndex);
            cell.setCellValue(customer.getCustomerName());
            cell = sheet.getRow(contractRowIndex).getCell(contractColIndex);
            cell.setCellValue(contract.getContractNo());
            cell = sheet.getRow(orderdayRowIndex).getCell(orderdayColIndex);
            cell.setCellValue(dateFormat.format(contract.getOrderDay()));
            cell = sheet.getRow(contactRowIndex).getCell(contactColIndex);
            cell.setCellValue(customer.getContact());
            cell = sheet.getRow(phoneRowIndex).getCell(phoneColIndex);
            cell.setCellValue(customer.getPhone());
            cell = sheet.getRow(addressRowIndex).getCell(addressColIndex);
            cell.setCellValue(customer.getAddress());
            cell = sheet.getRow(billerRowIndex).getCell(billerColIndex);
            cell.setCellValue(SecurityUtils.getNickname());
            cell = sheet.getRow(billdayRowIndex).getCell(billdayColIndex);
            cell.setCellValue(dateFormat.format(DateUtils.getNowDate()));

            //写入单身
            for (int i=0; contractItemList!=null && i<contractItemList.size();i++){
                OrdContractItem item = contractItemList.get(i);

                //取值
                BasUnit unit = unitService.selectBasUnitById(item.getUnitId());
                double taxedPrice = item.getDiscountPrice().doubleValue()*(1+invoicePattern.getPatternRate().doubleValue());
                taxedPrice = Math.round(taxedPrice*100)/100;
                double taxedAmount = item.getFinalAmount().doubleValue()*(1+invoicePattern.getPatternRate().doubleValue());
                taxedAmount = Math.round(taxedAmount*100)/100;


                //非首行，则创建新行
                if (i>0){
                    Row row = sheet.createRow(bodyRowIndex+i);
                    row.setRowStyle(sheet.getRow(bodyRowIndex).getRowStyle());

                    for (int j=0;j<14;j++){
                        cell = row.createCell(j);
                        cell.setCellStyle(sheet.getRow(bodyRowIndex).getCell(j).getCellStyle());
                    }
                }

                //给各列赋值
                cell = sheet.getRow(bodyRowIndex+i).getCell(0);
                cell.setCellValue(item.getSeqNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(1);
                cell.setCellValue(item.getProductCode());
                cell = sheet.getRow(bodyRowIndex+i).getCell(2);
                cell.setCellValue(item.getProductName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(3);
                cell.setCellValue(item.getProductSpec());
                cell = sheet.getRow(bodyRowIndex+i).getCell(4);
                cell.setCellValue(item.getDrawNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(5);
                cell.setCellValue(item.getCustomerProductNo());
                cell = sheet.getRow(bodyRowIndex+i).getCell(6);
                cell.setCellValue(dateFormat.format(item.getNeedDay()));
                cell = sheet.getRow(bodyRowIndex+i).getCell(7);
                cell.setCellValue(unit.getUnitName());
                cell = sheet.getRow(bodyRowIndex+i).getCell(8);
                cell.setCellValue(item.getQuantityOrder().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(9);
                cell.setCellValue(item.getDiscountPrice().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(10);
                cell.setCellValue(item.getFinalAmount().doubleValue());
                cell = sheet.getRow(bodyRowIndex+i).getCell(11);
                cell.setCellValue(taxedPrice);
                cell = sheet.getRow(bodyRowIndex+i).getCell(12);
                cell.setCellValue(taxedAmount);
                cell = sheet.getRow(bodyRowIndex+i).getCell(13);
                cell.setCellValue(item.getRemark());
            }

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
