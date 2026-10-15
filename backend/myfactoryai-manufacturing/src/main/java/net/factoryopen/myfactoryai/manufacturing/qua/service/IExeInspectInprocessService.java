package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectInprocess;

/**
 * 制检异常登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IExeInspectInprocessService 
{
    /**
     * 查询制检异常登记
     * 
     * @param id 制检异常登记主键
     * @return 制检异常登记
     */
    public ExeInspectInprocess selectExeInspectInprocessById(Long id);

    /**
     * 查询制检异常登记列表
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 制检异常登记集合
     */
    public List<ExeInspectInprocess> selectExeInspectInprocessList(ExeInspectInprocess exeInspectInprocess);

    /**
     * 新增制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    public int insertExeInspectInprocess(ExeInspectInprocess exeInspectInprocess);

    /**
     * 修改制检异常登记
     * 
     * @param exeInspectInprocess 制检异常登记
     * @return 结果
     */
    public int updateExeInspectInprocess(ExeInspectInprocess exeInspectInprocess);

    /**
     * 批量删除制检异常登记
     * 
     * @param ids 需要删除的制检异常登记主键集合
     * @return 结果
     */
    public int deleteExeInspectInprocessByIds(Long[] ids);

    /**
     * 删除制检异常登记信息
     * 
     * @param id 制检异常登记主键
     * @return 结果
     */
    public int deleteExeInspectInprocessById(Long id);

    /**
     * 请判
     *
     * @param exeInspectInprocess 请判
     * @return 结果
     */
    public int requestDeal(ExeInspectInprocess exeInspectInprocess) throws Exception;


}
