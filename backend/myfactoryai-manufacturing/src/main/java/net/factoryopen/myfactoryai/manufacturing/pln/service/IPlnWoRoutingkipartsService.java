package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingkiparts;

/**
 * 工单关重料Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IPlnWoRoutingkipartsService 
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
     * 批量删除工单关重料
     * 
     * @param ids 需要删除的工单关重料主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingkipartsByIds(Long[] ids);

    /**
     * 删除工单关重料信息
     * 
     * @param id 工单关重料主键
     * @return 结果
     */
    public int deletePlnWoRoutingkipartsById(Long id);


}
