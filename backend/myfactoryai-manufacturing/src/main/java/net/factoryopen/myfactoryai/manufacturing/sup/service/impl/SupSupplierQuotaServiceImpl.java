package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupSupplierQuotaMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierQuota;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierQuotaService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商配额Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupSupplierQuotaServiceImpl implements ISupSupplierQuotaService 
{
    @Autowired(required = false)
    private SupSupplierQuotaMapper supSupplierQuotaMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商配额
     * 
     * @param id 供应商配额主键
     * @return 供应商配额
     */
    @Override
    public SupSupplierQuota selectSupSupplierQuotaById(Long id)
    {
        return supSupplierQuotaMapper.selectSupSupplierQuotaById(id);
    }

    /**
     * 查询供应商配额列表
     * 
     * @param supSupplierQuota 供应商配额
     * @return 供应商配额
     */
    @Override
    public List<SupSupplierQuota> selectSupSupplierQuotaList(SupSupplierQuota supSupplierQuota)
    {
        return supSupplierQuotaMapper.selectSupSupplierQuotaList(supSupplierQuota);
    }

    /**
     * 新增供应商配额
     * 
     * @param supSupplierQuota 供应商配额
     * @return 结果
     */
    @Override
    public int insertSupSupplierQuota(SupSupplierQuota supSupplierQuota)
    {
        supSupplierQuota.setCreateTime(DateUtils.getNowDate());
        return supSupplierQuotaMapper.insertSupSupplierQuota(supSupplierQuota);
    }

    /**
     * 修改供应商配额
     * 
     * @param supSupplierQuota 供应商配额
     * @return 结果
     */
    @Override
    public int updateSupSupplierQuota(SupSupplierQuota supSupplierQuota)
    {
        supSupplierQuota.setUpdateTime(DateUtils.getNowDate());
        return supSupplierQuotaMapper.updateSupSupplierQuota(supSupplierQuota);
    }

    /**
     * 批量删除供应商配额
     * 
     * @param ids 需要删除的供应商配额主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierQuotaByIds(Long[] ids)
    {
        return supSupplierQuotaMapper.deleteSupSupplierQuotaByIds(ids);
    }

    /**
     * 删除供应商配额信息
     * 
     * @param id 供应商配额主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierQuotaById(Long id)
    {
        return supSupplierQuotaMapper.deleteSupSupplierQuotaById(id);
    }


}
