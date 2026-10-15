package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaSalesMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaSales;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaSalesService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 销售员预设Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-26
 */
@Service
public class ChaSalesServiceImpl implements IChaSalesService 
{
    @Autowired(required = false)
    private ChaSalesMapper chaSalesMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询销售员预设
     * 
     * @param id 销售员预设主键
     * @return 销售员预设
     */
    @Override
    public ChaSales selectChaSalesById(Long id)
    {
        return chaSalesMapper.selectChaSalesById(id);
    }

    /**
     * 查询销售员预设列表
     * 
     * @param chaSales 销售员预设
     * @return 销售员预设
     */
    @Override
    public List<ChaSales> selectChaSalesList(ChaSales chaSales)
    {
        return chaSalesMapper.selectChaSalesList(chaSales);
    }

    /**
     * 新增销售员预设
     * 
     * @param chaSales 销售员预设
     * @return 结果
     */
    @Override
    public int insertChaSales(ChaSales chaSales)
    {
        //创建戳
        chaSales.setCreateBy(SecurityUtils.getUsername());
        chaSales.setCreateTime(DateUtils.getNowDate());
        chaSales.setUpdateBy(SecurityUtils.getUsername());
        chaSales.setUpdateTime(DateUtils.getNowDate());
        return chaSalesMapper.insertChaSales(chaSales);
    }

    /**
     * 修改销售员预设
     * 
     * @param chaSales 销售员预设
     * @return 结果
     */
    @Override
    public int updateChaSales(ChaSales chaSales)
    {
        chaSales.setUpdateBy(SecurityUtils.getUsername());
        chaSales.setUpdateTime(DateUtils.getNowDate());
        return chaSalesMapper.updateChaSales(chaSales);
    }

    /**
     * 批量删除销售员预设
     * 
     * @param ids 需要删除的销售员预设主键
     * @return 结果
     */
    @Override
    public int deleteChaSalesByIds(Long[] ids)
    {
        return chaSalesMapper.deleteChaSalesByIds(ids);
    }

    /**
     * 删除销售员预设信息
     * 
     * @param id 销售员预设主键
     * @return 结果
     */
    @Override
    public int deleteChaSalesById(Long id)
    {
        return chaSalesMapper.deleteChaSalesById(id);
    }


}
