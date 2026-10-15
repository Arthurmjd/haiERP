package net.factoryopen.core.myfactoryai.manufacturing.del;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.exception.DataNotExistException;
import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.TypeMismatchException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.BillType;
import net.factoryopen.core.common.type.ErType;
import net.factoryopen.core.common.type.InventoryTransactionType;
import net.factoryopen.core.common.type.MaterialBaseType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngErMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.*;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 自制主计划ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-06-28
 */
@Service
public class DelMpsAlgorithm
{
    @Autowired(required = false)
    private DelMpsMapper mpsMapper;

    @Autowired(required = false)
    private EngProductBasisMapper productMapper;

    @Autowired(required = false)
    private EngProductBomMapper pbomMapper;

    @Autowired(required = false)
    private EngProductRoutingMapper proutingMapper;

    @Autowired(required = false)
    private PlnWoMapper woMapper;

    @Autowired(required = false)
    private PlnMrpMpMapper mpMapper;

    @Autowired(required = false)
    private PlnMrpMrMapper mrMapper;

    @Autowired(required = false)
    private EngErMapper erMapper;

    @Autowired(required = false)
    private OrdSoItemMapper soItemMapper;

    @Autowired(required = false)
    private OrdContractItemMapper contractItemMapper;

    @Autowired(required = false)
    private OrdContractMapper contractMapper;

    @Autowired
    private BillcodeManager billcodeManager;


    public void detectrefreshMpsProcessed(Long mpsId){
        boolean mpsProcessed = true;

        //检查MrpWo转完否
        List<PlnWo> woList = woMapper.selectPlnWoListByMpsId(mpsId);
        if (woList!=null){
            for(PlnWo item : woList){
                mpsProcessed = mpsProcessed && item.getStatus()==BillStatus.ACTIVE_PROCESSING;
                if (!mpsProcessed) break;
            }
        }

        //检查MrpMr转完否
        if (mpsProcessed){
            List<PlnMrpMr> mrList =  mrMapper.selectPlnMrpMrListByMpsId(mpsId);
            if (mrList!=null){
                for(PlnMrpMr item:mrList){
                    mpsProcessed = mpsProcessed && item.getStatus()==BillStatus.FINISH;
                    if (!mpsProcessed) break;
                }
            }
        }

        //检查MrpMp转完否
        if(mpsProcessed){
            List<PlnMrpMp> mpList = mpMapper.selectPlnMrpMpListByMpsId(mpsId);
            if (mpList!=null){
                for(PlnMrpMp item:mpList){
                    mpsProcessed = mpsProcessed && item.getStatus()==BillStatus.FINISH;
                    if (!mpsProcessed) break;
                }
            }
        }

        //刷新状态、时间戳
        DelMps mps = mpsMapper.selectDelMpsById(mpsId);
        mps.refreshUpdatingStamp();
        if (mpsProcessed){
            mps.setStatus(BillStatus.ACTIVE_PROCESSING);
        }
        mpsMapper.updateDelMps(mps);
    }

    /**
     * 通知工艺
     *
     * @param delMps 主计划项
     * @return 结果
     */
    public int noticeEngineering(DelMps delMps) throws Exception{
        delMps = mpsMapper.selectDelMpsById(delMps.getId());
        EngProductBasis product = productMapper.selectEngProductBasisById(delMps.getProductId());

        //取存单号
        String billNo = billcodeManager.getNextBillno("ER");
        billcodeManager.updateBillSeq("ER");

        EngEr er = new EngEr();
        er.setBillNo(billNo);
        er.setStatus(BillStatus.BASE_ENABLE);
        er.setErType(ErType.CREATING);
        er.setMpsId(delMps.getId());
        er.setMpsNo(delMps.getBillNo());
        er.setProductId(delMps.getProductId());
        er.setProductCode(delMps.getProductCode());
        er.setProductName(delMps.getProductName());
        er.setProductSpec(delMps.getProductSpec());
        er.setDrawNo(delMps.getDrawNo());
        er.setCustomerProductNo(delMps.getCustomerProductNo());
        er.setAbroadProductNo(product.getAbroadProductNo());
        er.setUnitId(delMps.getUnitId());
        er.refreshCreatingStamp();
        erMapper.insertEngEr(er);

        delMps.setStatus(BillStatus.ACTIVE_STARTED);//待工艺
        delMps.refreshUpdatingStamp();
        mpsMapper.updateDelMps(delMps);

        return 1;
    }

    /**
     * 转正料号
     *
     * @param mps 主计划项
     * @return 结果
     */
    public int normalizeCode(DelMps mps) throws Exception{
        //校验料号重复否
        EngProductBasis productTarget = productMapper.selectEngProductBasisById(mps.getProductId());
        if (productTarget==null || productTarget.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
            String msg = "正式料号[" + mps.getProductCode() + "]不存在！";
            throw new MaterialUnavailableException(msg);
        }
        //校验状态
        if (mps.getStatus()>= BillStatus.BASE_ENABLE){
            String msg = "自制主计划[" + mps.getBillNo() + "]当前状态生成料号是非法的！";
            throw new MaterialUnavailableException(msg);
        }
        //校验临时料号
        DelMps mpsDatabase = mpsMapper.selectDelMpsById(mps.getId());
        EngProductBasis productSource = productMapper.selectEngProductBasisById(mpsDatabase.getProductId());
        if (!productSource.getBaseType().equals(String.valueOf(MaterialBaseType.TEMPORARY))){
            String msg = "被转料号[" + mps.getProductCode() + "]非临时料号！";
            throw new MaterialUnavailableException(msg);
        }

//        //检验工艺路线
//        EngProductRouting prouting=new EngProductRouting();
//        prouting.setProductId(mps.getProductId());
//        List<EngProductRouting> proutingList = proutingMapper.selectEngProductRoutingList(prouting);
//        if (proutingList==null || proutingList.isEmpty()){
//            String msg = "自制主计划[" + mps.getBillNo() + "]所生产的产品，无工艺路线！";
//            throw new DataNotExistException(msg);
//        }
//        //检验BOM
//        EngProductBom pbom=new EngProductBom();
//        pbom.setProductId(mps.getProductId());
//        List<EngProductBom> pbomList = pbomMapper.selectEngProductBomList(pbom);
//        if (pbomList==null || pbomList.isEmpty()){
//            String msg = "自制主计划[" + mps.getBillNo() + "]所生产的产品，无物料清单（BOM）！";
//            throw new DataNotExistException(msg);
//        }

        //替代主计划的临时料
        DelMps mpsTemp=new DelMps();
        mpsTemp.setProductId(productSource.getId());
        List<DelMps> mpsList = mpsMapper.selectDelMpsList(mpsTemp);
        if (mpsList!=null){
            for (DelMps item : mpsList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                item.setProductName(productTarget.getMaterialName());
                item.setProductSpec(productTarget.getMaterialSpec());
                item.setDrawNo(productTarget.getDrawNo());
                item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.setAbroadProductNo(productTarget.getAbroadProductNo());
                item.setUnitId(productTarget.getUnitId());
                item.refreshUpdatingStamp();
                mpsMapper.updateDelMps(item);
            }
        }

        //替代订单明细的临时料
        OrdSoItem soItem=new OrdSoItem();
        soItem.setProductId(productSource.getId());
        List<OrdSoItem> soItemList = soItemMapper.selectOrdSoItemList(soItem);
        if (soItemList!=null){
            for (OrdSoItem item : soItemList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                item.setProductName(productTarget.getMaterialName());
                item.setProductSpec(productTarget.getMaterialSpec());
                item.setDrawNo(productTarget.getDrawNo());
                item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.setUnitId(productTarget.getUnitId());
                item.refreshUpdatingStamp();
                soItemMapper.updateOrdSoItem(item);
            }
        }

        //替代合同明细的临时料
        OrdContractItem contractItem=new OrdContractItem();
        contractItem.setProductId(productSource.getId());
        List<OrdContractItem> contractItemList = contractItemMapper.selectOrdContractItemList(contractItem);
        if (contractItemList!=null){
            for (OrdContractItem item : contractItemList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                item.setUnitId(productTarget.getUnitId());
                //保留原合同数据
                //item.setDrawNo(productTarget.getDrawNo());
                //item.setProductName(productTarget.getMaterialName());
                //item.setProductSpec(productTarget.getMaterialSpec());
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                //item.setAbroadProductNo(productTarget.getAbroadProductNo());
                item.refreshUpdatingStamp();
                contractItemMapper.updateOrdContractItem(item);
            }
        }

        //替代合同头的临时料
        OrdContract contract = new OrdContract();
        contract.setProductId(productSource.getId());
        List<OrdContract> contractList = contractMapper.selectOrdContractList(contract);
        if (contractList!=null){
            for (OrdContract item : contractList){
                item.setProductId(productTarget.getId());
                item.setProductCode(productTarget.getMaterialCode());
                //保留原合同数据
                //item.setCustomerProductNo(productTarget.getCustomerProductNo());
                item.refreshUpdatingStamp();
                contractMapper.updateOrdContract(item);
            }
        }

        return 1;
    }
}
