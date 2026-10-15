package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionReasontype;

/**
 * 异常原因分类Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-11-07
 */
public interface QuaMexceptionReasontypeMapper 
{
    /**
     * 查询异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 异常原因分类
     */
    public QuaMexceptionReasontype selectQuaMexceptionReasontypeById(Long id);

    /**
     * 查询异常原因分类列表
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 异常原因分类集合
     */
    public List<QuaMexceptionReasontype> selectQuaMexceptionReasontypeList(QuaMexceptionReasontype quaMexceptionReasontype);

    /**
     * 查询异常原因分类列表
     *
     * @param categoryId 异常分类
     * @return 异常原因分类集合
     */
    public List<QuaMexceptionReasontype> selectQuaMexceptionReasontypeListByCategoryId(Long categoryId);

    /**
     * 新增异常原因分类
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 结果
     */
    public int insertQuaMexceptionReasontype(QuaMexceptionReasontype quaMexceptionReasontype);

    /**
     * 修改异常原因分类
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 结果
     */
    public int updateQuaMexceptionReasontype(QuaMexceptionReasontype quaMexceptionReasontype);

    /**
     * 删除异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 结果
     */
    public int deleteQuaMexceptionReasontypeById(Long id);

    /**
     * 批量删除异常原因分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaMexceptionReasontypeByIds(Long[] ids);

    /**
     * 查询异常原因分类列表
     *
     * @param categoryId 异常分类
     * @return 异常原因分类集合
     */
    public int deleteQuaMexceptionReasontypeByCategoryId(Long categoryId);
}
