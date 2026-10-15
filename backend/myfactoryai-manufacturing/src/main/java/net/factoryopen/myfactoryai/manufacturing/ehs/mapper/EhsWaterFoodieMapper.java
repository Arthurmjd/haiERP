package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterFoodie;

/**
 * 水耗设备Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsWaterFoodieMapper 
{
    /**
     * 查询水耗设备
     * 
     * @param id 水耗设备主键
     * @return 水耗设备
     */
    public EhsWaterFoodie selectEhsWaterFoodieById(Long id);

    /**
     * 查询水耗设备列表
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 水耗设备集合
     */
    public List<EhsWaterFoodie> selectEhsWaterFoodieList(EhsWaterFoodie ehsWaterFoodie);

    /**
     * 查询水耗设备列表
     *
     * @param categoryId 设备分类
     * @return 水耗设备集合
     */
    public List<EhsWaterFoodie> selectEhsWaterFoodieListByCategoryId(Long categoryId);

    /**
     * 新增水耗设备
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 结果
     */
    public int insertEhsWaterFoodie(EhsWaterFoodie ehsWaterFoodie);

    /**
     * 修改水耗设备
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 结果
     */
    public int updateEhsWaterFoodie(EhsWaterFoodie ehsWaterFoodie);

    /**
     * 删除水耗设备
     * 
     * @param id 水耗设备主键
     * @return 结果
     */
    public int deleteEhsWaterFoodieById(Long id);

    /**
     * 批量删除水耗设备
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsWaterFoodieByIds(Long[] ids);

    /**
     * 查询水耗设备列表
     *
     * @param categoryId 设备分类
     * @return 水耗设备集合
     */
    public int deleteEhsWaterFoodieByCategoryId(Long categoryId);
}
