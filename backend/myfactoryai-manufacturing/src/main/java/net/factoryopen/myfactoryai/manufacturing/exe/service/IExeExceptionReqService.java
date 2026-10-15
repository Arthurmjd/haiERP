package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeExceptionReq;

/**
 * 工艺异常登记Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IExeExceptionReqService 
{
    /**
     * 查询工艺异常登记
     * 
     * @param id 工艺异常登记主键
     * @return 工艺异常登记
     */
    public ExeExceptionReq selectExeExceptionReqById(Long id);

    /**
     * 查询工艺异常登记列表
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 工艺异常登记集合
     */
    public List<ExeExceptionReq> selectExeExceptionReqList(ExeExceptionReq exeExceptionReq);

    /**
     * 新增工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    public int insertExeExceptionReq(ExeExceptionReq exeExceptionReq);

    /**
     * 修改工艺异常登记
     * 
     * @param exeExceptionReq 工艺异常登记
     * @return 结果
     */
    public int updateExeExceptionReq(ExeExceptionReq exeExceptionReq);

    /**
     * 批量删除工艺异常登记
     * 
     * @param ids 需要删除的工艺异常登记主键集合
     * @return 结果
     */
    public int deleteExeExceptionReqByIds(Long[] ids);

    /**
     * 删除工艺异常登记信息
     * 
     * @param id 工艺异常登记主键
     * @return 结果
     */
    public int deleteExeExceptionReqById(Long id);

    /**
     * 请判
     *
     * @param exeExceptionReq 请判
     * @return 结果
     */
    public int requestDeal(ExeExceptionReq exeExceptionReq) throws Exception;


}
