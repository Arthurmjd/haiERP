package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoComplain;

/**
 * 订单客诉登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface OrdSoComplainMapper 
{
    /**
     * 查询订单客诉登记
     * 
     * @param id 订单客诉登记主键
     * @return 订单客诉登记
     */
    public OrdSoComplain selectOrdSoComplainById(Long id);

    /**
     * 查询订单客诉登记列表
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 订单客诉登记集合
     */
    public List<OrdSoComplain> selectOrdSoComplainList(OrdSoComplain ordSoComplain);

    /**
     * 查询订单客诉登记列表
     *
     * @param soId 销售订单
     * @return 订单客诉登记集合
     */
    public List<OrdSoComplain> selectOrdSoComplainListBySoId(Long soId);

    /**
     * 查询订单客诉登记列表
     *
     * @param productId 产品编码
     * @return 订单客诉登记集合
     */
    public List<OrdSoComplain> selectOrdSoComplainListByProductId(Long productId);

    /**
     * 新增订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    public int insertOrdSoComplain(OrdSoComplain ordSoComplain);

    /**
     * 修改订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    public int updateOrdSoComplain(OrdSoComplain ordSoComplain);

    /**
     * 删除订单客诉登记
     * 
     * @param id 订单客诉登记主键
     * @return 结果
     */
    public int deleteOrdSoComplainById(Long id);

    /**
     * 批量删除订单客诉登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoComplainByIds(Long[] ids);

    /**
     * 查询订单客诉登记列表
     *
     * @param soId 销售订单
     * @return 订单客诉登记集合
     */
    public int deleteOrdSoComplainBySoId(Long soId);

    /**
     * 查询订单客诉登记列表
     *
     * @param productId 产品编码
     * @return 订单客诉登记集合
     */
    public int deleteOrdSoComplainByProductId(Long productId);
}
