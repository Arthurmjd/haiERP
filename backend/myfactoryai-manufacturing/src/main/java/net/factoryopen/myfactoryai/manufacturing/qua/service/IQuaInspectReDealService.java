package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReDeal;

/**
 * 质检请求处理Service接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface IQuaInspectReDealService 
{
    /**
     * 查询质检请求处理
     * 
     * @param id 质检请求处理主键
     * @return 质检请求处理
     */
    public QuaInspectReDeal selectQuaInspectReDealById(Long id);

    /**
     * 查询质检请求处理列表
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 质检请求处理集合
     */
    public List<QuaInspectReDeal> selectQuaInspectReDealList(QuaInspectReDeal quaInspectReDeal);

    /**
     * 新增质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    public int insertQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal);

    /**
     * 修改质检请求处理
     * 
     * @param quaInspectReDeal 质检请求处理
     * @return 结果
     */
    public int updateQuaInspectReDeal(QuaInspectReDeal quaInspectReDeal);

    /**
     * 批量删除质检请求处理
     * 
     * @param ids 需要删除的质检请求处理主键集合
     * @return 结果
     */
    public int deleteQuaInspectReDealByIds(Long[] ids);

    /**
     * 删除质检请求处理信息
     * 
     * @param id 质检请求处理主键
     * @return 结果
     */
    public int deleteQuaInspectReDealById(Long id);

    /**
     * 收检
     *
     * @param quaInspectReDeal 收检
     * @return 结果
     */
    public int acceptInspection(QuaInspectReDeal quaInspectReDeal) throws Exception;


}
