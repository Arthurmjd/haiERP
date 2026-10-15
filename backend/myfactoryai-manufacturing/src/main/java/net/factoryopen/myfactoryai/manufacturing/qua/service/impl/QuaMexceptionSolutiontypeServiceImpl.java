package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaMexceptionSolutiontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaMexceptionSolutiontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质量异常处理类型Service业务层处理
 * 
 * @author admin
 * @date 2024-11-07
 */
@Service
public class QuaMexceptionSolutiontypeServiceImpl implements IQuaMexceptionSolutiontypeService 
{
    @Autowired(required = false)
    private QuaMexceptionSolutiontypeMapper quaMexceptionSolutiontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 质量异常处理类型
     */
    @Override
    public QuaMexceptionSolutiontype selectQuaMexceptionSolutiontypeById(Long id)
    {
        return quaMexceptionSolutiontypeMapper.selectQuaMexceptionSolutiontypeById(id);
    }

    /**
     * 查询质量异常处理类型列表
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 质量异常处理类型
     */
    @Override
    public List<QuaMexceptionSolutiontype> selectQuaMexceptionSolutiontypeList(QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        return quaMexceptionSolutiontypeMapper.selectQuaMexceptionSolutiontypeList(quaMexceptionSolutiontype);
    }

    /**
     * 新增质量异常处理类型
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    @Override
    public int insertQuaMexceptionSolutiontype(QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        quaMexceptionSolutiontype.setCreateTime(DateUtils.getNowDate());
        return quaMexceptionSolutiontypeMapper.insertQuaMexceptionSolutiontype(quaMexceptionSolutiontype);
    }

    /**
     * 修改质量异常处理类型
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    @Override
    public int updateQuaMexceptionSolutiontype(QuaMexceptionSolutiontype quaMexceptionSolutiontype)
    {
        quaMexceptionSolutiontype.setUpdateTime(DateUtils.getNowDate());
        return quaMexceptionSolutiontypeMapper.updateQuaMexceptionSolutiontype(quaMexceptionSolutiontype);
    }

    /**
     * 批量删除质量异常处理类型
     * 
     * @param ids 需要删除的质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionSolutiontypeByIds(Long[] ids)
    {
        return quaMexceptionSolutiontypeMapper.deleteQuaMexceptionSolutiontypeByIds(ids);
    }

    /**
     * 删除质量异常处理类型信息
     * 
     * @param id 质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaMexceptionSolutiontypeById(Long id)
    {
        return quaMexceptionSolutiontypeMapper.deleteQuaMexceptionSolutiontypeById(id);
    }


}
