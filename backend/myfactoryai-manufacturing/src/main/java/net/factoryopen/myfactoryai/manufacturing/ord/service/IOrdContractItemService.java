package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;

/**
 * 合同明细Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdContractItemService 
{
    /**
     * 查询合同明细
     * 
     * @param id 合同明细主键
     * @return 合同明细
     */
    public OrdContractItem selectOrdContractItemById(Long id);

    /**
     * 查询合同明细列表
     * 
     * @param ordContractItem 合同明细
     * @return 合同明细集合
     */
    public List<OrdContractItem> selectOrdContractItemList(OrdContractItem ordContractItem);

    /**
     * 查询合同明细列表
     *
     * @param soId 订单ID
     * @return 合同明细集合
     */
    public List<OrdContractItem> selectOrdContractItemListByContractId(Long soId);

    /**
     * 新增合同明细
     * 
     * @param ordContractItem 合同明细
     * @return 结果
     */
    public int insertOrdContractItem(OrdContractItem ordContractItem);

    /**
     * 修改合同明细
     * 
     * @param ordContractItem 合同明细
     * @return 结果
     */
    public int updateOrdContractItem(OrdContractItem ordContractItem);

    /**
     * 批量删除合同明细
     * 
     * @param ids 需要删除的合同明细主键集合
     * @return 结果
     */
    public int deleteOrdContractItemByIds(Long[] ids);

    /**
     * 删除合同明细信息
     * 
     * @param id 合同明细主键
     * @return 结果
     */
    public int deleteOrdContractItemById(Long id);

    /**
     * 请求临时料号
     *
     * @param ids 合同明细ID集合
     * @return 结果
     */
    public int requestTempProduct(Long[] ids) throws Exception;


}
