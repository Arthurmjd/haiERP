package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionCategorytype;

/**
 * 制造异常分类Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-11-07
 */
public interface IQuaMexceptionCategorytypeService 
{
    /**
     * 查询制造异常分类
     * 
     * @param id 制造异常分类主键
     * @return 制造异常分类
     */
    public QuaMexceptionCategorytype selectQuaMexceptionCategorytypeById(Long id);

    /**
     * 查询制造异常分类列表
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 制造异常分类集合
     */
    public List<QuaMexceptionCategorytype> selectQuaMexceptionCategorytypeList(QuaMexceptionCategorytype quaMexceptionCategorytype);

    /**
     * 新增制造异常分类
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 结果
     */
    public int insertQuaMexceptionCategorytype(QuaMexceptionCategorytype quaMexceptionCategorytype);

    /**
     * 修改制造异常分类
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 结果
     */
    public int updateQuaMexceptionCategorytype(QuaMexceptionCategorytype quaMexceptionCategorytype);

    /**
     * 批量删除制造异常分类
     * 
     * @param ids 需要删除的制造异常分类主键集合
     * @return 结果
     */
    public int deleteQuaMexceptionCategorytypeByIds(Long[] ids);

    /**
     * 删除制造异常分类信息
     * 
     * @param id 制造异常分类主键
     * @return 结果
     */
    public int deleteQuaMexceptionCategorytypeById(Long id);


}
