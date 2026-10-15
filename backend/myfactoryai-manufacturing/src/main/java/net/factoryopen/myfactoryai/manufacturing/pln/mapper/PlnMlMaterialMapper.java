package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMaterial;

/**
 * 批次备料单Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface PlnMlMaterialMapper 
{
    /**
     * 查询批次备料单
     * 
     * @param id 批次备料单主键
     * @return 批次备料单
     */
    public PlnMlMaterial selectPlnMlMaterialById(Long id);

    /**
     * 查询批次备料单列表
     * 
     * @param plnMlMaterial 批次备料单
     * @return 批次备料单集合
     */
    public List<PlnMlMaterial> selectPlnMlMaterialList(PlnMlMaterial plnMlMaterial);

    /**
     * 查询批次备料单列表
     *
     * @param mlId 制造批
     * @return 批次备料单集合
     */
    public List<PlnMlMaterial> selectPlnMlMaterialListByMlId(Long mlId);

    /**
     * 查询批次备料单列表
     *
     * @param materialId 物料编码
     * @return 批次备料单集合
     */
    public List<PlnMlMaterial> selectPlnMlMaterialListByMaterialId(Long materialId);

    /**
     * 查询批次备料单列表
     *
     * @param unitId 单位ID
     * @return 批次备料单集合
     */
    public List<PlnMlMaterial> selectPlnMlMaterialListByUnitId(Long unitId);

    /**
     * 新增批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    public int insertPlnMlMaterial(PlnMlMaterial plnMlMaterial);

    /**
     * 修改批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    public int updatePlnMlMaterial(PlnMlMaterial plnMlMaterial);

    /**
     * 删除批次备料单
     * 
     * @param id 批次备料单主键
     * @return 结果
     */
    public int deletePlnMlMaterialById(Long id);

    /**
     * 批量删除批次备料单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlMaterialByIds(Long[] ids);

    /**
     * 查询批次备料单列表
     *
     * @param mlId 制造批
     * @return 批次备料单集合
     */
    public int deletePlnMlMaterialByMlId(Long mlId);

    /**
     * 查询批次备料单列表
     *
     * @param materialId 物料编码
     * @return 批次备料单集合
     */
    public int deletePlnMlMaterialByMaterialId(Long materialId);

    /**
     * 查询批次备料单列表
     *
     * @param unitId 单位ID
     * @return 批次备料单集合
     */
    public int deletePlnMlMaterialByUnitId(Long unitId);
}
