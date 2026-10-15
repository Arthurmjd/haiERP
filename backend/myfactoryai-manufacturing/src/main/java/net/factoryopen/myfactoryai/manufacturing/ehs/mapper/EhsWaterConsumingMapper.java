package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterConsuming;

/**
 * 水耗登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsWaterConsumingMapper 
{
    /**
     * 查询水耗登记
     * 
     * @param id 水耗登记主键
     * @return 水耗登记
     */
    public EhsWaterConsuming selectEhsWaterConsumingById(Long id);

    /**
     * 查询水耗登记列表
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 水耗登记集合
     */
    public List<EhsWaterConsuming> selectEhsWaterConsumingList(EhsWaterConsuming ehsWaterConsuming);

    /**
     * 查询水耗登记列表
     *
     * @param foodieId 设备ID
     * @return 水耗登记集合
     */
    public List<EhsWaterConsuming> selectEhsWaterConsumingListByFoodieId(Long foodieId);

    /**
     * 查询水耗登记列表
     *
     * @param unitId 单位
     * @return 水耗登记集合
     */
    public List<EhsWaterConsuming> selectEhsWaterConsumingListByUnitId(Long unitId);

    /**
     * 新增水耗登记
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 结果
     */
    public int insertEhsWaterConsuming(EhsWaterConsuming ehsWaterConsuming);

    /**
     * 修改水耗登记
     * 
     * @param ehsWaterConsuming 水耗登记
     * @return 结果
     */
    public int updateEhsWaterConsuming(EhsWaterConsuming ehsWaterConsuming);

    /**
     * 删除水耗登记
     * 
     * @param id 水耗登记主键
     * @return 结果
     */
    public int deleteEhsWaterConsumingById(Long id);

    /**
     * 批量删除水耗登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsWaterConsumingByIds(Long[] ids);

    /**
     * 查询水耗登记列表
     *
     * @param foodieId 设备ID
     * @return 水耗登记集合
     */
    public int deleteEhsWaterConsumingByFoodieId(Long foodieId);

    /**
     * 查询水耗登记列表
     *
     * @param unitId 单位
     * @return 水耗登记集合
     */
    public int deleteEhsWaterConsumingByUnitId(Long unitId);
}
