package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupBuyersMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupBuyers;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupBuyersService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购员配置Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupBuyersServiceImpl implements ISupBuyersService 
{
    @Autowired(required = false)
    private SupBuyersMapper supBuyersMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购员配置
     * 
     * @param id 采购员配置主键
     * @return 采购员配置
     */
    @Override
    public SupBuyers selectSupBuyersById(Long id)
    {
        return supBuyersMapper.selectSupBuyersById(id);
    }

    /**
     * 查询采购员配置列表
     * 
     * @param supBuyers 采购员配置
     * @return 采购员配置
     */
    @Override
    public List<SupBuyers> selectSupBuyersList(SupBuyers supBuyers)
    {
        return supBuyersMapper.selectSupBuyersList(supBuyers);
    }

    /**
     * 新增采购员配置
     * 
     * @param supBuyers 采购员配置
     * @return 结果
     */
    @Override
    public int insertSupBuyers(SupBuyers supBuyers)
    {
        supBuyers.setCreateTime(DateUtils.getNowDate());
        return supBuyersMapper.insertSupBuyers(supBuyers);
    }

    /**
     * 修改采购员配置
     * 
     * @param supBuyers 采购员配置
     * @return 结果
     */
    @Override
    public int updateSupBuyers(SupBuyers supBuyers)
    {
        supBuyers.setUpdateTime(DateUtils.getNowDate());
        return supBuyersMapper.updateSupBuyers(supBuyers);
    }

    /**
     * 批量删除采购员配置
     * 
     * @param ids 需要删除的采购员配置主键
     * @return 结果
     */
    @Override
    public int deleteSupBuyersByIds(Long[] ids)
    {
        return supBuyersMapper.deleteSupBuyersByIds(ids);
    }

    /**
     * 删除采购员配置信息
     * 
     * @param id 采购员配置主键
     * @return 结果
     */
    @Override
    public int deleteSupBuyersById(Long id)
    {
        return supBuyersMapper.deleteSupBuyersById(id);
    }


}
