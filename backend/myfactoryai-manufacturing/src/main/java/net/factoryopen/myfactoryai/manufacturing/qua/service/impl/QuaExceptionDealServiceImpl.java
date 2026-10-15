package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.qua.QuaExceptionDealAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaExceptionDealMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaExceptionDealService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质量异常判处Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class QuaExceptionDealServiceImpl implements IQuaExceptionDealService 
{
    @Autowired(required = false)
    private QuaExceptionDealMapper quaExceptionDealMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private QuaExceptionDealAlgorithm quaExceptionDealAlgorithm;

    /**
     * 查询质量异常判处
     * 
     * @param id 质量异常判处主键
     * @return 质量异常判处
     */
    @Override
    public QuaExceptionDeal selectQuaExceptionDealById(Long id)
    {
        return quaExceptionDealMapper.selectQuaExceptionDealById(id);
    }

    /**
     * 查询质量异常判处列表
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 质量异常判处
     */
    @Override
    public List<QuaExceptionDeal> selectQuaExceptionDealList(QuaExceptionDeal quaExceptionDeal)
    {
        return quaExceptionDealMapper.selectQuaExceptionDealList(quaExceptionDeal);
    }

    /**
     * 新增质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    @Override
    public int insertQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal)
    {
        quaExceptionDeal.setCreateTime(DateUtils.getNowDate());
        return quaExceptionDealMapper.insertQuaExceptionDeal(quaExceptionDeal);
    }

    /**
     * 修改质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    @Override
    public int updateQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal)
    {
        quaExceptionDeal.setUpdateTime(DateUtils.getNowDate());
        return quaExceptionDealMapper.updateQuaExceptionDeal(quaExceptionDeal);
    }

    /**
     * 批量删除质量异常判处
     * 
     * @param ids 需要删除的质量异常判处主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionDealByIds(Long[] ids)
    {
        return quaExceptionDealMapper.deleteQuaExceptionDealByIds(ids);
    }

    /**
     * 删除质量异常判处信息
     * 
     * @param id 质量异常判处主键
     * @return 结果
     */
    @Override
    public int deleteQuaExceptionDealById(Long id)
    {
        return quaExceptionDealMapper.deleteQuaExceptionDealById(id);
    }

    /**
     * 判处
     *
     * @param quaExceptionDeal 判处
     * @return 结果
     */
    @Override
    public int judge(QuaExceptionDeal quaExceptionDeal) throws Exception{
        try{
            return quaExceptionDealAlgorithm.judge(quaExceptionDeal);
        }catch (Exception ex){
            throw ex;
        }
    }

}
