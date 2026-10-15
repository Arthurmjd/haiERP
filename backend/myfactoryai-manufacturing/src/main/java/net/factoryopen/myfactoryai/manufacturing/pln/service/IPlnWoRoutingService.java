package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRouting;

/**
 * 工单工艺管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
public interface IPlnWoRoutingService 
{
    /**
     * 查询工单工艺管理
     * 
     * @param id 工单工艺管理主键
     * @return 工单工艺管理
     */
    public PlnWoRouting selectPlnWoRoutingById(Long id);
    /**
     * 查询工单工艺管理
     *
     * @param woId 工单Id
     * @return 工单工艺管理
     */
    public PlnWoRouting selectPlnWoRoutingFirstByWoId(Long woId);

    /**
     * 查询工单工艺管理列表
     * 
     * @param plnWoRouting 工单工艺管理
     * @return 工单工艺管理集合
     */
    public List<PlnWoRouting> selectPlnWoRoutingList(PlnWoRouting plnWoRouting);

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
     * 批量删除工单工艺管理
     * 
     * @param ids 需要删除的工单工艺管理主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingByIds(Long[] ids);

    /**
     * 删除工单工艺管理信息
     * 
     * @param id 工单工艺管理主键
     * @return 结果
     */
    public int deletePlnWoRoutingById(Long id);


}
