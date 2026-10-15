package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterFoodieCategory;

/**
 * 水耗设备分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsWaterFoodieCategoryService 
{
    /**
     * 查询水耗设备分类
     * 
     * @param id 水耗设备分类主键
     * @return 水耗设备分类
     */
    public EhsWaterFoodieCategory selectEhsWaterFoodieCategoryById(Long id);

    /**
     * 查询水耗设备分类列表
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 水耗设备分类集合
     */
    public List<EhsWaterFoodieCategory> selectEhsWaterFoodieCategoryList(EhsWaterFoodieCategory ehsWaterFoodieCategory);

    /**
     * 新增水耗设备分类
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 结果
     */
    public int insertEhsWaterFoodieCategory(EhsWaterFoodieCategory ehsWaterFoodieCategory);

    /**
     * 修改水耗设备分类
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 结果
     */
    public int updateEhsWaterFoodieCategory(EhsWaterFoodieCategory ehsWaterFoodieCategory);

    /**
     * 批量删除水耗设备分类
     * 
     * @param ids 需要删除的水耗设备分类主键集合
     * @return 结果
     */
    public int deleteEhsWaterFoodieCategoryByIds(Long[] ids);

    /**
     * 删除水耗设备分类信息
     * 
     * @param id 水耗设备分类主键
     * @return 结果
     */
    public int deleteEhsWaterFoodieCategoryById(Long id);


}
