package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionReasontype;

/**
 * 工艺异常原因Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEngExceptionReasontypeService 
{
    /**
     * 查询工艺异常原因
     * 
     * @param id 工艺异常原因主键
     * @return 工艺异常原因
     */
    public EngExceptionReasontype selectEngExceptionReasontypeById(Long id);

    /**
     * 查询工艺异常原因列表
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 工艺异常原因集合
     */
    public List<EngExceptionReasontype> selectEngExceptionReasontypeList(EngExceptionReasontype engExceptionReasontype);

    /**
     * 新增工艺异常原因
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 结果
     */
    public int insertEngExceptionReasontype(EngExceptionReasontype engExceptionReasontype);

    /**
     * 修改工艺异常原因
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 结果
     */
    public int updateEngExceptionReasontype(EngExceptionReasontype engExceptionReasontype);

    /**
     * 批量删除工艺异常原因
     * 
     * @param ids 需要删除的工艺异常原因主键集合
     * @return 结果
     */
    public int deleteEngExceptionReasontypeByIds(Long[] ids);

    /**
     * 删除工艺异常原因信息
     * 
     * @param id 工艺异常原因主键
     * @return 结果
     */
    public int deleteEngExceptionReasontypeById(Long id);


}
