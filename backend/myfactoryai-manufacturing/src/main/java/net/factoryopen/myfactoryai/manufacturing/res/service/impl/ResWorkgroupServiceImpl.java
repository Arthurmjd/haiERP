package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班组管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public class ResWorkgroupServiceImpl implements IResWorkgroupService 
{
    @Autowired(required = false)
    private ResWorkgroupMapper resWorkgroupMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班组管理
     * 
     * @param id 班组管理主键
     * @return 班组管理
     */
    @Override
    public ResWorkgroup selectResWorkgroupById(Long id)
    {
        return resWorkgroupMapper.selectResWorkgroupById(id);
    }

    /**
     * 查询班组管理列表
     * 
     * @param resWorkgroup 班组管理
     * @return 班组管理
     */
    @Override
    public List<ResWorkgroup> selectResWorkgroupList(ResWorkgroup resWorkgroup)
    {
        return resWorkgroupMapper.selectResWorkgroupList(resWorkgroup);
    }

    /**
     * 新增班组管理
     * 
     * @param resWorkgroup 班组管理
     * @return 结果
     */
    @Override
    public int insertResWorkgroup(ResWorkgroup resWorkgroup)
    {
        resWorkgroup.setCreateTime(DateUtils.getNowDate());
        return resWorkgroupMapper.insertResWorkgroup(resWorkgroup);
    }

    /**
     * 修改班组管理
     * 
     * @param resWorkgroup 班组管理
     * @return 结果
     */
    @Override
    public int updateResWorkgroup(ResWorkgroup resWorkgroup)
    {
        resWorkgroup.setUpdateTime(DateUtils.getNowDate());
        return resWorkgroupMapper.updateResWorkgroup(resWorkgroup);
    }

    /**
     * 批量删除班组管理
     * 
     * @param ids 需要删除的班组管理主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupByIds(Long[] ids)
    {
        return resWorkgroupMapper.deleteResWorkgroupByIds(ids);
    }

    /**
     * 删除班组管理信息
     * 
     * @param id 班组管理主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupById(Long id)
    {
        return resWorkgroupMapper.deleteResWorkgroupById(id);
    }


}
