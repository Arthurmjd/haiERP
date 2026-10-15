package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterConsuming;

/**
 * 水耗登记Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsWaterConsumingService 
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
     * 批量删除水耗登记
     * 
     * @param ids 需要删除的水耗登记主键集合
     * @return 结果
     */
    public int deleteEhsWaterConsumingByIds(Long[] ids);

    /**
     * 删除水耗登记信息
     * 
     * @param id 水耗登记主键
     * @return 结果
     */
    public int deleteEhsWaterConsumingById(Long id);


}
