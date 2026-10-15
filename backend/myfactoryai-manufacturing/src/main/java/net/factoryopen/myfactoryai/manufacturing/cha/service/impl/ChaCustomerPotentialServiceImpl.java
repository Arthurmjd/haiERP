package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaCustomerPotentialMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerPotential;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaCustomerPotentialService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 潜客备案Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaCustomerPotentialServiceImpl implements IChaCustomerPotentialService 
{
    @Autowired(required = false)
    private ChaCustomerPotentialMapper chaCustomerPotentialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询潜客备案
     * 
     * @param id 潜客备案主键
     * @return 潜客备案
     */
    @Override
    public ChaCustomerPotential selectChaCustomerPotentialById(Long id)
    {
        return chaCustomerPotentialMapper.selectChaCustomerPotentialById(id);
    }

    /**
     * 查询潜客备案列表
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 潜客备案
     */
    @Override
    public List<ChaCustomerPotential> selectChaCustomerPotentialList(ChaCustomerPotential chaCustomerPotential)
    {
        return chaCustomerPotentialMapper.selectChaCustomerPotentialList(chaCustomerPotential);
    }

    /**
     * 新增潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    @Override
    public int insertChaCustomerPotential(ChaCustomerPotential chaCustomerPotential)
    {
        //创建戳
        chaCustomerPotential.setCreateBy(SecurityUtils.getUsername());
        chaCustomerPotential.setCreateTime(DateUtils.getNowDate());
        chaCustomerPotential.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerPotential.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerPotentialMapper.insertChaCustomerPotential(chaCustomerPotential);
    }

    /**
     * 修改潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    @Override
    public int updateChaCustomerPotential(ChaCustomerPotential chaCustomerPotential)
    {
        chaCustomerPotential.setUpdateBy(SecurityUtils.getUsername());
        chaCustomerPotential.setUpdateTime(DateUtils.getNowDate());
        return chaCustomerPotentialMapper.updateChaCustomerPotential(chaCustomerPotential);
    }

    /**
     * 批量删除潜客备案
     * 
     * @param ids 需要删除的潜客备案主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerPotentialByIds(Long[] ids)
    {
        return chaCustomerPotentialMapper.deleteChaCustomerPotentialByIds(ids);
    }

    /**
     * 删除潜客备案信息
     * 
     * @param id 潜客备案主键
     * @return 结果
     */
    @Override
    public int deleteChaCustomerPotentialById(Long id)
    {
        return chaCustomerPotentialMapper.deleteChaCustomerPotentialById(id);
    }


}
