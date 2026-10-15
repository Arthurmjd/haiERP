package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequestItem;

/**
 * 请检Mapper接口
 * 
 * @author admin
 * @date 2024-10-06
 */
public interface QuaInspectRequestItemMapper 
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
     * 查询请检列表
     *
     * @param requestId 请求ID
     * @return 请检集合
     */
    public List<QuaInspectRequestItem> selectQuaInspectRequestItemListByRequestId(Long requestId);

    /**
     * 查询请检列表
     *
     * @param materialId 物料ID
     * @return 请检集合
     */
    public List<QuaInspectRequestItem> selectQuaInspectRequestItemListByMaterialId(Long materialId);

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
     * 删除请检
     * 
     * @param id 请检主键
     * @return 结果
     */
    public int deleteQuaInspectRequestItemById(Long id);

    /**
     * 批量删除请检
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaInspectRequestItemByIds(Long[] ids);

    /**
     * 查询请检列表
     *
     * @param requestId 请求ID
     * @return 请检集合
     */
    public int deleteQuaInspectRequestItemByRequestId(Long requestId);

    /**
     * 查询请检列表
     *
     * @param materialId 物料ID
     * @return 请检集合
     */
    public int deleteQuaInspectRequestItemByMaterialId(Long materialId);
}
