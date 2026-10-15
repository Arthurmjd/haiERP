package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductDrawing;

/**
 * 产品图纸Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngProductDrawingMapper 
{
    /**
     * 查询产品图纸
     * 
     * @param id 产品图纸主键
     * @return 产品图纸
     */
    public EngProductDrawing selectEngProductDrawingById(Long id);

    /**
     * 查询产品图纸列表
     * 
     * @param engProductDrawing 产品图纸
     * @return 产品图纸集合
     */
    public List<EngProductDrawing> selectEngProductDrawingList(EngProductDrawing engProductDrawing);

    /**
     * 查询产品图纸列表
     *
     * @param productId 产品编码
     * @return 产品图纸集合
     */
    public List<EngProductDrawing> selectEngProductDrawingListByProductId(Long productId);

    /**
     * 新增产品图纸
     * 
     * @param engProductDrawing 产品图纸
     * @return 结果
     */
    public int insertEngProductDrawing(EngProductDrawing engProductDrawing);

    /**
     * 修改产品图纸
     * 
     * @param engProductDrawing 产品图纸
     * @return 结果
     */
    public int updateEngProductDrawing(EngProductDrawing engProductDrawing);

    /**
     * 删除产品图纸
     * 
     * @param id 产品图纸主键
     * @return 结果
     */
    public int deleteEngProductDrawingById(Long id);

    /**
     * 批量删除产品图纸
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductDrawingByIds(Long[] ids);

    /**
     * 查询产品图纸列表
     *
     * @param productId 产品编码
     * @return 产品图纸集合
     */
    public int deleteEngProductDrawingByProductId(Long productId);
}
