package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionReasontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionReasontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 异常原因分类Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class QuaExceptionReasontypeServiceImpl implements IQuaExceptionReasontypeService 
{
    @Autowired(required = false)
    private QuaExceptionReasontypeMapper quaExceptionReasontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询异常原因分类
     * 
     * @param id 异常原因分类主键
     * @return 异常原因分类
     */
    @Override
    public QuaExceptionReasontype selectQuaExceptionReasontypeById(Long id)
    {
        return quaExceptionReasontypeMapper.selectQuaExceptionReasontypeById(id);
    }

    /**
     * 查询异常原因分类列表
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 异常原因分类
     */
    @Override
    public List<QuaExceptionReasontype> selectQuaExceptionReasontypeList(QuaExceptionReasontype quaExceptionReasontype)
    {
        return quaExceptionReasontypeMapper.selectQuaExceptionReasontypeList(quaExceptionReasontype);
    }

    /**
     * 新增异常原因分类
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 结果
     */
    @Override
    public int insertQuaExceptionReasontype(QuaExceptionReasontype quaExceptionReasontype)
    {
        quaExceptionReasontype.setCreateTime(DateUtils.getNowDate());
        return quaExceptionReasontypeMapper.insertQuaExceptionReasontype(quaExceptionReasontype);
    }

    /**
     * 修改异常原因分类
     * 
     * @param quaExceptionReasontype 异常原因分类
     * @return 结果
     */
    @Override
    public int updateQuaExceptionReasontype(QuaExceptionReasontype quaExceptionReasontype)
    {
        quaExceptionReasontype.setUpdateTime(DateUtils.getNowDate());
        return quaExceptionReasontypeMapper.updateQuaExceptionReasontype(quaExceptionReasontype);
    }

    /**
     * 批量删除异常原因分类
     * 
     * @param ids 需要删除的异常原因分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionReasontypeByIds(Long[] ids)
    {
        return quaExceptionReasontypeMapper.deleteQuaExceptionReasontypeByIds(ids);
    }

    /**
     * 删除异常原因分类信息
     * 
     * @param id 异常原因分类主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionReasontypeById(Long id)
    {
        return quaExceptionReasontypeMapper.deleteQuaExceptionReasontypeById(id);
    }


}
