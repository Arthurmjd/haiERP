package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialProduction;

/**
 * 物料生产维Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface EngMaterialProductionMapper 
{
    /**
     * 查询物料生产维
     * 
     * @param id 物料生产维主键
     * @return 物料生产维
     */
    public EngMaterialProduction selectEngMaterialProductionById(Long id);

    /**
     * 查询物料生产维列表
     * 
     * @param engMaterialProduction 物料生产维
     * @return 物料生产维集合
     */
    public List<EngMaterialProduction> selectEngMaterialProductionList(EngMaterialProduction engMaterialProduction);

    /**
     * 查询物料生产维列表
     *
     * @param materialId 物料编码
     * @return 物料生产维集合
     */
    public List<EngMaterialProduction> selectEngMaterialProductionListByMaterialId(Long materialId);

    /**
     * 新增物料生产维
     * 
     * @param engMaterialProduction 物料生产维
     * @return 结果
     */
    public int insertEngMaterialProduction(EngMaterialProduction engMaterialProduction);

    /**
     * 修改物料生产维
     * 
     * @param engMaterialProduction 物料生产维
     * @return 结果
     */
    public int updateEngMaterialProduction(EngMaterialProduction engMaterialProduction);

    /**
     * 删除物料生产维
     * 
     * @param id 物料生产维主键
     * @return 结果
     */
    public int deleteEngMaterialProductionById(Long id);

    /**
     * 批量删除物料生产维
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialProductionByIds(Long[] ids);

    /**
     * 查询物料生产维列表
     *
     * @param materialId 物料编码
     * @return 物料生产维集合
     */
    public int deleteEngMaterialProductionByMaterialId(Long materialId);
}
