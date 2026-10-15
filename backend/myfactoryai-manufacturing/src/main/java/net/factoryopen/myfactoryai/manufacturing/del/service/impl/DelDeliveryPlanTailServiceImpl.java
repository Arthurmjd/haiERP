package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryPlanTailMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryPlanTail;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelDeliveryPlanTailService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 发货计划尾情Service业务层处理
 * 
 * @author admin
 * @date 2024-07-09
 */
@Service
public class DelDeliveryPlanTailServiceImpl implements IDelDeliveryPlanTailService 
{
    @Autowired(required = false)
    private DelDeliveryPlanTailMapper delDeliveryPlanTailMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询发货计划尾情
     * 
     * @param id 发货计划尾情主键
     * @return 发货计划尾情
     */
    @Override
    public DelDeliveryPlanTail selectDelDeliveryPlanTailById(Long id)
    {
        return delDeliveryPlanTailMapper.selectDelDeliveryPlanTailById(id);
    }

    /**
     * 查询发货计划尾情列表
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 发货计划尾情
     */
    @Override
    public List<DelDeliveryPlanTail> selectDelDeliveryPlanTailList(DelDeliveryPlanTail delDeliveryPlanTail)
    {
        return delDeliveryPlanTailMapper.selectDelDeliveryPlanTailList(delDeliveryPlanTail);
    }

    /**
     * 新增发货计划尾情
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 结果
     */
    @Override
    public int insertDelDeliveryPlanTail(DelDeliveryPlanTail delDeliveryPlanTail)
    {
        delDeliveryPlanTail.setCreateTime(DateUtils.getNowDate());
        return delDeliveryPlanTailMapper.insertDelDeliveryPlanTail(delDeliveryPlanTail);
    }

    /**
     * 修改发货计划尾情
     * 
     * @param delDeliveryPlanTail 发货计划尾情
     * @return 结果
     */
    @Override
    public int updateDelDeliveryPlanTail(DelDeliveryPlanTail delDeliveryPlanTail)
    {
        delDeliveryPlanTail.setUpdateTime(DateUtils.getNowDate());
        return delDeliveryPlanTailMapper.updateDelDeliveryPlanTail(delDeliveryPlanTail);
    }

    /**
     * 批量删除发货计划尾情
     * 
     * @param ids 需要删除的发货计划尾情主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryPlanTailByIds(Long[] ids)
    {
        return delDeliveryPlanTailMapper.deleteDelDeliveryPlanTailByIds(ids);
    }

    /**
     * 删除发货计划尾情信息
     * 
     * @param id 发货计划尾情主键
     * @return 结果
     */
    @Override
    public int deleteDelDeliveryPlanTailById(Long id)
    {
        return delDeliveryPlanTailMapper.deleteDelDeliveryPlanTailById(id);
    }


}
