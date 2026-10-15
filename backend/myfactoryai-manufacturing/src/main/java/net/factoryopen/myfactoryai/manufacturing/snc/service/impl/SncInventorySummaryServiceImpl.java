package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncInventorySummaryMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventorySummary;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventorySummaryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 库存总览表Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
@Service
public class SncInventorySummaryServiceImpl implements ISncInventorySummaryService 
{
    @Autowired(required = false)
    private SncInventorySummaryMapper sncInventorySummaryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询库存总览表
     * 
     * @param id 库存总览表主键
     * @return 库存总览表
     */
    @Override
    public SncInventorySummary selectSncInventorySummaryById(Long id)
    {
        return sncInventorySummaryMapper.selectSncInventorySummaryById(id);
    }

    /**
     * 查询库存总览表列表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 库存总览表
     */
    @Override
    public List<SncInventorySummary> selectSncInventorySummaryList(SncInventorySummary sncInventorySummary)
    {
        List<SncInventorySummary> result = sncInventorySummaryMapper.selectSncInventorySummaryList(sncInventorySummary);
        for (int i = 0; result !=null && i < result.size(); i++) {
            SncInventorySummary item = result.get(i);
            double quantityAvailable = item.getQuantityQualified().doubleValue()
                    -item.getQuantityExpired().doubleValue()-item.getQuantityBooked().doubleValue();
            item.setQuantityAvailable(new BigDecimal(quantityAvailable).setScale(4, BigDecimal.ROUND_HALF_UP));
        }
        return result;
    }

    /**
     * 新增库存总览表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 结果
     */
    @Override
    public int insertSncInventorySummary(SncInventorySummary sncInventorySummary)
    {
        sncInventorySummary.setCreateTime(DateUtils.getNowDate());
        return sncInventorySummaryMapper.insertSncInventorySummary(sncInventorySummary);
    }

    /**
     * 修改库存总览表
     * 
     * @param sncInventorySummary 库存总览表
     * @return 结果
     */
    @Override
    public int updateSncInventorySummary(SncInventorySummary sncInventorySummary)
    {
        sncInventorySummary.setUpdateTime(DateUtils.getNowDate());
        return sncInventorySummaryMapper.updateSncInventorySummary(sncInventorySummary);
    }

    /**
     * 批量删除库存总览表
     * 
     * @param ids 需要删除的库存总览表主键
     * @return 结果
     */
    @Override
    public int deleteSncInventorySummaryByIds(Long[] ids)
    {
        return sncInventorySummaryMapper.deleteSncInventorySummaryByIds(ids);
    }

    /**
     * 删除库存总览表信息
     * 
     * @param id 库存总览表主键
     * @return 结果
     */
    @Override
    public int deleteSncInventorySummaryById(Long id)
    {
        return sncInventorySummaryMapper.deleteSncInventorySummaryById(id);
    }


}
