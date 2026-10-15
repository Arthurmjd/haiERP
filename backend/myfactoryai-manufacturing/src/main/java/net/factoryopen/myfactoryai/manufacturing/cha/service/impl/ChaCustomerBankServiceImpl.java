package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerBankMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerBank;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerBankService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客户账号Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaCustomerBankServiceImpl implements IChaCustomerBankService 
{
    @Autowired(required = false)
    private ChaCustomerBankMapper chaCustomerBankMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客户账号
     * 
     * @param id 客户账号主键
     * @return 客户账号
     */
    @Override
    public ChaCustomerBank selectChaCustomerBankById(Long id)
    {
        return chaCustomerBankMapper.selectChaCustomerBankById(id);
    }

    /**
     * 查询客户账号列表
     * 
     * @param chaCustomerBank 客户账号
     * @return 客户账号
     */
    @Override
    public List<ChaCustomerBank> selectChaCustomerBankList(ChaCustomerBank chaCustomerBank)
    {
        return chaCustomerBankMapper.selectChaCustomerBankList(chaCustomerBank);
    }

    /**
     * 新增客户账号
     * 
     * @param chaCustomerBank 客户账号
     * @return 结果
     */
    @Override
    public int insertChaCustomerBank(ChaCustomerBank chaCustomerBank)
    {
        //创建戳
        chaCustomerBank.setCreateBy(SecurityUtils.getUsername());
        chaCustomerBank.setCreateTime(DateUtils.getNowDate());
        chaCustomerBank.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerBank.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerBankMapper.insertChaCustomerBank(chaCustomerBank);
    }

    /**
     * 修改客户账号
     * 
     * @param chaCustomerBank 客户账号
     * @return 结果
     */
    @Override
    public int updateChaCustomerBank(ChaCustomerBank chaCustomerBank)
    {
        chaCustomerBank.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerBank.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerBankMapper.updateChaCustomerBank(chaCustomerBank);
    }

    /**
     * 批量删除客户账号
     * 
     * @param ids 需要删除的客户账号主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerBankByIds(Long[] ids)
    {
        return chaCustomerBankMapper.deleteChaCustomerBankByIds(ids);
    }

    /**
     * 删除客户账号信息
     * 
     * @param id 客户账号主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerBankById(Long id)
    {
        return chaCustomerBankMapper.deleteChaCustomerBankById(id);
    }


}
