package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanItemMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPlanItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 发货计划Service业务层处理
 * 
 * @author admin
 * @date 2024-07-09
 */
@Service
public class DelPlanItemServiceImpl implements IDelPlanItemService 
{
    @Autowired(required = false)
    private DelPlanItemMapper delPlanItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询发货计划
     * 
     * @param id 发货计划主键
     * @return 发货计划
     */
    @Override
    public DelPlanItem selectDelPlanItemById(Long id)
    {
        return delPlanItemMapper.selectDelPlanItemById(id);
    }

    /**
     * 查询发货计划列表
     * 
     * @param delPlanItem 发货计划
     * @return 发货计划
     */
    @Override
    public List<DelPlanItem> selectDelPlanItemList(DelPlanItem delPlanItem)
    {
        return delPlanItemMapper.selectDelPlanItemList(delPlanItem);
    }

    /**
     * 新增发货计划
     * 
     * @param delPlanItem 发货计划
     * @return 结果
     */
    @Override
    public int insertDelPlanItem(DelPlanItem delPlanItem)
    {
        //创建戳
        delPlanItem.setCreateBy(SecurityUtils.getUsername());
        delPlanItem.setCreateTime(DateUtils.getNowDate());
        delPlanItem.setUpdateBy(SecurityUtils.getUsername());
        delPlanItem.setUpdateTime(DateUtils.getNowDate());
        return delPlanItemMapper.insertDelPlanItem(delPlanItem);
    }

    /**
     * 修改发货计划
     * 
     * @param delPlanItem 发货计划
     * @return 结果
     */
    @Override
    public int updateDelPlanItem(DelPlanItem delPlanItem)
    {
        delPlanItem.refreshUpdatingStamp();
        return delPlanItemMapper.updateDelPlanItem(delPlanItem);
    }

    /**
     * 批量删除发货计划
     * 
     * @param ids 需要删除的发货计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanItemByIds(Long[] ids)
    {
        return delPlanItemMapper.deleteDelPlanItemByIds(ids);
    }

    /**
     * 删除发货计划信息
     * 
     * @param id 发货计划主键
     * @return 结果
     */
    @Override
    public int deleteDelPlanItemById(Long id)
    {
        return delPlanItemMapper.deleteDelPlanItemById(id);
    }


}
