package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoComputing;

/**
 * 计划分解Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IPlnWoComputingService 
{
    /**
     * 查询计划分解
     * 
     * @param id 计划分解主键
     * @return 计划分解
     */
    public PlnWoComputing selectPlnWoComputingById(Long id);

    /**
     * 查询计划分解列表
     * 
     * @param plnWoComputing 计划分解
     * @return 计划分解集合
     */
    public List<PlnWoComputing> selectPlnWoComputingList(PlnWoComputing plnWoComputing);

    /**
     * 新增计划分解
     * 
     * @param plnWoComputing 计划分解
     * @return 结果
     */
    public int insertPlnWoComputing(PlnWoComputing plnWoComputing);

    /**
     * 修改计划分解
     * 
     * @param plnWoComputing 计划分解
     * @return 结果
     */
    public int updatePlnWoComputing(PlnWoComputing plnWoComputing);

    /**
     * 批量删除计划分解
     * 
     * @param ids 需要删除的计划分解主键集合
     * @return 结果
     */
    public int deletePlnWoComputingByIds(Long[] ids);

    /**
     * 删除计划分解信息
     * 
     * @param id 计划分解主键
     * @return 结果
     */
    public int deletePlnWoComputingById(Long id);

    /**
     * 分解
     *
     * @param ids id集合
     * @return 结果
     */
    public int splitMps(Long[] ids) throws Exception;


}
