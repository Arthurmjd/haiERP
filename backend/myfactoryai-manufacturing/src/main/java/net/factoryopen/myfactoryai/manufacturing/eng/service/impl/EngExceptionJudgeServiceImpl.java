package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.eng.EngExceptionJudgeAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngExceptionJudgeMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionJudge;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionJudgeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常判处Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EngExceptionJudgeServiceImpl implements IEngExceptionJudgeService 
{
    @Autowired(required = false)
    private EngExceptionJudgeMapper engExceptionJudgeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private EngExceptionJudgeAlgorithm engExceptionJudgeAlgorithm;

    /**
     * 查询工艺异常判处
     * 
     * @param id 工艺异常判处主键
     * @return 工艺异常判处
     */
    @Override
    public EngExceptionJudge selectEngExceptionJudgeById(Long id)
    {
        return engExceptionJudgeMapper.selectEngExceptionJudgeById(id);
    }

    /**
     * 查询工艺异常判处列表
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 工艺异常判处
     */
    @Override
    public List<EngExceptionJudge> selectEngExceptionJudgeList(EngExceptionJudge engExceptionJudge)
    {
        return engExceptionJudgeMapper.selectEngExceptionJudgeList(engExceptionJudge);
    }

    /**
     * 新增工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    @Override
    public int insertEngExceptionJudge(EngExceptionJudge engExceptionJudge)
    {
        engExceptionJudge.setCreateTime(DateUtils.getNowDate());
        return engExceptionJudgeMapper.insertEngExceptionJudge(engExceptionJudge);
    }

    /**
     * 修改工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    @Override
    public int updateEngExceptionJudge(EngExceptionJudge engExceptionJudge)
    {
        engExceptionJudge.setUpdateTime(DateUtils.getNowDate());
        return engExceptionJudgeMapper.updateEngExceptionJudge(engExceptionJudge);
    }

    /**
     * 批量删除工艺异常判处
     * 
     * @param ids 需要删除的工艺异常判处主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionJudgeByIds(Long[] ids)
    {
        return engExceptionJudgeMapper.deleteEngExceptionJudgeByIds(ids);
    }

    /**
     * 删除工艺异常判处信息
     * 
     * @param id 工艺异常判处主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionJudgeById(Long id)
    {
        return engExceptionJudgeMapper.deleteEngExceptionJudgeById(id);
    }

    /**
     * 判处
     *
     * @param engExceptionJudge 判处
     * @return 结果
     */
    @Override
    public int judge(EngExceptionJudge engExceptionJudge) throws Exception{
        try{
            return engExceptionJudgeAlgorithm.judge(engExceptionJudge);
        }catch (Exception ex){
            throw ex;
        }
    }

}
