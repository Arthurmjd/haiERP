package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanMps;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanMpsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 主计划Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelPlanMpsServiceImpl implements IDelPlanMpsService 
{
    @Autowired(required = false)
    private DelPlanMpsMapper delPlanMpsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询主计划
     * 
     * @param id 主计划主键
     * @return 主计划
     */
    @Override
    public DelPlanMps selectDelPlanMpsById(Long id)
    {
        return delPlanMpsMapper.selectDelPlanMpsById(id);
    }

    /**
     * 查询主计划列表
     * 
     * @param delPlanMps 主计划
     * @return 主计划
     */
    @Override
    public List<DelPlanMps> selectDelPlanMpsList(DelPlanMps delPlanMps)
    {
        return delPlanMpsMapper.selectDelPlanMpsList(delPlanMps);
    }

    /**
     * 新增主计划
     * 
     * @param delPlanMps 主计划
     * @return 结果
     */
    @Override
    public int insertDelPlanMps(DelPlanMps delPlanMps)
    {
        //创建戳
        delPlanMps.setCreateBy(SecurityUtils.getUsername());
        delPlanMps.setCreateTime(DateUtils.getNowDate());
        delPlanMps.setUpdateBy(SecurityUtils.getUsername());
        delPlanMps.setUpdateTime(DateUtils.getNowDate());
        return delPlanMpsMapper.insertDelPlanMps(delPlanMps);
    }

    /**
     * 修改主计划
     * 
     * @param delPlanMps 主计划
     * @return 结果
     */
    @Override
    public int updateDelPlanMps(DelPlanMps delPlanMps)
    {
        delPlanMps.refreshUpdatingStamp();
        return delPlanMpsMapper.updateDelPlanMps(delPlanMps);
    }

    /**
     * 批量删除主计划
     * 
     * @param ids 需要删除的主计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanMpsByIds(Long[] ids)
    {
        return delPlanMpsMapper.deleteDelPlanMpsByIds(ids);
    }

    /**
     * 删除主计划信息
     * 
     * @param id 主计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanMpsById(Long id)
    {
        return delPlanMpsMapper.deleteDelPlanMpsById(id);
    }


}
