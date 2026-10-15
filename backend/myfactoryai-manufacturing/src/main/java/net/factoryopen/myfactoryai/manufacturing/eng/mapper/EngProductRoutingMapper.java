package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;

/**
 * 制造工艺管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngProductRoutingMapper 
{
    /**
     * 查询制造工艺管理
     * 
     * @param id 制造工艺管理主键
     * @return 制造工艺管理
     */
    public EngProductRouting selectEngProductRoutingById(Long id);

    /**
     * 查询制造工艺管理列表
     * 
     * @param engProductRouting 制造工艺管理
     * @return 制造工艺管理集合
     */
    public List<EngProductRouting> selectEngProductRoutingList(EngProductRouting engProductRouting);

    /**
     * 查询制造工艺管理列表
     *
     * @param productId 产品编码
     * @return 制造工艺管理集合
     */
    public List<EngProductRouting> selectEngProductRoutingListByProductId(Long productId);

    /**
     * 查询制造工艺管理列表
     *
     * @param lineId 产线ID
     * @return 制造工艺管理集合
     */
    public List<EngProductRouting> selectEngProductRoutingListByLineId(Long lineId);

    /**
     * 查询制造工艺管理列表
     *
     * @param processId 工序
     * @return 制造工艺管理集合
     */
    public List<EngProductRouting> selectEngProductRoutingListByProcessId(Long processId);

    /**
     * 新增制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    public int insertEngProductRouting(EngProductRouting engProductRouting);

    /**
     * 修改制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    public int updateEngProductRouting(EngProductRouting engProductRouting);

    /**
     * 删除制造工艺管理
     * 
     * @param id 制造工艺管理主键
     * @return 结果
     */
    public int deleteEngProductRoutingById(Long id);

    /**
     * 批量删除制造工艺管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingByIds(Long[] ids);

    /**
     * 查询制造工艺管理列表
     *
     * @param productId 产品编码
     * @return 制造工艺管理集合
     */
    public int deleteEngProductRoutingByProductId(Long productId);

    /**
     * 查询制造工艺管理列表
     *
     * @param lineId 产线ID
     * @return 制造工艺管理集合
     */
    public int deleteEngProductRoutingByLineId(Long lineId);

    /**
     * 查询制造工艺管理列表
     *
     * @param processId 工序
     * @return 制造工艺管理集合
     */
    public int deleteEngProductRoutingByProcessId(Long processId);
}
