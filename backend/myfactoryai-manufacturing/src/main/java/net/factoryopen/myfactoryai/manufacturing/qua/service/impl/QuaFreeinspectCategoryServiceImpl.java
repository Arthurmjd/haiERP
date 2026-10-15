package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaFreeinspectCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectCategory;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 免检品类Service业务层处理
 * 
 * @author admin
 * @date 2024-11-12
 */
@Service
public class QuaFreeinspectCategoryServiceImpl implements IQuaFreeinspectCategoryService 
{
    @Autowired(required = false)
    private QuaFreeinspectCategoryMapper quaFreeinspectCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询免检品类
     * 
     * @param id 免检品类主键
     * @return 免检品类
     */
    @Override
    public QuaFreeinspectCategory selectQuaFreeinspectCategoryById(Long id)
    {
        return quaFreeinspectCategoryMapper.selectQuaFreeinspectCategoryById(id);
    }

    /**
     * 查询免检品类列表
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 免检品类
     */
    @Override
    public List<QuaFreeinspectCategory> selectQuaFreeinspectCategoryList(QuaFreeinspectCategory quaFreeinspectCategory)
    {
        return quaFreeinspectCategoryMapper.selectQuaFreeinspectCategoryList(quaFreeinspectCategory);
    }

    /**
     * 新增免检品类
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 结果
     */
    @Override
    public int insertQuaFreeinspectCategory(QuaFreeinspectCategory quaFreeinspectCategory)
    {
        quaFreeinspectCategory.setCreateTime(DateUtils.getNowDate());
        return quaFreeinspectCategoryMapper.insertQuaFreeinspectCategory(quaFreeinspectCategory);
    }

    /**
     * 修改免检品类
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 结果
     */
    @Override
    public int updateQuaFreeinspectCategory(QuaFreeinspectCategory quaFreeinspectCategory)
    {
        quaFreeinspectCategory.setUpdateTime(DateUtils.getNowDate());
        return quaFreeinspectCategoryMapper.updateQuaFreeinspectCategory(quaFreeinspectCategory);
    }

    /**
     * 批量删除免检品类
     * 
     * @param ids 需要删除的免检品类主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectCategoryByIds(Long[] ids)
    {
        return quaFreeinspectCategoryMapper.deleteQuaFreeinspectCategoryByIds(ids);
    }

    /**
     * 删除免检品类信息
     * 
     * @param id 免检品类主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectCategoryById(Long id)
    {
        return quaFreeinspectCategoryMapper.deleteQuaFreeinspectCategoryById(id);
    }


}
