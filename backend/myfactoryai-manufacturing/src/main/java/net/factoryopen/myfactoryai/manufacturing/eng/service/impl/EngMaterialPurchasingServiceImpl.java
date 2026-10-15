package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialPurchasingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialPurchasing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialPurchasingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料采购维Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialPurchasingServiceImpl implements IEngMaterialPurchasingService 
{
    @Autowired(required = false)
    private EngMaterialPurchasingMapper engMaterialPurchasingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料采购维
     * 
     * @param id 物料采购维主键
     * @return 物料采购维
     */
    @Override
    public EngMaterialPurchasing selectEngMaterialPurchasingById(Long id)
    {
        return engMaterialPurchasingMapper.selectEngMaterialPurchasingById(id);
    }

    /**
     * 查询物料采购维列表
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 物料采购维
     */
    @Override
    public List<EngMaterialPurchasing> selectEngMaterialPurchasingList(EngMaterialPurchasing engMaterialPurchasing)
    {
        return engMaterialPurchasingMapper.selectEngMaterialPurchasingList(engMaterialPurchasing);
    }

    /**
     * 新增物料采购维
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 结果
     */
    @Override
    public int insertEngMaterialPurchasing(EngMaterialPurchasing engMaterialPurchasing)
    {
        //创建戳
        engMaterialPurchasing.setCreateBy(SecurityUtils.getUsername());
        engMaterialPurchasing.setCreateTime(DateUtils.getNowDate());
        engMaterialPurchasing.setUpdateBy(SecurityUtils.getUsername());
        engMaterialPurchasing.setUpdateTime(DateUtils.getNowDate());
        return engMaterialPurchasingMapper.insertEngMaterialPurchasing(engMaterialPurchasing);
    }

    /**
     * 修改物料采购维
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 结果
     */
    @Override
    public int updateEngMaterialPurchasing(EngMaterialPurchasing engMaterialPurchasing)
    {
        engMaterialPurchasing.setUpdateTime(DateUtils.getNowDate());
        return engMaterialPurchasingMapper.updateEngMaterialPurchasing(engMaterialPurchasing);
    }

    /**
     * 批量删除物料采购维
     * 
     * @param ids 需要删除的物料采购维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialPurchasingByIds(Long[] ids)
    {
        return engMaterialPurchasingMapper.deleteEngMaterialPurchasingByIds(ids);
    }

    /**
     * 删除物料采购维信息
     * 
     * @param id 物料采购维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialPurchasingById(Long id)
    {
        return engMaterialPurchasingMapper.deleteEngMaterialPurchasingById(id);
    }


}
