package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcess;

/**
 * 基本工序管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2026-02-22
 */
public interface IEngProcessService 
{
    /**
     * 查询基本工序管理
     * 
     * @param id 基本工序管理主键
     * @return 基本工序管理
     */
    public EngProcess selectEngProcessById(Long id);

    /**
     * 查询基本工序管理列表
     * 
     * @param engProcess 基本工序管理
     * @return 基本工序管理集合
     */
    public List<EngProcess> selectEngProcessList(EngProcess engProcess);

    /**
     * 新增基本工序管理
     * 
     * @param engProcess 基本工序管理
     * @return 结果
     */
    public int insertEngProcess(EngProcess engProcess);

    /**
     * 修改基本工序管理
     * 
     * @param engProcess 基本工序管理
     * @return 结果
     */
    public int updateEngProcess(EngProcess engProcess);

    /**
     * 批量删除基本工序管理
     * 
     * @param ids 需要删除的基本工序管理主键集合
     * @return 结果
     */
    public int deleteEngProcessByIds(Long[] ids);

    /**
     * 删除基本工序管理信息
     * 
     * @param id 基本工序管理主键
     * @return 结果
     */
    public int deleteEngProcessById(Long id);


}
