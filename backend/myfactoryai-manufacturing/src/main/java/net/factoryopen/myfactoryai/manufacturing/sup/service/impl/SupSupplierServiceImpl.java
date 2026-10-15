package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupSupplierMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplier;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-26
 */
@Service
public class SupSupplierServiceImpl implements ISupSupplierService 
{
    @Autowired(required = false)
    private SupSupplierMapper supSupplierMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商管理
     * 
     * @param id 供应商管理主键
     * @return 供应商管理
     */
    @Override
    public SupSupplier selectSupSupplierById(Long id)
    {
        return supSupplierMapper.selectSupSupplierById(id);
    }

    /**
     * 查询供应商管理列表
     * 
     * @param supSupplier 供应商管理
     * @return 供应商管理
     */
    @Override
    public List<SupSupplier> selectSupSupplierList(SupSupplier supSupplier)
    {
        return supSupplierMapper.selectSupSupplierList(supSupplier);
    }

    /**
     * 新增供应商管理
     * 
     * @param supSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int insertSupSupplier(SupSupplier supSupplier)
    {
        supSupplier.setCreateTime(DateUtils.getNowDate());
        return supSupplierMapper.insertSupSupplier(supSupplier);
    }

    /**
     * 修改供应商管理
     * 
     * @param supSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int updateSupSupplier(SupSupplier supSupplier)
    {
        supSupplier.setUpdateTime(DateUtils.getNowDate());
        return supSupplierMapper.updateSupSupplier(supSupplier);
    }

    /**
     * 批量删除供应商管理
     * 
     * @param ids 需要删除的供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierByIds(Long[] ids)
    {
        return supSupplierMapper.deleteSupSupplierByIds(ids);
    }

    /**
     * 删除供应商管理信息
     * 
     * @param id 供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierById(Long id)
    {
        return supSupplierMapper.deleteSupSupplierById(id);
    }


}
