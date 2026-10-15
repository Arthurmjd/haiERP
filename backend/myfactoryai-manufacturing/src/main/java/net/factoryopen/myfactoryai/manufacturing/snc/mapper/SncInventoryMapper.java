package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventory;

/**
 * 仓库存货详单Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-21
 */
public interface SncInventoryMapper 
{
    /**
     * 查询仓库存货详单
     * 
     * @param id 仓库存货详单主键
     * @return 仓库存货详单
     */
    public SncInventory selectSncInventoryById(Long id);
    /**
     * 查询仓库存货详单
     *
     * @param id 仓库存货详单主键
     * @return 仓库存货详单
     */
    public List<SncInventory> selectSncInventoryListByPositionAndMaterial(SncInventory sncInventory);

    /**
     * 查询仓库存货详单列表
     * 
     * @param sncInventory 仓库存货详单
     * @return 仓库存货详单集合
     */
    public List<SncInventory> selectSncInventoryList(SncInventory sncInventory);

    /**
     * 查询仓库存货详单列表
     *
     * @param houseId 仓库
     * @return 仓库存货详单集合
     */
    public List<SncInventory> selectSncInventoryListByHouseId(Long houseId);

    /**
     * 查询仓库存货详单列表
     *
     * @param positionId 储位
     * @return 仓库存货详单集合
     */
    public List<SncInventory> selectSncInventoryListByPositionId(Long positionId);

    /**
     * 查询仓库存货详单列表
     *
     * @param materialId 物料编码
     * @return 仓库存货详单集合
     */
    public List<SncInventory> selectSncInventoryListByMaterialId(Long materialId);

    /**
     * 新增仓库存货详单
     * 
     * @param sncInventory 仓库存货详单
     * @return 结果
     */
    public int insertSncInventory(SncInventory sncInventory);

    /**
     * 修改仓库存货详单
     * 
     * @param sncInventory 仓库存货详单
     * @return 结果
     */
    public int updateSncInventory(SncInventory sncInventory);

    /**
     * 删除仓库存货详单
     * 
     * @param id 仓库存货详单主键
     * @return 结果
     */
    public int deleteSncInventoryById(Long id);

    /**
     * 批量删除仓库存货详单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncInventoryByIds(Long[] ids);

    /**
     * 查询仓库存货详单列表
     *
     * @param houseId 仓库
     * @return 仓库存货详单集合
     */
    public int deleteSncInventoryByHouseId(Long houseId);

    /**
     * 查询仓库存货详单列表
     *
     * @param positionId 储位
     * @return 仓库存货详单集合
     */
    public int deleteSncInventoryByPositionId(Long positionId);

    /**
     * 查询仓库存货详单列表
     *
     * @param materialId 物料编码
     * @return 仓库存货详单集合
     */
    public int deleteSncInventoryByMaterialId(Long materialId);
}
