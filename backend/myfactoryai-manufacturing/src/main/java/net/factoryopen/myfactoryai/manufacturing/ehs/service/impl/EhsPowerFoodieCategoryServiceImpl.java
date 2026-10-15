package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPowerFoodieCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPowerFoodieCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPowerFoodieCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 电耗设备分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPowerFoodieCategoryServiceImpl implements IEhsPowerFoodieCategoryService 
{
    @Autowired(required = false)
    private EhsPowerFoodieCategoryMapper ehsPowerFoodieCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询电耗设备分类
     * 
     * @param id 电耗设备分类主键
     * @return 电耗设备分类
     */
    @Override
    public EhsPowerFoodieCategory selectEhsPowerFoodieCategoryById(Long id)
    {
        return ehsPowerFoodieCategoryMapper.selectEhsPowerFoodieCategoryById(id);
    }

    /**
     * 查询电耗设备分类列表
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 电耗设备分类
     */
    @Override
    public List<EhsPowerFoodieCategory> selectEhsPowerFoodieCategoryList(EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        return ehsPowerFoodieCategoryMapper.selectEhsPowerFoodieCategoryList(ehsPowerFoodieCategory);
    }

    /**
     * 新增电耗设备分类
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 结果
     */
    @Override
    public int insertEhsPowerFoodieCategory(EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        ehsPowerFoodieCategory.setCreateTime(DateUtils.getNowDate());
        return ehsPowerFoodieCategoryMapper.insertEhsPowerFoodieCategory(ehsPowerFoodieCategory);
    }

    /**
     * 修改电耗设备分类
     * 
     * @param ehsPowerFoodieCategory 电耗设备分类
     * @return 结果
     */
    @Override
    public int updateEhsPowerFoodieCategory(EhsPowerFoodieCategory ehsPowerFoodieCategory)
    {
        ehsPowerFoodieCategory.setUpdateTime(DateUtils.getNowDate());
        return ehsPowerFoodieCategoryMapper.updateEhsPowerFoodieCategory(ehsPowerFoodieCategory);
    }

    /**
     * 批量删除电耗设备分类
     * 
     * @param ids 需要删除的电耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerFoodieCategoryByIds(Long[] ids)
    {
        return ehsPowerFoodieCategoryMapper.deleteEhsPowerFoodieCategoryByIds(ids);
    }

    /**
     * 删除电耗设备分类信息
     * 
     * @param id 电耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsPowerFoodieCategoryById(Long id)
    {
        return ehsPowerFoodieCategoryMapper.deleteEhsPowerFoodieCategoryById(id);
    }


}
