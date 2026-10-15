package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResMachineTypeMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachineType;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResMachineTypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 机型Service业务层处理
 * 
 * @author admin
 * @date 2024-08-11
 */
@Service
public class ResMachineTypeServiceImpl implements IResMachineTypeService 
{
    @Autowired(required = false)
    private ResMachineTypeMapper resMachineTypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询机型
     * 
     * @param id 机型主键
     * @return 机型
     */
    @Override
    public ResMachineType selectResMachineTypeById(Long id)
    {
        return resMachineTypeMapper.selectResMachineTypeById(id);
    }

    /**
     * 查询机型列表
     * 
     * @param resMachineType 机型
     * @return 机型
     */
    @Override
    public List<ResMachineType> selectResMachineTypeList(ResMachineType resMachineType)
    {
        return resMachineTypeMapper.selectResMachineTypeList(resMachineType);
    }

    /**
     * 新增机型
     * 
     * @param resMachineType 机型
     * @return 结果
     */
    @Override
    public int insertResMachineType(ResMachineType resMachineType)
    {
        resMachineType.setCreateTime(DateUtils.getNowDate());
        return resMachineTypeMapper.insertResMachineType(resMachineType);
    }

    /**
     * 修改机型
     * 
     * @param resMachineType 机型
     * @return 结果
     */
    @Override
    public int updateResMachineType(ResMachineType resMachineType)
    {
        resMachineType.setUpdateTime(DateUtils.getNowDate());
        return resMachineTypeMapper.updateResMachineType(resMachineType);
    }

    /**
     * 批量删除机型
     * 
     * @param ids 需要删除的机型主键
     * @return 结果
     */
    @Override
    public int deleteResMachineTypeByIds(Long[] ids)
    {
        return resMachineTypeMapper.deleteResMachineTypeByIds(ids);
    }

    /**
     * 删除机型信息
     * 
     * @param id 机型主键
     * @return 结果
     */
    @Override
    public int deleteResMachineTypeById(Long id)
    {
        return resMachineTypeMapper.deleteResMachineTypeById(id);
    }


}
