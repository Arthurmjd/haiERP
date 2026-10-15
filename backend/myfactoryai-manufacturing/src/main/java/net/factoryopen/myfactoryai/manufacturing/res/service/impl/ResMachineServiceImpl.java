package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResMachineMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResMachineService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 机台管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public class ResMachineServiceImpl implements IResMachineService 
{
    @Autowired(required = false)
    private ResMachineMapper resMachineMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询机台管理
     * 
     * @param id 机台管理主键
     * @return 机台管理
     */
    @Override
    public ResMachine selectResMachineById(Long id)
    {
        return resMachineMapper.selectResMachineById(id);
    }

    /**
     * 查询机台管理列表
     * 
     * @param resMachine 机台管理
     * @return 机台管理
     */
    @Override
    public List<ResMachine> selectResMachineList(ResMachine resMachine)
    {
        return resMachineMapper.selectResMachineList(resMachine);
    }

    /**
     * 新增机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    @Override
    public int insertResMachine(ResMachine resMachine)
    {
        resMachine.setCreateTime(DateUtils.getNowDate());
        return resMachineMapper.insertResMachine(resMachine);
    }

    /**
     * 修改机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    @Override
    public int updateResMachine(ResMachine resMachine)
    {
        resMachine.setUpdateTime(DateUtils.getNowDate());
        return resMachineMapper.updateResMachine(resMachine);
    }

    /**
     * 批量删除机台管理
     * 
     * @param ids 需要删除的机台管理主键
     * @return 结果
     */
    @Override
    public int deleteResMachineByIds(Long[] ids)
    {
        return resMachineMapper.deleteResMachineByIds(ids);
    }

    /**
     * 删除机台管理信息
     * 
     * @param id 机台管理主键
     * @return 结果
     */
    @Override
    public int deleteResMachineById(Long id)
    {
        return resMachineMapper.deleteResMachineById(id);
    }


}
