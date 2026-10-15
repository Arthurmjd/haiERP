package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTrace;

/**
 * 在制批次跟踪Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IExeMlTraceService 
{
    /**
     * 查询在制批次跟踪
     * 
     * @param id 在制批次跟踪主键
     * @return 在制批次跟踪
     */
    public ExeMlTrace selectExeMlTraceById(Long id);

    /**
     * 查询在制批次跟踪列表
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 在制批次跟踪集合
     */
    public List<ExeMlTrace> selectExeMlTraceList(ExeMlTrace exeMlTrace);

    /**
     * 新增在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    public int insertExeMlTrace(ExeMlTrace exeMlTrace);

    /**
     * 修改在制批次跟踪
     * 
     * @param exeMlTrace 在制批次跟踪
     * @return 结果
     */
    public int updateExeMlTrace(ExeMlTrace exeMlTrace);

    /**
     * 批量删除在制批次跟踪
     * 
     * @param ids 需要删除的在制批次跟踪主键集合
     * @return 结果
     */
    public int deleteExeMlTraceByIds(Long[] ids);

    /**
     * 删除在制批次跟踪信息
     * 
     * @param id 在制批次跟踪主键
     * @return 结果
     */
    public int deleteExeMlTraceById(Long id);


}
