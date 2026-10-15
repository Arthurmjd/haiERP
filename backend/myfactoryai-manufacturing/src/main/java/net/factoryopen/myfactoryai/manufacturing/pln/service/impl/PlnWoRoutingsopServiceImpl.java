package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingsopMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingsopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单SOPService业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class PlnWoRoutingsopServiceImpl implements IPlnWoRoutingsopService 
{
    @Autowired(required = false)
    private PlnWoRoutingsopMapper plnWoRoutingsopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单SOP
     * 
     * @param id 工单SOP主键
     * @return 工单SOP
     */
    @Override
    public PlnWoRoutingsop selectPlnWoRoutingsopById(Long id)
    {
        return plnWoRoutingsopMapper.selectPlnWoRoutingsopById(id);
    }

    /**
     * 查询工单SOP列表
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 工单SOP
     */
    @Override
    public List<PlnWoRoutingsop> selectPlnWoRoutingsopList(PlnWoRoutingsop plnWoRoutingsop)
    {
        return plnWoRoutingsopMapper.selectPlnWoRoutingsopList(plnWoRoutingsop);
    }

    /**
     * 新增工单SOP
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 结果
     */
    @Override
    public int insertPlnWoRoutingsop(PlnWoRoutingsop plnWoRoutingsop)
    {
        plnWoRoutingsop.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingsopMapper.insertPlnWoRoutingsop(plnWoRoutingsop);
    }

    /**
     * 修改工单SOP
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 结果
     */
    @Override
    public int updatePlnWoRoutingsop(PlnWoRoutingsop plnWoRoutingsop)
    {
        plnWoRoutingsop.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingsopMapper.updatePlnWoRoutingsop(plnWoRoutingsop);
    }

    /**
     * 批量删除工单SOP
     * 
     * @param ids 需要删除的工单SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingsopByIds(Long[] ids)
    {
        return plnWoRoutingsopMapper.deletePlnWoRoutingsopByIds(ids);
    }

    /**
     * 删除工单SOP信息
     * 
     * @param id 工单SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingsopById(Long id)
    {
        return plnWoRoutingsopMapper.deletePlnWoRoutingsopById(id);
    }


}
