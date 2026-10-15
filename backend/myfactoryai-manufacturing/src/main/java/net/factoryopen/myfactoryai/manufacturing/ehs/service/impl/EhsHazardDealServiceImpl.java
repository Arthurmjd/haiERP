package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsHazardDealMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardDeal;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsHazardDealService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 安全风险处理Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsHazardDealServiceImpl implements IEhsHazardDealService 
{
    @Autowired(required = false)
    private EhsHazardDealMapper ehsHazardDealMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询安全风险处理
     * 
     * @param id 安全风险处理主键
     * @return 安全风险处理
     */
    @Override
    public EhsHazardDeal selectEhsHazardDealById(Long id)
    {
        return ehsHazardDealMapper.selectEhsHazardDealById(id);
    }

    /**
     * 查询安全风险处理列表
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 安全风险处理
     */
    @Override
    public List<EhsHazardDeal> selectEhsHazardDealList(EhsHazardDeal ehsHazardDeal)
    {
        return ehsHazardDealMapper.selectEhsHazardDealList(ehsHazardDeal);
    }

    /**
     * 新增安全风险处理
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 结果
     */
    @Override
    public int insertEhsHazardDeal(EhsHazardDeal ehsHazardDeal)
    {
        ehsHazardDeal.setCreateTime(DateUtils.getNowDate());
        return ehsHazardDealMapper.insertEhsHazardDeal(ehsHazardDeal);
    }

    /**
     * 修改安全风险处理
     * 
     * @param ehsHazardDeal 安全风险处理
     * @return 结果
     */
    @Override
    public int updateEhsHazardDeal(EhsHazardDeal ehsHazardDeal)
    {
        ehsHazardDeal.setUpdateTime(DateUtils.getNowDate());
        return ehsHazardDealMapper.updateEhsHazardDeal(ehsHazardDeal);
    }

    /**
     * 批量删除安全风险处理
     * 
     * @param ids 需要删除的安全风险处理主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardDealByIds(Long[] ids)
    {
        return ehsHazardDealMapper.deleteEhsHazardDealByIds(ids);
    }

    /**
     * 删除安全风险处理信息
     * 
     * @param id 安全风险处理主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardDealById(Long id)
    {
        return ehsHazardDealMapper.deleteEhsHazardDealById(id);
    }


}
