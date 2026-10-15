package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;

/**
 * 工序移转记录Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IExeProcessMovesService 
{
    /**
     * 查询工序移转记录
     * 
     * @param id 工序移转记录主键
     * @return 工序移转记录
     */
    public ExeProcessMoves selectExeProcessMovesById(Long id);

    /**
     * 查询工序移转记录列表
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 工序移转记录集合
     */
    public List<ExeProcessMoves> selectExeProcessMovesList(ExeProcessMoves exeProcessMoves);

    /**
     * 新增工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    public int insertExeProcessMoves(ExeProcessMoves exeProcessMoves);

    /**
     * 修改工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    public int updateExeProcessMoves(ExeProcessMoves exeProcessMoves);

    /**
     * 批量删除工序移转记录
     * 
     * @param ids 需要删除的工序移转记录主键集合
     * @return 结果
     */
    public int deleteExeProcessMovesByIds(Long[] ids);

    /**
     * 删除工序移转记录信息
     * 
     * @param id 工序移转记录主键
     * @return 结果
     */
    public int deleteExeProcessMovesById(Long id);


}
