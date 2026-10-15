package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaPexceptionSolutiontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionSolutiontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购质量异常处理类型Service业务层处理
 * 
 * @author admin
 * @date 2024-11-07
 */
@Service
public class QuaPexceptionSolutiontypeServiceImpl implements IQuaPexceptionSolutiontypeService 
{
    @Autowired(required = false)
    private QuaPexceptionSolutiontypeMapper quaPexceptionSolutiontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购质量异常处理类型
     * 
     * @param id 采购质量异常处理类型主键
     * @return 采购质量异常处理类型
     */
    @Override
    public QuaPexceptionSolutiontype selectQuaPexceptionSolutiontypeById(Long id)
    {
        return quaPexceptionSolutiontypeMapper.selectQuaPexceptionSolutiontypeById(id);
    }

    /**
     * 查询采购质量异常处理类型列表
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 采购质量异常处理类型
     */
    @Override
    public List<QuaPexceptionSolutiontype> selectQuaPexceptionSolutiontypeList(QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        return quaPexceptionSolutiontypeMapper.selectQuaPexceptionSolutiontypeList(quaPexceptionSolutiontype);
    }

    /**
     * 新增采购质量异常处理类型
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 结果
     */
    @Override
    public int insertQuaPexceptionSolutiontype(QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        quaPexceptionSolutiontype.setCreateTime(DateUtils.getNowDate());
        return quaPexceptionSolutiontypeMapper.insertQuaPexceptionSolutiontype(quaPexceptionSolutiontype);
    }

    /**
     * 修改采购质量异常处理类型
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 结果
     */
    @Override
    public int updateQuaPexceptionSolutiontype(QuaPexceptionSolutiontype quaPexceptionSolutiontype)
    {
        quaPexceptionSolutiontype.setUpdateTime(DateUtils.getNowDate());
        return quaPexceptionSolutiontypeMapper.updateQuaPexceptionSolutiontype(quaPexceptionSolutiontype);
    }

    /**
     * 批量删除采购质量异常处理类型
     * 
     * @param ids 需要删除的采购质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionSolutiontypeByIds(Long[] ids)
    {
        return quaPexceptionSolutiontypeMapper.deleteQuaPexceptionSolutiontypeByIds(ids);
    }

    /**
     * 删除采购质量异常处理类型信息
     * 
     * @param id 采购质量异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionSolutiontypeById(Long id)
    {
        return quaPexceptionSolutiontypeMapper.deleteQuaPexceptionSolutiontypeById(id);
    }


}
