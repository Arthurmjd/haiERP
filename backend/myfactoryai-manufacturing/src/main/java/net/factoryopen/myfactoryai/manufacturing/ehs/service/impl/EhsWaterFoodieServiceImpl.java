package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsWaterFoodieMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsWaterFoodieService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 水耗设备Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsWaterFoodieServiceImpl implements IEhsWaterFoodieService 
{
    @Autowired(required = false)
    private EhsWaterFoodieMapper ehsWaterFoodieMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询水耗设备
     * 
     * @param id 水耗设备主键
     * @return 水耗设备
     */
    @Override
    public EhsWaterFoodie selectEhsWaterFoodieById(Long id)
    {
        return ehsWaterFoodieMapper.selectEhsWaterFoodieById(id);
    }

    /**
     * 查询水耗设备列表
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 水耗设备
     */
    @Override
    public List<EhsWaterFoodie> selectEhsWaterFoodieList(EhsWaterFoodie ehsWaterFoodie)
    {
        return ehsWaterFoodieMapper.selectEhsWaterFoodieList(ehsWaterFoodie);
    }

    /**
     * 新增水耗设备
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 结果
     */
    @Override
    public int insertEhsWaterFoodie(EhsWaterFoodie ehsWaterFoodie)
    {
        ehsWaterFoodie.setCreateTime(DateUtils.getNowDate());
        return ehsWaterFoodieMapper.insertEhsWaterFoodie(ehsWaterFoodie);
    }

    /**
     * 修改水耗设备
     * 
     * @param ehsWaterFoodie 水耗设备
     * @return 结果
     */
    @Override
    public int updateEhsWaterFoodie(EhsWaterFoodie ehsWaterFoodie)
    {
        ehsWaterFoodie.setUpdateTime(DateUtils.getNowDate());
        return ehsWaterFoodieMapper.updateEhsWaterFoodie(ehsWaterFoodie);
    }

    /**
     * 批量删除水耗设备
     * 
     * @param ids 需要删除的水耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterFoodieByIds(Long[] ids)
    {
        return ehsWaterFoodieMapper.deleteEhsWaterFoodieByIds(ids);
    }

    /**
     * 删除水耗设备信息
     * 
     * @param id 水耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterFoodieById(Long id)
    {
        return ehsWaterFoodieMapper.deleteEhsWaterFoodieById(id);
    }


}
