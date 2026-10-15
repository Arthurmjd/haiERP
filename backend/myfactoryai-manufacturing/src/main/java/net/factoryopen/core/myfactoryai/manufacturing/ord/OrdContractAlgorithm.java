package net.factoryopen.core.myfactoryai.manufacturing.ord;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.priority.PriorityLevel;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.ContractType;
import net.factoryopen.core.common.type.MaterialBaseType;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@Service
public class OrdContractAlgorithm
{
    @Autowired(required = false)
    private OrdContractMapper contractMapper;

    @Autowired(required = false)
    private OrdContractItemMapper contractItemMapper;

    @Autowired(required = false)
    private OrdSoMapper soMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soItemMapper;

    @Autowired
    private EngProductBasisMapper productManager;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     *  转单
     *
     * @param ids 订单ID集合
     * @return 结果
     */
    public int toSo(Long[] ids) throws Exception
    {
        for (Long id:ids){
            OrdContract contract = contractMapper.selectOrdContractById(id);

            //状态检查
            if(contract.getStatus()!=BillStatus.BASE_ENABLE){
                String msg = "合同[" + contract.getContractNo() + "]在当前状态下不能转订单！";
                throw new StatusUnavailableException(msg);
            }

            //若为总成合同，则检查总成品料号
            if (contract.getContractType()==ContractType.ASSEMBLY){
                if (contract.getProductId()==null){
                    String msg = "总成合同[" + contract.getContractNo() + "]的物料代码不存在！";
                    throw new DataNotExistException(msg);
                }
            }
            //若为普通合同，则检查明细项料号
            else{
                boolean hasEmptyProduct =false;
                List<OrdContractItem> itemList = contractItemMapper.selectOrdContractItemListByContractId(contract.getId());
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

            //检查下单日期
            if(contract.getOrderDay()==null){
                String msg = "合同[" + contract.getContractNo() + "]的合同日期不能为空！";
                throw new DataNotExistException(msg);
            }
        }

        OrdContract contract = null;
        for (int i = 0; i < ids.length; i++){
            contract= contractMapper.selectOrdContractById(ids[i]);

            //取更异常单号
            String billNo = billcodeManager.getNextBillno("SO");
            billcodeManager.updateBillSeq("SO");
            //创建SO
            OrdSo so = new OrdSo();
            so.setBillNo(billNo);
            so.setStatus(BillStatus.BASE_AUDIT_PASS);
            so.setInvoicePatternId(contract.getInvoicePatternId());
            so.setPayPatternId(contract.getPayPatternId());
            so.setCurrencyId(contract.getCurrencyId());
            so.setShipmentTypeId(contract.getShipmentTypeId());
            so.setExchangeRate(contract.getExchangeRate());
            so.setContractNo(contract.getContractNo());
            so.setCustomerId(contract.getCustomerId());
            so.setInvoicePatternId(contract.getInvoicePatternId());
            so.setAmountUntaxed(contract.getAmountUntaxed());
            so.setAmountTaxed(contract.getAmountTaxed());
            so.setOrderDay(DateUtils.getNowDate());
            so.refreshCreatingStamp();
            soMapper.insertOrdSo(so);

            List<OrdContractItem> list = contractItemMapper.selectOrdContractItemListByContractId(contract.getId());
            //若为总成合同，创建一条明细
            if (contract.getContractType()==ContractType.ASSEMBLY){
                //取数
                double amountUntaxed = contract.getAmountUntaxed().doubleValue();
                double amountTaxed = contract.getAmountTaxed().doubleValue();
                double quantity = contract.getProductQuantity()==null?1:contract.getProductQuantity().intValue();
                //取料
                EngProductBasis product = productManager.selectEngProductBasisById(contract.getProductId());
                //取需求日期
                Date needDay = null;
                if (list != null) {
                    for (OrdContractItem item : list) {
                        if (item.getNeedDay()!=null){
                            if (needDay==null) needDay = item.getNeedDay();
                            else{
                                if (needDay.getTime()>item.getNeedDay().getTime()) needDay=item.getNeedDay();
                            }
                        }
                    }
                }

                //保存订单明细
                OrdSoItem soItem = new OrdSoItem();
                soItem.setStatus(BillStatus.BASE_AUDIT_PASS);
                soItem.setSoId(so.getId());
                soItem.setSeqNo("1");
                soItem.setProductId(product.getId());
                soItem.setProductCode(product.getMaterialCode());
                soItem.setProductName(product.getMaterialName());
                soItem.setProductSpec(product.getMaterialSpec());
                soItem.setDrawNo(product.getDrawNo());
                soItem.setCustomerProductNo(product.getCustomerProductNo());
                soItem.setCProductName(product.getMaterialName());
                soItem.setCProductSpec(product.getMaterialSpec());
                soItem.setCDrawNo(product.getDrawNo());
                soItem.setCCustomerProductNo(product.getCustomerProductNo());
                soItem.setUnitId(product.getUnitId());
                soItem.setQuantityOrder(new BigDecimal(quantity));
                soItem.setPrice(new BigDecimal(amountUntaxed/quantity).setScale(2, BigDecimal.ROUND_HALF_UP));
                soItem.setAmount(new BigDecimal(amountUntaxed));
                soItem.setPriority(PriorityLevel.PRIORITY_NORMAL);
                soItem.setNeedDay(needDay);
                soItem.refreshCreatingStamp();
                soItemMapper.insertOrdSoItem(soItem);
            }
            //若为普通合同，则直转明细
            else{
                if (list != null) {
                    for (OrdContractItem item : list) {
                        OrdSoItem soItem = new OrdSoItem();
                        soItem.setStatus(BillStatus.BASE_AUDIT_PASS);
                        soItem.setSoId(so.getId());
                        soItem.setSeqNo(item.getSeqNo());
                        soItem.setContractItemId(item.getId());
                        soItem.setProductId(item.getProductId());
                        soItem.setProductCode(item.getProductCode());
                        soItem.setProductName(item.getProductName());
                        soItem.setProductSpec(item.getProductSpec());
                        soItem.setDrawNo(item.getDrawNo());
                        soItem.setCustomerProductNo(item.getCustomerProductNo());
                        soItem.setCProductName(item.getProductName());
                        soItem.setCProductSpec(item.getProductSpec());
                        soItem.setCDrawNo(item.getDrawNo());
                        soItem.setCCustomerProductNo(item.getCustomerProductNo());
                        soItem.setQuantityOrder(item.getQuantityOrder());
                        soItem.setUnitId(item.getUnitId());
                        soItem.setPrice(item.getPrice());
                        soItem.setDiscountPrice(item.getDiscountPrice());
                        soItem.setAmount(item.getAmount());
                        soItem.setDiscountRate(item.getDiscountRate());
                        soItem.setFinalAmount(item.getFinalAmount());
                        soItem.setPriority(item.getPriority());
                        soItem.setNeedDay(item.getNeedDay());
                        soItem.refreshCreatingStamp();
                        soItemMapper.insertOrdSoItem(soItem);
                    }
                }
            }

            contract.setStatus(BillStatus.FINISH);
            contract.refreshUpdatingStamp();
            contractMapper.updateOrdContract(contract);
        }

        return 1;
    }

    /**
     * 请求临时料号
     *
     * @param ordContract 合同
     * @return 结果
     */
    public int requestTempProduct(OrdContract ordContract) throws Exception{
        //取临时产品编号
        String billNo = billcodeManager.getNextBillno("T01-");
        billcodeManager.updateBillSeq("T01-");

        EngProductBasis product = new EngProductBasis();
        product.setCategoryId(1L);
        product.setMaterialCode(billNo);
        product.setMaterialName(billNo);
        product.setUnitId(6L);
        product.setCategoryId(121L);
        product.setBaseType(String.valueOf(MaterialBaseType.TEMPORARY));
        product.setStatus(BillStatus.BASE_ENABLE);
        product.refreshCreatingStamp();
        productManager.insertEngProductBasis(product);

        OrdContract contract = contractMapper.selectOrdContractById(ordContract.getId());
        contract.setContractType(ContractType.ASSEMBLY);
        contract.setProductId(product.getId());
        contract.setProductCode(product.getMaterialCode());
        contract.setProductQuantity(1);
        contract.refreshUpdatingStamp();
        contractMapper.updateOrdContract(contract);

        return 1;
    }
}
