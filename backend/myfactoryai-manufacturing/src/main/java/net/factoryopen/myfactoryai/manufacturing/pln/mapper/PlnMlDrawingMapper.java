package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDrawing;
import org.springframework.stereotype.Service;

/**
 * 批次图纸Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public interface PlnMlDrawingMapper 
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
     * 查询批次图纸列表
     *
     * @param mlId 制造批
     * @return 批次图纸集合
     */
    public List<PlnMlDrawing> selectPlnMlDrawingListByMlId(Long mlId);

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
     * 删除批次图纸
     * 
     * @param id 批次图纸主键
     * @return 结果
     */
    public int deletePlnMlDrawingById(Long id);

    /**
     * 批量删除批次图纸
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlDrawingByIds(Long[] ids);

    /**
     * 查询批次图纸列表
     *
     * @param mlId 制造批
     * @return 批次图纸集合
     */
    public int deletePlnMlDrawingByMlId(Long mlId);
}
