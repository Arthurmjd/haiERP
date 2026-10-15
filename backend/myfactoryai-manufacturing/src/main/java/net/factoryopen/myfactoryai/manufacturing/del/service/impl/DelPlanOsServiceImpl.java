package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanOsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanOs;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanOsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外协计划Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelPlanOsServiceImpl implements IDelPlanOsService 
{
    @Autowired(required = false)
    private DelPlanOsMapper delPlanOsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询外协计划
     * 
     * @param id 外协计划主键
     * @return 外协计划
     */
    @Override
    public DelPlanOs selectDelPlanOsById(Long id)
    {
        return delPlanOsMapper.selectDelPlanOsById(id);
    }

    /**
     * 查询外协计划列表
     * 
     * @param delPlanOs 外协计划
     * @return 外协计划
     */
    @Override
    public List<DelPlanOs> selectDelPlanOsList(DelPlanOs delPlanOs)
    {
        return delPlanOsMapper.selectDelPlanOsList(delPlanOs);
    }

    /**
     * 新增外协计划
     * 
     * @param delPlanOs 外协计划
     * @return 结果
     */
    @Override
    public int insertDelPlanOs(DelPlanOs delPlanOs)
    {
        //创建戳
        delPlanOs.setCreateBy(SecurityUtils.getUsername());
        delPlanOs.setCreateTime(DateUtils.getNowDate());
        delPlanOs.setUpdateBy(SecurityUtils.getUsername());
        delPlanOs.setUpdateTime(DateUtils.getNowDate());
        return delPlanOsMapper.insertDelPlanOs(delPlanOs);
    }

    /**
     * 修改外协计划
     * 
     * @param delPlanOs 外协计划
     * @return 结果
     */
    @Override
    public int updateDelPlanOs(DelPlanOs delPlanOs)
    {
        delPlanOs.refreshUpdatingStamp();
        return delPlanOsMapper.updateDelPlanOs(delPlanOs);
    }

    /**
     * 批量删除外协计划
     * 
     * @param ids 需要删除的外协计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanOsByIds(Long[] ids)
    {
        return delPlanOsMapper.deleteDelPlanOsByIds(ids);
    }

    /**
     * 删除外协计划信息
     * 
     * @param id 外协计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanOsById(Long id)
    {
        return delPlanOsMapper.deleteDelPlanOsById(id);
    }


}
