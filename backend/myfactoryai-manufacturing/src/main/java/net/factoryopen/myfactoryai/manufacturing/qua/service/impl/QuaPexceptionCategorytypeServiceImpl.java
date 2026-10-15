package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaPexceptionCategorytypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionCategorytype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionCategorytypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购异常分类Service业务层处理
 * 
 * @author admin
 * @date 2024-11-07
 */
@Service
public class QuaPexceptionCategorytypeServiceImpl implements IQuaPexceptionCategorytypeService 
{
    @Autowired(required = false)
    private QuaPexceptionCategorytypeMapper quaPexceptionCategorytypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购异常分类
     * 
     * @param id 采购异常分类主键
     * @return 采购异常分类
     */
    @Override
    public QuaPexceptionCategorytype selectQuaPexceptionCategorytypeById(Long id)
    {
        return quaPexceptionCategorytypeMapper.selectQuaPexceptionCategorytypeById(id);
    }

    /**
     * 查询采购异常分类列表
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 采购异常分类
     */
    @Override
    public List<QuaPexceptionCategorytype> selectQuaPexceptionCategorytypeList(QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        return quaPexceptionCategorytypeMapper.selectQuaPexceptionCategorytypeList(quaPexceptionCategorytype);
    }

    /**
     * 新增采购异常分类
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 结果
     */
    @Override
    public int insertQuaPexceptionCategorytype(QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        quaPexceptionCategorytype.setCreateTime(DateUtils.getNowDate());
        return quaPexceptionCategorytypeMapper.insertQuaPexceptionCategorytype(quaPexceptionCategorytype);
    }

    /**
     * 修改采购异常分类
     * 
     * @param quaPexceptionCategorytype 采购异常分类
     * @return 结果
     */
    @Override
    public int updateQuaPexceptionCategorytype(QuaPexceptionCategorytype quaPexceptionCategorytype)
    {
        quaPexceptionCategorytype.setUpdateTime(DateUtils.getNowDate());
        return quaPexceptionCategorytypeMapper.updateQuaPexceptionCategorytype(quaPexceptionCategorytype);
    }

    /**
     * 批量删除采购异常分类
     * 
     * @param ids 需要删除的采购异常分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionCategorytypeByIds(Long[] ids)
    {
        return quaPexceptionCategorytypeMapper.deleteQuaPexceptionCategorytypeByIds(ids);
    }

    /**
     * 删除采购异常分类信息
     * 
     * @param id 采购异常分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionCategorytypeById(Long id)
    {
        return quaPexceptionCategorytypeMapper.deleteQuaPexceptionCategorytypeById(id);
    }


}
