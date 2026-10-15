package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDealResult;

/**
 * 质量异常判处结果Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface QuaExceptionDealResultMapper 
{
    /**
     * 查询质量异常判处结果
     * 
     * @param id 质量异常判处结果主键
     * @return 质量异常判处结果
     */
    public QuaExceptionDealResult selectQuaExceptionDealResultById(Long id);

    /**
     * 查询质量异常判处结果列表
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultList(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 查询质量异常判处结果列表
     *
     * @param materialId 物料ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMaterialId(Long materialId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param materialSpec 物料规格
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMaterialSpec(Long materialSpec);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mlId 制造批
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMlId(Long mlId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param supplierId 供应商ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListBySupplierId(Long supplierId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param inspectBy 检查人
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByInspectBy(Long inspectBy);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mcategoryId 制异分类ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMcategoryId(Long mcategoryId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param pcategoryId 购异分类ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByPcategoryId(Long pcategoryId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyLineId 责任产线ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByDutyLineId(Long dutyLineId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dealDeptId 处理部门ID
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByDealDeptId(Long dealDeptId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dealBy 处理人
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultListByDealBy(Long dealBy);

    /**
     * 新增质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    public int insertQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 修改质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    public int updateQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 删除质量异常判处结果
     * 
     * @param id 质量异常判处结果主键
     * @return 结果
     */
    public int deleteQuaExceptionDealResultById(Long id);

    /**
     * 批量删除质量异常判处结果
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaExceptionDealResultByIds(Long[] ids);

    /**
     * 查询质量异常判处结果列表
     *
     * @param materialId 物料ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMaterialId(Long materialId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param materialSpec 物料规格
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMaterialSpec(Long materialSpec);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mlId 制造批
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMlId(Long mlId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param supplierId 供应商ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultBySupplierId(Long supplierId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByInspectDeptId(Long inspectDeptId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param inspectBy 检查人
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByInspectBy(Long inspectBy);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mcategoryId 制异分类ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMcategoryId(Long mcategoryId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param pcategoryId 购异分类ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByPcategoryId(Long pcategoryId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyLineId 责任产线ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByDutyLineId(Long dutyLineId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByDutyProcessId(Long dutyProcessId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByDutyGroupId(Long dutyGroupId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dealDeptId 处理部门ID
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByDealDeptId(Long dealDeptId);

    /**
     * 查询质量异常判处结果列表
     *
     * @param dealBy 处理人
     * @return 质量异常判处结果集合
     */
    public int deleteQuaExceptionDealResultByDealBy(Long dealBy);
}
