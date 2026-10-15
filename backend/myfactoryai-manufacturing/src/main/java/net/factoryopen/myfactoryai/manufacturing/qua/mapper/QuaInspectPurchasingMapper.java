package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectPurchasing;

/**
 * 外购检异登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface QuaInspectPurchasingMapper 
{
    /**
     * 查询外购检异登记
     * 
     * @param id 外购检异登记主键
     * @return 外购检异登记
     */
    public QuaInspectPurchasing selectQuaInspectPurchasingById(Long id);

    /**
     * 查询外购检异登记列表
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingList(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 查询外购检异登记列表
     *
     * @param materialId 产品ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByMaterialId(Long materialId);

    /**
     * 查询外购检异登记列表
     *
     * @param mlId 制造批
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByMlId(Long mlId);

    /**
     * 查询外购检异登记列表
     *
     * @param supplierId 供应商ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListBySupplierId(Long supplierId);

    /**
     * 查询外购检异登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByMcategoryId(Long mcategoryId);

    /**
     * 查询外购检异登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByPcategoryId(Long pcategoryId);

    /**
     * 查询外购检异登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByDutyLineId(Long dutyLineId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询外购检异登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByDealDeptId(Long dealDeptId);

    /**
     * 查询外购检异登记列表
     *
     * @param dealBy 处理人
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingListByDealBy(Long dealBy);

    /**
     * 新增外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    public int insertQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 修改外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    public int updateQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 删除外购检异登记
     * 
     * @param id 外购检异登记主键
     * @return 结果
     */
    public int deleteQuaInspectPurchasingById(Long id);

    /**
     * 批量删除外购检异登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaInspectPurchasingByIds(Long[] ids);

    /**
     * 查询外购检异登记列表
     *
     * @param materialId 产品ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByMaterialId(Long materialId);

    /**
     * 查询外购检异登记列表
     *
     * @param mlId 制造批
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByMlId(Long mlId);

    /**
     * 查询外购检异登记列表
     *
     * @param supplierId 供应商ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingBySupplierId(Long supplierId);

    /**
     * 查询外购检异登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByMcategoryId(Long mcategoryId);

    /**
     * 查询外购检异登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByPcategoryId(Long pcategoryId);

    /**
     * 查询外购检异登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByDutyLineId(Long dutyLineId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByDutyProcessId(Long dutyProcessId);

    /**
     * 查询外购检异登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByDutyGroupId(Long dutyGroupId);

    /**
     * 查询外购检异登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByDealDeptId(Long dealDeptId);

    /**
     * 查询外购检异登记列表
     *
     * @param dealBy 处理人
     * @return 外购检异登记集合
     */
    public int deleteQuaInspectPurchasingByDealBy(Long dealBy);
}
