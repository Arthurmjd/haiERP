package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProcessCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcessCategory;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProcessCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制程数据Service业务层处理
 * 
 * @author generator
 * @date 2026-02-20
 */
@Service
public class EngProcessCategoryServiceImpl implements IEngProcessCategoryService 
{
    @Autowired(required = false)
    private EngProcessCategoryMapper engProcessCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询制程数据
     * 
     * @param id 制程数据主键
     * @return 制程数据
     */
    @Override
    public EngProcessCategory selectEngProcessCategoryById(Long id)
    {
        return engProcessCategoryMapper.selectEngProcessCategoryById(id);
    }

    /**
     * 查询制程数据列表
     * 
     * @param engProcessCategory 制程数据
     * @return 制程数据
     */
    @Override
    public List<EngProcessCategory> selectEngProcessCategoryList(EngProcessCategory engProcessCategory)
    {
        return engProcessCategoryMapper.selectEngProcessCategoryList(engProcessCategory);
    }

    /**
     * 新增制程数据
     * 
     * @param engProcessCategory 制程数据
     * @return 结果
     */
    @Override
    public int insertEngProcessCategory(EngProcessCategory engProcessCategory)
    {
        engProcessCategory.setCreateTime(DateUtils.getNowDate());
        return engProcessCategoryMapper.insertEngProcessCategory(engProcessCategory);
    }

    /**
     * 修改制程数据
     * 
     * @param engProcessCategory 制程数据
     * @return 结果
     */
    @Override
    public int updateEngProcessCategory(EngProcessCategory engProcessCategory)
    {
        engProcessCategory.setUpdateTime(DateUtils.getNowDate());
        return engProcessCategoryMapper.updateEngProcessCategory(engProcessCategory);
    }

    /**
     * 批量删除制程数据
     * 
     * @param ids 需要删除的制程数据主键
     * @return 结果
     */
    @Override
    public int deleteEngProcessCategoryByIds(Long[] ids)
    {
        return engProcessCategoryMapper.deleteEngProcessCategoryByIds(ids);
    }

    /**
     * 删除制程数据信息
     * 
     * @param id 制程数据主键
     * @return 结果
     */
    @Override
    public int deleteEngProcessCategoryById(Long id)
    {
        return engProcessCategoryMapper.deleteEngProcessCategoryById(id);
    }


}
