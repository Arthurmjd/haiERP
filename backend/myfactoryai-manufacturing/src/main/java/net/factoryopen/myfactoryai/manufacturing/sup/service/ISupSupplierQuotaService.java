package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplierQuota;

/**
 * 供应商配额Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISupSupplierQuotaService 
{
    /**
     * 查询供应商配额
     * 
     * @param id 供应商配额主键
     * @return 供应商配额
     */
    public SupSupplierQuota selectSupSupplierQuotaById(Long id);

    /**
     * 查询供应商配额列表
     * 
     * @param supSupplierQuota 供应商配额
     * @return 供应商配额集合
     */
    public List<SupSupplierQuota> selectSupSupplierQuotaList(SupSupplierQuota supSupplierQuota);

    /**
     * 新增供应商配额
     * 
     * @param supSupplierQuota 供应商配额
     * @return 结果
     */
    public int insertSupSupplierQuota(SupSupplierQuota supSupplierQuota);

    /**
     * 修改供应商配额
     * 
     * @param supSupplierQuota 供应商配额
     * @return 结果
     */
    public int updateSupSupplierQuota(SupSupplierQuota supSupplierQuota);

    /**
     * 批量删除供应商配额
     * 
     * @param ids 需要删除的供应商配额主键集合
     * @return 结果
     */
    public int deleteSupSupplierQuotaByIds(Long[] ids);

    /**
     * 删除供应商配额信息
     * 
     * @param id 供应商配额主键
     * @return 结果
     */
    public int deleteSupSupplierQuotaById(Long id);


}
