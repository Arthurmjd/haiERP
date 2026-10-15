package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncGrItemMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGrItem;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncGrItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 收货单明细Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SncGrItemServiceImpl implements ISncGrItemService 
{
    @Autowired(required = false)
    private SncGrItemMapper sncGrItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询收货单明细
     * 
     * @param id 收货单明细主键
     * @return 收货单明细
     */
    @Override
    public SncGrItem selectSncGrItemById(Long id)
    {
        return sncGrItemMapper.selectSncGrItemById(id);
    }

    /**
     * 查询收货单明细列表
     * 
     * @param sncGrItem 收货单明细
     * @return 收货单明细
     */
    @Override
    public List<SncGrItem> selectSncGrItemList(SncGrItem sncGrItem)
    {
        return sncGrItemMapper.selectSncGrItemList(sncGrItem);
    }

    /**
     * 新增收货单明细
     * 
     * @param sncGrItem 收货单明细
     * @return 结果
     */
    @Override
    public int insertSncGrItem(SncGrItem sncGrItem)
    {
        sncGrItem.setCreateTime(DateUtils.getNowDate());
        return sncGrItemMapper.insertSncGrItem(sncGrItem);
    }

    /**
     * 修改收货单明细
     * 
     * @param sncGrItem 收货单明细
     * @return 结果
     */
    @Override
    public int updateSncGrItem(SncGrItem sncGrItem)
    {
        sncGrItem.setUpdateTime(DateUtils.getNowDate());
        return sncGrItemMapper.updateSncGrItem(sncGrItem);
    }

    /**
     * 批量删除收货单明细
     * 
     * @param ids 需要删除的收货单明细主键
     * @return 结果
     */
    @Override
    public int deleteSncGrItemByIds(Long[] ids)
    {
        return sncGrItemMapper.deleteSncGrItemByIds(ids);
    }

    /**
     * 删除收货单明细信息
     * 
     * @param id 收货单明细主键
     * @return 结果
     */
    @Override
    public int deleteSncGrItemById(Long id)
    {
        return sncGrItemMapper.deleteSncGrItemById(id);
    }


}
