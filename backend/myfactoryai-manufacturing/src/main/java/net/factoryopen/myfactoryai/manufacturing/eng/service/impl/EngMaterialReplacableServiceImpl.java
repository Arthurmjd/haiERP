package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialReplacableMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialReplacable;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialReplacableService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料替代Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialReplacableServiceImpl implements IEngMaterialReplacableService 
{
    @Autowired(required = false)
    private EngMaterialReplacableMapper engMaterialReplacableMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料替代
     * 
     * @param id 物料替代主键
     * @return 物料替代
     */
    @Override
    public EngMaterialReplacable selectEngMaterialReplacableById(Long id)
    {
        return engMaterialReplacableMapper.selectEngMaterialReplacableById(id);
    }

    /**
     * 查询物料替代列表
     * 
     * @param engMaterialReplacable 物料替代
     * @return 物料替代
     */
    @Override
    public List<EngMaterialReplacable> selectEngMaterialReplacableList(EngMaterialReplacable engMaterialReplacable)
    {
        return engMaterialReplacableMapper.selectEngMaterialReplacableList(engMaterialReplacable);
    }

    /**
     * 新增物料替代
     * 
     * @param engMaterialReplacable 物料替代
     * @return 结果
     */
    @Override
    public int insertEngMaterialReplacable(EngMaterialReplacable engMaterialReplacable)
    {
        //创建戳
        engMaterialReplacable.setCreateBy(SecurityUtils.getUsername());
        engMaterialReplacable.setCreateTime(DateUtils.getNowDate());
        engMaterialReplacable.setUpdateBy(SecurityUtils.getUsername());
        engMaterialReplacable.setUpdateTime(DateUtils.getNowDate());
        return engMaterialReplacableMapper.insertEngMaterialReplacable(engMaterialReplacable);
    }

    /**
     * 修改物料替代
     * 
     * @param engMaterialReplacable 物料替代
     * @return 结果
     */
    @Override
    public int updateEngMaterialReplacable(EngMaterialReplacable engMaterialReplacable)
    {
        engMaterialReplacable.setUpdateTime(DateUtils.getNowDate());
        return engMaterialReplacableMapper.updateEngMaterialReplacable(engMaterialReplacable);
    }

    /**
     * 批量删除物料替代
     * 
     * @param ids 需要删除的物料替代主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialReplacableByIds(Long[] ids)
    {
        return engMaterialReplacableMapper.deleteEngMaterialReplacableByIds(ids);
    }

    /**
     * 删除物料替代信息
     * 
     * @param id 物料替代主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialReplacableById(Long id)
    {
        return engMaterialReplacableMapper.deleteEngMaterialReplacableById(id);
    }


}
