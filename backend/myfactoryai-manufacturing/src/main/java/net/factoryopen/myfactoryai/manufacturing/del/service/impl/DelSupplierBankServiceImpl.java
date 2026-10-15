package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelSupplierBankMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierBank;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierBankService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商账号Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class DelSupplierBankServiceImpl implements IDelSupplierBankService 
{
    @Autowired(required = false)
    private DelSupplierBankMapper delSupplierBankMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商账号
     * 
     * @param id 供应商账号主键
     * @return 供应商账号
     */
    @Override
    public DelSupplierBank selectDelSupplierBankById(Long id)
    {
        return delSupplierBankMapper.selectDelSupplierBankById(id);
    }

    /**
     * 查询供应商账号列表
     * 
     * @param delSupplierBank 供应商账号
     * @return 供应商账号
     */
    @Override
    public List<DelSupplierBank> selectDelSupplierBankList(DelSupplierBank delSupplierBank)
    {
        return delSupplierBankMapper.selectDelSupplierBankList(delSupplierBank);
    }

    /**
     * 新增供应商账号
     * 
     * @param delSupplierBank 供应商账号
     * @return 结果
     */
    @Override
    public int insertDelSupplierBank(DelSupplierBank delSupplierBank)
    {
        //创建戳
        delSupplierBank.setCreateBy(SecurityUtils.getUsername());
        delSupplierBank.setCreateTime(DateUtils.getNowDate());
        delSupplierBank.setUpdateBy(SecurityUtils.getUsername());
        delSupplierBank.setUpdateTime(DateUtils.getNowDate());
        return delSupplierBankMapper.insertDelSupplierBank(delSupplierBank);
    }

    /**
     * 修改供应商账号
     * 
     * @param delSupplierBank 供应商账号
     * @return 结果
     */
    @Override
    public int updateDelSupplierBank(DelSupplierBank delSupplierBank)
    {
        delSupplierBank.refreshUpdatingStamp();
        return delSupplierBankMapper.updateDelSupplierBank(delSupplierBank);
    }

    /**
     * 批量删除供应商账号
     * 
     * @param ids 需要删除的供应商账号主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierBankByIds(Long[] ids)
    {
        return delSupplierBankMapper.deleteDelSupplierBankByIds(ids);
    }

    /**
     * 删除供应商账号信息
     * 
     * @param id 供应商账号主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierBankById(Long id)
    {
        return delSupplierBankMapper.deleteDelSupplierBankById(id);
    }


}
