package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsWaterFoodieCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsWaterFoodieCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsWaterFoodieCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 水耗设备分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsWaterFoodieCategoryServiceImpl implements IEhsWaterFoodieCategoryService 
{
    @Autowired(required = false)
    private EhsWaterFoodieCategoryMapper ehsWaterFoodieCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询水耗设备分类
     * 
     * @param id 水耗设备分类主键
     * @return 水耗设备分类
     */
    @Override
    public EhsWaterFoodieCategory selectEhsWaterFoodieCategoryById(Long id)
    {
        return ehsWaterFoodieCategoryMapper.selectEhsWaterFoodieCategoryById(id);
    }

    /**
     * 查询水耗设备分类列表
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 水耗设备分类
     */
    @Override
    public List<EhsWaterFoodieCategory> selectEhsWaterFoodieCategoryList(EhsWaterFoodieCategory ehsWaterFoodieCategory)
    {
        return ehsWaterFoodieCategoryMapper.selectEhsWaterFoodieCategoryList(ehsWaterFoodieCategory);
    }

    /**
     * 新增水耗设备分类
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 结果
     */
    @Override
    public int insertEhsWaterFoodieCategory(EhsWaterFoodieCategory ehsWaterFoodieCategory)
    {
        ehsWaterFoodieCategory.setCreateTime(DateUtils.getNowDate());
        return ehsWaterFoodieCategoryMapper.insertEhsWaterFoodieCategory(ehsWaterFoodieCategory);
    }

    /**
     * 修改水耗设备分类
     * 
     * @param ehsWaterFoodieCategory 水耗设备分类
     * @return 结果
     */
    @Override
    public int updateEhsWaterFoodieCategory(EhsWaterFoodieCategory ehsWaterFoodieCategory)
    {
        ehsWaterFoodieCategory.setUpdateTime(DateUtils.getNowDate());
        return ehsWaterFoodieCategoryMapper.updateEhsWaterFoodieCategory(ehsWaterFoodieCategory);
    }

    /**
     * 批量删除水耗设备分类
     * 
     * @param ids 需要删除的水耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterFoodieCategoryByIds(Long[] ids)
    {
        return ehsWaterFoodieCategoryMapper.deleteEhsWaterFoodieCategoryByIds(ids);
    }

    /**
     * 删除水耗设备分类信息
     * 
     * @param id 水耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsWaterFoodieCategoryById(Long id)
    {
        return ehsWaterFoodieCategoryMapper.deleteEhsWaterFoodieCategoryById(id);
    }


}
