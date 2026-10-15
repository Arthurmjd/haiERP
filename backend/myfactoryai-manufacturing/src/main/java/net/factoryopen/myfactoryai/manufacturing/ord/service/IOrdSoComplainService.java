package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoComplain;

/**
 * 订单客诉登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdSoComplainService 
{
    /**
     * 查询订单客诉登记
     * 
     * @param id 订单客诉登记主键
     * @return 订单客诉登记
     */
    public OrdSoComplain selectOrdSoComplainById(Long id);

    /**
     * 查询订单客诉登记列表
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 订单客诉登记集合
     */
    public List<OrdSoComplain> selectOrdSoComplainList(OrdSoComplain ordSoComplain);

    /**
     * 新增订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    public int insertOrdSoComplain(OrdSoComplain ordSoComplain);

    /**
     * 修改订单客诉登记
     * 
     * @param ordSoComplain 订单客诉登记
     * @return 结果
     */
    public int updateOrdSoComplain(OrdSoComplain ordSoComplain);

    /**
     * 批量删除订单客诉登记
     * 
     * @param ids 需要删除的订单客诉登记主键集合
     * @return 结果
     */
    public int deleteOrdSoComplainByIds(Long[] ids);

    /**
     * 删除订单客诉登记信息
     * 
     * @param id 订单客诉登记主键
     * @return 结果
     */
    public int deleteOrdSoComplainById(Long id);

    /**
     * 客诉
     *
     * @param ordSoComplain 客诉
     * @return 结果
     */
    public int complainReg(OrdSoComplain ordSoComplain) throws Exception;


}
