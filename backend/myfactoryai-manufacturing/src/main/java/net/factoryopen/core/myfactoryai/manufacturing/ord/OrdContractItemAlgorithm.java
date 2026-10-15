package net.factoryopen.core.myfactoryai.manufacturing.ord;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 合同ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@Service
public class OrdContractItemAlgorithm
{
    @Autowired(required = false)
    private OrdContractItemMapper contractItemMapper;

    @Autowired
    private EngProductBasisMapper productManager;

    @Autowired
    private BillcodeManager billcodeManager;

    /**
     * 请求临时料号
     *
     * @param ids 合同明细ID集合
     * @return 结果
     */
    public int requestTempProduct(Long[] ids) throws Exception{
        for (Long contactItemId:ids){
            //取合同明细
            OrdContractItem item = contractItemMapper.selectOrdContractItemById(contactItemId);
            //产品编码存在则跳过
            if (item.getProductId()!=null) continue;

            //取临时产品编号
            String billNo = billcodeManager.getNextBillno("T02-");
            billcodeManager.updateBillSeq("T02-");
            //创建临时料号
            EngProductBasis product = new EngProductBasis();
            product.setCategoryId(1L);
            product.setMaterialCode(billNo);
            product.setMaterialName(item.getProductName());
            product.setMaterialSpec(item.getProductSpec());
            product.setDrawNo(item.getDrawNo());
            product.setCustomerProductNo(item.getCustomerProductNo());
            product.setAbroadProductNo(item.getAbroadProductNo());
            product.setUnitId(6L);
            product.setCategoryId(120L);
            product.setBaseType(String.valueOf(MaterialBaseType.TEMPORARY));
            product.setStatus(BillStatus.BASE_ENABLE);
            product.refreshCreatingStamp();
            productManager.insertEngProductBasis(product);

            //套用临时料号
            item.setProductId(product.getId());
            item.setProductCode(product.getMaterialCode());
            item.refreshUpdatingStamp();
            contractItemMapper.updateOrdContractItem(item);
        }

        return 1;
    }
}
