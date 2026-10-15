package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionSolutiontype;

/**
 * 质量异常处理类型Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IQuaExceptionSolutiontypeService 
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
     * 批量删除质量异常处理类型
     * 
     * @param ids 需要删除的质量异常处理类型主键集合
     * @return 结果
     */
    public int deleteQuaExceptionSolutiontypeByIds(Long[] ids);

    /**
     * 删除质量异常处理类型信息
     * 
     * @param id 质量异常处理类型主键
     * @return 结果
     */
    public int deleteQuaExceptionSolutiontypeById(Long id);


}
