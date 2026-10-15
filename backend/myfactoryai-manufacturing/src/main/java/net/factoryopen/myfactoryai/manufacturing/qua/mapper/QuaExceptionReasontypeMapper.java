package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionReasontype;

/**
 * 异常原因分类Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface QuaExceptionReasontypeMapper 
{
    /**
     * 查询异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 异常原因分类
     */
    public QuaExceptionReasontype selectQuaExceptionReasontypeById(Long id);

    /**
     * 查询异常原因分类列表
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 异常原因分类集合
     */
    public List<QuaExceptionReasontype> selectQuaExceptionReasontypeList(QuaExceptionReasontype quaExceptionReasontype);

    /**
     * 查询异常原因分类列表
     *
     * @param categoryId 异常分类
     * @return 异常原因分类集合
     */
    public List<QuaExceptionReasontype> selectQuaExceptionReasontypeListByCategoryId(Long categoryId);

    /**
     * 新增异常原因分类
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 结果
     */
    public int insertQuaExceptionReasontype(QuaExceptionReasontype quaExceptionReasontype);

    /**
     * 修改异常原因分类
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 结果
     */
    public int updateQuaExceptionReasontype(QuaExceptionReasontype quaExceptionReasontype);

    /**
     * 删除异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 结果
     */
    public int deleteQuaExceptionReasontypeById(Long id);

    /**
     * 批量删除异常原因分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaExceptionReasontypeByIds(Long[] ids);

    /**
     * 查询异常原因分类列表
     *
     * @param categoryId 异常分类
     * @return 异常原因分类集合
     */
    public int deleteQuaExceptionReasontypeByCategoryId(Long categoryId);
}
