package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import org.springframework.stereotype.Service;

/**
 * 合同明细Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface OrdContractItemMapper
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
     * @param contractId 合同
     * @return 合同明细集合
     */
    public List<OrdContractItem> selectOrdContractItemListByContractId(Long contractId);

    /**
     * 查询合同明细列表
     *
     * @param productId 产品编码
     * @return 合同明细集合
     */
    public List<OrdContractItem> selectOrdContractItemListByProductId(Long productId);

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
     * 删除合同明细
     * 
     * @param id 合同明细主键
     * @return 结果
     */
    public int deleteOrdContractItemById(Long id);

    /**
     * 批量删除合同明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdContractItemByIds(Long[] ids);

    /**
     * 查询合同明细列表
     *
     * @param contractId 合同
     * @return 合同明细集合
     */
    public int deleteOrdContractItemByContractId(Long contractId);

    /**
     * 查询合同明细列表
     *
     * @param productId 产品编码
     * @return 合同明细集合
     */
    public int deleteOrdContractItemByProductId(Long productId);
}
