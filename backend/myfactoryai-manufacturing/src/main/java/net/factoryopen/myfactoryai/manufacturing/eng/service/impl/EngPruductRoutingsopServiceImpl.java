package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductRoutingsopMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingsop;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductRoutingsopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品SOPService业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngPruductRoutingsopServiceImpl implements IEngPruductRoutingsopService 
{
    @Autowired(required = false)
    private EngPruductRoutingsopMapper engPruductRoutingsopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品SOP
     * 
     * @param id 产品SOP主键
     * @return 产品SOP
     */
    @Override
    public EngPruductRoutingsop selectEngPruductRoutingsopById(Long id)
    {
        return engPruductRoutingsopMapper.selectEngPruductRoutingsopById(id);
    }

    /**
     * 查询产品SOP列表
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 产品SOP
     */
    @Override
    public List<EngPruductRoutingsop> selectEngPruductRoutingsopList(EngPruductRoutingsop engPruductRoutingsop)
    {
        return engPruductRoutingsopMapper.selectEngPruductRoutingsopList(engPruductRoutingsop);
    }

    /**
     * 新增产品SOP
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 结果
     */
    @Override
    public int insertEngPruductRoutingsop(EngPruductRoutingsop engPruductRoutingsop)
    {
        //创建戳
        engPruductRoutingsop.setCreateBy(SecurityUtils.getUsername());
        engPruductRoutingsop.setCreateTime(DateUtils.getNowDate());
        engPruductRoutingsop.setUpdateBy(SecurityUtils.getUsername());
        engPruductRoutingsop.setUpdateTime(DateUtils.getNowDate());
        return engPruductRoutingsopMapper.insertEngPruductRoutingsop(engPruductRoutingsop);
    }

    /**
     * 修改产品SOP
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 结果
     */
    @Override
    public int updateEngPruductRoutingsop(EngPruductRoutingsop engPruductRoutingsop)
    {
        engPruductRoutingsop.setUpdateTime(DateUtils.getNowDate());
        return engPruductRoutingsopMapper.updateEngPruductRoutingsop(engPruductRoutingsop);
    }

    /**
     * 批量删除产品SOP
     * 
     * @param ids 需要删除的产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductRoutingsopByIds(Long[] ids)
    {
        return engPruductRoutingsopMapper.deleteEngPruductRoutingsopByIds(ids);
    }

    /**
     * 删除产品SOP信息
     * 
     * @param id 产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductRoutingsopById(Long id)
    {
        return engPruductRoutingsopMapper.deleteEngPruductRoutingsopById(id);
    }


}
