package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionDealResultMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDealResult;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionDealResultService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质量异常判处结果Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class QuaExceptionDealResultServiceImpl implements IQuaExceptionDealResultService 
{
    @Autowired(required = false)
    private QuaExceptionDealResultMapper quaExceptionDealResultMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询质量异常判处结果
     * 
     * @param id 质量异常判处结果主键
     * @return 质量异常判处结果
     */
    @Override
    public QuaExceptionDealResult selectQuaExceptionDealResultById(Long id)
    {
        return quaExceptionDealResultMapper.selectQuaExceptionDealResultById(id);
    }

    /**
     * 查询质量异常判处结果列表
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 质量异常判处结果
     */
    @Override
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultList(QuaExceptionDealResult quaExceptionDealResult)
    {
        return quaExceptionDealResultMapper.selectQuaExceptionDealResultList(quaExceptionDealResult);
    }

    /**
     * 新增质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    @Override
    public int insertQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult)
    {
        quaExceptionDealResult.setCreateTime(DateUtils.getNowDate());
        return quaExceptionDealResultMapper.insertQuaExceptionDealResult(quaExceptionDealResult);
    }

    /**
     * 修改质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    @Override
    public int updateQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult)
    {
        quaExceptionDealResult.setUpdateTime(DateUtils.getNowDate());
        return quaExceptionDealResultMapper.updateQuaExceptionDealResult(quaExceptionDealResult);
    }

    /**
     * 批量删除质量异常判处结果
     * 
     * @param ids 需要删除的质量异常判处结果主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionDealResultByIds(Long[] ids)
    {
        return quaExceptionDealResultMapper.deleteQuaExceptionDealResultByIds(ids);
    }

    /**
     * 删除质量异常判处结果信息
     * 
     * @param id 质量异常判处结果主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionDealResultById(Long id)
    {
        return quaExceptionDealResultMapper.deleteQuaExceptionDealResultById(id);
    }


}
