package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDrawing;

/**
 * 批次图纸Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlDrawingService 
{
    /**
     * 查询批次图纸
     * 
     * @param id 批次图纸主键
     * @return 批次图纸
     */
    public PlnMlDrawing selectPlnMlDrawingById(Long id);

    /**
     * 查询批次图纸列表
     * 
     * @param plnMlDrawing 批次图纸
     * @return 批次图纸集合
     */
    public List<PlnMlDrawing> selectPlnMlDrawingList(PlnMlDrawing plnMlDrawing);

    /**
     * 新增批次图纸
     * 
     * @param plnMlDrawing 批次图纸
     * @return 结果
     */
    public int insertPlnMlDrawing(PlnMlDrawing plnMlDrawing);

    /**
     * 修改批次图纸
     * 
     * @param plnMlDrawing 批次图纸
     * @return 结果
     */
    public int updatePlnMlDrawing(PlnMlDrawing plnMlDrawing);

    /**
     * 批量删除批次图纸
     * 
     * @param ids 需要删除的批次图纸主键集合
     * @return 结果
     */
    public int deletePlnMlDrawingByIds(Long[] ids);

    /**
     * 删除批次图纸信息
     * 
     * @param id 批次图纸主键
     * @return 结果
     */
    public int deletePlnMlDrawingById(Long id);


}
