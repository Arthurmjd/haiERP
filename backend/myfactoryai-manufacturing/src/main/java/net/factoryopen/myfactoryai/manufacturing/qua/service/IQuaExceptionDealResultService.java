package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDealResult;

/**
 * 质量异常判处结果Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IQuaExceptionDealResultService 
{
    /**
     * 查询质量异常判处结果
     * 
     * @param id 质量异常判处结果主键
     * @return 质量异常判处结果
     */
    public QuaExceptionDealResult selectQuaExceptionDealResultById(Long id);

    /**
     * 查询质量异常判处结果列表
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 质量异常判处结果集合
     */
    public List<QuaExceptionDealResult> selectQuaExceptionDealResultList(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 新增质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    public int insertQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 修改质量异常判处结果
     * 
     * @param quaExceptionDealResult 质量异常判处结果
     * @return 结果
     */
    public int updateQuaExceptionDealResult(QuaExceptionDealResult quaExceptionDealResult);

    /**
     * 批量删除质量异常判处结果
     * 
     * @param ids 需要删除的质量异常判处结果主键集合
     * @return 结果
     */
    public int deleteQuaExceptionDealResultByIds(Long[] ids);

    /**
     * 删除质量异常判处结果信息
     * 
     * @param id 质量异常判处结果主键
     * @return 结果
     */
    public int deleteQuaExceptionDealResultById(Long id);


}
