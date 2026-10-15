package net.factoryopen.myfactoryai.manufacturing.idx.mapper;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveInventory;

import java.util.List;

/**
 * 存货现Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface HomAchieveInventoryMapper {
    /**
     * 查询存货现
     *
     * @param id 存货现主键
     * @return 存货现
     */
    public HomAchieveInventory selectHomAchieveInventoryById(Long id);

    /**
     * 查询存货现列表
     *
     * @param homAchieveInventory 存货现
     * @return 存货现集合
     */
    public List<HomAchieveInventory> selectHomAchieveInventoryList(HomAchieveInventory homAchieveInventory);

    /**
     * 新增存货现
     *
     * @param homAchieveInventory 存货现
     * @return 结果
     */
    public int insertHomAchieveInventory(HomAchieveInventory homAchieveInventory);

    /**
     * 修改存货现
     *
     * @param homAchieveInventory 存货现
     * @return 结果
     */
    public int updateHomAchieveInventory(HomAchieveInventory homAchieveInventory);

    /**
     * 删除存货现
     *
     * @param id 存货现主键
     * @return 结果
     */
    public int deleteHomAchieveInventoryById(Long id);

    /**
     * 批量删除存货现
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomAchieveInventoryByIds(Long[] ids);
}
