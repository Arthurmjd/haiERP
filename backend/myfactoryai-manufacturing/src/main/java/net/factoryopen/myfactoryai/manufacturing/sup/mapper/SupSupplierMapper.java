package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupSupplier;

/**
 * 供应商管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-26
 */
public interface SupSupplierMapper 
{
    /**
     * 查询供应商管理
     * 
     * @param id 供应商管理主键
     * @return 供应商管理
     */
    public SupSupplier selectSupSupplierById(Long id);

    /**
     * 查询供应商管理列表
     * 
     * @param supSupplier 供应商管理
     * @return 供应商管理集合
     */
    public List<SupSupplier> selectSupSupplierList(SupSupplier supSupplier);

    /**
     * 新增供应商管理
     * 
     * @param supSupplier 供应商管理
     * @return 结果
     */
    public int insertSupSupplier(SupSupplier supSupplier);

    /**
     * 修改供应商管理
     * 
     * @param supSupplier 供应商管理
     * @return 结果
     */
    public int updateSupSupplier(SupSupplier supSupplier);

    /**
     * 删除供应商管理
     * 
     * @param id 供应商管理主键
     * @return 结果
     */
    public int deleteSupSupplierById(Long id);

    /**
     * 批量删除供应商管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupSupplierByIds(Long[] ids);
}
