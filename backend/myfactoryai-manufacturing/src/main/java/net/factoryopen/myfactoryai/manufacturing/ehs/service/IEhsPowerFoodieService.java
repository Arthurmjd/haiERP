package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodie;

/**
 * 电耗设备Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPowerFoodieService 
{
    /**
     * 查询电耗设备
     * 
     * @param id 电耗设备主键
     * @return 电耗设备
     */
    public EhsPowerFoodie selectEhsPowerFoodieById(Long id);

    /**
     * 查询电耗设备列表
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 电耗设备集合
     */
    public List<EhsPowerFoodie> selectEhsPowerFoodieList(EhsPowerFoodie ehsPowerFoodie);

    /**
     * 新增电耗设备
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 结果
     */
    public int insertEhsPowerFoodie(EhsPowerFoodie ehsPowerFoodie);

    /**
     * 修改电耗设备
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 结果
     */
    public int updateEhsPowerFoodie(EhsPowerFoodie ehsPowerFoodie);

    /**
     * 批量删除电耗设备
     * 
     * @param ids 需要删除的电耗设备主键集合
     * @return 结果
     */
    public int deleteEhsPowerFoodieByIds(Long[] ids);

    /**
     * 删除电耗设备信息
     * 
     * @param id 电耗设备主键
     * @return 结果
     */
    public int deleteEhsPowerFoodieById(Long id);


}
