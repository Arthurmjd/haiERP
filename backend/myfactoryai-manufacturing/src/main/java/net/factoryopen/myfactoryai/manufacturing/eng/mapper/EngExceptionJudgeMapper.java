package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionJudge;

/**
 * 工艺异常判处Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EngExceptionJudgeMapper 
{
    /**
     * 查询工艺异常判处
     * 
     * @param id 工艺异常判处主键
     * @return 工艺异常判处
     */
    public EngExceptionJudge selectEngExceptionJudgeById(Long id);

    /**
     * 查询工艺异常判处列表
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeList(EngExceptionJudge engExceptionJudge);

    /**
     * 查询工艺异常判处列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByMlId(Long mlId);

    /**
     * 查询工艺异常判处列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常判处列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常判处列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByCategoryId(Long categoryId);

    /**
     * 查询工艺异常判处列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常判处列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dealBy 处理人
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeListByDealBy(Long dealBy);

    /**
     * 新增工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    public int insertEngExceptionJudge(EngExceptionJudge engExceptionJudge);

    /**
     * 修改工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    public int updateEngExceptionJudge(EngExceptionJudge engExceptionJudge);

    /**
     * 删除工艺异常判处
     * 
     * @param id 工艺异常判处主键
     * @return 结果
     */
    public int deleteEngExceptionJudgeById(Long id);

    /**
     * 批量删除工艺异常判处
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngExceptionJudgeByIds(Long[] ids);

    /**
     * 查询工艺异常判处列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByMlId(Long mlId);

    /**
     * 查询工艺异常判处列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常判处列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常判处列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByCategoryId(Long categoryId);

    /**
     * 查询工艺异常判处列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常判处列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常判处列表
     *
     * @param dealBy 处理人
     * @return 工艺异常判处集合
     */
    public int deleteEngExceptionJudgeByDealBy(Long dealBy);
}
