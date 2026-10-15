package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsGasFoodieMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodie;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasFoodieService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 气耗设备Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsGasFoodieServiceImpl implements IEhsGasFoodieService 
{
    @Autowired(required = false)
    private EhsGasFoodieMapper ehsGasFoodieMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询气耗设备
     * 
     * @param id 气耗设备主键
     * @return 气耗设备
     */
    @Override
    public EhsGasFoodie selectEhsGasFoodieById(Long id)
    {
        return ehsGasFoodieMapper.selectEhsGasFoodieById(id);
    }

    /**
     * 查询气耗设备列表
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 气耗设备
     */
    @Override
    public List<EhsGasFoodie> selectEhsGasFoodieList(EhsGasFoodie ehsGasFoodie)
    {
        return ehsGasFoodieMapper.selectEhsGasFoodieList(ehsGasFoodie);
    }

    /**
     * 新增气耗设备
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 结果
     */
    @Override
    public int insertEhsGasFoodie(EhsGasFoodie ehsGasFoodie)
    {
        ehsGasFoodie.setCreateTime(DateUtils.getNowDate());
        return ehsGasFoodieMapper.insertEhsGasFoodie(ehsGasFoodie);
    }

    /**
     * 修改气耗设备
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 结果
     */
    @Override
    public int updateEhsGasFoodie(EhsGasFoodie ehsGasFoodie)
    {
        ehsGasFoodie.setUpdateTime(DateUtils.getNowDate());
        return ehsGasFoodieMapper.updateEhsGasFoodie(ehsGasFoodie);
    }

    /**
     * 批量删除气耗设备
     * 
     * @param ids 需要删除的气耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasFoodieByIds(Long[] ids)
    {
        return ehsGasFoodieMapper.deleteEhsGasFoodieByIds(ids);
    }

    /**
     * 删除气耗设备信息
     * 
     * @param id 气耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasFoodieById(Long id)
    {
        return ehsGasFoodieMapper.deleteEhsGasFoodieById(id);
    }


}
