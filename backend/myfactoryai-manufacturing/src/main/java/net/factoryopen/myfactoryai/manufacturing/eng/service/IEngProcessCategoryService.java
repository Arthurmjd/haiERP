package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcessCategory;

/**
 * 制程数据Service接口
 * 
 * @author generator
 * @date 2026-02-20
 */
public interface IEngProcessCategoryService 
{
    /**
     * 查询制程数据
     * 
     * @param id 制程数据主键
     * @return 制程数据
     */
    public EngProcessCategory selectEngProcessCategoryById(Long id);

    /**
     * 查询制程数据列表
     * 
     * @param engProcessCategory 制程数据
     * @return 制程数据集合
     */
    public List<EngProcessCategory> selectEngProcessCategoryList(EngProcessCategory engProcessCategory);

    /**
     * 新增制程数据
     * 
     * @param engProcessCategory 制程数据
     * @return 结果
     */
    public int insertEngProcessCategory(EngProcessCategory engProcessCategory);

    /**
     * 修改制程数据
     * 
     * @param engProcessCategory 制程数据
     * @return 结果
     */
    public int updateEngProcessCategory(EngProcessCategory engProcessCategory);

    /**
     * 批量删除制程数据
     * 
     * @param ids 需要删除的制程数据主键集合
     * @return 结果
     */
    public int deleteEngProcessCategoryByIds(Long[] ids);

    /**
     * 删除制程数据信息
     * 
     * @param id 制程数据主键
     * @return 结果
     */
    public int deleteEngProcessCategoryById(Long id);


}
