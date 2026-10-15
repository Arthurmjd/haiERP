package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingkiparts;

/**
 * 工单关重料Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface PlnWoRoutingkipartsMapper 
{
    /**
     * 查询工单关重料
     * 
     * @param id 工单关重料主键
     * @return 工单关重料
     */
    public PlnWoRoutingkiparts selectPlnWoRoutingkipartsById(Long id);

    /**
     * 查询工单关重料列表
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 工单关重料集合
     */
    public List<PlnWoRoutingkiparts> selectPlnWoRoutingkipartsList(PlnWoRoutingkiparts plnWoRoutingkiparts);

    /**
     * 查询工单关重料列表
     *
     * @param routingId 工序
     * @return 工单关重料集合
     */
    public List<PlnWoRoutingkiparts> selectPlnWoRoutingkipartsListByRoutingId(Long routingId);

    /**
     * 查询工单关重料列表
     *
     * @param materialId 物料编码
     * @return 工单关重料集合
     */
    public List<PlnWoRoutingkiparts> selectPlnWoRoutingkipartsListByMaterialId(Long materialId);

    /**
     * 新增工单关重料
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 结果
     */
    public int insertPlnWoRoutingkiparts(PlnWoRoutingkiparts plnWoRoutingkiparts);

    /**
     * 修改工单关重料
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 结果
     */
    public int updatePlnWoRoutingkiparts(PlnWoRoutingkiparts plnWoRoutingkiparts);

    /**
     * 删除工单关重料
     * 
     * @param id 工单关重料主键
     * @return 结果
     */
    public int deletePlnWoRoutingkipartsById(Long id);

    /**
     * 批量删除工单关重料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingkipartsByIds(Long[] ids);

    /**
     * 查询工单关重料列表
     *
     * @param routingId 工序
     * @return 工单关重料集合
     */
    public int deletePlnWoRoutingkipartsByRoutingId(Long routingId);

    /**
     * 查询工单关重料列表
     *
     * @param materialId 物料编码
     * @return 工单关重料集合
     */
    public int deletePlnWoRoutingkipartsByMaterialId(Long materialId);
}
