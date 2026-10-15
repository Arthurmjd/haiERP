package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;

/**
 * 质检请求通知Service接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface IQuaInspectRequestService 
{
    /**
     * 查询质检请求通知
     * 
     * @param id 质检请求通知主键
     * @return 质检请求通知
     */
    public QuaInspectRequest selectQuaInspectRequestById(Long id);

    /**
     * 查询质检请求通知列表
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 质检请求通知集合
     */
    public List<QuaInspectRequest> selectQuaInspectRequestList(QuaInspectRequest quaInspectRequest);

    /**
     * 新增质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    public int insertQuaInspectRequest(QuaInspectRequest quaInspectRequest);

    /**
     * 修改质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    public int updateQuaInspectRequest(QuaInspectRequest quaInspectRequest);

    /**
     * 批量删除质检请求通知
     * 
     * @param ids 需要删除的质检请求通知主键集合
     * @return 结果
     */
    public int deleteQuaInspectRequestByIds(Long[] ids);

    /**
     * 删除质检请求通知信息
     * 
     * @param id 质检请求通知主键
     * @return 结果
     */
    public int deleteQuaInspectRequestById(Long id);


}
