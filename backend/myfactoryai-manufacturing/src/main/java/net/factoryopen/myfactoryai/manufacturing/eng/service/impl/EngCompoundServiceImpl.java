package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCompoundMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCompound;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCompoundService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 材质数据Service业务层处理
 * 
 * @author admin
 * @date 2024-11-29
 */
@Service
public class EngCompoundServiceImpl implements IEngCompoundService 
{
    @Autowired(required = false)
    private EngCompoundMapper engCompoundMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询材质数据
     * 
     * @param id 材质数据主键
     * @return 材质数据
     */
    @Override
    public EngCompound selectEngCompoundById(Long id)
    {
        return engCompoundMapper.selectEngCompoundById(id);
    }

    /**
     * 查询材质数据列表
     * 
     * @param engCompound 材质数据
     * @return 材质数据
     */
    @Override
    public List<EngCompound> selectEngCompoundList(EngCompound engCompound)
    {
        return engCompoundMapper.selectEngCompoundList(engCompound);
    }

    /**
     * 新增材质数据
     * 
     * @param engCompound 材质数据
     * @return 结果
     */
    @Override
    public int insertEngCompound(EngCompound engCompound)
    {
        engCompound.setCreateTime(DateUtils.getNowDate());
        return engCompoundMapper.insertEngCompound(engCompound);
    }

    /**
     * 修改材质数据
     * 
     * @param engCompound 材质数据
     * @return 结果
     */
    @Override
    public int updateEngCompound(EngCompound engCompound)
    {
        engCompound.setUpdateTime(DateUtils.getNowDate());
        return engCompoundMapper.updateEngCompound(engCompound);
    }

    /**
     * 批量删除材质数据
     * 
     * @param ids 需要删除的材质数据主键
     * @return 结果
     */
    @Override
    public int deleteEngCompoundByIds(Long[] ids)
    {
        return engCompoundMapper.deleteEngCompoundByIds(ids);
    }

    /**
     * 删除材质数据信息
     * 
     * @param id 材质数据主键
     * @return 结果
     */
    @Override
    public int deleteEngCompoundById(Long id)
    {
        return engCompoundMapper.deleteEngCompoundById(id);
    }


}
