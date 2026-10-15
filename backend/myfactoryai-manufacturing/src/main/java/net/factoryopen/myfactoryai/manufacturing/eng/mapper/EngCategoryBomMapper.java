package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryBom;

/**
 * 品类BOMMapper接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface EngCategoryBomMapper 
{
    /**
     * 查询品类BOM
     * 
     * @param id 品类BOM主键
     * @return 品类BOM
     */
    public EngCategoryBom selectEngCategoryBomById(Long id);

    /**
     * 查询品类BOM列表
     * 
     * @param engCategoryBom 品类BOM
     * @return 品类BOM集合
     */
    public List<EngCategoryBom> selectEngCategoryBomList(EngCategoryBom engCategoryBom);

    /**
     * 查询品类BOM列表
     *
     * @param categoryId 品类ID
     * @return 品类BOM集合
     */
    public List<EngCategoryBom> selectEngCategoryBomListByCategoryId(Long categoryId);

    /**
     * 查询品类BOM列表
     *
     * @param materialId 物料ID
     * @return 品类BOM集合
     */
    public List<EngCategoryBom> selectEngCategoryBomListByMaterialId(Long materialId);

    /**
     * 查询品类BOM列表
     *
     * @param lineId 产线ID
     * @return 品类BOM集合
     */
    public List<EngCategoryBom> selectEngCategoryBomListByLineId(Long lineId);

    /**
     * 新增品类BOM
     * 
     * @param engCategoryBom 品类BOM
     * @return 结果
     */
    public int insertEngCategoryBom(EngCategoryBom engCategoryBom);

    /**
     * 修改品类BOM
     * 
     * @param engCategoryBom 品类BOM
     * @return 结果
     */
    public int updateEngCategoryBom(EngCategoryBom engCategoryBom);

    /**
     * 删除品类BOM
     * 
     * @param id 品类BOM主键
     * @return 结果
     */
    public int deleteEngCategoryBomById(Long id);

    /**
     * 批量删除品类BOM
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngCategoryBomByIds(Long[] ids);

    /**
     * 查询品类BOM列表
     *
     * @param categoryId 品类ID
     * @return 品类BOM集合
     */
    public int deleteEngCategoryBomByCategoryId(Long categoryId);

    /**
     * 查询品类BOM列表
     *
     * @param materialId 物料ID
     * @return 品类BOM集合
     */
    public int deleteEngCategoryBomByMaterialId(Long materialId);

    /**
     * 查询品类BOM列表
     *
     * @param lineId 产线ID
     * @return 品类BOM集合
     */
    public int deleteEngCategoryBomByLineId(Long lineId);
}
