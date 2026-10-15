package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoFinance;
import org.springframework.stereotype.Service;

/**
 * 订单明细Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public interface OrdSoFinanceMapper
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
     * 查询订单明细列表
     *
     * @param soId 销售订单
     * @return 订单明细集合
     */
    public List<OrdSoFinance> selectOrdSoFinanceListBySoId(Long soId);

    /**
     * 查询订单明细列表
     *
     * @param productId 产品编码
     * @return 订单明细集合
     */
    public List<OrdSoFinance> selectOrdSoFinanceListByProductId(Long productId);

    /**
     * 修改订单明细
     *
     * @param soFinance 订单明细
     * @return 结果
     */
    public int updateOrdSoFinance(OrdSoFinance soFinance);
}
