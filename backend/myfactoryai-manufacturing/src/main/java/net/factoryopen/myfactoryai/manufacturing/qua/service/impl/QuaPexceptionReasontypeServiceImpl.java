package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaPexceptionReasontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaPexceptionReasontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 采购质量异常原因Service业务层处理
 * 
 * @author admin
 * @date 2024-11-07
 */
@Service
public class QuaPexceptionReasontypeServiceImpl implements IQuaPexceptionReasontypeService 
{
    @Autowired(required = false)
    private QuaPexceptionReasontypeMapper quaPexceptionReasontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询采购质量异常原因
     * 
     * @param id 采购质量异常原因主键
     * @return 采购质量异常原因
     */
    @Override
    public QuaPexceptionReasontype selectQuaPexceptionReasontypeById(Long id)
    {
        return quaPexceptionReasontypeMapper.selectQuaPexceptionReasontypeById(id);
    }

    /**
     * 查询采购质量异常原因列表
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 采购质量异常原因
     */
    @Override
    public List<QuaPexceptionReasontype> selectQuaPexceptionReasontypeList(QuaPexceptionReasontype quaPexceptionReasontype)
    {
        return quaPexceptionReasontypeMapper.selectQuaPexceptionReasontypeList(quaPexceptionReasontype);
    }

    /**
     * 新增采购质量异常原因
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 结果
     */
    @Override
    public int insertQuaPexceptionReasontype(QuaPexceptionReasontype quaPexceptionReasontype)
    {
        quaPexceptionReasontype.setCreateTime(DateUtils.getNowDate());
        return quaPexceptionReasontypeMapper.insertQuaPexceptionReasontype(quaPexceptionReasontype);
    }

    /**
     * 修改采购质量异常原因
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 结果
     */
    @Override
    public int updateQuaPexceptionReasontype(QuaPexceptionReasontype quaPexceptionReasontype)
    {
        quaPexceptionReasontype.setUpdateTime(DateUtils.getNowDate());
        return quaPexceptionReasontypeMapper.updateQuaPexceptionReasontype(quaPexceptionReasontype);
    }

    /**
     * 批量删除采购质量异常原因
     * 
     * @param ids 需要删除的采购质量异常原因主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionReasontypeByIds(Long[] ids)
    {
        return quaPexceptionReasontypeMapper.deleteQuaPexceptionReasontypeByIds(ids);
    }

    /**
     * 删除采购质量异常原因信息
     * 
     * @param id 采购质量异常原因主键
     * @return 结果
     */
    @Override
    public int deleteQuaPexceptionReasontypeById(Long id)
    {
        return quaPexceptionReasontypeMapper.deleteQuaPexceptionReasontypeById(id);
    }


}
