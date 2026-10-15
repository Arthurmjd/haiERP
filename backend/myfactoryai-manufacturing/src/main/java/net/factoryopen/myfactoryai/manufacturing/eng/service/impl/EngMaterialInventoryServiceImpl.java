package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInventoryMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInventory;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialInventoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料存货维Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialInventoryServiceImpl implements IEngMaterialInventoryService 
{
    @Autowired(required = false)
    private EngMaterialInventoryMapper engMaterialInventoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料存货维
     * 
     * @param id 物料存货维主键
     * @return 物料存货维
     */
    @Override
    public EngMaterialInventory selectEngMaterialInventoryById(Long id)
    {
        return engMaterialInventoryMapper.selectEngMaterialInventoryById(id);
    }

    /**
     * 查询物料存货维列表
     * 
     * @param engMaterialInventory 物料存货维
     * @return 物料存货维
     */
    @Override
    public List<EngMaterialInventory> selectEngMaterialInventoryList(EngMaterialInventory engMaterialInventory)
    {
        return engMaterialInventoryMapper.selectEngMaterialInventoryList(engMaterialInventory);
    }

    /**
     * 新增物料存货维
     * 
     * @param engMaterialInventory 物料存货维
     * @return 结果
     */
    @Override
    public int insertEngMaterialInventory(EngMaterialInventory engMaterialInventory)
    {
        //创建戳
        engMaterialInventory.setCreateBy(SecurityUtils.getUsername());
        engMaterialInventory.setCreateTime(DateUtils.getNowDate());
        engMaterialInventory.setUpdateBy(SecurityUtils.getUsername());
        engMaterialInventory.setUpdateTime(DateUtils.getNowDate());
        return engMaterialInventoryMapper.insertEngMaterialInventory(engMaterialInventory);
    }

    /**
     * 修改物料存货维
     * 
     * @param engMaterialInventory 物料存货维
     * @return 结果
     */
    @Override
    public int updateEngMaterialInventory(EngMaterialInventory engMaterialInventory)
    {
        engMaterialInventory.setUpdateTime(DateUtils.getNowDate());
        return engMaterialInventoryMapper.updateEngMaterialInventory(engMaterialInventory);
    }

    /**
     * 批量删除物料存货维
     * 
     * @param ids 需要删除的物料存货维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialInventoryByIds(Long[] ids)
    {
        return engMaterialInventoryMapper.deleteEngMaterialInventoryByIds(ids);
    }

    /**
     * 删除物料存货维信息
     * 
     * @param id 物料存货维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialInventoryById(Long id)
    {
        return engMaterialInventoryMapper.deleteEngMaterialInventoryById(id);
    }


}
