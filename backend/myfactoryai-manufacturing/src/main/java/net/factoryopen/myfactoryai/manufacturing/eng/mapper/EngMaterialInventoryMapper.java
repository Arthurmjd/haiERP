package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInventory;

/**
 * 物料存货维Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface EngMaterialInventoryMapper 
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
     * 查询物料存货维列表
     *
     * @param materialId 物料编码
     * @return 物料存货维集合
     */
    public EngMaterialInventory selectEngMaterialInventoryListByMaterialId(Long materialId);

    /**
     * 查询物料存货维列表
     *
     * @param houseId 主仓库
     * @return 物料存货维集合
     */
    public List<EngMaterialInventory> selectEngMaterialInventoryListByHouseId(Long houseId);

    /**
     * 查询物料存货维列表
     *
     * @param positionId 主储位
     * @return 物料存货维集合
     */
    public List<EngMaterialInventory> selectEngMaterialInventoryListByPositionId(Long positionId);

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
     * 删除物料存货维
     * 
     * @param id 物料存货维主键
     * @return 结果
     */
    public int deleteEngMaterialInventoryById(Long id);

    /**
     * 批量删除物料存货维
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialInventoryByIds(Long[] ids);

    /**
     * 查询物料存货维列表
     *
     * @param materialId 物料编码
     * @return 物料存货维集合
     */
    public int deleteEngMaterialInventoryByMaterialId(Long materialId);

    /**
     * 查询物料存货维列表
     *
     * @param houseId 主仓库
     * @return 物料存货维集合
     */
    public int deleteEngMaterialInventoryByHouseId(Long houseId);

    /**
     * 查询物料存货维列表
     *
     * @param positionId 主储位
     * @return 物料存货维集合
     */
    public int deleteEngMaterialInventoryByPositionId(Long positionId);
}
