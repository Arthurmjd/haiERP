package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeExceptionReq;

/**
 * 工艺异常登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface ExeExceptionReqMapper 
{
    /**
     * 查询工艺异常登记
     * 
     * @param id 工艺异常登记主键
     * @return 工艺异常登记
     */
    public ExeExceptionReq selectExeExceptionReqById(Long id);

    /**
     * 查询工艺异常登记列表
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqList(ExeExceptionReq exeExceptionReq);

    /**
     * 查询工艺异常登记列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByMlId(Long mlId);

    /**
     * 查询工艺异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常登记列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常登记列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByCategoryId(Long categoryId);

    /**
     * 查询工艺异常登记列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常登记列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dealBy 处理人
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqListByDealBy(Long dealBy);

    /**
     * 新增工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    public int insertExeExceptionReq(ExeExceptionReq exeExceptionReq);

    /**
     * 修改工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    public int updateExeExceptionReq(ExeExceptionReq exeExceptionReq);

    /**
     * 删除工艺异常登记
     * 
     * @param id 工艺异常登记主键
     * @return 结果
     */
    public int deleteExeExceptionReqById(Long id);

    /**
     * 批量删除工艺异常登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeExceptionReqByIds(Long[] ids);

    /**
     * 查询工艺异常登记列表
     *
     * @param mlId 制造批ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByMlId(Long mlId);

    /**
     * 查询工艺异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByInspectDeptId(Long inspectDeptId);

    /**
     * 查询工艺异常登记列表
     *
     * @param inspectBy 检查人
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByInspectBy(Long inspectBy);

    /**
     * 查询工艺异常登记列表
     *
     * @param categoryId 异常分类ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByCategoryId(Long categoryId);

    /**
     * 查询工艺异常登记列表
     *
     * @param reasonTypeId 原因类型ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByReasonTypeId(Long reasonTypeId);

    /**
     * 查询工艺异常登记列表
     *
     * @param solutionTypeId 方案类型ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqBySolutionTypeId(Long solutionTypeId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyOwnerId 责任人ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDutyOwnerId(Long dutyOwnerId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDutyLineId(Long dutyLineId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDutyProcessId(Long dutyProcessId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDutyGroupId(Long dutyGroupId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDealDeptId(Long dealDeptId);

    /**
     * 查询工艺异常登记列表
     *
     * @param dealBy 处理人
     * @return 工艺异常登记集合
     */
    public int deleteExeExceptionReqByDealBy(Long dealBy);
}
