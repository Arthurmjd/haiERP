package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngExceptionDealResultMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionDealResult;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionDealResultService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常判处结果Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EngExceptionDealResultServiceImpl implements IEngExceptionDealResultService 
{
    @Autowired(required = false)
    private EngExceptionDealResultMapper engExceptionDealResultMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工艺异常判处结果
     * 
     * @param id 工艺异常判处结果主键
     * @return 工艺异常判处结果
     */
    @Override
    public EngExceptionDealResult selectEngExceptionDealResultById(Long id)
    {
        return engExceptionDealResultMapper.selectEngExceptionDealResultById(id);
    }

    /**
     * 查询工艺异常判处结果列表
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 工艺异常判处结果
     */
    @Override
    public List<EngExceptionDealResult> selectEngExceptionDealResultList(EngExceptionDealResult engExceptionDealResult)
    {
        return engExceptionDealResultMapper.selectEngExceptionDealResultList(engExceptionDealResult);
    }

    /**
     * 新增工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    @Override
    public int insertEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult)
    {
        engExceptionDealResult.setCreateTime(DateUtils.getNowDate());
        return engExceptionDealResultMapper.insertEngExceptionDealResult(engExceptionDealResult);
    }

    /**
     * 修改工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    @Override
    public int updateEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult)
    {
        engExceptionDealResult.setUpdateTime(DateUtils.getNowDate());
        return engExceptionDealResultMapper.updateEngExceptionDealResult(engExceptionDealResult);
    }

    /**
     * 批量删除工艺异常判处结果
     * 
     * @param ids 需要删除的工艺异常判处结果主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionDealResultByIds(Long[] ids)
    {
        return engExceptionDealResultMapper.deleteEngExceptionDealResultByIds(ids);
    }

    /**
     * 删除工艺异常判处结果信息
     * 
     * @param id 工艺异常判处结果主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionDealResultById(Long id)
    {
        return engExceptionDealResultMapper.deleteEngExceptionDealResultById(id);
    }


}
