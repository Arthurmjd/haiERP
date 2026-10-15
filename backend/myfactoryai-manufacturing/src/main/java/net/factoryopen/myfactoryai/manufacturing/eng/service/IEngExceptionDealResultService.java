package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionDealResult;

/**
 * 工艺异常判处结果Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEngExceptionDealResultService 
{
    /**
     * 查询工艺异常判处结果
     * 
     * @param id 工艺异常判处结果主键
     * @return 工艺异常判处结果
     */
    public EngExceptionDealResult selectEngExceptionDealResultById(Long id);

    /**
     * 查询工艺异常判处结果列表
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 工艺异常判处结果集合
     */
    public List<EngExceptionDealResult> selectEngExceptionDealResultList(EngExceptionDealResult engExceptionDealResult);

    /**
     * 新增工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    public int insertEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult);

    /**
     * 修改工艺异常判处结果
     * 
     * @param engExceptionDealResult 工艺异常判处结果
     * @return 结果
     */
    public int updateEngExceptionDealResult(EngExceptionDealResult engExceptionDealResult);

    /**
     * 批量删除工艺异常判处结果
     * 
     * @param ids 需要删除的工艺异常判处结果主键集合
     * @return 结果
     */
    public int deleteEngExceptionDealResultByIds(Long[] ids);

    /**
     * 删除工艺异常判处结果信息
     * 
     * @param id 工艺异常判处结果主键
     * @return 结果
     */
    public int deleteEngExceptionDealResultById(Long id);


}
