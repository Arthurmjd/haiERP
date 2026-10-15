package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import org.springframework.stereotype.Service;

/**
 * 订单明细Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface OrdSoItemMapper 
{
    /**
     * 查询订单明细
     * 
     * @param id 订单明细主键
     * @return 订单明细
     */
    public OrdSoItem selectOrdSoItemById(Long id);

    /**
     * 查询订单明细列表
     * 
     * @param ordSoItem 订单明细
     * @return 订单明细集合
     */
    public List<OrdSoItem> selectOrdSoItemList(OrdSoItem ordSoItem);

    /**
     * 查询订单明细列表
     *
     * @param soId 销售订单
     * @return 订单明细集合
     */
    public List<OrdSoItem> selectOrdSoItemListBySoId(Long soId);

    /**
     * 查询订单明细列表
     *
     * @param productId 产品编码
     * @return 订单明细集合
     */
    public List<OrdSoItem> selectOrdSoItemListByProductId(Long productId);

    /**
     * 新增订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    public int insertOrdSoItem(OrdSoItem ordSoItem);

    /**
     * 修改订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    public int updateOrdSoItem(OrdSoItem ordSoItem);

    /**
     * 删除订单明细
     * 
     * @param id 订单明细主键
     * @return 结果
     */
    public int deleteOrdSoItemById(Long id);

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdSoItemByIds(Long[] ids);

    /**
     * 查询订单明细列表
     *
     * @param soId 销售订单
     * @return 订单明细集合
     */
    public int deleteOrdSoItemBySoId(Long soId);

    /**
     * 查询订单明细列表
     *
     * @param productId 产品编码
     * @return 订单明细集合
     */
    public int deleteOrdSoItemByProductId(Long productId);
}
