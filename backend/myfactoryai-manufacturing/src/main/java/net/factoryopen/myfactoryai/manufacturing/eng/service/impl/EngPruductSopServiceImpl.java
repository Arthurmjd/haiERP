package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductSopMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductSop;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductSopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品SOPService业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class EngPruductSopServiceImpl implements IEngPruductSopService 
{
    @Autowired(required = false)
    private EngPruductSopMapper engPruductSopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品SOP
     * 
     * @param id 产品SOP主键
     * @return 产品SOP
     */
    @Override
    public EngPruductSop selectEngPruductSopById(Long id)
    {
        return engPruductSopMapper.selectEngPruductSopById(id);
    }

    /**
     * 查询产品SOP列表
     * 
     * @param engPruductSop 产品SOP
     * @return 产品SOP
     */
    @Override
    public List<EngPruductSop> selectEngPruductSopList(EngPruductSop engPruductSop)
    {
        return engPruductSopMapper.selectEngPruductSopList(engPruductSop);
    }

    /**
     * 新增产品SOP
     * 
     * @param engPruductSop 产品SOP
     * @return 结果
     */
    @Override
    public int insertEngPruductSop(EngPruductSop engPruductSop)
    {
        engPruductSop.setCreateTime(DateUtils.getNowDate());
        return engPruductSopMapper.insertEngPruductSop(engPruductSop);
    }

    /**
     * 修改产品SOP
     * 
     * @param engPruductSop 产品SOP
     * @return 结果
     */
    @Override
    public int updateEngPruductSop(EngPruductSop engPruductSop)
    {
        engPruductSop.setUpdateTime(DateUtils.getNowDate());
        return engPruductSopMapper.updateEngPruductSop(engPruductSop);
    }

    /**
     * 批量删除产品SOP
     * 
     * @param ids 需要删除的产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductSopByIds(Long[] ids)
    {
        return engPruductSopMapper.deleteEngPruductSopByIds(ids);
    }

    /**
     * 删除产品SOP信息
     * 
     * @param id 产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductSopById(Long id)
    {
        return engPruductSopMapper.deleteEngPruductSopById(id);
    }


}
