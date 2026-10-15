package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductEngineering;

/**
 * 产品工艺管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngProductEngineeringMapper 
{
    /**
     * 查询产品工艺管理
     * 
     * @param id 产品工艺管理主键
     * @return 产品工艺管理
     */
    public EngProductEngineering selectEngProductEngineeringById(Long id);

    /**
     * 查询产品工艺管理列表
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 产品工艺管理集合
     */
    public List<EngProductEngineering> selectEngProductEngineeringList(EngProductEngineering engProductEngineering);

    /**
     * 查询产品工艺管理列表
     *
     * @param unitId 单位
     * @return 产品工艺管理集合
     */
    public List<EngProductEngineering> selectEngProductEngineeringListByUnitId(Long unitId);

    /**
     * 查询产品工艺管理列表
     *
     * @param categoryId 品类
     * @return 产品工艺管理集合
     */
    public List<EngProductEngineering> selectEngProductEngineeringListByCategoryId(Long categoryId);

    /**
     * 查询产品工艺管理列表
     *
     * @param lineId 产线ID
     * @return 产品工艺管理集合
     */
    public List<EngProductEngineering> selectEngProductEngineeringListByLineId(Long lineId);

    /**
     * 新增产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    public int insertEngProductEngineering(EngProductEngineering engProductEngineering);

    /**
     * 修改产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    public int updateEngProductEngineering(EngProductEngineering engProductEngineering);

    /**
     * 删除产品工艺管理
     * 
     * @param id 产品工艺管理主键
     * @return 结果
     */
    public int deleteEngProductEngineeringById(Long id);

    /**
     * 批量删除产品工艺管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductEngineeringByIds(Long[] ids);

    /**
     * 查询产品工艺管理列表
     *
     * @param unitId 单位
     * @return 产品工艺管理集合
     */
    public int deleteEngProductEngineeringByUnitId(Long unitId);

    /**
     * 查询产品工艺管理列表
     *
     * @param categoryId 品类
     * @return 产品工艺管理集合
     */
    public int deleteEngProductEngineeringByCategoryId(Long categoryId);

    /**
     * 查询产品工艺管理列表
     *
     * @param lineId 产线ID
     * @return 产品工艺管理集合
     */
    public int deleteEngProductEngineeringByLineId(Long lineId);
}
