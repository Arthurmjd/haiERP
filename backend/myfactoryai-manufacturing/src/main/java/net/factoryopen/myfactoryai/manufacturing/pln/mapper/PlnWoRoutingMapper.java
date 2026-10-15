package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRouting;

/**
 * 工单工艺管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
public interface PlnWoRoutingMapper 
{
    /**
     * 查询工单工艺管理
     * 
     * @param id 工单工艺管理主键
     * @return 工单工艺管理
     */
    public PlnWoRouting selectPlnWoRoutingById(Long id);

    /**
     * 查询工单工艺管理列表
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 工单工艺管理集合
     */
    public List<PlnWoRouting> selectPlnWoRoutingList(PlnWoRouting plnWoRouting);

    /**
     * 查询工单工艺管理列表
     *
     * @param woId 工单
     * @return 工单工艺管理集合
     */
    public List<PlnWoRouting> selectPlnWoRoutingListByWoId(Long woId);

    /**
     * 查询工单工艺路线第一条
     *
     * @param woId 工单
     * @return 工单工艺管理集合
     */
    public PlnWoRouting selectPlnWoRoutingFirstByWoId(Long woId);

    /**
     * 查询工单工艺管理列表
     *
     * @param lineId 产线ID
     * @return 工单工艺管理集合
     */
    public List<PlnWoRouting> selectPlnWoRoutingListByLineId(Long lineId);

    /**
     * 查询工单工艺管理列表
     *
     * @param processId 工序
     * @return 工单工艺管理集合
     */
    public List<PlnWoRouting> selectPlnWoRoutingListByProcessId(Long processId);

    /**
     * 新增工单工艺管理
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 结果
     */
    public int insertPlnWoRouting(PlnWoRouting plnWoRouting);

    /**
     * 修改工单工艺管理
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 结果
     */
    public int updatePlnWoRouting(PlnWoRouting plnWoRouting);

    /**
     * 删除工单工艺管理
     * 
     * @param id 工单工艺管理主键
     * @return 结果
     */
    public int deletePlnWoRoutingById(Long id);

    /**
     * 批量删除工单工艺管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingByIds(Long[] ids);

    /**
     * 查询工单工艺管理列表
     *
     * @param woId 工单
     * @return 工单工艺管理集合
     */
    public int deletePlnWoRoutingByWoId(Long woId);

    /**
     * 查询工单工艺管理列表
     *
     * @param lineId 产线ID
     * @return 工单工艺管理集合
     */
    public int deletePlnWoRoutingByLineId(Long lineId);

    /**
     * 查询工单工艺管理列表
     *
     * @param processId 工序
     * @return 工单工艺管理集合
     */
    public int deletePlnWoRoutingByProcessId(Long processId);
}
