package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReturn;

/**
 * 退货检异登记Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface QuaInspectReturnMapper 
{
    /**
     * 查询退货检异登记
     * 
     * @param id 退货检异登记主键
     * @return 退货检异登记
     */
    public QuaInspectReturn selectQuaInspectReturnById(Long id);

    /**
     * 查询退货检异登记列表
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnList(QuaInspectReturn quaInspectReturn);

    /**
     * 查询退货检异登记列表
     *
     * @param materialId 产品ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByMaterialId(Long materialId);

    /**
     * 查询退货检异登记列表
     *
     * @param mlId 制造批
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByMlId(Long mlId);

    /**
     * 查询退货检异登记列表
     *
     * @param supplierId 供应商ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListBySupplierId(Long supplierId);

    /**
     * 查询退货检异登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByMcategoryId(Long mcategoryId);

    /**
     * 查询退货检异登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByPcategoryId(Long pcategoryId);

    /**
     * 查询退货检异登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByDutyLineId(Long dutyLineId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByDutyProcessId(Long dutyProcessId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByDutyGroupId(Long dutyGroupId);

    /**
     * 查询退货检异登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByDealDeptId(Long dealDeptId);

    /**
     * 查询退货检异登记列表
     *
     * @param dealBy 处理人
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnListByDealBy(Long dealBy);

    /**
     * 新增退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    public int insertQuaInspectReturn(QuaInspectReturn quaInspectReturn);

    /**
     * 修改退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    public int updateQuaInspectReturn(QuaInspectReturn quaInspectReturn);

    /**
     * 删除退货检异登记
     * 
     * @param id 退货检异登记主键
     * @return 结果
     */
    public int deleteQuaInspectReturnById(Long id);

    /**
     * 批量删除退货检异登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaInspectReturnByIds(Long[] ids);

    /**
     * 查询退货检异登记列表
     *
     * @param materialId 产品ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByMaterialId(Long materialId);

    /**
     * 查询退货检异登记列表
     *
     * @param mlId 制造批
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByMlId(Long mlId);

    /**
     * 查询退货检异登记列表
     *
     * @param supplierId 供应商ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnBySupplierId(Long supplierId);

    /**
     * 查询退货检异登记列表
     *
     * @param mcategoryId 制异分类ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByMcategoryId(Long mcategoryId);

    /**
     * 查询退货检异登记列表
     *
     * @param mreasonTypeId 制异原因ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByMreasonTypeId(Long mreasonTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param msolutionTypeId 制异方案ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByMsolutionTypeId(Long msolutionTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param pcategoryId 购异分类ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByPcategoryId(Long pcategoryId);

    /**
     * 查询退货检异登记列表
     *
     * @param preasonTypeId 购异原因ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByPreasonTypeId(Long preasonTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param psolutionTypeId 购异方案ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByPsolutionTypeId(Long psolutionTypeId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyLineId 责任产线ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByDutyLineId(Long dutyLineId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyProcessId 责任工序ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByDutyProcessId(Long dutyProcessId);

    /**
     * 查询退货检异登记列表
     *
     * @param dutyGroupId 责任班组ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByDutyGroupId(Long dutyGroupId);

    /**
     * 查询退货检异登记列表
     *
     * @param dealDeptId 处理部门ID
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByDealDeptId(Long dealDeptId);

    /**
     * 查询退货检异登记列表
     *
     * @param dealBy 处理人
     * @return 退货检异登记集合
     */
    public int deleteQuaInspectReturnByDealBy(Long dealBy);
}
