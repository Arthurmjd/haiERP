package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;

/**
 * 产品BOMService接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IEngProductBomService 
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
     * 批量删除产品BOM
     * 
     * @param ids 需要删除的产品BOM主键集合
     * @return 结果
     */
    public int deleteEngProductBomByIds(Long[] ids);

    /**
     * 删除产品BOM信息
     * 
     * @param id 产品BOM主键
     * @return 结果
     */
    public int deleteEngProductBomById(Long id);


}
