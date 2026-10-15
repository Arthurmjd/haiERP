package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;

/**
 * 机台管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface IResMachineService 
{
    /**
     * 查询机台管理
     * 
     * @param id 机台管理主键
     * @return 机台管理
     */
    public ResMachine selectResMachineById(Long id);

    /**
     * 查询机台管理列表
     * 
     * @param resMachine 机台管理
     * @return 机台管理集合
     */
    public List<ResMachine> selectResMachineList(ResMachine resMachine);

    /**
     * 新增机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    public int insertResMachine(ResMachine resMachine);

    /**
     * 修改机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    public int updateResMachine(ResMachine resMachine);

    /**
     * 批量删除机台管理
     * 
     * @param ids 需要删除的机台管理主键集合
     * @return 结果
     */
    public int deleteResMachineByIds(Long[] ids);

    /**
     * 删除机台管理信息
     * 
     * @param id 机台管理主键
     * @return 结果
     */
    public int deleteResMachineById(Long id);


}
