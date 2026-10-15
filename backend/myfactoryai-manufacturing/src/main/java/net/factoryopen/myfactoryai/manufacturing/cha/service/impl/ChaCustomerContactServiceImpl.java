package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerContactMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerContact;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerContactService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客户人员Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaCustomerContactServiceImpl implements IChaCustomerContactService 
{
    @Autowired(required = false)
    private ChaCustomerContactMapper chaCustomerContactMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客户人员
     * 
     * @param id 客户人员主键
     * @return 客户人员
     */
    @Override
    public ChaCustomerContact selectChaCustomerContactById(Long id)
    {
        return chaCustomerContactMapper.selectChaCustomerContactById(id);
    }

    /**
     * 查询客户人员列表
     * 
     * @param chaCustomerContact 客户人员
     * @return 客户人员
     */
    @Override
    public List<ChaCustomerContact> selectChaCustomerContactList(ChaCustomerContact chaCustomerContact)
    {
        return chaCustomerContactMapper.selectChaCustomerContactList(chaCustomerContact);
    }

    /**
     * 新增客户人员
     * 
     * @param chaCustomerContact 客户人员
     * @return 结果
     */
    @Override
    public int insertChaCustomerContact(ChaCustomerContact chaCustomerContact)
    {
        //创建戳
        chaCustomerContact.setCreateBy(SecurityUtils.getUsername());
        chaCustomerContact.setCreateTime(DateUtils.getNowDate());
        chaCustomerContact.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerContact.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerContactMapper.insertChaCustomerContact(chaCustomerContact);
    }

    /**
     * 修改客户人员
     * 
     * @param chaCustomerContact 客户人员
     * @return 结果
     */
    @Override
    public int updateChaCustomerContact(ChaCustomerContact chaCustomerContact)
    {
        chaCustomerContact.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerContact.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerContactMapper.updateChaCustomerContact(chaCustomerContact);
    }

    /**
     * 批量删除客户人员
     * 
     * @param ids 需要删除的客户人员主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerContactByIds(Long[] ids)
    {
        return chaCustomerContactMapper.deleteChaCustomerContactByIds(ids);
    }

    /**
     * 删除客户人员信息
     * 
     * @param id 客户人员主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerContactById(Long id)
    {
        return chaCustomerContactMapper.deleteChaCustomerContactById(id);
    }


}
