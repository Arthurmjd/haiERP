package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaMexceptionCategorytypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionCategorytypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制造异常分类Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-11-07
 */
@Service
public class QuaMexceptionCategorytypeServiceImpl implements IQuaMexceptionCategorytypeService 
{
    @Autowired(required = false)
    private QuaMexceptionCategorytypeMapper quaMexceptionCategorytypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询制造异常分类
     * 
     * @param id 制造异常分类主键
     * @return 制造异常分类
     */
    @Override
    public QuaMexceptionCategorytype selectQuaMexceptionCategorytypeById(Long id)
    {
        return quaMexceptionCategorytypeMapper.selectQuaMexceptionCategorytypeById(id);
    }

    /**
     * 查询制造异常分类列表
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 制造异常分类
     */
    @Override
    public List<QuaMexceptionCategorytype> selectQuaMexceptionCategorytypeList(QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        return quaMexceptionCategorytypeMapper.selectQuaMexceptionCategorytypeList(quaMexceptionCategorytype);
    }

    /**
     * 新增制造异常分类
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 结果
     */
    @Override
    public int insertQuaMexceptionCategorytype(QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        quaMexceptionCategorytype.setCreateTime(DateUtils.getNowDate());
        return quaMexceptionCategorytypeMapper.insertQuaMexceptionCategorytype(quaMexceptionCategorytype);
    }

    /**
     * 修改制造异常分类
     * 
     * @param quaMexceptionCategorytype 制造异常分类
     * @return 结果
     */
    @Override
    public int updateQuaMexceptionCategorytype(QuaMexceptionCategorytype quaMexceptionCategorytype)
    {
        quaMexceptionCategorytype.setUpdateTime(DateUtils.getNowDate());
        return quaMexceptionCategorytypeMapper.updateQuaMexceptionCategorytype(quaMexceptionCategorytype);
    }

    /**
     * 批量删除制造异常分类
     * 
     * @param ids 需要删除的制造异常分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionCategorytypeByIds(Long[] ids)
    {
        return quaMexceptionCategorytypeMapper.deleteQuaMexceptionCategorytypeByIds(ids);
    }

    /**
     * 删除制造异常分类信息
     * 
     * @param id 制造异常分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionCategorytypeById(Long id)
    {
        return quaMexceptionCategorytypeMapper.deleteQuaMexceptionCategorytypeById(id);
    }


}
