package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectPurchasingMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectPurchasing;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectPurchasingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外购检异登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class QuaInspectPurchasingServiceImpl implements IQuaInspectPurchasingService 
{
    @Autowired(required = false)
    private QuaInspectPurchasingMapper quaInspectPurchasingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询外购检异登记
     * 
     * @param id 外购检异登记主键
     * @return 外购检异登记
     */
    @Override
    public QuaInspectPurchasing selectQuaInspectPurchasingById(Long id)
    {
        return quaInspectPurchasingMapper.selectQuaInspectPurchasingById(id);
    }

    /**
     * 查询外购检异登记列表
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 外购检异登记
     */
    @Override
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingList(QuaInspectPurchasing quaInspectPurchasing)
    {
        return quaInspectPurchasingMapper.selectQuaInspectPurchasingList(quaInspectPurchasing);
    }

    /**
     * 新增外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    @Override
    public int insertQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing)
    {
        quaInspectPurchasing.setCreateTime(DateUtils.getNowDate());
        return quaInspectPurchasingMapper.insertQuaInspectPurchasing(quaInspectPurchasing);
    }

    /**
     * 修改外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    @Override
    public int updateQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing)
    {
        quaInspectPurchasing.setUpdateTime(DateUtils.getNowDate());
        return quaInspectPurchasingMapper.updateQuaInspectPurchasing(quaInspectPurchasing);
    }

    /**
     * 批量删除外购检异登记
     * 
     * @param ids 需要删除的外购检异登记主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectPurchasingByIds(Long[] ids)
    {
        return quaInspectPurchasingMapper.deleteQuaInspectPurchasingByIds(ids);
    }

    /**
     * 删除外购检异登记信息
     * 
     * @param id 外购检异登记主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectPurchasingById(Long id)
    {
        return quaInspectPurchasingMapper.deleteQuaInspectPurchasingById(id);
    }


}
