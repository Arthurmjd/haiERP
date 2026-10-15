package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;

/**
 * 品类工艺Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
public interface IEngCategoryEngineeringService 
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
     * 批量删除品类工艺
     * 
     * @param ids 需要删除的品类工艺主键集合
     * @return 结果
     */
    public int deleteEngCategoryEngineeringByIds(Long[] ids);

    /**
     * 删除品类工艺信息
     * 
     * @param id 品类工艺主键
     * @return 结果
     */
    public int deleteEngCategoryEngineeringById(Long id);

    /**
     * 填补子孙
     *
     * @param engCategoryEngineering 填补子孙
     * @return 结果
     */
    public int fillChildren(EngCategoryEngineering engCategoryEngineering) throws Exception;

    /**
     * 覆盖子孙
     *
     * @param engCategoryEngineering 覆盖子孙
     * @return 结果
     */
    public int coverChildren(EngCategoryEngineering engCategoryEngineering) throws Exception;


}
