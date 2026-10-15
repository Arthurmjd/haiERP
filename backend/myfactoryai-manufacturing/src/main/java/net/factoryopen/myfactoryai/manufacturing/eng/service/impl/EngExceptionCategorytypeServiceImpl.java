package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngExceptionCategorytypeMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionCategorytypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常分类Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EngExceptionCategorytypeServiceImpl implements IEngExceptionCategorytypeService 
{
    @Autowired(required = false)
    private EngExceptionCategorytypeMapper engExceptionCategorytypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工艺异常分类
     * 
     * @param id 工艺异常分类主键
     * @return 工艺异常分类
     */
    @Override
    public EngExceptionCategorytype selectEngExceptionCategorytypeById(Long id)
    {
        return engExceptionCategorytypeMapper.selectEngExceptionCategorytypeById(id);
    }

    /**
     * 查询工艺异常分类列表
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 工艺异常分类
     */
    @Override
    public List<EngExceptionCategorytype> selectEngExceptionCategorytypeList(EngExceptionCategorytype engExceptionCategorytype)
    {
        return engExceptionCategorytypeMapper.selectEngExceptionCategorytypeList(engExceptionCategorytype);
    }

    /**
     * 新增工艺异常分类
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 结果
     */
    @Override
    public int insertEngExceptionCategorytype(EngExceptionCategorytype engExceptionCategorytype)
    {
        engExceptionCategorytype.setCreateTime(DateUtils.getNowDate());
        return engExceptionCategorytypeMapper.insertEngExceptionCategorytype(engExceptionCategorytype);
    }

    /**
     * 修改工艺异常分类
     * 
     * @param engExceptionCategorytype 工艺异常分类
     * @return 结果
     */
    @Override
    public int updateEngExceptionCategorytype(EngExceptionCategorytype engExceptionCategorytype)
    {
        engExceptionCategorytype.setUpdateTime(DateUtils.getNowDate());
        return engExceptionCategorytypeMapper.updateEngExceptionCategorytype(engExceptionCategorytype);
    }

    /**
     * 批量删除工艺异常分类
     * 
     * @param ids 需要删除的工艺异常分类主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionCategorytypeByIds(Long[] ids)
    {
        return engExceptionCategorytypeMapper.deleteEngExceptionCategorytypeByIds(ids);
    }

    /**
     * 删除工艺异常分类信息
     * 
     * @param id 工艺异常分类主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionCategorytypeById(Long id)
    {
        return engExceptionCategorytypeMapper.deleteEngExceptionCategorytypeById(id);
    }


}
