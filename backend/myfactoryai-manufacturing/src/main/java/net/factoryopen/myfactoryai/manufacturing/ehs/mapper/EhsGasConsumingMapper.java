package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasConsuming;

/**
 * 气耗登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsGasConsumingMapper 
{
    /**
     * 查询气耗登记
     * 
     * @param id 气耗登记主键
     * @return 气耗登记
     */
    public EhsGasConsuming selectEhsGasConsumingById(Long id);

    /**
     * 查询气耗登记列表
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 气耗登记集合
     */
    public List<EhsGasConsuming> selectEhsGasConsumingList(EhsGasConsuming ehsGasConsuming);

    /**
     * 查询气耗登记列表
     *
     * @param foodieId 设备ID
     * @return 气耗登记集合
     */
    public List<EhsGasConsuming> selectEhsGasConsumingListByFoodieId(Long foodieId);

    /**
     * 查询气耗登记列表
     *
     * @param unitId 单位
     * @return 气耗登记集合
     */
    public List<EhsGasConsuming> selectEhsGasConsumingListByUnitId(Long unitId);

    /**
     * 新增气耗登记
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 结果
     */
    public int insertEhsGasConsuming(EhsGasConsuming ehsGasConsuming);

    /**
     * 修改气耗登记
     * 
     * @param ehsGasConsuming 气耗登记
     * @return 结果
     */
    public int updateEhsGasConsuming(EhsGasConsuming ehsGasConsuming);

    /**
     * 删除气耗登记
     * 
     * @param id 气耗登记主键
     * @return 结果
     */
    public int deleteEhsGasConsumingById(Long id);

    /**
     * 批量删除气耗登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsGasConsumingByIds(Long[] ids);

    /**
     * 查询气耗登记列表
     *
     * @param foodieId 设备ID
     * @return 气耗登记集合
     */
    public int deleteEhsGasConsumingByFoodieId(Long foodieId);

    /**
     * 查询气耗登记列表
     *
     * @param unitId 单位
     * @return 气耗登记集合
     */
    public int deleteEhsGasConsumingByUnitId(Long unitId);
}
