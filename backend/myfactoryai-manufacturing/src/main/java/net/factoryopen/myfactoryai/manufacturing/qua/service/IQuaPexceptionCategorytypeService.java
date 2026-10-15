package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionCategorytype;

/**
 * 采购异常分类Service接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface IQuaPexceptionCategorytypeService 
{
    /**
     * 查询采购异常分类
     * 
     * @param id 采购异常分类主键
     * @return 采购异常分类
     */
    public QuaPexceptionCategorytype selectQuaPexceptionCategorytypeById(Long id);

    /**
     * 查询采购异常分类列表
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 采购异常分类集合
     */
    public List<QuaPexceptionCategorytype> selectQuaPexceptionCategorytypeList(QuaPexceptionCategorytype quaPexceptionCategorytype);

    /**
     * 新增采购异常分类
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 结果
     */
    public int insertQuaPexceptionCategorytype(QuaPexceptionCategorytype quaPexceptionCategorytype);

    /**
     * 修改采购异常分类
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 结果
     */
    public int updateQuaPexceptionCategorytype(QuaPexceptionCategorytype quaPexceptionCategorytype);

    /**
     * 批量删除采购异常分类
     * 
     * @param ids 需要删除的采购异常分类主键集合
     * @return 结果
     */
    public int deleteQuaPexceptionCategorytypeByIds(Long[] ids);

    /**
     * 删除采购异常分类信息
     * 
     * @param id 采购异常分类主键
     * @return 结果
     */
    public int deleteQuaPexceptionCategorytypeById(Long id);


}
