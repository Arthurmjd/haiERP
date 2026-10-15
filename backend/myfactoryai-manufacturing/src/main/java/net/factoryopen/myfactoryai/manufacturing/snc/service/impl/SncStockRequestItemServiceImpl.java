package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockRequestItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockRequestItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 出入库请求明细Service业务层处理
 * 
 * @author admin
 * @date 2025-05-17
 */
@Service
public class SncStockRequestItemServiceImpl implements ISncStockRequestItemService 
{
    @Autowired(required = false)
    private SncStockRequestItemMapper sncStockRequestItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询出入库请求明细
     * 
     * @param id 出入库请求明细主键
     * @return 出入库请求明细
     */
    @Override
    public SncStockRequestItem selectSncStockRequestItemById(Long id)
    {
        return sncStockRequestItemMapper.selectSncStockRequestItemById(id);
    }

    /**
     * 查询出入库请求明细列表
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 出入库请求明细
     */
    @Override
    public List<SncStockRequestItem> selectSncStockRequestItemList(SncStockRequestItem sncStockRequestItem)
    {
        return sncStockRequestItemMapper.selectSncStockRequestItemList(sncStockRequestItem);
    }

    /**
     * 新增出入库请求明细
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 结果
     */
    @Override
    public int insertSncStockRequestItem(SncStockRequestItem sncStockRequestItem)
    {
        sncStockRequestItem.setCreateTime(DateUtils.getNowDate());
        return sncStockRequestItemMapper.insertSncStockRequestItem(sncStockRequestItem);
    }

    /**
     * 修改出入库请求明细
     * 
     * @param sncStockRequestItem 出入库请求明细
     * @return 结果
     */
    @Override
    public int updateSncStockRequestItem(SncStockRequestItem sncStockRequestItem)
    {
        sncStockRequestItem.setUpdateTime(DateUtils.getNowDate());
        return sncStockRequestItemMapper.updateSncStockRequestItem(sncStockRequestItem);
    }

    /**
     * 批量删除出入库请求明细
     * 
     * @param ids 需要删除的出入库请求明细主键
     * @return 结果
     */
    @Override
    public int deleteSncStockRequestItemByIds(Long[] ids)
    {
        return sncStockRequestItemMapper.deleteSncStockRequestItemByIds(ids);
    }

    /**
     * 删除出入库请求明细信息
     * 
     * @param id 出入库请求明细主键
     * @return 结果
     */
    @Override
    public int deleteSncStockRequestItemById(Long id)
    {
        return sncStockRequestItemMapper.deleteSncStockRequestItemById(id);
    }


}
