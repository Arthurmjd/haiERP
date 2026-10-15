package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingsopMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingsopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次SOPService业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingsopServiceImpl implements IPlnMlRoutingsopService 
{
    @Autowired(required = false)
    private PlnMlRoutingsopMapper plnMlRoutingsopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次SOP
     * 
     * @param id 批次SOP主键
     * @return 批次SOP
     */
    @Override
    public PlnMlRoutingsop selectPlnMlRoutingsopById(Long id)
    {
        return plnMlRoutingsopMapper.selectPlnMlRoutingsopById(id);
    }

    /**
     * 查询批次SOP列表
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 批次SOP
     */
    @Override
    public List<PlnMlRoutingsop> selectPlnMlRoutingsopList(PlnMlRoutingsop plnMlRoutingsop)
    {
        return plnMlRoutingsopMapper.selectPlnMlRoutingsopList(plnMlRoutingsop);
    }

    /**
     * 新增批次SOP
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 结果
     */
    @Override
    public int insertPlnMlRoutingsop(PlnMlRoutingsop plnMlRoutingsop)
    {
        plnMlRoutingsop.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingsopMapper.insertPlnMlRoutingsop(plnMlRoutingsop);
    }

    /**
     * 修改批次SOP
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 结果
     */
    @Override
    public int updatePlnMlRoutingsop(PlnMlRoutingsop plnMlRoutingsop)
    {
        plnMlRoutingsop.refreshUpdatingStamp();
        return plnMlRoutingsopMapper.updatePlnMlRoutingsop(plnMlRoutingsop);
    }

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的批次SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingsopByIds(Long[] ids)
    {
        return plnMlRoutingsopMapper.deletePlnMlRoutingsopByIds(ids);
    }

    /**
     * 删除批次SOP信息
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingsopById(Long id)
    {
        return plnMlRoutingsopMapper.deletePlnMlRoutingsopById(id);
    }


}
