package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;

/**
 * 品类工艺Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
public interface EngCategoryEngineeringMapper 
{
    /**
     * 查询品类工艺
     * 
     * @param id 品类工艺主键
     * @return 品类工艺
     */
    public EngCategoryEngineering selectEngCategoryEngineeringById(Long id);

    /**
     * 查询品类工艺列表
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 品类工艺集合
     */
    public List<EngCategoryEngineering> selectEngCategoryEngineeringList(EngCategoryEngineering engCategoryEngineering);

    /**
     * 查询品类工艺列表
     *
     * @param parentId 父品类
     * @return 品类工艺集合
     */
    public List<EngCategoryEngineering> selectEngCategoryEngineeringListByParentId(Long parentId);

    /**
     * 查询品类工艺列表
     *
     * @param lineId 产线ID
     * @return 品类工艺集合
     */
    public List<EngCategoryEngineering> selectEngCategoryEngineeringListByLineId(Long lineId);

    /**
     * 新增品类工艺
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 结果
     */
    public int insertEngCategoryEngineering(EngCategoryEngineering engCategoryEngineering);

    /**
     * 修改品类工艺
     * 
     * @param engCategoryEngineering 品类工艺
     * @return 结果
     */
    public int updateEngCategoryEngineering(EngCategoryEngineering engCategoryEngineering);

    /**
     * 删除品类工艺
     * 
     * @param id 品类工艺主键
     * @return 结果
     */
    public int deleteEngCategoryEngineeringById(Long id);

    /**
     * 批量删除品类工艺
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngCategoryEngineeringByIds(Long[] ids);

    /**
     * 查询品类工艺列表
     *
     * @param parentId 父品类
     * @return 品类工艺集合
     */
    public int deleteEngCategoryEngineeringByParentId(Long parentId);

    /**
     * 查询品类工艺列表
     *
     * @param lineId 产线ID
     * @return 品类工艺集合
     */
    public int deleteEngCategoryEngineeringByLineId(Long lineId);
}
