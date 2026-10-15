package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResShiftsysMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShiftsys;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResShiftsysService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班制管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
@Service
public class ResShiftsysServiceImpl implements IResShiftsysService 
{
    @Autowired(required = false)
    private ResShiftsysMapper resShiftsysMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班制管理
     * 
     * @param id 班制管理主键
     * @return 班制管理
     */
    @Override
    public ResShiftsys selectResShiftsysById(Long id)
    {
        return resShiftsysMapper.selectResShiftsysById(id);
    }

    /**
     * 查询班制管理列表
     * 
     * @param resShiftsys 班制管理
     * @return 班制管理
     */
    @Override
    public List<ResShiftsys> selectResShiftsysList(ResShiftsys resShiftsys)
    {
        return resShiftsysMapper.selectResShiftsysList(resShiftsys);
    }

    /**
     * 新增班制管理
     * 
     * @param resShiftsys 班制管理
     * @return 结果
     */
    @Override
    public int insertResShiftsys(ResShiftsys resShiftsys)
    {
        resShiftsys.setCreateTime(DateUtils.getNowDate());
        return resShiftsysMapper.insertResShiftsys(resShiftsys);
    }

    /**
     * 修改班制管理
     * 
     * @param resShiftsys 班制管理
     * @return 结果
     */
    @Override
    public int updateResShiftsys(ResShiftsys resShiftsys)
    {
        resShiftsys.setUpdateTime(DateUtils.getNowDate());
        return resShiftsysMapper.updateResShiftsys(resShiftsys);
    }

    /**
     * 批量删除班制管理
     * 
     * @param ids 需要删除的班制管理主键
     * @return 结果
     */
    @Override
    public int deleteResShiftsysByIds(Long[] ids)
    {
        return resShiftsysMapper.deleteResShiftsysByIds(ids);
    }

    /**
     * 删除班制管理信息
     * 
     * @param id 班制管理主键
     * @return 结果
     */
    @Override
    public int deleteResShiftsysById(Long id)
    {
        return resShiftsysMapper.deleteResShiftsysById(id);
    }


}
