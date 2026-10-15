package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInventory;

/**
 * 物料存货维Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialInventoryService 
{
    /**
     * 查询物料存货维
     * 
     * @param id 物料存货维主键
     * @return 物料存货维
     */
    public EngMaterialInventory selectEngMaterialInventoryById(Long id);

    /**
     * 查询物料存货维列表
     * 
     * @param engMaterialInventory 物料存货维
     * @return 物料存货维集合
     */
    public List<EngMaterialInventory> selectEngMaterialInventoryList(EngMaterialInventory engMaterialInventory);

    /**
     * 新增物料存货维
     * 
     * @param engMaterialInventory 物料存货维
     * @return 结果
     */
    public int insertEngMaterialInventory(EngMaterialInventory engMaterialInventory);

    /**
     * 修改物料存货维
     * 
     * @param engMaterialInventory 物料存货维
     * @return 结果
     */
    public int updateEngMaterialInventory(EngMaterialInventory engMaterialInventory);

    /**
     * 批量删除物料存货维
     * 
     * @param ids 需要删除的物料存货维主键集合
     * @return 结果
     */
    public int deleteEngMaterialInventoryByIds(Long[] ids);

    /**
     * 删除物料存货维信息
     * 
     * @param id 物料存货维主键
     * @return 结果
     */
    public int deleteEngMaterialInventoryById(Long id);


}
