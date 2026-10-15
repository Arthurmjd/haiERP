package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerAddressMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerAddress;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerAddressService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客户地址Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaCustomerAddressServiceImpl implements IChaCustomerAddressService 
{
    @Autowired(required = false)
    private ChaCustomerAddressMapper chaCustomerAddressMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客户地址
     * 
     * @param id 客户地址主键
     * @return 客户地址
     */
    @Override
    public ChaCustomerAddress selectChaCustomerAddressById(Long id)
    {
        return chaCustomerAddressMapper.selectChaCustomerAddressById(id);
    }

    /**
     * 查询客户地址列表
     * 
     * @param chaCustomerAddress 客户地址
     * @return 客户地址
     */
    @Override
    public List<ChaCustomerAddress> selectChaCustomerAddressList(ChaCustomerAddress chaCustomerAddress)
    {
        return chaCustomerAddressMapper.selectChaCustomerAddressList(chaCustomerAddress);
    }

    /**
     * 新增客户地址
     * 
     * @param chaCustomerAddress 客户地址
     * @return 结果
     */
    @Override
    public int insertChaCustomerAddress(ChaCustomerAddress chaCustomerAddress)
    {
        //创建戳
        chaCustomerAddress.setCreateBy(SecurityUtils.getUsername());
        chaCustomerAddress.setCreateTime(DateUtils.getNowDate());
        chaCustomerAddress.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerAddress.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerAddressMapper.insertChaCustomerAddress(chaCustomerAddress);
    }

    /**
     * 修改客户地址
     * 
     * @param chaCustomerAddress 客户地址
     * @return 结果
     */
    @Override
    public int updateChaCustomerAddress(ChaCustomerAddress chaCustomerAddress)
    {
        chaCustomerAddress.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerAddress.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerAddressMapper.updateChaCustomerAddress(chaCustomerAddress);
    }

    /**
     * 批量删除客户地址
     * 
     * @param ids 需要删除的客户地址主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerAddressByIds(Long[] ids)
    {
        return chaCustomerAddressMapper.deleteChaCustomerAddressByIds(ids);
    }

    /**
     * 删除客户地址信息
     * 
     * @param id 客户地址主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerAddressById(Long id)
    {
        return chaCustomerAddressMapper.deleteChaCustomerAddressById(id);
    }


}
