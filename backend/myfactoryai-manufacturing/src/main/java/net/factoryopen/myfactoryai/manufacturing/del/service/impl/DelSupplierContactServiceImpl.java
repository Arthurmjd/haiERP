package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelSupplierContactMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplierContact;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierContactService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 供应商人员Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class DelSupplierContactServiceImpl implements IDelSupplierContactService 
{
    @Autowired(required = false)
    private DelSupplierContactMapper delSupplierContactMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询供应商人员
     * 
     * @param id 供应商人员主键
     * @return 供应商人员
     */
    @Override
    public DelSupplierContact selectDelSupplierContactById(Long id)
    {
        return delSupplierContactMapper.selectDelSupplierContactById(id);
    }

    /**
     * 查询供应商人员列表
     * 
     * @param delSupplierContact 供应商人员
     * @return 供应商人员
     */
    @Override
    public List<DelSupplierContact> selectDelSupplierContactList(DelSupplierContact delSupplierContact)
    {
        return delSupplierContactMapper.selectDelSupplierContactList(delSupplierContact);
    }

    /**
     * 新增供应商人员
     * 
     * @param delSupplierContact 供应商人员
     * @return 结果
     */
    @Override
    public int insertDelSupplierContact(DelSupplierContact delSupplierContact)
    {
        //创建戳
        delSupplierContact.setCreateBy(SecurityUtils.getUsername());
        delSupplierContact.setCreateTime(DateUtils.getNowDate());
        delSupplierContact.setUpdateBy(SecurityUtils.getUsername());
        delSupplierContact.setUpdateTime(DateUtils.getNowDate());
        return delSupplierContactMapper.insertDelSupplierContact(delSupplierContact);
    }

    /**
     * 修改供应商人员
     * 
     * @param delSupplierContact 供应商人员
     * @return 结果
     */
    @Override
    public int updateDelSupplierContact(DelSupplierContact delSupplierContact)
    {
        delSupplierContact.refreshUpdatingStamp();
        return delSupplierContactMapper.updateDelSupplierContact(delSupplierContact);
    }

    /**
     * 批量删除供应商人员
     * 
     * @param ids 需要删除的供应商人员主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierContactByIds(Long[] ids)
    {
        return delSupplierContactMapper.deleteDelSupplierContactByIds(ids);
    }

    /**
     * 删除供应商人员信息
     * 
     * @param id 供应商人员主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierContactById(Long id)
    {
        return delSupplierContactMapper.deleteDelSupplierContactById(id);
    }


}
