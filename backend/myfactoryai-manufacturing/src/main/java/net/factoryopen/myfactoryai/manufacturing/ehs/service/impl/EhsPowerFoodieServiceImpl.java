package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPowerFoodieMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerFoodieService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 电耗设备Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPowerFoodieServiceImpl implements IEhsPowerFoodieService 
{
    @Autowired(required = false)
    private EhsPowerFoodieMapper ehsPowerFoodieMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询电耗设备
     * 
     * @param id 电耗设备主键
     * @return 电耗设备
     */
    @Override
    public EhsPowerFoodie selectEhsPowerFoodieById(Long id)
    {
        return ehsPowerFoodieMapper.selectEhsPowerFoodieById(id);
    }

    /**
     * 查询电耗设备列表
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 电耗设备
     */
    @Override
    public List<EhsPowerFoodie> selectEhsPowerFoodieList(EhsPowerFoodie ehsPowerFoodie)
    {
        return ehsPowerFoodieMapper.selectEhsPowerFoodieList(ehsPowerFoodie);
    }

    /**
     * 新增电耗设备
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 结果
     */
    @Override
    public int insertEhsPowerFoodie(EhsPowerFoodie ehsPowerFoodie)
    {
        ehsPowerFoodie.setCreateTime(DateUtils.getNowDate());
        return ehsPowerFoodieMapper.insertEhsPowerFoodie(ehsPowerFoodie);
    }

    /**
     * 修改电耗设备
     * 
     * @param ehsPowerFoodie 电耗设备
     * @return 结果
     */
    @Override
    public int updateEhsPowerFoodie(EhsPowerFoodie ehsPowerFoodie)
    {
        ehsPowerFoodie.setUpdateTime(DateUtils.getNowDate());
        return ehsPowerFoodieMapper.updateEhsPowerFoodie(ehsPowerFoodie);
    }

    /**
     * 批量删除电耗设备
     * 
     * @param ids 需要删除的电耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerFoodieByIds(Long[] ids)
    {
        return ehsPowerFoodieMapper.deleteEhsPowerFoodieByIds(ids);
    }

    /**
     * 删除电耗设备信息
     * 
     * @param id 电耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerFoodieById(Long id)
    {
        return ehsPowerFoodieMapper.deleteEhsPowerFoodieById(id);
    }


}
