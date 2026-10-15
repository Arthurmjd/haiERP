package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectSelf;

/**
 * 自检异常登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IExeInspectSelfService 
{
    /**
     * 查询自检异常登记
     * 
     * @param id 自检异常登记主键
     * @return 自检异常登记
     */
    public ExeInspectSelf selectExeInspectSelfById(Long id);

    /**
     * 查询自检异常登记列表
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfList(ExeInspectSelf exeInspectSelf);

    /**
     * 查询自检异常登记列表
     *
     * @param exeInspectSelf 自检异常登记
     * @return 自检异常登记集合
     */
    public List<ExeInspectSelf> selectExeInspectSelfListByOperator(ExeInspectSelf exeInspectSelf);

    /**
     * 新增自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    public int insertExeInspectSelf(ExeInspectSelf exeInspectSelf);

    /**
     * 修改自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    public int updateExeInspectSelf(ExeInspectSelf exeInspectSelf);

    /**
     * 批量删除自检异常登记
     * 
     * @param ids 需要删除的自检异常登记主键集合
     * @return 结果
     */
    public int deleteExeInspectSelfByIds(Long[] ids);

    /**
     * 删除自检异常登记信息
     * 
     * @param id 自检异常登记主键
     * @return 结果
     */
    public int deleteExeInspectSelfById(Long id);

    /**
     * 请判
     *
     * @param exeInspectSelf 请判
     * @return 结果
     */
    public int requestDeal(ExeInspectSelf exeInspectSelf) throws Exception;


}
