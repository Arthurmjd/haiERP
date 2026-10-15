package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoMaterial;

/**
 * 工单发料Mapper接口
 * 
 * @author admin
 * @date 2024-07-12
 */
public interface PlnWoMaterialMapper 
{
    /**
     * 查询工单发料
     * 
     * @param id 工单发料主键
     * @return 工单发料
     */
    public PlnWoMaterial selectPlnWoMaterialById(Long id);

    /**
     * 查询工单发料列表
     * 
     * @param plnWoMaterial 工单发料
     * @return 工单发料集合
     */
    public List<PlnWoMaterial> selectPlnWoMaterialList(PlnWoMaterial plnWoMaterial);

    /**
     * 查询工单发料列表
     *
     * @param woId 工单ID
     * @return 工单发料集合
     */
    public List<PlnWoMaterial> selectPlnWoMaterialListByWoId(Long woId);

    /**
     * 查询工单发料列表
     *
     * @param materialId 物料ID
     * @return 工单发料集合
     */
    public List<PlnWoMaterial> selectPlnWoMaterialListByMaterialId(Long materialId);

    /**
     * 查询工单发料列表
     *
     * @param unitId 单位ID
     * @return 工单发料集合
     */
    public List<PlnWoMaterial> selectPlnWoMaterialListByUnitId(Long unitId);

    /**
     * 新增工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    public int insertPlnWoMaterial(PlnWoMaterial plnWoMaterial);

    /**
     * 修改工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    public int updatePlnWoMaterial(PlnWoMaterial plnWoMaterial);

    /**
     * 删除工单发料
     * 
     * @param id 工单发料主键
     * @return 结果
     */
    public int deletePlnWoMaterialById(Long id);

    /**
     * 批量删除工单发料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoMaterialByIds(Long[] ids);

    /**
     * 查询工单发料列表
     *
     * @param woId 工单ID
     * @return 工单发料集合
     */
    public int deletePlnWoMaterialByWoId(Long woId);

    /**
     * 查询工单发料列表
     *
     * @param materialId 物料ID
     * @return 工单发料集合
     */
    public int deletePlnWoMaterialByMaterialId(Long materialId);

    /**
     * 查询工单发料列表
     *
     * @param unitId 单位ID
     * @return 工单发料集合
     */
    public int deletePlnWoMaterialByUnitId(Long unitId);
}
