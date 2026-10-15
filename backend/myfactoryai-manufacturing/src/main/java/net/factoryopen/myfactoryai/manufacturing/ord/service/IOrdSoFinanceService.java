package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoFinance;

/**
 * 订单明细Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IOrdSoFinanceService 
{
    /**
     * 查询订单明细
     * 
     * @param id 订单明细主键
     * @return 订单明细
     */
    public OrdSoFinance selectOrdSoFinanceById(Long id);

    /**
     * 查询订单明细列表
     * 
     * @param soFinance 订单明细
     * @return 订单明细集合
     */
    public List<OrdSoFinance> selectOrdSoFinanceList(OrdSoFinance soFinance);

    /**
     * 查询订单明细列表
     *
     * @param soFinance 订单明细
     * @return 订单明细集合
     */
    public List<OrdSoFinance> selectOrdSoFinanceListAll(OrdSoFinance soFinance);

    /**
     * 修改订单明细
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    public int updateOrdSoFinance(OrdSoFinance soFinance);

    /**
     * 开票登记
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    public int registerInvoice(OrdSoFinance soFinance) throws Exception;
}
