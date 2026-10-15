package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupSupplierAddressMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierAddress;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierAddressService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商地址Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupSupplierAddressServiceImpl implements ISupSupplierAddressService 
{
    @Autowired(required = false)
    private SupSupplierAddressMapper supSupplierAddressMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商地址
     * 
     * @param id 供应商地址主键
     * @return 供应商地址
     */
    @Override
    public SupSupplierAddress selectSupSupplierAddressById(Long id)
    {
        return supSupplierAddressMapper.selectSupSupplierAddressById(id);
    }

    /**
     * 查询供应商地址列表
     * 
     * @param supSupplierAddress 供应商地址
     * @return 供应商地址
     */
    @Override
    public List<SupSupplierAddress> selectSupSupplierAddressList(SupSupplierAddress supSupplierAddress)
    {
        return supSupplierAddressMapper.selectSupSupplierAddressList(supSupplierAddress);
    }

    /**
     * 新增供应商地址
     * 
     * @param supSupplierAddress 供应商地址
     * @return 结果
     */
    @Override
    public int insertSupSupplierAddress(SupSupplierAddress supSupplierAddress)
    {
        supSupplierAddress.setCreateTime(DateUtils.getNowDate());
        return supSupplierAddressMapper.insertSupSupplierAddress(supSupplierAddress);
    }

    /**
     * 修改供应商地址
     * 
     * @param supSupplierAddress 供应商地址
     * @return 结果
     */
    @Override
    public int updateSupSupplierAddress(SupSupplierAddress supSupplierAddress)
    {
        supSupplierAddress.setUpdateTime(DateUtils.getNowDate());
        return supSupplierAddressMapper.updateSupSupplierAddress(supSupplierAddress);
    }

    /**
     * 批量删除供应商地址
     * 
     * @param ids 需要删除的供应商地址主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierAddressByIds(Long[] ids)
    {
        return supSupplierAddressMapper.deleteSupSupplierAddressByIds(ids);
    }

    /**
     * 删除供应商地址信息
     * 
     * @param id 供应商地址主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierAddressById(Long id)
    {
        return supSupplierAddressMapper.deleteSupSupplierAddressById(id);
    }


}
