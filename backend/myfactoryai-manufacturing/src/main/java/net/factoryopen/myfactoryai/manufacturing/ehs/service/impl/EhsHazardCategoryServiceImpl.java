package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsHazardCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsHazardCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 安全风险分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsHazardCategoryServiceImpl implements IEhsHazardCategoryService 
{
    @Autowired(required = false)
    private EhsHazardCategoryMapper ehsHazardCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询安全风险分类
     * 
     * @param id 安全风险分类主键
     * @return 安全风险分类
     */
    @Override
    public EhsHazardCategory selectEhsHazardCategoryById(Long id)
    {
        return ehsHazardCategoryMapper.selectEhsHazardCategoryById(id);
    }

    /**
     * 查询安全风险分类列表
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 安全风险分类
     */
    @Override
    public List<EhsHazardCategory> selectEhsHazardCategoryList(EhsHazardCategory ehsHazardCategory)
    {
        return ehsHazardCategoryMapper.selectEhsHazardCategoryList(ehsHazardCategory);
    }

    /**
     * 新增安全风险分类
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 结果
     */
    @Override
    public int insertEhsHazardCategory(EhsHazardCategory ehsHazardCategory)
    {
        ehsHazardCategory.setCreateTime(DateUtils.getNowDate());
        return ehsHazardCategoryMapper.insertEhsHazardCategory(ehsHazardCategory);
    }

    /**
     * 修改安全风险分类
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 结果
     */
    @Override
    public int updateEhsHazardCategory(EhsHazardCategory ehsHazardCategory)
    {
        ehsHazardCategory.setUpdateTime(DateUtils.getNowDate());
        return ehsHazardCategoryMapper.updateEhsHazardCategory(ehsHazardCategory);
    }

    /**
     * 批量删除安全风险分类
     * 
     * @param ids 需要删除的安全风险分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardCategoryByIds(Long[] ids)
    {
        return ehsHazardCategoryMapper.deleteEhsHazardCategoryByIds(ids);
    }

    /**
     * 删除安全风险分类信息
     * 
     * @param id 安全风险分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardCategoryById(Long id)
    {
        return ehsHazardCategoryMapper.deleteEhsHazardCategoryById(id);
    }


}
