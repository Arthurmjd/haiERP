package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdCheckbusi;

/**
 * 订单对账服务Service接口
 * 
 * @author  jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdCheckbusiService 
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
     * 批量删除订单对账服务
     * 
     * @param ids 需要删除的订单对账服务主键集合
     * @return 结果
     */
    public int deleteOrdCheckbusiByIds(Long[] ids);

    /**
     * 删除订单对账服务信息
     * 
     * @param id 订单对账服务主键
     * @return 结果
     */
    public int deleteOrdCheckbusiById(Long id);

    /**
     * 对账
     *
     * @param ordCheckbusi 对账
     * @return 结果
     */
    public int checkOrderBusi(OrdCheckbusi ordCheckbusi) throws Exception;


}
