package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategory;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料分类管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class EngCategoryServiceImpl implements IEngCategoryService 
{
    @Autowired(required = false)
    private EngCategoryMapper engCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料分类管理
     * 
     * @param id 物料分类管理主键
     * @return 物料分类管理
     */
    @Override
    public EngCategory selectEngCategoryById(Long id)
    {
        return engCategoryMapper.selectEngCategoryById(id);
    }

    /**
     * 查询物料分类管理列表
     * 
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngCategoryList(engCategory);
    }

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngProductCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngProductCategoryList(engCategory);
    }

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngPureProductCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngPureProductCategoryList(engCategory);
    }

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngMaterialCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngMaterialCategoryList(engCategory);
    }

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngPureMaterialOrProductCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngPureMaterialOrProductCategoryList(engCategory);
    }

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理
     */
    @Override
    public List<EngCategory> selectEngUtilityCategoryList(EngCategory engCategory)
    {
        return engCategoryMapper.selectEngUtilityCategoryList(engCategory);
    }

    /**
     * 新增物料分类管理
     * 
     * @param engCategory 物料分类管理
     * @return 结果
     */
    @Override
    public int insertEngCategory(EngCategory engCategory)
    {
        //创建戳
        engCategory.setCreateBy(SecurityUtils.getUsername());
        engCategory.setCreateTime(DateUtils.getNowDate());
        engCategory.setUpdateBy(SecurityUtils.getUsername());
        engCategory.setUpdateTime(DateUtils.getNowDate());
        return engCategoryMapper.insertEngCategory(engCategory);
    }

    /**
     * 修改物料分类管理
     * 
     * @param engCategory 物料分类管理
     * @return 结果
     */
    @Override
    public int updateEngCategory(EngCategory engCategory)
    {
        engCategory.setUpdateTime(DateUtils.getNowDate());
        return engCategoryMapper.updateEngCategory(engCategory);
    }

    /**
     * 批量删除物料分类管理
     * 
     * @param ids 需要删除的物料分类管理主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryByIds(Long[] ids)
    {
        return engCategoryMapper.deleteEngCategoryByIds(ids);
    }

    /**
     * 删除物料分类管理信息
     * 
     * @param id 物料分类管理主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryById(Long id)
    {
        return engCategoryMapper.deleteEngCategoryById(id);
    }


}
