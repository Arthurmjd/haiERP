package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SuplSupplierBankMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SuplSupplierBank;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISuplSupplierBankService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商账号Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SuplSupplierBankServiceImpl implements ISuplSupplierBankService 
{
    @Autowired(required = false)
    private SuplSupplierBankMapper suplSupplierBankMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商账号
     * 
     * @param id 供应商账号主键
     * @return 供应商账号
     */
    @Override
    public SuplSupplierBank selectSuplSupplierBankById(Long id)
    {
        return suplSupplierBankMapper.selectSuplSupplierBankById(id);
    }

    /**
     * 查询供应商账号列表
     * 
     * @param suplSupplierBank 供应商账号
     * @return 供应商账号
     */
    @Override
    public List<SuplSupplierBank> selectSuplSupplierBankList(SuplSupplierBank suplSupplierBank)
    {
        return suplSupplierBankMapper.selectSuplSupplierBankList(suplSupplierBank);
    }

    /**
     * 新增供应商账号
     * 
     * @param suplSupplierBank 供应商账号
     * @return 结果
     */
    @Override
    public int insertSuplSupplierBank(SuplSupplierBank suplSupplierBank)
    {
        suplSupplierBank.setCreateTime(DateUtils.getNowDate());
        return suplSupplierBankMapper.insertSuplSupplierBank(suplSupplierBank);
    }

    /**
     * 修改供应商账号
     * 
     * @param suplSupplierBank 供应商账号
     * @return 结果
     */
    @Override
    public int updateSuplSupplierBank(SuplSupplierBank suplSupplierBank)
    {
        suplSupplierBank.setUpdateTime(DateUtils.getNowDate());
        return suplSupplierBankMapper.updateSuplSupplierBank(suplSupplierBank);
    }

    /**
     * 批量删除供应商账号
     * 
     * @param ids 需要删除的供应商账号主键
     * @return 结果
     */
    @Override
    public int deleteSuplSupplierBankByIds(Long[] ids)
    {
        return suplSupplierBankMapper.deleteSuplSupplierBankByIds(ids);
    }

    /**
     * 删除供应商账号信息
     * 
     * @param id 供应商账号主键
     * @return 结果
     */
    @Override
    public int deleteSuplSupplierBankById(Long id)
    {
        return suplSupplierBankMapper.deleteSuplSupplierBankById(id);
    }


}
