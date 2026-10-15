package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryBom;

/**
 * 品类BOMService接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface IEngCategoryBomService 
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
     * 批量删除品类BOM
     * 
     * @param ids 需要删除的品类BOM主键集合
     * @return 结果
     */
    public int deleteEngCategoryBomByIds(Long[] ids);

    /**
     * 删除品类BOM信息
     * 
     * @param id 品类BOM主键
     * @return 结果
     */
    public int deleteEngCategoryBomById(Long id);


}
