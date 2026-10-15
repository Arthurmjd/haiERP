package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerConsuming;

/**
 * 电耗登记Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPowerConsumingService 
{
    /**
     * 查询电耗登记
     * 
     * @param id 电耗登记主键
     * @return 电耗登记
     */
    public EhsPowerConsuming selectEhsPowerConsumingById(Long id);

    /**
     * 查询电耗登记列表
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 电耗登记集合
     */
    public List<EhsPowerConsuming> selectEhsPowerConsumingList(EhsPowerConsuming ehsPowerConsuming);

    /**
     * 新增电耗登记
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 结果
     */
    public int insertEhsPowerConsuming(EhsPowerConsuming ehsPowerConsuming);

    /**
     * 修改电耗登记
     * 
     * @param ehsPowerConsuming 电耗登记
     * @return 结果
     */
    public int updateEhsPowerConsuming(EhsPowerConsuming ehsPowerConsuming);

    /**
     * 批量删除电耗登记
     * 
     * @param ids 需要删除的电耗登记主键集合
     * @return 结果
     */
    public int deleteEhsPowerConsumingByIds(Long[] ids);

    /**
     * 删除电耗登记信息
     * 
     * @param id 电耗登记主键
     * @return 结果
     */
    public int deleteEhsPowerConsumingById(Long id);


}
