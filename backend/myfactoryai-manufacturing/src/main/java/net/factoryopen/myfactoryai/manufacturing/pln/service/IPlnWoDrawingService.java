package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoDrawing;

/**
 * 工单图纸Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
public interface IPlnWoDrawingService 
{
    /**
     * 查询工单图纸
     * 
     * @param id 工单图纸主键
     * @return 工单图纸
     */
    public PlnWoDrawing selectPlnWoDrawingById(Long id);

    /**
     * 查询工单图纸列表
     * 
     * @param plnWoDrawing 工单图纸
     * @return 工单图纸集合
     */
    public List<PlnWoDrawing> selectPlnWoDrawingList(PlnWoDrawing plnWoDrawing);

    /**
     * 新增工单图纸
     * 
     * @param plnWoDrawing 工单图纸
     * @return 结果
     */
    public int insertPlnWoDrawing(PlnWoDrawing plnWoDrawing);

    /**
     * 修改工单图纸
     * 
     * @param plnWoDrawing 工单图纸
     * @return 结果
     */
    public int updatePlnWoDrawing(PlnWoDrawing plnWoDrawing);

    /**
     * 批量删除工单图纸
     * 
     * @param ids 需要删除的工单图纸主键集合
     * @return 结果
     */
    public int deletePlnWoDrawingByIds(Long[] ids);

    /**
     * 删除工单图纸信息
     * 
     * @param id 工单图纸主键
     * @return 结果
     */
    public int deletePlnWoDrawingById(Long id);


}
