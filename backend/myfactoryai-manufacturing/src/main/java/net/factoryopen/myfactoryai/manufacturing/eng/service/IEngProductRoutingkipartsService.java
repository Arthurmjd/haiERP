package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingkiparts;

/**
 * 产品关重料Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IEngProductRoutingkipartsService 
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
     * 批量删除产品关重料
     * 
     * @param ids 需要删除的产品关重料主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingkipartsByIds(Long[] ids);

    /**
     * 删除产品关重料信息
     * 
     * @param id 产品关重料主键
     * @return 结果
     */
    public int deleteEngProductRoutingkipartsById(Long id);


}
