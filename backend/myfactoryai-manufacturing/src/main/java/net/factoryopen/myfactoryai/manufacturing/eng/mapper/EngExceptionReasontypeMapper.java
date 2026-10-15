package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionReasontype;

/**
 * 工艺异常原因Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EngExceptionReasontypeMapper 
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
     * 查询工艺异常原因列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常原因集合
     */
    public List<EngExceptionReasontype> selectEngExceptionReasontypeListByCategoryId(Long categoryId);

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
     * 删除工艺异常原因
     * 
     * @param id 工艺异常原因主键
     * @return 结果
     */
    public int deleteEngExceptionReasontypeById(Long id);

    /**
     * 批量删除工艺异常原因
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngExceptionReasontypeByIds(Long[] ids);

    /**
     * 查询工艺异常原因列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常原因集合
     */
    public int deleteEngExceptionReasontypeByCategoryId(Long categoryId);
}
