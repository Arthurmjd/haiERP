package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;

/**
 * 请检Service接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface IQuaInspectRequestItemService 
{
    /**
     * 查询请检
     * 
     * @param id 请检主键
     * @return 请检
     */
    public QuaInspectRequestItem selectQuaInspectRequestItemById(Long id);

    /**
     * 查询请检列表
     * 
     * @param quaInspectRequestItem 请检
     * @return 请检集合
     */
    public List<QuaInspectRequestItem> selectQuaInspectRequestItemList(QuaInspectRequestItem quaInspectRequestItem);

    /**
     * 新增请检
     * 
     * @param quaInspectRequestItem 请检
     * @return 结果
     */
    public int insertQuaInspectRequestItem(QuaInspectRequestItem quaInspectRequestItem);

    /**
     * 修改请检
     * 
     * @param quaInspectRequestItem 请检
     * @return 结果
     */
    public int updateQuaInspectRequestItem(QuaInspectRequestItem quaInspectRequestItem);

    /**
     * 批量删除请检
     * 
     * @param ids 需要删除的请检主键集合
     * @return 结果
     */
    public int deleteQuaInspectRequestItemByIds(Long[] ids);

    /**
     * 删除请检信息
     * 
     * @param id 请检主键
     * @return 结果
     */
    public int deleteQuaInspectRequestItemById(Long id);


    /**
     * 检良
     *
     * @param quaInspectRequestItem 请求
     * @return 结果
     */
    public int registerGoodInspection(Long requestId, QuaInspectRequestItem quaInspectRequestItem) throws Exception;


    /**
     * 检异
     *
     * @param quaInspectRequestItem 请求
     * @return 结果
     */
    public int registerExceptionalInspection(Long requestId, QuaInspectRequestItem quaInspectRequestItem) throws Exception;

    /**
     * 请判
     *
     * @param quaInspectRequestItem 请判
     * @return 结果
     */
    public int requestJugde(QuaInspectRequestItem quaInspectRequestItem) throws Exception;

}
