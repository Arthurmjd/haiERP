package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;

import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdSoFinanceAlgorithm;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoFinanceMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoFinance;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoFinanceService;

/**
 * 订单明细Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class OrdSoFinanceServiceImpl implements IOrdSoFinanceService 
{
    @Autowired(required = false)
    private OrdSoFinanceMapper soFinanceMapper;

    @Autowired(required = false)
    private OrdSoFinanceAlgorithm sofinanceAlgorithm;

    /**
     * 查询订单明细
     * 
     * @param id 订单明细主键
     * @return 订单明细
     */
    @Override
    public OrdSoFinance selectOrdSoFinanceById(Long id)
    {
        OrdSoFinance deal = soFinanceMapper.selectOrdSoFinanceById(id);
        return deal;
    }

    /**
     * 查询订单明细列表
     * 
     * @param soFinance 订单明细
     * @return 订单明细
     */
    @Override
    public List<OrdSoFinance> selectOrdSoFinanceList(OrdSoFinance soFinance)
    {
        List<OrdSoFinance> list = soFinanceMapper.selectOrdSoFinanceList(soFinance);
        return list;
    }

    /**
     * 查询订单明细列表
     *
     * @param soFinance 订单明细
     * @return 订单明细
     */
    @Override
    public List<OrdSoFinance> selectOrdSoFinanceListAll(OrdSoFinance soFinance)
    {
        List<OrdSoFinance> list = soFinanceMapper.selectOrdSoFinanceListAll(soFinance);
        return list;
    }

    /**
     * 修改订单明细
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    @Override
    public int updateOrdSoFinance(OrdSoFinance soFinance)
    {
        soFinance.refreshUpdatingStamp();
        return soFinanceMapper.updateOrdSoFinance(soFinance);
    }

    /**
     * 开票登记
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    @Override
    public int registerInvoice(OrdSoFinance soFinance) throws Exception{
        try{
            return sofinanceAlgorithm.registerInvoice(soFinance);
        }catch (Exception ex){
            throw ex;
        }
    }
}
