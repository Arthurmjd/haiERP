package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaExceptionDeal;

/**
 * 质量异常判处Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IQuaExceptionDealService 
{
    /**
     * 查询质量异常判处
     * 
     * @param id 质量异常判处主键
     * @return 质量异常判处
     */
    public QuaExceptionDeal selectQuaExceptionDealById(Long id);

    /**
     * 查询质量异常判处列表
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 质量异常判处集合
     */
    public List<QuaExceptionDeal> selectQuaExceptionDealList(QuaExceptionDeal quaExceptionDeal);

    /**
     * 新增质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    public int insertQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal);

    /**
     * 修改质量异常判处
     * 
     * @param quaExceptionDeal 质量异常判处
     * @return 结果
     */
    public int updateQuaExceptionDeal(QuaExceptionDeal quaExceptionDeal);

    /**
     * 批量删除质量异常判处
     * 
     * @param ids 需要删除的质量异常判处主键集合
     * @return 结果
     */
    public int deleteQuaExceptionDealByIds(Long[] ids);

    /**
     * 删除质量异常判处信息
     * 
     * @param id 质量异常判处主键
     * @return 结果
     */
    public int deleteQuaExceptionDealById(Long id);

    /**
     * 判处
     *
     * @param quaExceptionDeal 判处
     * @return 结果
     */
    public int judge(QuaExceptionDeal quaExceptionDeal) throws Exception;


}
