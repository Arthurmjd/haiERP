package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodie;

/**
 * 电耗设备Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsPowerFoodieMapper 
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
     * 查询电耗设备列表
     *
     * @param categoryId 设备分类
     * @return 电耗设备集合
     */
    public List<EhsPowerFoodie> selectEhsPowerFoodieListByCategoryId(Long categoryId);

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
     * 删除电耗设备
     * 
     * @param id 电耗设备主键
     * @return 结果
     */
    public int deleteEhsPowerFoodieById(Long id);

    /**
     * 批量删除电耗设备
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsPowerFoodieByIds(Long[] ids);

    /**
     * 查询电耗设备列表
     *
     * @param categoryId 设备分类
     * @return 电耗设备集合
     */
    public int deleteEhsPowerFoodieByCategoryId(Long categoryId);
}
