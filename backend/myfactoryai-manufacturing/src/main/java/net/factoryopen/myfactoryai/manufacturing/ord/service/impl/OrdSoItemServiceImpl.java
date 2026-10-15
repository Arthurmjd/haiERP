package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单明细Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdSoItemServiceImpl implements IOrdSoItemService 
{
    @Autowired(required = false)
    private OrdSoItemMapper ordSoItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询订单明细
     * 
     * @param id 订单明细主键
     * @return 订单明细
     */
    @Override
    public OrdSoItem selectOrdSoItemById(Long id)
    {
        return ordSoItemMapper.selectOrdSoItemById(id);
    }

    /**
     * 查询订单明细列表
     * 
     * @param ordSoItem 订单明细
     * @return 订单明细
     */
    @Override
    public List<OrdSoItem> selectOrdSoItemList(OrdSoItem ordSoItem)
    {
        return ordSoItemMapper.selectOrdSoItemList(ordSoItem);
    }

    /**
     * 查询订单登记发布列表
     *
     * @param soId 订单ID
     * @return 订单登记发布
     */
    @Override
    public List<OrdSoItem> selectOrdSoItemListBySoId(Long soId) { return ordSoItemMapper.selectOrdSoItemListBySoId(soId); }

    /**
     * 新增订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    @Override
    public int insertOrdSoItem(OrdSoItem ordSoItem)
    {
        ordSoItem.setCreateTime(DateUtils.getNowDate());
        return ordSoItemMapper.insertOrdSoItem(ordSoItem);
    }

    /**
     * 修改订单明细
     * 
     * @param ordSoItem 订单明细
     * @return 结果
     */
    @Override
    public int updateOrdSoItem(OrdSoItem ordSoItem)
    {
        ordSoItem.refreshUpdatingStamp();
        return ordSoItemMapper.updateOrdSoItem(ordSoItem);
    }

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoItemByIds(Long[] ids)
    {
        return ordSoItemMapper.deleteOrdSoItemByIds(ids);
    }

    /**
     * 删除订单明细信息
     * 
     * @param id 订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoItemById(Long id)
    {
        return ordSoItemMapper.deleteOrdSoItemById(id);
    }


}
