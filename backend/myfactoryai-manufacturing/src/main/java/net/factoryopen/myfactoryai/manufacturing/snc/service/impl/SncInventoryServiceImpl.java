package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventory;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 仓库存货详单Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-21
 */
@Service
public class SncInventoryServiceImpl implements ISncInventoryService 
{
    @Autowired(required = false)
    private SncInventoryMapper sncInventoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询仓库存货详单
     * 
     * @param id 仓库存货详单主键
     * @return 仓库存货详单
     */
    @Override
    public SncInventory selectSncInventoryById(Long id)
    {
        return sncInventoryMapper.selectSncInventoryById(id);
    }

    /**
     * 查询仓库存货详单列表
     * 
     * @param sncInventory 仓库存货详单
     * @return 仓库存货详单
     */
    @Override
    public List<SncInventory> selectSncInventoryList(SncInventory sncInventory)
    {
        return sncInventoryMapper.selectSncInventoryList(sncInventory);
    }

    /**
     * 新增仓库存货详单
     * 
     * @param sncInventory 仓库存货详单
     * @return 结果
     */
    @Override
    public int insertSncInventory(SncInventory sncInventory)
    {
        sncInventory.setCreateTime(DateUtils.getNowDate());
        return sncInventoryMapper.insertSncInventory(sncInventory);
    }

    /**
     * 修改仓库存货详单
     * 
     * @param sncInventory 仓库存货详单
     * @return 结果
     */
    @Override
    public int updateSncInventory(SncInventory sncInventory)
    {
        sncInventory.setUpdateTime(DateUtils.getNowDate());
        return sncInventoryMapper.updateSncInventory(sncInventory);
    }

    /**
     * 批量删除仓库存货详单
     * 
     * @param ids 需要删除的仓库存货详单主键
     * @return 结果
     */
    @Override
    public int deleteSncInventoryByIds(Long[] ids)
    {
        return sncInventoryMapper.deleteSncInventoryByIds(ids);
    }

    /**
     * 删除仓库存货详单信息
     * 
     * @param id 仓库存货详单主键
     * @return 结果
     */
    @Override
    public int deleteSncInventoryById(Long id)
    {
        return sncInventoryMapper.deleteSncInventoryById(id);
    }


}
