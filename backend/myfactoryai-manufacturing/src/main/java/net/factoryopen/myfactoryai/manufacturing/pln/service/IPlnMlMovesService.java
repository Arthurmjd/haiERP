package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMoves;

/**
 * 批次移转记录Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlMovesService 
{
    /**
     * 查询批次移转记录
     * 
     * @param id 批次移转记录主键
     * @return 批次移转记录
     */
    public PlnMlMoves selectPlnMlMovesById(Long id);

    /**
     * 查询批次移转记录列表
     * 
     * @param plnMlMoves 批次移转记录
     * @return 批次移转记录集合
     */
    public List<PlnMlMoves> selectPlnMlMovesList(PlnMlMoves plnMlMoves);

    /**
     * 新增批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    public int insertPlnMlMoves(PlnMlMoves plnMlMoves);

    /**
     * 修改批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    public int updatePlnMlMoves(PlnMlMoves plnMlMoves);

    /**
     * 批量删除批次移转记录
     * 
     * @param ids 需要删除的批次移转记录主键集合
     * @return 结果
     */
    public int deletePlnMlMovesByIds(Long[] ids);

    /**
     * 删除批次移转记录信息
     * 
     * @param id 批次移转记录主键
     * @return 结果
     */
    public int deletePlnMlMovesById(Long id);


}
