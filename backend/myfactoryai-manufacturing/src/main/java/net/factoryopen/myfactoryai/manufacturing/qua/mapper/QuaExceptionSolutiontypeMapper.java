package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionSolutiontype;

/**
 * 质量异常处理类型Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface QuaExceptionSolutiontypeMapper 
{
    /**
     * 查询质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 质量异常处理类型
     */
    public QuaExceptionSolutiontype selectQuaExceptionSolutiontypeById(Long id);

    /**
     * 查询质量异常处理类型列表
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 质量异常处理类型集合
     */
    public List<QuaExceptionSolutiontype> selectQuaExceptionSolutiontypeList(QuaExceptionSolutiontype quaExceptionSolutiontype);

    /**
     * 查询质量异常处理类型列表
     *
     * @param categoryId 分类ID
     * @return 质量异常处理类型集合
     */
    public List<QuaExceptionSolutiontype> selectQuaExceptionSolutiontypeListByCategoryId(Long categoryId);

    /**
     * 新增质量异常处理类型
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    public int insertQuaExceptionSolutiontype(QuaExceptionSolutiontype quaExceptionSolutiontype);

    /**
     * 修改质量异常处理类型
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    public int updateQuaExceptionSolutiontype(QuaExceptionSolutiontype quaExceptionSolutiontype);

    /**
     * 删除质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 结果
     */
    public int deleteQuaExceptionSolutiontypeById(Long id);

    /**
     * 批量删除质量异常处理类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaExceptionSolutiontypeByIds(Long[] ids);

    /**
     * 查询质量异常处理类型列表
     *
     * @param categoryId 分类ID
     * @return 质量异常处理类型集合
     */
    public int deleteQuaExceptionSolutiontypeByCategoryId(Long categoryId);
}
