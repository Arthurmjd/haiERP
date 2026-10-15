package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;

/**
 * 产品BOMMapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngProductBomMapper 
{
    /**
     * 查询产品BOM
     * 
     * @param id 产品BOM主键
     * @return 产品BOM
     */
    public EngProductBom selectEngProductBomById(Long id);

    /**
     * 查询产品BOM列表
     * 
     * @param engProductBom 产品BOM
     * @return 产品BOM集合
     */
    public List<EngProductBom> selectEngProductBomList(EngProductBom engProductBom);

    /**
     * 查询产品BOM列表
     *
     * @param productId 产品编码
     * @return 产品BOM集合
     */
    public List<EngProductBom> selectEngProductBomListByProductId(Long productId);

    /**
     * 查询产品BOM列表
     *
     * @param materialId 物料编码
     * @return 产品BOM集合
     */
    public List<EngProductBom> selectEngProductBomListByMaterialId(Long materialId);

    /**
     * 新增产品BOM
     * 
     * @param engProductBom 产品BOM
     * @return 结果
     */
    public int insertEngProductBom(EngProductBom engProductBom);

    /**
     * 修改产品BOM
     * 
     * @param engProductBom 产品BOM
     * @return 结果
     */
    public int updateEngProductBom(EngProductBom engProductBom);

    /**
     * 删除产品BOM
     * 
     * @param id 产品BOM主键
     * @return 结果
     */
    public int deleteEngProductBomById(Long id);

    /**
     * 批量删除产品BOM
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductBomByIds(Long[] ids);

    /**
     * 查询产品BOM列表
     *
     * @param productId 产品编码
     * @return 产品BOM集合
     */
    public int deleteEngProductBomByProductId(Long productId);

    /**
     * 查询产品BOM列表
     *
     * @param materialId 物料编码
     * @return 产品BOM集合
     */
    public int deleteEngProductBomByMaterialId(Long materialId);
}
