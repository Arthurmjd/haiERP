package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMovesTail;

/**
 * 工序移转记尾情Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface IExeProcessMovesTailService 
{
    /**
     * 查询工序移转记尾情
     * 
     * @param id 工序移转记尾情主键
     * @return 工序移转记尾情
     */
    public ExeProcessMovesTail selectExeProcessMovesTailById(Long id);

    /**
     * 查询工序移转记尾情列表
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 工序移转记尾情集合
     */
    public List<ExeProcessMovesTail> selectExeProcessMovesTailList(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 新增工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    public int insertExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 修改工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    public int updateExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail);

    /**
     * 批量删除工序移转记尾情
     * 
     * @param ids 需要删除的工序移转记尾情主键集合
     * @return 结果
     */
    public int deleteExeProcessMovesTailByIds(Long[] ids);

    /**
     * 删除工序移转记尾情信息
     * 
     * @param id 工序移转记尾情主键
     * @return 结果
     */
    public int deleteExeProcessMovesTailById(Long id);


}
