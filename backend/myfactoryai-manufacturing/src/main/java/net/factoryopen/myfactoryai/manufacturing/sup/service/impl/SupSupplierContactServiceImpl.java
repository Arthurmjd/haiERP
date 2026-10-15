package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupSupplierContactMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierContact;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupSupplierContactService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商人员Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SupSupplierContactServiceImpl implements ISupSupplierContactService 
{
    @Autowired(required = false)
    private SupSupplierContactMapper supSupplierContactMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商人员
     * 
     * @param id 供应商人员主键
     * @return 供应商人员
     */
    @Override
    public SupSupplierContact selectSupSupplierContactById(Long id)
    {
        return supSupplierContactMapper.selectSupSupplierContactById(id);
    }

    /**
     * 查询供应商人员列表
     * 
     * @param supSupplierContact 供应商人员
     * @return 供应商人员
     */
    @Override
    public List<SupSupplierContact> selectSupSupplierContactList(SupSupplierContact supSupplierContact)
    {
        return supSupplierContactMapper.selectSupSupplierContactList(supSupplierContact);
    }

    /**
     * 新增供应商人员
     * 
     * @param supSupplierContact 供应商人员
     * @return 结果
     */
    @Override
    public int insertSupSupplierContact(SupSupplierContact supSupplierContact)
    {
        supSupplierContact.setCreateTime(DateUtils.getNowDate());
        return supSupplierContactMapper.insertSupSupplierContact(supSupplierContact);
    }

    /**
     * 修改供应商人员
     * 
     * @param supSupplierContact 供应商人员
     * @return 结果
     */
    @Override
    public int updateSupSupplierContact(SupSupplierContact supSupplierContact)
    {
        supSupplierContact.setUpdateTime(DateUtils.getNowDate());
        return supSupplierContactMapper.updateSupSupplierContact(supSupplierContact);
    }

    /**
     * 批量删除供应商人员
     * 
     * @param ids 需要删除的供应商人员主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierContactByIds(Long[] ids)
    {
        return supSupplierContactMapper.deleteSupSupplierContactByIds(ids);
    }

    /**
     * 删除供应商人员信息
     * 
     * @param id 供应商人员主键
     * @return 结果
     */
    @Override
    public int deleteSupSupplierContactById(Long id)
    {
        return supSupplierContactMapper.deleteSupSupplierContactById(id);
    }


}
