package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupMachine;

/**
 * 班组机台Service接口
 * 
 * @author admin
 * @date 2025-01-02
 */
public interface IResWorkgroupMachineService 
{
    /**
     * 查询班组机台
     * 
     * @param id 班组机台主键
     * @return 班组机台
     */
    public ResWorkgroupMachine selectResWorkgroupMachineById(Long id);

    /**
     * 查询班组机台列表
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 班组机台集合
     */
    public List<ResWorkgroupMachine> selectResWorkgroupMachineList(ResWorkgroupMachine resWorkgroupMachine);

    /**
     * 查询班组机台列表
     *
     * @param groupId 班组ID
     * @return 班组机台序集合
     */
    public List<ResWorkgroupMachine> selectStartableMachineListByGroupId(Long groupId);

    /**
     * 新增班组机台
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 结果
     */
    public int insertResWorkgroupMachine(ResWorkgroupMachine resWorkgroupMachine);

    /**
     * 修改班组机台
     * 
     * @param resWorkgroupMachine 班组机台
     * @return 结果
     */
    public int updateResWorkgroupMachine(ResWorkgroupMachine resWorkgroupMachine);

    /**
     * 批量删除班组机台
     * 
     * @param ids 需要删除的班组机台主键集合
     * @return 结果
     */
    public int deleteResWorkgroupMachineByIds(Long[] ids);

    /**
     * 删除班组机台信息
     * 
     * @param id 班组机台主键
     * @return 结果
     */
    public int deleteResWorkgroupMachineById(Long id);


}
