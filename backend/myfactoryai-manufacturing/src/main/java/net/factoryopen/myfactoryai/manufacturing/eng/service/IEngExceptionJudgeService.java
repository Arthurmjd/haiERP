package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionJudge;

/**
 * 工艺异常判处Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEngExceptionJudgeService 
{
    /**
     * 查询工艺异常判处
     * 
     * @param id 工艺异常判处主键
     * @return 工艺异常判处
     */
    public EngExceptionJudge selectEngExceptionJudgeById(Long id);

    /**
     * 查询工艺异常判处列表
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 工艺异常判处集合
     */
    public List<EngExceptionJudge> selectEngExceptionJudgeList(EngExceptionJudge engExceptionJudge);

    /**
     * 新增工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    public int insertEngExceptionJudge(EngExceptionJudge engExceptionJudge);

    /**
     * 修改工艺异常判处
     * 
     * @param engExceptionJudge 工艺异常判处
     * @return 结果
     */
    public int updateEngExceptionJudge(EngExceptionJudge engExceptionJudge);

    /**
     * 批量删除工艺异常判处
     * 
     * @param ids 需要删除的工艺异常判处主键集合
     * @return 结果
     */
    public int deleteEngExceptionJudgeByIds(Long[] ids);

    /**
     * 删除工艺异常判处信息
     * 
     * @param id 工艺异常判处主键
     * @return 结果
     */
    public int deleteEngExceptionJudgeById(Long id);

    /**
     * 判处
     *
     * @param engExceptionJudge 判处
     * @return 结果
     */
    public int judge(EngExceptionJudge engExceptionJudge) throws Exception;


}
