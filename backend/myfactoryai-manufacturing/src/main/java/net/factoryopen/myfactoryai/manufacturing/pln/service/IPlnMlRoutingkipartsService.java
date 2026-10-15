package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingkiparts;

/**
 * 批次关重料Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlRoutingkipartsService 
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
     * 批量删除批次关重料
     * 
     * @param ids 需要删除的批次关重料主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingkipartsByIds(Long[] ids);

    /**
     * 删除批次关重料信息
     * 
     * @param id 批次关重料主键
     * @return 结果
     */
    public int deletePlnMlRoutingkipartsById(Long id);


}
