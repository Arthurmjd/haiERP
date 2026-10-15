package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingkiparts;

/**
 * 批次关重料Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface PlnMlRoutingkipartsMapper 
{
    /**
     * 查询批次关重料
     * 
     * @param id 批次关重料主键
     * @return 批次关重料
     */
    public PlnMlRoutingkiparts selectPlnMlRoutingkipartsById(Long id);

    /**
     * 查询批次关重料列表
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 批次关重料集合
     */
    public List<PlnMlRoutingkiparts> selectPlnMlRoutingkipartsList(PlnMlRoutingkiparts plnMlRoutingkiparts);

    /**
     * 查询批次关重料列表
     *
     * @param routingId 工序
     * @return 批次关重料集合
     */
    public List<PlnMlRoutingkiparts> selectPlnMlRoutingkipartsListByRoutingId(Long routingId);

    /**
     * 新增批次关重料
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 结果
     */
    public int insertPlnMlRoutingkiparts(PlnMlRoutingkiparts plnMlRoutingkiparts);

    /**
     * 修改批次关重料
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 结果
     */
    public int updatePlnMlRoutingkiparts(PlnMlRoutingkiparts plnMlRoutingkiparts);

    /**
     * 删除批次关重料
     * 
     * @param id 批次关重料主键
     * @return 结果
     */
    public int deletePlnMlRoutingkipartsById(Long id);

    /**
     * 批量删除批次关重料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingkipartsByIds(Long[] ids);

    /**
     * 查询批次关重料列表
     *
     * @param routingId 工序
     * @return 批次关重料集合
     */
    public int deletePlnMlRoutingkipartsByRoutingId(Long routingId);
}
