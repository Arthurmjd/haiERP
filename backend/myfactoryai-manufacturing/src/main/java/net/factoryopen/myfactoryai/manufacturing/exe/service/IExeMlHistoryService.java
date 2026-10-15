package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlHistory;

/**
 * 历史批次查询Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IExeMlHistoryService 
{
    /**
     * 查询历史批次查询
     * 
     * @param id 历史批次查询主键
     * @return 历史批次查询
     */
    public ExeMlHistory selectExeMlHistoryById(Long id);

    /**
     * 查询历史批次查询列表
     * 
     * @param exeMlHistory 历史批次查询
     * @return 历史批次查询集合
     */
    public List<ExeMlHistory> selectExeMlHistoryList(ExeMlHistory exeMlHistory);

    /**
     * 新增历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    public int insertExeMlHistory(ExeMlHistory exeMlHistory);

    /**
     * 修改历史批次查询
     * 
     * @param exeMlHistory 历史批次查询
     * @return 结果
     */
    public int updateExeMlHistory(ExeMlHistory exeMlHistory);

    /**
     * 批量删除历史批次查询
     * 
     * @param ids 需要删除的历史批次查询主键集合
     * @return 结果
     */
    public int deleteExeMlHistoryByIds(Long[] ids);

    /**
     * 删除历史批次查询信息
     * 
     * @param id 历史批次查询主键
     * @return 结果
     */
    public int deleteExeMlHistoryById(Long id);


}
