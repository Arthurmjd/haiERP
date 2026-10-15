package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomer;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客户数据管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaCustomerServiceImpl implements IChaCustomerService 
{
    @Autowired(required = false)
    private ChaCustomerMapper chaCustomerMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客户数据管理
     * 
     * @param id 客户数据管理主键
     * @return 客户数据管理
     */
    @Override
    public ChaCustomer selectChaCustomerById(Long id)
    {
        return chaCustomerMapper.selectChaCustomerById(id);
    }

    /**
     * 查询客户数据管理
     *
     * @param name 客户名称
     * @return 客户数据管理
     */
    public ChaCustomer selectChaCustomerByName(String name)
    {
        return chaCustomerMapper.selectChaCustomerByName(name);
    }

    /**
     * 查询客户数据管理列表
     * 
     * @param chaCustomer 客户数据管理
     * @return 客户数据管理
     */
    @Override
    public List<ChaCustomer> selectChaCustomerList(ChaCustomer chaCustomer)
    {
        return chaCustomerMapper.selectChaCustomerList(chaCustomer);
    }

    /**
     * 新增客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    @Override
    public int insertChaCustomer(ChaCustomer chaCustomer)
    {
        //创建戳
        chaCustomer.setCreateBy(SecurityUtils.getUsername());
        chaCustomer.setCreateTime(DateUtils.getNowDate());
        chaCustomer.setUpdateBy(SecurityUtils.getUsername());
        chaCustomer.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerMapper.insertChaCustomer(chaCustomer);
    }

    /**
     * 修改客户数据管理
     * 
     * @param chaCustomer 客户数据管理
     * @return 结果
     */
    @Override
    public int updateChaCustomer(ChaCustomer chaCustomer)
    {
        chaCustomer.setUpdateBy(SecurityUtils.getUsername());
        chaCustomer.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerMapper.updateChaCustomer(chaCustomer);
    }

    /**
     * 批量删除客户数据管理
     * 
     * @param ids 需要删除的客户数据管理主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerByIds(Long[] ids)
    {
        return chaCustomerMapper.deleteChaCustomerByIds(ids);
    }

    /**
     * 删除客户数据管理信息
     * 
     * @param id 客户数据管理主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerById(Long id)
    {
        return chaCustomerMapper.deleteChaCustomerById(id);
    }


}
