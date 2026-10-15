package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialRequirements;

/**
 * 物料需求Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface PlnMaterialRequirementsMapper 
{
    /**
     * 查询物料需求
     * 
     * @param id 物料需求主键
     * @return 物料需求
     */
    public PlnMaterialRequirements selectPlnMaterialRequirementsById(Long id);

    /**
     * 查询物料需求列表
     * 
     * @param plnMaterialRequirements 物料需求
     * @return 物料需求集合
     */
    public List<PlnMaterialRequirements> selectPlnMaterialRequirementsList(PlnMaterialRequirements plnMaterialRequirements);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public List<PlnMaterialRequirements> selectPlnMaterialRequirementsListByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public List<PlnMaterialRequirements> selectPlnMaterialRequirementsListByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public List<PlnMaterialRequirements> selectPlnMaterialRequirementsListByUnitId(Long unitId);

    /**
     * 新增物料需求
     * 
     * @param plnMaterialRequirements 物料需求
     * @return 结果
     */
    public int insertPlnMaterialRequirements(PlnMaterialRequirements plnMaterialRequirements);

    /**
     * 修改物料需求
     * 
     * @param plnMaterialRequirements 物料需求
     * @return 结果
     */
    public int updatePlnMaterialRequirements(PlnMaterialRequirements plnMaterialRequirements);

    /**
     * 删除物料需求
     * 
     * @param id 物料需求主键
     * @return 结果
     */
    public int deletePlnMaterialRequirementsById(Long id);

    /**
     * 批量删除物料需求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMaterialRequirementsByIds(Long[] ids);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public int deletePlnMaterialRequirementsByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public int deletePlnMaterialRequirementsByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public int deletePlnMaterialRequirementsByUnitId(Long unitId);
}
