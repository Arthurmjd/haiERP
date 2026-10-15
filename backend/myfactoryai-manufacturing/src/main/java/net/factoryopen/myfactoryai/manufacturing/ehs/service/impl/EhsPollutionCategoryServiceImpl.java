package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPollutionCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 污染排放分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPollutionCategoryServiceImpl implements IEhsPollutionCategoryService 
{
    @Autowired(required = false)
    private EhsPollutionCategoryMapper ehsPollutionCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询污染排放分类
     * 
     * @param id 污染排放分类主键
     * @return 污染排放分类
     */
    @Override
    public EhsPollutionCategory selectEhsPollutionCategoryById(Long id)
    {
        return ehsPollutionCategoryMapper.selectEhsPollutionCategoryById(id);
    }

    /**
     * 查询污染排放分类列表
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 污染排放分类
     */
    @Override
    public List<EhsPollutionCategory> selectEhsPollutionCategoryList(EhsPollutionCategory ehsPollutionCategory)
    {
        return ehsPollutionCategoryMapper.selectEhsPollutionCategoryList(ehsPollutionCategory);
    }

    /**
     * 新增污染排放分类
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 结果
     */
    @Override
    public int insertEhsPollutionCategory(EhsPollutionCategory ehsPollutionCategory)
    {
        ehsPollutionCategory.setCreateTime(DateUtils.getNowDate());
        return ehsPollutionCategoryMapper.insertEhsPollutionCategory(ehsPollutionCategory);
    }

    /**
     * 修改污染排放分类
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 结果
     */
    @Override
    public int updateEhsPollutionCategory(EhsPollutionCategory ehsPollutionCategory)
    {
        ehsPollutionCategory.setUpdateTime(DateUtils.getNowDate());
        return ehsPollutionCategoryMapper.updateEhsPollutionCategory(ehsPollutionCategory);
    }

    /**
     * 批量删除污染排放分类
     * 
     * @param ids 需要删除的污染排放分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionCategoryByIds(Long[] ids)
    {
        return ehsPollutionCategoryMapper.deleteEhsPollutionCategoryByIds(ids);
    }

    /**
     * 删除污染排放分类信息
     * 
     * @param id 污染排放分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionCategoryById(Long id)
    {
        return ehsPollutionCategoryMapper.deleteEhsPollutionCategoryById(id);
    }


}
