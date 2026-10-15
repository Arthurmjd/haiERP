package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaMexceptionReasontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionReasontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 异常原因分类Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-11-07
 */
@Service
public class QuaMexceptionReasontypeServiceImpl implements IQuaMexceptionReasontypeService 
{
    @Autowired(required = false)
    private QuaMexceptionReasontypeMapper quaMexceptionReasontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 异常原因分类
     */
    @Override
    public QuaMexceptionReasontype selectQuaMexceptionReasontypeById(Long id)
    {
        return quaMexceptionReasontypeMapper.selectQuaMexceptionReasontypeById(id);
    }

    /**
     * 查询异常原因分类列表
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 异常原因分类
     */
    @Override
    public List<QuaMexceptionReasontype> selectQuaMexceptionReasontypeList(QuaMexceptionReasontype quaMexceptionReasontype)
    {
        return quaMexceptionReasontypeMapper.selectQuaMexceptionReasontypeList(quaMexceptionReasontype);
    }

    /**
     * 新增异常原因分类
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 结果
     */
    @Override
    public int insertQuaMexceptionReasontype(QuaMexceptionReasontype quaMexceptionReasontype)
    {
        quaMexceptionReasontype.setCreateTime(DateUtils.getNowDate());
        return quaMexceptionReasontypeMapper.insertQuaMexceptionReasontype(quaMexceptionReasontype);
    }

    /**
     * 修改异常原因分类
     * 
     * @param quaMexceptionReasontype 异常原因分类
     * @return 结果
     */
    @Override
    public int updateQuaMexceptionReasontype(QuaMexceptionReasontype quaMexceptionReasontype)
    {
        quaMexceptionReasontype.setUpdateTime(DateUtils.getNowDate());
        return quaMexceptionReasontypeMapper.updateQuaMexceptionReasontype(quaMexceptionReasontype);
    }

    /**
     * 批量删除异常原因分类
     * 
     * @param ids 需要删除的异常原因分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionReasontypeByIds(Long[] ids)
    {
        return quaMexceptionReasontypeMapper.deleteQuaMexceptionReasontypeByIds(ids);
    }

    /**
     * 删除异常原因分类信息
     * 
     * @param id 异常原因分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionReasontypeById(Long id)
    {
        return quaMexceptionReasontypeMapper.deleteQuaMexceptionReasontypeById(id);
    }


}
