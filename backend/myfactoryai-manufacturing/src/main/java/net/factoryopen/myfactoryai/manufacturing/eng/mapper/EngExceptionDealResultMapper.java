package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionDealResult;

/**
 * 工艺异常判处结果Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EngExceptionDealResultMapper 
{
    /**
     * 查询工艺异常判处结果
     * 
     * @param id 工艺异常判处结果主键
     * @return 工艺异常判处结果
     */
    public EngExceptionDealResult selectEngExceptionDealResultById(Long id);

    /**
     * 查询工艺异常判处结果列表
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultList(EngExceptionDealResult engExceptionDealResult);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByMlId(Long mlId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByCategoryId(Long categoryId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dealBy 处理人
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultListByDealBy(Long dealBy);

    /**
     * 新增工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    public int insertEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult);

    /**
     * 修改工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    public int updateEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult);

    /**
     * 删除工艺异常判处结果
     * 
     * @param id 工艺异常判处结果主键
     * @return 结果
     */
    public int deleteEngExceptionDealResultById(Long id);

    /**
     * 批量删除工艺异常判处结果
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngExceptionDealResultByIds(Long[] ids);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByMlId(Long mlId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByCategoryId(Long categoryId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常判处结果列表
     *
     * @param dealBy 处理人
     * @return 工艺异常判处结果集合
     */
    public int deleteEngExceptionDealResultByDealBy(Long dealBy);
}
