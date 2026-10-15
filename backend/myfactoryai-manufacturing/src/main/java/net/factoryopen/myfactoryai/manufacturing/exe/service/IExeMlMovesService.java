package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlMoves;

/**
 * 历史移转查询Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface IExeMlMovesService 
{
    /**
     * 查询历史移转查询
     * 
     * @param id 历史移转查询主键
     * @return 历史移转查询
     */
    public ExeMlMoves selectExeMlMovesById(Long id);

    /**
     * 查询历史移转查询列表
     * 
     * @param exeMlMoves 历史移转查询
     * @return 历史移转查询集合
     */
    public List<ExeMlMoves> selectExeMlMovesList(ExeMlMoves exeMlMoves);

    /**
     * 新增历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    public int insertExeMlMoves(ExeMlMoves exeMlMoves);

    /**
     * 修改历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    public int updateExeMlMoves(ExeMlMoves exeMlMoves);

    /**
     * 批量删除历史移转查询
     * 
     * @param ids 需要删除的历史移转查询主键集合
     * @return 结果
     */
    public int deleteExeMlMovesByIds(Long[] ids);

    /**
     * 删除历史移转查询信息
     * 
     * @param id 历史移转查询主键
     * @return 结果
     */
    public int deleteExeMlMovesById(Long id);


}
