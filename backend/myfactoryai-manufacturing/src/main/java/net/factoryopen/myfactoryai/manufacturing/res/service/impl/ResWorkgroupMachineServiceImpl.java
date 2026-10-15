package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupMachineMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupMachine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupMachineService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班组机台Service业务层处理
 * 
 * @author admin
 * @date 2025-01-02
 */
@Service
public class ResWorkgroupMachineServiceImpl implements IResWorkgroupMachineService 
{
    @Autowired(required = false)
    private ResWorkgroupMachineMapper resWorkgroupMachineMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班组机台
     * 
     * @param id 班组机台主键
     * @return 班组机台
     */
    @Override
    public ResWorkgroupMachine selectResWorkgroupMachineById(Long id)
    {
        return resWorkgroupMachineMapper.selectResWorkgroupMachineById(id);
    }

    /**
     * 查询班组机台列表
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 班组机台
     */
    @Override
    public List<ResWorkgroupMachine> selectResWorkgroupMachineList(ResWorkgroupMachine resWorkgroupMachine)
    {
        return resWorkgroupMachineMapper.selectResWorkgroupMachineList(resWorkgroupMachine);
    }

    /**
     * 查询班组机台列表
     *
     * @param groupId 班组Id
     * @return 班组机台
     */
    @Override
    public List<ResWorkgroupMachine> selectStartableMachineListByGroupId(Long groupId)
    {
        return resWorkgroupMachineMapper.selectStartableMachineListByGroupId(groupId);
    }

    /**
     * 新增班组机台
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 结果
     */
    @Override
    public int insertResWorkgroupMachine(ResWorkgroupMachine resWorkgroupMachine)
    {
        resWorkgroupMachine.setCreateTime(DateUtils.getNowDate());
        return resWorkgroupMachineMapper.insertResWorkgroupMachine(resWorkgroupMachine);
    }

    /**
     * 修改班组机台
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 结果
     */
    @Override
    public int updateResWorkgroupMachine(ResWorkgroupMachine resWorkgroupMachine)
    {
        resWorkgroupMachine.setUpdateTime(DateUtils.getNowDate());
        return resWorkgroupMachineMapper.updateResWorkgroupMachine(resWorkgroupMachine);
    }

    /**
     * 批量删除班组机台
     * 
     * @param ids 需要删除的班组机台主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupMachineByIds(Long[] ids)
    {
        return resWorkgroupMachineMapper.deleteResWorkgroupMachineByIds(ids);
    }

    /**
     * 删除班组机台信息
     * 
     * @param id 班组机台主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupMachineById(Long id)
    {
        return resWorkgroupMachineMapper.deleteResWorkgroupMachineById(id);
    }


}
