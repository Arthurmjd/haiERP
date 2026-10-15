package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialProduction;

/**
 * 物料生产维Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialProductionService 
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
     * 批量删除物料生产维
     * 
     * @param ids 需要删除的物料生产维主键集合
     * @return 结果
     */
    public int deleteEngMaterialProductionByIds(Long[] ids);

    /**
     * 删除物料生产维信息
     * 
     * @param id 物料生产维主键
     * @return 结果
     */
    public int deleteEngMaterialProductionById(Long id);


}
