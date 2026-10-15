package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购单明细Service业务层处理
 * 
 * @author admin
 * @date 2024-09-23
 */
@Service
public class SupPoItemServiceImpl implements ISupPoItemService 
{
    @Autowired(required = false)
    private SupPoItemMapper supPoItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购单明细
     * 
     * @param id 采购单明细主键
     * @return 采购单明细
     */
    @Override
    public SupPoItem selectSupPoItemById(Long id)
    {
        return supPoItemMapper.selectSupPoItemById(id);
    }

    /**
     * 查询采购单明细列表
     * 
     * @param supPoItem 采购单明细
     * @return 采购单明细
     */
    @Override
    public List<SupPoItem> selectSupPoItemList(SupPoItem supPoItem)
    {
        return supPoItemMapper.selectSupPoItemList(supPoItem);
    }

    /**
     * 新增采购单明细
     * 
     * @param supPoItem 采购单明细
     * @return 结果
     */
    @Override
    public int insertSupPoItem(SupPoItem supPoItem)
    {
        supPoItem.setCreateTime(DateUtils.getNowDate());
        return supPoItemMapper.insertSupPoItem(supPoItem);
    }

    /**
     * 修改采购单明细
     * 
     * @param supPoItem 采购单明细
     * @return 结果
     */
    @Override
    public int updateSupPoItem(SupPoItem supPoItem)
    {
        supPoItem.setUpdateTime(DateUtils.getNowDate());
        return supPoItemMapper.updateSupPoItem(supPoItem);
    }

    /**
     * 批量删除采购单明细
     * 
     * @param ids 需要删除的采购单明细主键
     * @return 结果
     */
    @Override
    public int deleteSupPoItemByIds(Long[] ids)
    {
        return supPoItemMapper.deleteSupPoItemByIds(ids);
    }

    /**
     * 删除采购单明细信息
     * 
     * @param id 采购单明细主键
     * @return 结果
     */
    @Override
    public int deleteSupPoItemById(Long id)
    {
        return supPoItemMapper.deleteSupPoItemById(id);
    }


}
