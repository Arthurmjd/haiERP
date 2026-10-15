package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoSopMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoSop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoSopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单SOPService业务层处理
 * 
 * @author admin
 * @date 2024-07-12
 */
@Service
public class PlnWoSopServiceImpl implements IPlnWoSopService 
{
    @Autowired(required = false)
    private PlnWoSopMapper plnWoSopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单SOP
     * 
     * @param id 工单SOP主键
     * @return 工单SOP
     */
    @Override
    public PlnWoSop selectPlnWoSopById(Long id)
    {
        return plnWoSopMapper.selectPlnWoSopById(id);
    }

    /**
     * 查询工单SOP列表
     * 
     * @param plnWoSop 工单SOP
     * @return 工单SOP
     */
    @Override
    public List<PlnWoSop> selectPlnWoSopList(PlnWoSop plnWoSop)
    {
        return plnWoSopMapper.selectPlnWoSopList(plnWoSop);
    }

    /**
     * 新增工单SOP
     * 
     * @param plnWoSop 工单SOP
     * @return 结果
     */
    @Override
    public int insertPlnWoSop(PlnWoSop plnWoSop)
    {
        plnWoSop.setCreateTime(DateUtils.getNowDate());
        return plnWoSopMapper.insertPlnWoSop(plnWoSop);
    }

    /**
     * 修改工单SOP
     * 
     * @param plnWoSop 工单SOP
     * @return 结果
     */
    @Override
    public int updatePlnWoSop(PlnWoSop plnWoSop)
    {
        plnWoSop.setUpdateTime(DateUtils.getNowDate());
        return plnWoSopMapper.updatePlnWoSop(plnWoSop);
    }

    /**
     * 批量删除工单SOP
     * 
     * @param ids 需要删除的工单SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnWoSopByIds(Long[] ids)
    {
        return plnWoSopMapper.deletePlnWoSopByIds(ids);
    }

    /**
     * 删除工单SOP信息
     * 
     * @param id 工单SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnWoSopById(Long id)
    {
        return plnWoSopMapper.deletePlnWoSopById(id);
    }


}
