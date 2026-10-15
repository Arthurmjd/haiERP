package net.factoryopen.myfactoryai.manufacturing.cha.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaCustomerPotential;

/**
 * 潜客备案Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface ChaCustomerPotentialMapper 
{
    /**
     * 查询潜客备案
     * 
     * @param id 潜客备案主键
     * @return 潜客备案
     */
    public ChaCustomerPotential selectChaCustomerPotentialById(Long id);

    /**
     * 查询潜客备案列表
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialList(ChaCustomerPotential chaCustomerPotential);

    /**
     * 查询潜客备案列表
     *
     * @param payPatternId 付款方式
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialListByPayPatternId(Long payPatternId);

    /**
     * 查询潜客备案列表
     *
     * @param invoicePatternId 发票类型
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialListByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询潜客备案列表
     *
     * @param currencyId 币别
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialListByCurrencyId(Long currencyId);

    /**
     * 查询潜客备案列表
     *
     * @param shipmentTypeId 出货方式
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialListByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询潜客备案列表
     *
     * @param salesId 销售员
     * @return 潜客备案集合
     */
    public List<ChaCustomerPotential> selectChaCustomerPotentialListBySalesId(Long salesId);

    /**
     * 新增潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    public int insertChaCustomerPotential(ChaCustomerPotential chaCustomerPotential);

    /**
     * 修改潜客备案
     * 
     * @param chaCustomerPotential 潜客备案
     * @return 结果
     */
    public int updateChaCustomerPotential(ChaCustomerPotential chaCustomerPotential);

    /**
     * 删除潜客备案
     * 
     * @param id 潜客备案主键
     * @return 结果
     */
    public int deleteChaCustomerPotentialById(Long id);

    /**
     * 批量删除潜客备案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChaCustomerPotentialByIds(Long[] ids);

    /**
     * 查询潜客备案列表
     *
     * @param payPatternId 付款方式
     * @return 潜客备案集合
     */
    public int deleteChaCustomerPotentialByPayPatternId(Long payPatternId);

    /**
     * 查询潜客备案列表
     *
     * @param invoicePatternId 发票类型
     * @return 潜客备案集合
     */
    public int deleteChaCustomerPotentialByInvoicePatternId(Long invoicePatternId);

    /**
     * 查询潜客备案列表
     *
     * @param currencyId 币别
     * @return 潜客备案集合
     */
    public int deleteChaCustomerPotentialByCurrencyId(Long currencyId);

    /**
     * 查询潜客备案列表
     *
     * @param shipmentTypeId 出货方式
     * @return 潜客备案集合
     */
    public int deleteChaCustomerPotentialByShipmentTypeId(Long shipmentTypeId);

    /**
     * 查询潜客备案列表
     *
     * @param salesId 销售员
     * @return 潜客备案集合
     */
    public int deleteChaCustomerPotentialBySalesId(Long salesId);
}
