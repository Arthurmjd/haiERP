package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventory;

/**
 * 仓库存货详单Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-21
 */
public interface ISncInventoryService 
{
    /**
     * 查询仓库存货详单
     * 
     * @param id 仓库存货详单主键
     * @return 仓库存货详单
     */
    public SncInventory selectSncInventoryById(Long id);

    /**
     * 查询仓库存货详单列表
     * 
     * @param sncInventory 仓库存货详单
     * @return 仓库存货详单集合
     */
    public List<SncInventory> selectSncInventoryList(SncInventory sncInventory);

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
     * 批量删除仓库存货详单
     * 
     * @param ids 需要删除的仓库存货详单主键集合
     * @return 结果
     */
    public int deleteSncInventoryByIds(Long[] ids);

    /**
     * 删除仓库存货详单信息
     * 
     * @param id 仓库存货详单主键
     * @return 结果
     */
    public int deleteSncInventoryById(Long id);


}
