package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPurchaseRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPurchaseRequestItem;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPurchaseRequestItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购申请明细Service业务层处理
 * 
 * @author admin
 * @date 2024-09-16
 */
@Service
public class SupPurchaseRequestItemServiceImpl implements ISupPurchaseRequestItemService 
{
    @Autowired(required = false)
    private SupPurchaseRequestItemMapper supPurchaseRequestItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购申请明细
     * 
     * @param id 采购申请明细主键
     * @return 采购申请明细
     */
    @Override
    public SupPurchaseRequestItem selectSupPurchaseRequestItemById(Long id)
    {
        return supPurchaseRequestItemMapper.selectSupPurchaseRequestItemById(id);
    }

    /**
     * 查询采购申请明细列表
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 采购申请明细
     */
    @Override
    public List<SupPurchaseRequestItem> selectSupPurchaseRequestItemList(SupPurchaseRequestItem supPurchaseRequestItem)
    {
        return supPurchaseRequestItemMapper.selectSupPurchaseRequestItemList(supPurchaseRequestItem);
    }

    /**
     * 新增采购申请明细
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 结果
     */
    @Override
    public int insertSupPurchaseRequestItem(SupPurchaseRequestItem supPurchaseRequestItem)
    {
        supPurchaseRequestItem.setCreateTime(DateUtils.getNowDate());
        return supPurchaseRequestItemMapper.insertSupPurchaseRequestItem(supPurchaseRequestItem);
    }

    /**
     * 修改采购申请明细
     * 
     * @param supPurchaseRequestItem 采购申请明细
     * @return 结果
     */
    @Override
    public int updateSupPurchaseRequestItem(SupPurchaseRequestItem supPurchaseRequestItem)
    {
        supPurchaseRequestItem.setUpdateTime(DateUtils.getNowDate());
        return supPurchaseRequestItemMapper.updateSupPurchaseRequestItem(supPurchaseRequestItem);
    }

    /**
     * 批量删除采购申请明细
     * 
     * @param ids 需要删除的采购申请明细主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseRequestItemByIds(Long[] ids)
    {
        return supPurchaseRequestItemMapper.deleteSupPurchaseRequestItemByIds(ids);
    }

    /**
     * 删除采购申请明细信息
     * 
     * @param id 采购申请明细主键
     * @return 结果
     */
    @Override
    public int deleteSupPurchaseRequestItemById(Long id)
    {
        return supPurchaseRequestItemMapper.deleteSupPurchaseRequestItemById(id);
    }


}
