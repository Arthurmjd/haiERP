package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialSalesMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialSales;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialSalesService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料销售维Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialSalesServiceImpl implements IEngMaterialSalesService 
{
    @Autowired(required = false)
    private EngMaterialSalesMapper engMaterialSalesMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料销售维
     * 
     * @param id 物料销售维主键
     * @return 物料销售维
     */
    @Override
    public EngMaterialSales selectEngMaterialSalesById(Long id)
    {
        return engMaterialSalesMapper.selectEngMaterialSalesById(id);
    }

    /**
     * 查询物料销售维列表
     * 
     * @param engMaterialSales 物料销售维
     * @return 物料销售维
     */
    @Override
    public List<EngMaterialSales> selectEngMaterialSalesList(EngMaterialSales engMaterialSales)
    {
        return engMaterialSalesMapper.selectEngMaterialSalesList(engMaterialSales);
    }

    /**
     * 新增物料销售维
     * 
     * @param engMaterialSales 物料销售维
     * @return 结果
     */
    @Override
    public int insertEngMaterialSales(EngMaterialSales engMaterialSales)
    {
        //创建戳
        engMaterialSales.setCreateBy(SecurityUtils.getUsername());
        engMaterialSales.setCreateTime(DateUtils.getNowDate());
        engMaterialSales.setUpdateBy(SecurityUtils.getUsername());
        engMaterialSales.setUpdateTime(DateUtils.getNowDate());
        return engMaterialSalesMapper.insertEngMaterialSales(engMaterialSales);
    }

    /**
     * 修改物料销售维
     * 
     * @param engMaterialSales 物料销售维
     * @return 结果
     */
    @Override
    public int updateEngMaterialSales(EngMaterialSales engMaterialSales)
    {
        engMaterialSales.setUpdateTime(DateUtils.getNowDate());
        return engMaterialSalesMapper.updateEngMaterialSales(engMaterialSales);
    }

    /**
     * 批量删除物料销售维
     * 
     * @param ids 需要删除的物料销售维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialSalesByIds(Long[] ids)
    {
        return engMaterialSalesMapper.deleteEngMaterialSalesByIds(ids);
    }

    /**
     * 删除物料销售维信息
     * 
     * @param id 物料销售维主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialSalesById(Long id)
    {
        return engMaterialSalesMapper.deleteEngMaterialSalesById(id);
    }


}
