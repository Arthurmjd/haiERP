package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlSplit;

/**
 * 制造批次分拆Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IExeMlSplitService 
{
    /**
     * 查询制造批次分拆
     * 
     * @param id 制造批次分拆主键
     * @return 制造批次分拆
     */
    public ExeMlSplit selectExeMlSplitById(Long id);

    /**
     * 查询制造批次分拆列表
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 制造批次分拆集合
     */
    public List<ExeMlSplit> selectExeMlSplitList(ExeMlSplit exeMlSplit);

    /**
     * 新增制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    public int insertExeMlSplit(ExeMlSplit exeMlSplit);

    /**
     * 修改制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    public int updateExeMlSplit(ExeMlSplit exeMlSplit);

    /**
     * 批量删除制造批次分拆
     * 
     * @param ids 需要删除的制造批次分拆主键集合
     * @return 结果
     */
    public int deleteExeMlSplitByIds(Long[] ids);

    /**
     * 删除制造批次分拆信息
     * 
     * @param id 制造批次分拆主键
     * @return 结果
     */
    public int deleteExeMlSplitById(Long id);

    /**
     * 分批
     *
     * @param exeMlSplit 分批
     * @return 结果
     */
    public int splitMl(ExeMlSplit exeMlSplit) throws Exception;


}
