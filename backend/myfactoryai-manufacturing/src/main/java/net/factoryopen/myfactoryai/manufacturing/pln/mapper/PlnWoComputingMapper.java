package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoComputing;

/**
 * 计划分解Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface PlnWoComputingMapper 
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
     * 查询计划分解列表
     *
     * @param productId 产品编码
     * @return 计划分解集合
     */
    public List<PlnWoComputing> selectPlnWoComputingListByProductId(Long productId);

    /**
     * 查询计划分解列表
     *
     * @param customerId 客户ID
     * @return 计划分解集合
     */
    public List<PlnWoComputing> selectPlnWoComputingListByCustomerId(Long customerId);

    /**
     * 查询计划分解列表
     *
     * @param soId 销售订单
     * @return 计划分解集合
     */
    public List<PlnWoComputing> selectPlnWoComputingListBySoId(Long soId);

    /**
     * 查询计划分解列表
     *
     * @param soItemId 订单项
     * @return 计划分解集合
     */
    public List<PlnWoComputing> selectPlnWoComputingListBySoItemId(Long soItemId);

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
     * 删除计划分解
     * 
     * @param id 计划分解主键
     * @return 结果
     */
    public int deletePlnWoComputingById(Long id);

    /**
     * 批量删除计划分解
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoComputingByIds(Long[] ids);

    /**
     * 查询计划分解列表
     *
     * @param productId 产品编码
     * @return 计划分解集合
     */
    public int deletePlnWoComputingByProductId(Long productId);

    /**
     * 查询计划分解列表
     *
     * @param customerId 客户ID
     * @return 计划分解集合
     */
    public int deletePlnWoComputingByCustomerId(Long customerId);

    /**
     * 查询计划分解列表
     *
     * @param soId 销售订单
     * @return 计划分解集合
     */
    public int deletePlnWoComputingBySoId(Long soId);

    /**
     * 查询计划分解列表
     *
     * @param soItemId 订单项
     * @return 计划分解集合
     */
    public int deletePlnWoComputingBySoItemId(Long soItemId);
}
