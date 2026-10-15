package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsGasFoodieCategoryMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodieCategory;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsGasFoodieCategoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 气耗设备分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsGasFoodieCategoryServiceImpl implements IEhsGasFoodieCategoryService 
{
    @Autowired(required = false)
    private EhsGasFoodieCategoryMapper ehsGasFoodieCategoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询气耗设备分类
     * 
     * @param id 气耗设备分类主键
     * @return 气耗设备分类
     */
    @Override
    public EhsGasFoodieCategory selectEhsGasFoodieCategoryById(Long id)
    {
        return ehsGasFoodieCategoryMapper.selectEhsGasFoodieCategoryById(id);
    }

    /**
     * 查询气耗设备分类列表
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 气耗设备分类
     */
    @Override
    public List<EhsGasFoodieCategory> selectEhsGasFoodieCategoryList(EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        return ehsGasFoodieCategoryMapper.selectEhsGasFoodieCategoryList(ehsGasFoodieCategory);
    }

    /**
     * 新增气耗设备分类
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 结果
     */
    @Override
    public int insertEhsGasFoodieCategory(EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        ehsGasFoodieCategory.setCreateTime(DateUtils.getNowDate());
        return ehsGasFoodieCategoryMapper.insertEhsGasFoodieCategory(ehsGasFoodieCategory);
    }

    /**
     * 修改气耗设备分类
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 结果
     */
    @Override
    public int updateEhsGasFoodieCategory(EhsGasFoodieCategory ehsGasFoodieCategory)
    {
        ehsGasFoodieCategory.setUpdateTime(DateUtils.getNowDate());
        return ehsGasFoodieCategoryMapper.updateEhsGasFoodieCategory(ehsGasFoodieCategory);
    }

    /**
     * 批量删除气耗设备分类
     * 
     * @param ids 需要删除的气耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasFoodieCategoryByIds(Long[] ids)
    {
        return ehsGasFoodieCategoryMapper.deleteEhsGasFoodieCategoryByIds(ids);
    }

    /**
     * 删除气耗设备分类信息
     * 
     * @param id 气耗设备分类主键
     * @return 结果
     */
    @Override
    public int deleteEhsGasFoodieCategoryById(Long id)
    {
        return ehsGasFoodieCategoryMapper.deleteEhsGasFoodieCategoryById(id);
    }


}
