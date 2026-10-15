package net.factoryopen.myfactoryai.manufacturing.exe.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessOperating;

/**
 * 产线班组作业Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface ExeProcessOperatingMapper 
{
    /**
     * 查询产线班组作业
     * 
     * @param id 产线班组作业主键
     * @return 产线班组作业
     */
    public ExeProcessOperating selectExeProcessOperatingById(Long id);

    /**
     * 查询产线班组作业列表
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingList(ExeProcessOperating exeProcessOperating);

    /**
     * 查询产线班组作业列表
     *
     * @param mlId 制造批
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByMlId(Long mlId);

    /**
     * 查询产线班组作业列表
     *
     * @param productId 产品编码
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByProductId(Long productId);

    /**
     * 查询产线班组作业列表
     *
     * @param processId 工序码
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByProcessId(Long processId);

    /**
     * 查询产线班组作业列表
     *
     * @param lineId 产线
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByLineId(Long lineId);

    /**
     * 查询产线班组作业列表
     *
     * @param projectId 项目ID
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByProjectId(Long projectId);

    /**
     * 查询产线班组作业列表
     *
     * @param unitId 单位
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByUnitId(Long unitId);

    /**
     * 查询产线班组作业列表
     *
     * @param deptId 部门
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByDeptId(Long deptId);

    /**
     * 查询产线班组作业列表
     *
     * @param groupId 班组
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByGroupId(Long groupId);

    /**
     * 查询产线班组作业列表
     *
     * @param receiveBy 接收人
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByReceiveBy(Long receiveBy);

    /**
     * 查询产线班组作业列表
     *
     * @param postBy 转出人
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingListByPostBy(Long postBy);

    /**
     * 新增产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    public int insertExeProcessOperating(ExeProcessOperating exeProcessOperating);

    /**
     * 修改产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    public int updateExeProcessOperating(ExeProcessOperating exeProcessOperating);

    /**
     * 删除产线班组作业
     * 
     * @param id 产线班组作业主键
     * @return 结果
     */
    public int deleteExeProcessOperatingById(Long id);

    /**
     * 批量删除产线班组作业
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeProcessOperatingByIds(Long[] ids);

    /**
     * 查询产线班组作业列表
     *
     * @param mlId 制造批
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByMlId(Long mlId);

    /**
     * 查询产线班组作业列表
     *
     * @param productId 产品编码
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByProductId(Long productId);

    /**
     * 查询产线班组作业列表
     *
     * @param processId 工序码
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByProcessId(Long processId);

    /**
     * 查询产线班组作业列表
     *
     * @param lineId 产线
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByLineId(Long lineId);

    /**
     * 查询产线班组作业列表
     *
     * @param projectId 项目ID
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByProjectId(Long projectId);

    /**
     * 查询产线班组作业列表
     *
     * @param unitId 单位
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByUnitId(Long unitId);

    /**
     * 查询产线班组作业列表
     *
     * @param deptId 部门
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByDeptId(Long deptId);

    /**
     * 查询产线班组作业列表
     *
     * @param groupId 班组
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByGroupId(Long groupId);

    /**
     * 查询产线班组作业列表
     *
     * @param receiveBy 接收人
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByReceiveBy(Long receiveBy);

    /**
     * 查询产线班组作业列表
     *
     * @param postBy 转出人
     * @return 产线班组作业集合
     */
    public int deleteExeProcessOperatingByPostBy(Long postBy);
}
