package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdCheckbusi;

/**
 * 订单对账服务Mapper接口
 * 
 * @author  jitfactory-generator
 * @date 2024-07-04
 */
public interface OrdCheckbusiMapper 
{
    /**
     * 查询订单对账服务
     * 
     * @param id 订单对账服务主键
     * @return 订单对账服务
     */
    public OrdCheckbusi selectOrdCheckbusiById(Long id);

    /**
     * 查询订单对账服务列表
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 订单对账服务集合
     */
    public List<OrdCheckbusi> selectOrdCheckbusiList(OrdCheckbusi ordCheckbusi);

    /**
     * 查询订单对账服务列表
     *
     * @param customerId 客户
     * @return 订单对账服务集合
     */
    public List<OrdCheckbusi> selectOrdCheckbusiListByCustomerId(Long customerId);

    /**
     * 查询订单对账服务列表
     *
     * @param salesId 销售员
     * @return 订单对账服务集合
     */
    public List<OrdCheckbusi> selectOrdCheckbusiListBySalesId(Long salesId);

    /**
     * 查询订单对账服务列表
     *
     * @param deptId 部门
     * @return 订单对账服务集合
     */
    public List<OrdCheckbusi> selectOrdCheckbusiListByDeptId(Long deptId);

    /**
     * 新增订单对账服务
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 结果
     */
    public int insertOrdCheckbusi(OrdCheckbusi ordCheckbusi);

    /**
     * 修改订单对账服务
     * 
     * @param ordCheckbusi 订单对账服务
     * @return 结果
     */
    public int updateOrdCheckbusi(OrdCheckbusi ordCheckbusi);

    /**
     * 删除订单对账服务
     * 
     * @param id 订单对账服务主键
     * @return 结果
     */
    public int deleteOrdCheckbusiById(Long id);

    /**
     * 批量删除订单对账服务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdCheckbusiByIds(Long[] ids);

    /**
     * 查询订单对账服务列表
     *
     * @param customerId 客户
     * @return 订单对账服务集合
     */
    public int deleteOrdCheckbusiByCustomerId(Long customerId);

    /**
     * 查询订单对账服务列表
     *
     * @param salesId 销售员
     * @return 订单对账服务集合
     */
    public int deleteOrdCheckbusiBySalesId(Long salesId);

    /**
     * 查询订单对账服务列表
     *
     * @param deptId 部门
     * @return 订单对账服务集合
     */
    public int deleteOrdCheckbusiByDeptId(Long deptId);
}
