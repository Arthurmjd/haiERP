package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlRoutingChange;

/**
 * 批次制程变更Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IExeMlRoutingChangeService 
{
    /**
     * 查询批次制程变更
     * 
     * @param id 批次制程变更主键
     * @return 批次制程变更
     */
    public ExeMlRoutingChange selectExeMlRoutingChangeById(Long id);

    /**
     * 查询批次制程变更列表
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 批次制程变更集合
     */
    public List<ExeMlRoutingChange> selectExeMlRoutingChangeList(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 新增批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    public int insertExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 修改批次制程变更
     * 
     * @param exeMlRoutingChange 批次制程变更
     * @return 结果
     */
    public int updateExeMlRoutingChange(ExeMlRoutingChange exeMlRoutingChange);

    /**
     * 批量删除批次制程变更
     * 
     * @param ids 需要删除的批次制程变更主键集合
     * @return 结果
     */
    public int deleteExeMlRoutingChangeByIds(Long[] ids);

    /**
     * 删除批次制程变更信息
     * 
     * @param id 批次制程变更主键
     * @return 结果
     */
    public int deleteExeMlRoutingChangeById(Long id);


}
