package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;

/**
 * 订单登记发布Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdSoService 
{
    /**
     * 查询订单登记发布
     * 
     * @param id 订单登记发布主键
     * @return 订单登记发布
     */
    public OrdSo selectOrdSoById(Long id);

    /**
     * 查询订单登记发布列表
     * 
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoDraftList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoPublishingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoProcessingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoFinishingList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoHistoryList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoAllList(OrdSo ordSo);

    /**
     * 查询订单登记发布列表
     *
     * @param ordSo 订单登记发布
     * @return 订单登记发布集合
     */
    public List<OrdSo> selectOrdSoList4Adult(OrdSo ordSo);

    /**
     * 新增订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    public int insertOrdSo(OrdSo ordSo);

    /**
     * 修改订单登记发布
     * 
     * @param ordSo 订单登记发布
     * @return 结果
     */
    public int updateOrdSo(OrdSo ordSo);

    /**
     * 批量删除订单登记发布
     * 
     * @param ids 需要删除的订单登记发布主键集合
     * @return 结果
     */
    public int deleteOrdSoByIds(Long[] ids);

    /**
     * 删除订单登记发布信息
     * 
     * @param id 订单登记发布主键
     * @return 结果
     */
    public int deleteOrdSoById(Long id);

    /**
     * 发布
     *
     * @param ordSo 发布
     * @return 结果
     */
    public int enable(OrdSo ordSo);

    /**
     * 变更
     *
     * @param ordSo 变更
     * @return 结果
     */
    public int changeOrdSo(OrdSo ordSo);

    /**
     * 冻结
     *
     * @param ordSo 冻结
     * @return 结果
     */
    public int froze(OrdSo ordSo);

    /**
     * 解冻
     *
     * @param ordSo 解冻
     * @return 结果
     */
    public int unfroze(OrdSo ordSo);

    /**
     * 停用
     *
     * @param ordSo 停用
     * @return 结果
     */
    public int disable(OrdSo ordSo);

    /**
     * 强结
     *
     * @param ordSo 强结
     * @return 结果
     */
    public int finishforcely(OrdSo ordSo);


}
