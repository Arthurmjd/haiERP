package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionSolutiontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionSolutiontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质量异常处理类型Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class QuaExceptionSolutiontypeServiceImpl implements IQuaExceptionSolutiontypeService 
{
    @Autowired(required = false)
    private QuaExceptionSolutiontypeMapper quaExceptionSolutiontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 质量异常处理类型
     */
    @Override
    public QuaExceptionSolutiontype selectQuaExceptionSolutiontypeById(Long id)
    {
        return quaExceptionSolutiontypeMapper.selectQuaExceptionSolutiontypeById(id);
    }

    /**
     * 查询质量异常处理类型列表
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 质量异常处理类型
     */
    @Override
    public List<QuaExceptionSolutiontype> selectQuaExceptionSolutiontypeList(QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        return quaExceptionSolutiontypeMapper.selectQuaExceptionSolutiontypeList(quaExceptionSolutiontype);
    }

    /**
     * 新增质量异常处理类型
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    @Override
    public int insertQuaExceptionSolutiontype(QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        quaExceptionSolutiontype.setCreateTime(DateUtils.getNowDate());
        return quaExceptionSolutiontypeMapper.insertQuaExceptionSolutiontype(quaExceptionSolutiontype);
    }

    /**
     * 修改质量异常处理类型
     * 
     * @param quaExceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    @Override
    public int updateQuaExceptionSolutiontype(QuaExceptionSolutiontype quaExceptionSolutiontype)
    {
        quaExceptionSolutiontype.setUpdateTime(DateUtils.getNowDate());
        return quaExceptionSolutiontypeMapper.updateQuaExceptionSolutiontype(quaExceptionSolutiontype);
    }

    /**
     * 批量删除质量异常处理类型
     * 
     * @param ids 需要删除的质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionSolutiontypeByIds(Long[] ids)
    {
        return quaExceptionSolutiontypeMapper.deleteQuaExceptionSolutiontypeByIds(ids);
    }

    /**
     * 删除质量异常处理类型信息
     * 
     * @param id 质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionSolutiontypeById(Long id)
    {
        return quaExceptionSolutiontypeMapper.deleteQuaExceptionSolutiontypeById(id);
    }


}
