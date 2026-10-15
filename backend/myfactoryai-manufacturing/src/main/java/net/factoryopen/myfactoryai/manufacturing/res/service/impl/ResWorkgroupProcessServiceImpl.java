package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupProcessMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupProcess;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupProcessService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班组工序Service业务层处理
 * 
 * @author admin
 * @date 2025-01-02
 */
@Service
public class ResWorkgroupProcessServiceImpl implements IResWorkgroupProcessService 
{
    @Autowired(required = false)
    private ResWorkgroupProcessMapper resWorkgroupProcessMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班组工序
     * 
     * @param id 班组工序主键
     * @return 班组工序
     */
    @Override
    public ResWorkgroupProcess selectResWorkgroupProcessById(Long id)
    {
        return resWorkgroupProcessMapper.selectResWorkgroupProcessById(id);
    }

    /**
     * 查询班组工序列表
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 班组工序
     */
    @Override
    public List<ResWorkgroupProcess> selectResWorkgroupProcessList(ResWorkgroupProcess resWorkgroupProcess)
    {
        return resWorkgroupProcessMapper.selectResWorkgroupProcessList(resWorkgroupProcess);
    }

    /**
     * 新增班组工序
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 结果
     */
    @Override
    public int insertResWorkgroupProcess(ResWorkgroupProcess resWorkgroupProcess)
    {
        resWorkgroupProcess.setCreateTime(DateUtils.getNowDate());
        return resWorkgroupProcessMapper.insertResWorkgroupProcess(resWorkgroupProcess);
    }

    /**
     * 修改班组工序
     * 
     * @param resWorkgroupProcess 班组工序
     * @return 结果
     */
    @Override
    public int updateResWorkgroupProcess(ResWorkgroupProcess resWorkgroupProcess)
    {
        resWorkgroupProcess.setUpdateTime(DateUtils.getNowDate());
        return resWorkgroupProcessMapper.updateResWorkgroupProcess(resWorkgroupProcess);
    }

    /**
     * 批量删除班组工序
     * 
     * @param ids 需要删除的班组工序主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupProcessByIds(Long[] ids)
    {
        return resWorkgroupProcessMapper.deleteResWorkgroupProcessByIds(ids);
    }

    /**
     * 删除班组工序信息
     * 
     * @param id 班组工序主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupProcessById(Long id)
    {
        return resWorkgroupProcessMapper.deleteResWorkgroupProcessById(id);
    }


}
