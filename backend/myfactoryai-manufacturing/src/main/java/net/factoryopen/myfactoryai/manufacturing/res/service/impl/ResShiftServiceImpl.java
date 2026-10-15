package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResShiftMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShift;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResShiftService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班别Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
@Service
public class ResShiftServiceImpl implements IResShiftService 
{
    @Autowired(required = false)
    private ResShiftMapper resShiftMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班别
     * 
     * @param id 班别主键
     * @return 班别
     */
    @Override
    public ResShift selectResShiftById(Long id)
    {
        return resShiftMapper.selectResShiftById(id);
    }

    /**
     * 查询班别列表
     * 
     * @param resShift 班别
     * @return 班别
     */
    @Override
    public List<ResShift> selectResShiftList(ResShift resShift)
    {
        return resShiftMapper.selectResShiftList(resShift);
    }

    /**
     * 新增班别
     * 
     * @param resShift 班别
     * @return 结果
     */
    @Override
    public int insertResShift(ResShift resShift)
    {
        resShift.setCreateTime(DateUtils.getNowDate());
        return resShiftMapper.insertResShift(resShift);
    }

    /**
     * 修改班别
     * 
     * @param resShift 班别
     * @return 结果
     */
    @Override
    public int updateResShift(ResShift resShift)
    {
        resShift.setUpdateTime(DateUtils.getNowDate());
        return resShiftMapper.updateResShift(resShift);
    }

    /**
     * 批量删除班别
     * 
     * @param ids 需要删除的班别主键
     * @return 结果
     */
    @Override
    public int deleteResShiftByIds(Long[] ids)
    {
        return resShiftMapper.deleteResShiftByIds(ids);
    }

    /**
     * 删除班别信息
     * 
     * @param id 班别主键
     * @return 结果
     */
    @Override
    public int deleteResShiftById(Long id)
    {
        return resShiftMapper.deleteResShiftById(id);
    }


}
