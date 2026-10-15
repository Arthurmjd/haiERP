package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionCategorytype;

/**
 * 异常分类管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IQuaExceptionCategorytypeService 
{
    /**
     * 查询异常分类管理
     * 
     * @param id 异常分类管理主键
     * @return 异常分类管理
     */
    public QuaExceptionCategorytype selectQuaExceptionCategorytypeById(Long id);

    /**
     * 查询异常分类管理列表
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 异常分类管理集合
     */
    public List<QuaExceptionCategorytype> selectQuaExceptionCategorytypeList(QuaExceptionCategorytype quaExceptionCategorytype);

    /**
     * 新增异常分类管理
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 结果
     */
    public int insertQuaExceptionCategorytype(QuaExceptionCategorytype quaExceptionCategorytype);

    /**
     * 修改异常分类管理
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 结果
     */
    public int updateQuaExceptionCategorytype(QuaExceptionCategorytype quaExceptionCategorytype);

    /**
     * 批量删除异常分类管理
     * 
     * @param ids 需要删除的异常分类管理主键集合
     * @return 结果
     */
    public int deleteQuaExceptionCategorytypeByIds(Long[] ids);

    /**
     * 删除异常分类管理信息
     * 
     * @param id 异常分类管理主键
     * @return 结果
     */
    public int deleteQuaExceptionCategorytypeById(Long id);


}
