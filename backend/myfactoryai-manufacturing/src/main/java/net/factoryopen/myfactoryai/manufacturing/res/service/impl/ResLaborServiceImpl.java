package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLaborMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResLaborService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工人管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public class ResLaborServiceImpl implements IResLaborService 
{
    @Autowired(required = false)
    private ResLaborMapper resLaborMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工人管理
     * 
     * @param id 工人管理主键
     * @return 工人管理
     */
    @Override
    public ResLabor selectResLaborById(Long id)
    {
        return resLaborMapper.selectResLaborById(id);
    }

    /**
     * 查询工人管理列表
     * 
     * @param resLabor 工人管理
     * @return 工人管理
     */
    @Override
    public List<ResLabor> selectResLaborList(ResLabor resLabor)
    {
        return resLaborMapper.selectResLaborList(resLabor);
    }

    /**
     * 新增工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    @Override
    public int insertResLabor(ResLabor resLabor)
    {
        resLabor.setCreateTime(DateUtils.getNowDate());
        return resLaborMapper.insertResLabor(resLabor);
    }

    /**
     * 修改工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    @Override
    public int updateResLabor(ResLabor resLabor)
    {
        resLabor.setUpdateTime(DateUtils.getNowDate());
        return resLaborMapper.updateResLabor(resLabor);
    }

    /**
     * 批量删除工人管理
     * 
     * @param ids 需要删除的工人管理主键
     * @return 结果
     */
    @Override
    public int deleteResLaborByIds(Long[] ids)
    {
        return resLaborMapper.deleteResLaborByIds(ids);
    }

    /**
     * 删除工人管理信息
     * 
     * @param id 工人管理主键
     * @return 结果
     */
    @Override
    public int deleteResLaborById(Long id)
    {
        return resLaborMapper.deleteResLaborById(id);
    }


}
