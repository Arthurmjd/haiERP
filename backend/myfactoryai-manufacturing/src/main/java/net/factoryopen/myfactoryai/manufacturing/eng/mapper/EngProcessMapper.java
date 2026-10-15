package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProcess;

/**
 * 基本工序管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2026-02-22
 */
public interface EngProcessMapper 
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
     * 查询基本工序管理列表
     *
     * @param categoryId 制程段
     * @return 基本工序管理集合
     */
    public List<EngProcess> selectEngProcessListByCategoryId(Long categoryId);

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
     * 删除基本工序管理
     * 
     * @param id 基本工序管理主键
     * @return 结果
     */
    public int deleteEngProcessById(Long id);

    /**
     * 批量删除基本工序管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProcessByIds(Long[] ids);

    /**
     * 查询基本工序管理列表
     *
     * @param categoryId 制程段
     * @return 基本工序管理集合
     */
    public int deleteEngProcessByCategoryId(Long categoryId);
}
