package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialProductionMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialProduction;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialProductionService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料生产维Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialProductionServiceImpl implements IEngMaterialProductionService 
{
    @Autowired(required = false)
    private EngMaterialProductionMapper engMaterialProductionMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料生产维
     * 
     * @param id 物料生产维主键
     * @return 物料生产维
     */
    @Override
    public EngMaterialProduction selectEngMaterialProductionById(Long id)
    {
        return engMaterialProductionMapper.selectEngMaterialProductionById(id);
    }

    /**
     * 查询物料生产维列表
     * 
     * @param engMaterialProduction 物料生产维
     * @return 物料生产维
     */
    @Override
    public List<EngMaterialProduction> selectEngMaterialProductionList(EngMaterialProduction engMaterialProduction)
    {
        return engMaterialProductionMapper.selectEngMaterialProductionList(engMaterialProduction);
    }

    /**
     * 新增物料生产维
     * 
     * @param engMaterialProduction 物料生产维
     * @return 结果
     */
    @Override
    public int insertEngMaterialProduction(EngMaterialProduction engMaterialProduction)
    {
        //创建戳
        engMaterialProduction.setCreateBy(SecurityUtils.getUsername());
        engMaterialProduction.setCreateTime(DateUtils.getNowDate());
        engMaterialProduction.setUpdateBy(SecurityUtils.getUsername());
        engMaterialProduction.setUpdateTime(DateUtils.getNowDate());
        return engMaterialProductionMapper.insertEngMaterialProduction(engMaterialProduction);
    }

    /**
     * 修改物料生产维
     * 
     * @param engMaterialProduction 物料生产维
     * @return 结果
     */
    @Override
    public int updateEngMaterialProduction(EngMaterialProduction engMaterialProduction)
    {
        engMaterialProduction.setUpdateTime(DateUtils.getNowDate());
        return engMaterialProductionMapper.updateEngMaterialProduction(engMaterialProduction);
    }

    /**
     * 批量删除物料生产维
     * 
     * @param ids 需要删除的物料生产维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialProductionByIds(Long[] ids)
    {
        return engMaterialProductionMapper.deleteEngMaterialProductionByIds(ids);
    }

    /**
     * 删除物料生产维信息
     * 
     * @param id 物料生产维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialProductionById(Long id)
    {
        return engMaterialProductionMapper.deleteEngMaterialProductionById(id);
    }


}
