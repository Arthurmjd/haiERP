package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoTrace;

/**
 * 在制订单跟踪Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdSoTraceService 
{
    /**
     * 查询在制订单跟踪
     * 
     * @param id 在制订单跟踪主键
     * @return 在制订单跟踪
     */
    public OrdSoTrace selectOrdSoTraceById(Long id);

    /**
     * 查询在制订单跟踪列表
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 在制订单跟踪集合
     */
    public List<OrdSoTrace> selectOrdSoTraceList(OrdSoTrace ordSoTrace);

    /**
     * 新增在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    public int insertOrdSoTrace(OrdSoTrace ordSoTrace);

    /**
     * 修改在制订单跟踪
     * 
     * @param ordSoTrace 在制订单跟踪
     * @return 结果
     */
    public int updateOrdSoTrace(OrdSoTrace ordSoTrace);

    /**
     * 批量删除在制订单跟踪
     * 
     * @param ids 需要删除的在制订单跟踪主键集合
     * @return 结果
     */
    public int deleteOrdSoTraceByIds(Long[] ids);

    /**
     * 删除在制订单跟踪信息
     * 
     * @param id 在制订单跟踪主键
     * @return 结果
     */
    public int deleteOrdSoTraceById(Long id);


}
