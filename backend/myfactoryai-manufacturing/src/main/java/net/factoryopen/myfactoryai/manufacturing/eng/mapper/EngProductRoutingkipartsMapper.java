package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingkiparts;

/**
 * 产品关重料Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngProductRoutingkipartsMapper 
{
    /**
     * 查询产品关重料
     * 
     * @param id 产品关重料主键
     * @return 产品关重料
     */
    public EngProductRoutingkiparts selectEngProductRoutingkipartsById(Long id);

    /**
     * 查询产品关重料列表
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 产品关重料集合
     */
    public List<EngProductRoutingkiparts> selectEngProductRoutingkipartsList(EngProductRoutingkiparts engProductRoutingkiparts);

    /**
     * 查询产品关重料列表
     *
     * @param routingId 工序
     * @return 产品关重料集合
     */
    public List<EngProductRoutingkiparts> selectEngProductRoutingkipartsListByRoutingId(Long routingId);

    /**
     * 查询产品关重料列表
     *
     * @param materialId 物料编码
     * @return 产品关重料集合
     */
    public List<EngProductRoutingkiparts> selectEngProductRoutingkipartsListByMaterialId(Long materialId);

    /**
     * 新增产品关重料
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 结果
     */
    public int insertEngProductRoutingkiparts(EngProductRoutingkiparts engProductRoutingkiparts);

    /**
     * 修改产品关重料
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 结果
     */
    public int updateEngProductRoutingkiparts(EngProductRoutingkiparts engProductRoutingkiparts);

    /**
     * 删除产品关重料
     * 
     * @param id 产品关重料主键
     * @return 结果
     */
    public int deleteEngProductRoutingkipartsById(Long id);

    /**
     * 批量删除产品关重料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingkipartsByIds(Long[] ids);

    /**
     * 查询产品关重料列表
     *
     * @param routingId 工序
     * @return 产品关重料集合
     */
    public int deleteEngProductRoutingkipartsByRoutingId(Long routingId);

    /**
     * 查询产品关重料列表
     *
     * @param materialId 物料编码
     * @return 产品关重料集合
     */
    public int deleteEngProductRoutingkipartsByMaterialId(Long materialId);
}
