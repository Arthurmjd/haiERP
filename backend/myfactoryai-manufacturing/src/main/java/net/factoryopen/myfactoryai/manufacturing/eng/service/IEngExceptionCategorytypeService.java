package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionCategorytype;

/**
 * 工艺异常分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEngExceptionCategorytypeService 
{
    /**
     * 查询工艺异常分类
     * 
     * @param id 工艺异常分类主键
     * @return 工艺异常分类
     */
    public EngExceptionCategorytype selectEngExceptionCategorytypeById(Long id);

    /**
     * 查询工艺异常分类列表
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 工艺异常分类集合
     */
    public List<EngExceptionCategorytype> selectEngExceptionCategorytypeList(EngExceptionCategorytype engExceptionCategorytype);

    /**
     * 新增工艺异常分类
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 结果
     */
    public int insertEngExceptionCategorytype(EngExceptionCategorytype engExceptionCategorytype);

    /**
     * 修改工艺异常分类
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 结果
     */
    public int updateEngExceptionCategorytype(EngExceptionCategorytype engExceptionCategorytype);

    /**
     * 批量删除工艺异常分类
     * 
     * @param ids 需要删除的工艺异常分类主键集合
     * @return 结果
     */
    public int deleteEngExceptionCategorytypeByIds(Long[] ids);

    /**
     * 删除工艺异常分类信息
     * 
     * @param id 工艺异常分类主键
     * @return 结果
     */
    public int deleteEngExceptionCategorytypeById(Long id);


}
