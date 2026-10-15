package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodieCategory;

/**
 * 电耗设备分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPowerFoodieCategoryService 
{
    /**
     * 查询电耗设备分类
     * 
     * @param id 电耗设备分类主键
     * @return 电耗设备分类
     */
    public EhsPowerFoodieCategory selectEhsPowerFoodieCategoryById(Long id);

    /**
     * 查询电耗设备分类列表
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 电耗设备分类集合
     */
    public List<EhsPowerFoodieCategory> selectEhsPowerFoodieCategoryList(EhsPowerFoodieCategory ehsPowerFoodieCategory);

    /**
     * 新增电耗设备分类
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 结果
     */
    public int insertEhsPowerFoodieCategory(EhsPowerFoodieCategory ehsPowerFoodieCategory);

    /**
     * 修改电耗设备分类
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 结果
     */
    public int updateEhsPowerFoodieCategory(EhsPowerFoodieCategory ehsPowerFoodieCategory);

    /**
     * 批量删除电耗设备分类
     * 
     * @param ids 需要删除的电耗设备分类主键集合
     * @return 结果
     */
    public int deleteEhsPowerFoodieCategoryByIds(Long[] ids);

    /**
     * 删除电耗设备分类信息
     * 
     * @param id 电耗设备分类主键
     * @return 结果
     */
    public int deleteEhsPowerFoodieCategoryById(Long id);


}
