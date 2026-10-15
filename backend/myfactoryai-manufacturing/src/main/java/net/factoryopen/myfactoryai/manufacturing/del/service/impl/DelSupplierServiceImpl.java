package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelSupplierMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplier;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelSupplierService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外协厂商管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class DelSupplierServiceImpl implements IDelSupplierService 
{
    @Autowired(required = false)
    private DelSupplierMapper delSupplierMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询外协厂商管理
     * 
     * @param id 外协厂商管理主键
     * @return 外协厂商管理
     */
    @Override
    public DelSupplier selectDelSupplierById(Long id)
    {
        return delSupplierMapper.selectDelSupplierById(id);
    }

    /**
     * 查询外协厂商管理列表
     * 
     * @param delSupplier 外协厂商管理
     * @return 外协厂商管理
     */
    @Override
    public List<DelSupplier> selectDelSupplierList(DelSupplier delSupplier)
    {
        return delSupplierMapper.selectDelSupplierList(delSupplier);
    }

    /**
     * 新增外协厂商管理
     * 
     * @param delSupplier 外协厂商管理
     * @return 结果
     */
    @Override
    public int insertDelSupplier(DelSupplier delSupplier)
    {
        //创建戳
        delSupplier.setCreateBy(SecurityUtils.getUsername());
        delSupplier.setCreateTime(DateUtils.getNowDate());
        delSupplier.setUpdateBy(SecurityUtils.getUsername());
        delSupplier.setUpdateTime(DateUtils.getNowDate());
        return delSupplierMapper.insertDelSupplier(delSupplier);
    }

    /**
     * 修改外协厂商管理
     * 
     * @param delSupplier 外协厂商管理
     * @return 结果
     */
    @Override
    public int updateDelSupplier(DelSupplier delSupplier)
    {
        delSupplier.refreshUpdatingStamp();
        return delSupplierMapper.updateDelSupplier(delSupplier);
    }

    /**
     * 批量删除外协厂商管理
     * 
     * @param ids 需要删除的外协厂商管理主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierByIds(Long[] ids)
    {
        return delSupplierMapper.deleteDelSupplierByIds(ids);
    }

    /**
     * 删除外协厂商管理信息
     * 
     * @param id 外协厂商管理主键
     * @return 结果
     */
    @Override
    public int deleteDelSupplierById(Long id)
    {
        return delSupplierMapper.deleteDelSupplierById(id);
    }


}
