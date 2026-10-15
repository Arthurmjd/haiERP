package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelSupplierAddressMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierAddress;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierAddressService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商地址Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class DelSupplierAddressServiceImpl implements IDelSupplierAddressService 
{
    @Autowired(required = false)
    private DelSupplierAddressMapper delSupplierAddressMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商地址
     * 
     * @param id 供应商地址主键
     * @return 供应商地址
     */
    @Override
    public DelSupplierAddress selectDelSupplierAddressById(Long id)
    {
        return delSupplierAddressMapper.selectDelSupplierAddressById(id);
    }

    /**
     * 查询供应商地址列表
     * 
     * @param delSupplierAddress 供应商地址
     * @return 供应商地址
     */
    @Override
    public List<DelSupplierAddress> selectDelSupplierAddressList(DelSupplierAddress delSupplierAddress)
    {
        return delSupplierAddressMapper.selectDelSupplierAddressList(delSupplierAddress);
    }

    /**
     * 新增供应商地址
     * 
     * @param delSupplierAddress 供应商地址
     * @return 结果
     */
    @Override
    public int insertDelSupplierAddress(DelSupplierAddress delSupplierAddress)
    {
        //创建戳
        delSupplierAddress.setCreateBy(SecurityUtils.getUsername());
        delSupplierAddress.setCreateTime(DateUtils.getNowDate());
        delSupplierAddress.setUpdateBy(SecurityUtils.getUsername());
        delSupplierAddress.setUpdateTime(DateUtils.getNowDate());
        return delSupplierAddressMapper.insertDelSupplierAddress(delSupplierAddress);
    }

    /**
     * 修改供应商地址
     * 
     * @param delSupplierAddress 供应商地址
     * @return 结果
     */
    @Override
    public int updateDelSupplierAddress(DelSupplierAddress delSupplierAddress)
    {
        delSupplierAddress.refreshUpdatingStamp();
        return delSupplierAddressMapper.updateDelSupplierAddress(delSupplierAddress);
    }

    /**
     * 批量删除供应商地址
     * 
     * @param ids 需要删除的供应商地址主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierAddressByIds(Long[] ids)
    {
        return delSupplierAddressMapper.deleteDelSupplierAddressByIds(ids);
    }

    /**
     * 删除供应商地址信息
     * 
     * @param id 供应商地址主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierAddressById(Long id)
    {
        return delSupplierAddressMapper.deleteDelSupplierAddressById(id);
    }


}
