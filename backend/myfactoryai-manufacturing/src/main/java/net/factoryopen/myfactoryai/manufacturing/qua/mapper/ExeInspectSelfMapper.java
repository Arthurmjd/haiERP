package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectSelf;

/**
 * 自检异常登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface ExeInspectSelfMapper 
{
    /**
     * 查询自检异常登记
     * 
     * @param id 自检异常登记主键
     * @return 自检异常登记
     */
    public ExeInspectSelf selectExeInspectSelfById(Long id);

    /**
     * 查询自检异常登记列表
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfList(ExeInspectSelf exeInspectSelf);

    /**
     * 查询自检异常登记列表
     *
     * @param materialId 产品ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByMaterialId(Long materialId);

    /**
     * 查询自检异常登记列表
     *
     * @param mlId 制造批
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByMlId(Long mlId);

    /**
     * 查询自检异常登记列表
     *
     * @param supplierId 供应商ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListBySupplierId(Long supplierId);

    /**
     * 查询自检异常登记列表
     *
     * @param inspectBy 检查人
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByInspectBy(Long inspectBy);

    /**
     * 查询自检异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询自检异常登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByMcategoryId(Long mcategoryId);

    /**
     * 查询自检异常登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByPcategoryId(Long pcategoryId);

    /**
     * 查询自检异常登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByDutyLineId(Long dutyLineId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询自检异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByDealDeptId(Long dealDeptId);

    /**
     * 查询自检异常登记列表
     *
     * @param dealBy 处理人
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByDealBy(Long dealBy);

    /**
     * 新增自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    public int insertExeInspectSelf(ExeInspectSelf exeInspectSelf);

    /**
     * 修改自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    public int updateExeInspectSelf(ExeInspectSelf exeInspectSelf);

    /**
     * 删除自检异常登记
     * 
     * @param id 自检异常登记主键
     * @return 结果
     */
    public int deleteExeInspectSelfById(Long id);

    /**
     * 批量删除自检异常登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExeInspectSelfByIds(Long[] ids);

    /**
     * 查询自检异常登记列表
     *
     * @param materialId 产品ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByMaterialId(Long materialId);

    /**
     * 查询自检异常登记列表
     *
     * @param mlId 制造批
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByMlId(Long mlId);

    /**
     * 查询自检异常登记列表
     *
     * @param supplierId 供应商ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfBySupplierId(Long supplierId);

    /**
     * 查询自检异常登记列表
     *
     * @param inspectBy 检查人
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByInspectBy(Long inspectBy);

    /**
     * 查询自检异常登记列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByInspectDeptId(Long inspectDeptId);

    /**
     * 查询自检异常登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByMcategoryId(Long mcategoryId);

    /**
     * 查询自检异常登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByPcategoryId(Long pcategoryId);

    /**
     * 查询自检异常登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByDutyLineId(Long dutyLineId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByDutyProcessId(Long dutyProcessId);

    /**
     * 查询自检异常登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByDutyGroupId(Long dutyGroupId);

    /**
     * 查询自检异常登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByDealDeptId(Long dealDeptId);

    /**
     * 查询自检异常登记列表
     *
     * @param dealBy 处理人
     * @return 自检异常登记集合
     */
    public int deleteExeInspectSelfByDealBy(Long dealBy);
}
