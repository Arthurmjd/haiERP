package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlSopMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlSop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlSopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次SOPService业务层处理
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public class PlnMlSopServiceImpl implements IPlnMlSopService 
{
    @Autowired(required = false)
    private PlnMlSopMapper plnMlSopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次SOP
     * 
     * @param id 批次SOP主键
     * @return 批次SOP
     */
    @Override
    public PlnMlSop selectPlnMlSopById(Long id)
    {
        return plnMlSopMapper.selectPlnMlSopById(id);
    }

    /**
     * 查询批次SOP列表
     * 
     * @param plnMlSop 批次SOP
     * @return 批次SOP
     */
    @Override
    public List<PlnMlSop> selectPlnMlSopList(PlnMlSop plnMlSop)
    {
        return plnMlSopMapper.selectPlnMlSopList(plnMlSop);
    }

    /**
     * 新增批次SOP
     * 
     * @param plnMlSop 批次SOP
     * @return 结果
     */
    @Override
    public int insertPlnMlSop(PlnMlSop plnMlSop)
    {
        plnMlSop.setCreateTime(DateUtils.getNowDate());
        return plnMlSopMapper.insertPlnMlSop(plnMlSop);
    }

    /**
     * 修改批次SOP
     * 
     * @param plnMlSop 批次SOP
     * @return 结果
     */
    @Override
    public int updatePlnMlSop(PlnMlSop plnMlSop)
    {
        plnMlSop.setUpdateTime(DateUtils.getNowDate());
        return plnMlSopMapper.updatePlnMlSop(plnMlSop);
    }

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的批次SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnMlSopByIds(Long[] ids)
    {
        return plnMlSopMapper.deletePlnMlSopByIds(ids);
    }

    /**
     * 删除批次SOP信息
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnMlSopById(Long id)
    {
        return plnMlSopMapper.deletePlnMlSopById(id);
    }


}
