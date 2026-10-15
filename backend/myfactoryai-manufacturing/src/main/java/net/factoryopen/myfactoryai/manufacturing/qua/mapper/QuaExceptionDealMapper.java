package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;

/**
 * 质量异常判处Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface QuaExceptionDealMapper 
{
    /**
     * 查询质量异常判处
     * 
     * @param id 质量异常判处主键
     * @return 质量异常判处
     */
    public QuaExceptionDeal selectQuaExceptionDealById(Long id);

    /**
     * 查询质量异常判处列表
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealList(QuaExceptionDeal quaExceptionDeal);

    /**
     * 查询质量异常判处列表
     *
     * @param materialId 物料ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByMaterialId(Long materialId);

    /**
     * 查询质量异常判处列表
     *
     * @param mlId 制造批
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByMlId(Long mlId);

    /**
     * 查询质量异常判处列表
     *
     * @param supplierId 供应商ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListBySupplierId(Long supplierId);

    /**
     * 查询质量异常判处列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByInspectDeptId(Long inspectDeptId);

    /**
     * 查询质量异常判处列表
     *
     * @param inspectBy 检查人
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByInspectBy(Long inspectBy);

    /**
     * 查询质量异常判处列表
     *
     * @param mcategoryId 制异分类ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByMcategoryId(Long mcategoryId);

    /**
     * 查询质量异常判处列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param pcategoryId 购异分类ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByPcategoryId(Long pcategoryId);

    /**
     * 查询质量异常判处列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyLineId 责任产线ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByDutyLineId(Long dutyLineId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询质量异常判处列表
     *
     * @param dealDeptId 处理部门ID
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByDealDeptId(Long dealDeptId);

    /**
     * 查询质量异常判处列表
     *
     * @param dealBy 处理人
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealListByDealBy(Long dealBy);

    /**
     * 新增质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    public int insertQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal);

    /**
     * 修改质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    public int updateQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal);

    /**
     * 删除质量异常判处
     * 
     * @param id 质量异常判处主键
     * @return 结果
     */
    public int deleteQuaExceptionDealById(Long id);

    /**
     * 批量删除质量异常判处
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaExceptionDealByIds(Long[] ids);

    /**
     * 查询质量异常判处列表
     *
     * @param materialId 物料ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByMaterialId(Long materialId);

    /**
     * 查询质量异常判处列表
     *
     * @param mlId 制造批
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByMlId(Long mlId);

    /**
     * 查询质量异常判处列表
     *
     * @param supplierId 供应商ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealBySupplierId(Long supplierId);

    /**
     * 查询质量异常判处列表
     *
     * @param inspectDeptId 检查部门ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByInspectDeptId(Long inspectDeptId);

    /**
     * 查询质量异常判处列表
     *
     * @param inspectBy 检查人
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByInspectBy(Long inspectBy);

    /**
     * 查询质量异常判处列表
     *
     * @param mcategoryId 制异分类ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByMcategoryId(Long mcategoryId);

    /**
     * 查询质量异常判处列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param pcategoryId 购异分类ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByPcategoryId(Long pcategoryId);

    /**
     * 查询质量异常判处列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyLineId 责任产线ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByDutyLineId(Long dutyLineId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByDutyProcessId(Long dutyProcessId);

    /**
     * 查询质量异常判处列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByDutyGroupId(Long dutyGroupId);

    /**
     * 查询质量异常判处列表
     *
     * @param dealDeptId 处理部门ID
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByDealDeptId(Long dealDeptId);

    /**
     * 查询质量异常判处列表
     *
     * @param dealBy 处理人
     * @return 质量异常判处集合
     */
    public int deleteQuaExceptionDealByDealBy(Long dealBy);
}
