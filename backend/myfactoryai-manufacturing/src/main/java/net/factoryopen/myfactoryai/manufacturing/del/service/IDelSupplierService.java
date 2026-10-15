package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelSupplier;

/**
 * 外协厂商管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IDelSupplierService 
{
    /**
     * 查询外协厂商管理
     * 
     * @param id 外协厂商管理主键
     * @return 外协厂商管理
     */
    public DelSupplier selectDelSupplierById(Long id);

    /**
     * 查询外协厂商管理列表
     * 
     * @param delSupplier 外协厂商管理
     * @return 外协厂商管理集合
     */
    public List<DelSupplier> selectDelSupplierList(DelSupplier delSupplier);

    /**
     * 新增外协厂商管理
     * 
     * @param delSupplier 外协厂商管理
     * @return 结果
     */
    public int insertDelSupplier(DelSupplier delSupplier);

    /**
     * 修改外协厂商管理
     * 
     * @param delSupplier 外协厂商管理
     * @return 结果
     */
    public int updateDelSupplier(DelSupplier delSupplier);

    /**
     * 批量删除外协厂商管理
     * 
     * @param ids 需要删除的外协厂商管理主键集合
     * @return 结果
     */
    public int deleteDelSupplierByIds(Long[] ids);

    /**
     * 删除外协厂商管理信息
     * 
     * @param id 外协厂商管理主键
     * @return 结果
     */
    public int deleteDelSupplierById(Long id);


}
