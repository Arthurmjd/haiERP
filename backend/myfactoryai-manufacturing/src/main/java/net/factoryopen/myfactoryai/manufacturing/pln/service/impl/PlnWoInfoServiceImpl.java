package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoInfoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoInfo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoInfoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单信息Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class PlnWoInfoServiceImpl implements IPlnWoInfoService 
{
    @Autowired(required = false)
    private PlnWoInfoMapper plnWoInfoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单信息
     * 
     * @param id 工单信息主键
     * @return 工单信息
     */
    @Override
    public PlnWoInfo selectPlnWoInfoById(Long id)
    {
        return plnWoInfoMapper.selectPlnWoInfoById(id);
    }

    /**
     * 查询工单信息列表
     * 
     * @param plnWoInfo 工单信息
     * @return 工单信息
     */
    @Override
    public List<PlnWoInfo> selectPlnWoInfoList(PlnWoInfo plnWoInfo)
    {
        return plnWoInfoMapper.selectPlnWoInfoList(plnWoInfo);
    }

    /**
     * 新增工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    @Override
    public int insertPlnWoInfo(PlnWoInfo plnWoInfo)
    {
        plnWoInfo.setCreateTime(DateUtils.getNowDate());
        return plnWoInfoMapper.insertPlnWoInfo(plnWoInfo);
    }

    /**
     * 修改工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    @Override
    public int updatePlnWoInfo(PlnWoInfo plnWoInfo)
    {
        plnWoInfo.setUpdateTime(DateUtils.getNowDate());
        return plnWoInfoMapper.updatePlnWoInfo(plnWoInfo);
    }

    /**
     * 批量删除工单信息
     * 
     * @param ids 需要删除的工单信息主键
     * @return 结果
     */
    @Override
    public int deletePlnWoInfoByIds(Long[] ids)
    {
        return plnWoInfoMapper.deletePlnWoInfoByIds(ids);
    }

    /**
     * 删除工单信息信息
     * 
     * @param id 工单信息主键
     * @return 结果
     */
    @Override
    public int deletePlnWoInfoById(Long id)
    {
        return plnWoInfoMapper.deletePlnWoInfoById(id);
    }


}
