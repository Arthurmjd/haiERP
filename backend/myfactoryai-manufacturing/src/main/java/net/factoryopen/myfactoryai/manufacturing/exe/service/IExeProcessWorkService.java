package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;

/**
 * 工序出工Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
public interface IExeProcessWorkService 
{
    /**
     * 查询工序出工
     * 
     * @param id 工序出工主键
     * @return 工序出工
     */
    public ExeProcessWork selectExeProcessWorkById(Long id);

    /**
     * 查询工序出工列表
     * 
     * @param exeProcessWork 工序出工
     * @return 工序出工集合
     */
    public List<ExeProcessWork> selectExeProcessWorkList(ExeProcessWork exeProcessWork);

    /**
     * 查询工序出工列表
     *
     * @param ml 制造批
     * @return 工序出工集合
     */
    public List<ExeProcessWork> listByMl(PlnMl ml);

    /**
     * 新增工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    public int insertExeProcessWork(ExeProcessWork exeProcessWork);

    /**
     * 修改工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    public int updateExeProcessWork(ExeProcessWork exeProcessWork);

    /**
     * 批量删除工序出工
     * 
     * @param ids 需要删除的工序出工主键集合
     * @return 结果
     */
    public int deleteExeProcessWorkByIds(Long[] ids);

    /**
     * 删除工序出工信息
     * 
     * @param id 工序出工主键
     * @return 结果
     */
    public int deleteExeProcessWorkById(Long id);


}
