package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveInventory;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomAchieveInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存货现Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomAchieveInventoryServiceImpl implements IHomAchieveInventoryService {
    @Autowired
    private HomAchieveInventoryMapper homAchieveInventoryMapper;

    /**
     * 查询存货现
     *
     * @param id 存货现主键
     * @return 存货现
     */
    @Override
    public HomAchieveInventory selectHomAchieveInventoryById(Long id) {
        return homAchieveInventoryMapper.selectHomAchieveInventoryById(id);
    }

    /**
     * 查询存货现列表
     *
     * @param homAchieveInventory 存货现
     * @return 存货现
     */
    @Override
    public List<HomAchieveInventory> selectHomAchieveInventoryList(HomAchieveInventory homAchieveInventory) {
        return homAchieveInventoryMapper.selectHomAchieveInventoryList(homAchieveInventory);
    }

    /**
     * 新增存货现
     *
     * @param homAchieveInventory 存货现
     * @return 结果
     */
    @Override
    public int insertHomAchieveInventory(HomAchieveInventory homAchieveInventory) {
        return homAchieveInventoryMapper.insertHomAchieveInventory(homAchieveInventory);
    }

    /**
     * 修改存货现
     *
     * @param homAchieveInventory 存货现
     * @return 结果
     */
    @Override
    public int updateHomAchieveInventory(HomAchieveInventory homAchieveInventory) {
        return homAchieveInventoryMapper.updateHomAchieveInventory(homAchieveInventory);
    }

    /**
     * 批量删除存货现
     *
     * @param ids 需要删除的存货现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveInventoryByIds(Long[] ids) {
        return homAchieveInventoryMapper.deleteHomAchieveInventoryByIds(ids);
    }

    /**
     * 删除存货现信息
     *
     * @param id 存货现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveInventoryById(Long id) {
        return homAchieveInventoryMapper.deleteHomAchieveInventoryById(id);
    }
}
