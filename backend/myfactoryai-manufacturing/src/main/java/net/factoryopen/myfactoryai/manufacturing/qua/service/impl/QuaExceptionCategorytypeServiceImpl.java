package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionCategorytypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionCategorytypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 异常分类管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class QuaExceptionCategorytypeServiceImpl implements IQuaExceptionCategorytypeService 
{
    @Autowired(required = false)
    private QuaExceptionCategorytypeMapper quaExceptionCategorytypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询异常分类管理
     * 
     * @param id 异常分类管理主键
     * @return 异常分类管理
     */
    @Override
    public QuaExceptionCategorytype selectQuaExceptionCategorytypeById(Long id)
    {
        return quaExceptionCategorytypeMapper.selectQuaExceptionCategorytypeById(id);
    }

    /**
     * 查询异常分类管理列表
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 异常分类管理
     */
    @Override
    public List<QuaExceptionCategorytype> selectQuaExceptionCategorytypeList(QuaExceptionCategorytype quaExceptionCategorytype)
    {
        return quaExceptionCategorytypeMapper.selectQuaExceptionCategorytypeList(quaExceptionCategorytype);
    }

    /**
     * 新增异常分类管理
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 结果
     */
    @Override
    public int insertQuaExceptionCategorytype(QuaExceptionCategorytype quaExceptionCategorytype)
    {
        quaExceptionCategorytype.setCreateTime(DateUtils.getNowDate());
        return quaExceptionCategorytypeMapper.insertQuaExceptionCategorytype(quaExceptionCategorytype);
    }

    /**
     * 修改异常分类管理
     * 
     * @param quaExceptionCategorytype 异常分类管理
     * @return 结果
     */
    @Override
    public int updateQuaExceptionCategorytype(QuaExceptionCategorytype quaExceptionCategorytype)
    {
        quaExceptionCategorytype.setUpdateTime(DateUtils.getNowDate());
        return quaExceptionCategorytypeMapper.updateQuaExceptionCategorytype(quaExceptionCategorytype);
    }

    /**
     * 批量删除异常分类管理
     * 
     * @param ids 需要删除的异常分类管理主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionCategorytypeByIds(Long[] ids)
    {
        return quaExceptionCategorytypeMapper.deleteQuaExceptionCategorytypeByIds(ids);
    }

    /**
     * 删除异常分类管理信息
     * 
     * @param id 异常分类管理主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionCategorytypeById(Long id)
    {
        return quaExceptionCategorytypeMapper.deleteQuaExceptionCategorytypeById(id);
    }


}
