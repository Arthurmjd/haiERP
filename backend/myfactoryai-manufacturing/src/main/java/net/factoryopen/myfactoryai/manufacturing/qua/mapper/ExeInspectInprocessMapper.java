package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectInprocess;

/**
 * 制检异常登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface ExeInspectInprocessMapper 
{
    /**
     * 查询制检异常登记
     * 
     * @param id 制检异常登记主键
     * @return 制检异常登记
     */
    public ExeInspectInprocess selectExeInspectInprocessById(Long id);

    /**
     * 查询制检异常登记列表
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessList(ExeInspectInprocess exeInspectInprocess);

    /**
     * 查询制检异常登记列表
     *
     * @param materialId 产品ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByMaterialId(Long materialId);

    /**
     * 查询制检异常登记列表
     *
     * @param mlId 制造批
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByMlId(Long mlId);

    /**
     * 查询制检异常登记列表
     *
     * @param inspectBy 检查人
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByInspectBy(Long inspectBy);

    /**
     * 查询制检异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询制检异常登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByMcategoryId(Long mcategoryId);

    /**
     * 查询制检异常登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByPcategoryId(Long pcategoryId);

    /**
     * 查询制检异常登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByDutyLineId(Long dutyLineId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询制检异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByDealDeptId(Long dealDeptId);

    /**
     * 查询制检异常登记列表
     *
     * @param dealBy 处理人
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessListByDealBy(Long dealBy);

    /**
     * 新增制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    public int insertExeInspectInprocess(ExeInspectInprocess exeInspectInprocess);

    /**
     * 修改制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    public int updateExeInspectInprocess(ExeInspectInprocess exeInspectInprocess);

    /**
     * 删除制检异常登记
     * 
     * @param id 制检异常登记主键
     * @return 结果
     */
    public int deleteExeInspectInprocessById(Long id);

    /**
     * 批量删除制检异常登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeInspectInprocessByIds(Long[] ids);

    /**
     * 查询制检异常登记列表
     *
     * @param materialId 产品ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByMaterialId(Long materialId);

    /**
     * 查询制检异常登记列表
     *
     * @param mlId 制造批
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByMlId(Long mlId);

    /**
     * 查询制检异常登记列表
     *
     * @param inspectBy 检查人
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByInspectBy(Long inspectBy);

    /**
     * 查询制检异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByInspectDeptId(Long inspectDeptId);

    /**
     * 查询制检异常登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByMcategoryId(Long mcategoryId);

    /**
     * 查询制检异常登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByPcategoryId(Long pcategoryId);

    /**
     * 查询制检异常登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByDutyLineId(Long dutyLineId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByDutyProcessId(Long dutyProcessId);

    /**
     * 查询制检异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByDutyGroupId(Long dutyGroupId);

    /**
     * 查询制检异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByDealDeptId(Long dealDeptId);

    /**
     * 查询制检异常登记列表
     *
     * @param dealBy 处理人
     * @return 制检异常登记集合
     */
    public int deleteExeInspectInprocessByDealBy(Long dealBy);
}
