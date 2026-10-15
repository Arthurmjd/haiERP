package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionSolutiontype;

/**
 * 工艺异常处理类型Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EngExceptionSolutiontypeMapper 
{
    /**
     * 查询工艺异常处理类型
     * 
     * @param id 工艺异常处理类型主键
     * @return 工艺异常处理类型
     */
    public EngExceptionSolutiontype selectEngExceptionSolutiontypeById(Long id);

    /**
     * 查询工艺异常处理类型列表
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 工艺异常处理类型集合
     */
    public List<EngExceptionSolutiontype> selectEngExceptionSolutiontypeList(EngExceptionSolutiontype engExceptionSolutiontype);

    /**
     * 查询工艺异常处理类型列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常处理类型集合
     */
    public List<EngExceptionSolutiontype> selectEngExceptionSolutiontypeListByCategoryId(Long categoryId);

    /**
     * 新增工艺异常处理类型
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 结果
     */
    public int insertEngExceptionSolutiontype(EngExceptionSolutiontype engExceptionSolutiontype);

    /**
     * 修改工艺异常处理类型
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 结果
     */
    public int updateEngExceptionSolutiontype(EngExceptionSolutiontype engExceptionSolutiontype);

    /**
     * 删除工艺异常处理类型
     * 
     * @param id 工艺异常处理类型主键
     * @return 结果
     */
    public int deleteEngExceptionSolutiontypeById(Long id);

    /**
     * 批量删除工艺异常处理类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngExceptionSolutiontypeByIds(Long[] ids);

    /**
     * 查询工艺异常处理类型列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常处理类型集合
     */
    public int deleteEngExceptionSolutiontypeByCategoryId(Long categoryId);
}
