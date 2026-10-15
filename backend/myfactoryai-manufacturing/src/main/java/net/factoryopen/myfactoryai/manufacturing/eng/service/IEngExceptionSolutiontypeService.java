package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionSolutiontype;

/**
 * 工艺异常处理类型Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEngExceptionSolutiontypeService 
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
     * 批量删除工艺异常处理类型
     * 
     * @param ids 需要删除的工艺异常处理类型主键集合
     * @return 结果
     */
    public int deleteEngExceptionSolutiontypeByIds(Long[] ids);

    /**
     * 删除工艺异常处理类型信息
     * 
     * @param id 工艺异常处理类型主键
     * @return 结果
     */
    public int deleteEngExceptionSolutiontypeById(Long id);


}
