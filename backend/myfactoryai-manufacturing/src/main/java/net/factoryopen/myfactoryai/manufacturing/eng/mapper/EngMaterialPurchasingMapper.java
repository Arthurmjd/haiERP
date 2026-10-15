package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialPurchasing;

/**
 * 物料采购维Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface EngMaterialPurchasingMapper 
{
    /**
     * 查询物料采购维
     * 
     * @param id 物料采购维主键
     * @return 物料采购维
     */
    public EngMaterialPurchasing selectEngMaterialPurchasingById(Long id);

    /**
     * 查询物料采购维列表
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 物料采购维集合
     */
    public List<EngMaterialPurchasing> selectEngMaterialPurchasingList(EngMaterialPurchasing engMaterialPurchasing);

    /**
     * 查询物料采购维列表
     *
     * @param materialId 物料编码
     * @return 物料采购维集合
     */
    public List<EngMaterialPurchasing> selectEngMaterialPurchasingListByMaterialId(Long materialId);

    /**
     * 新增物料采购维
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 结果
     */
    public int insertEngMaterialPurchasing(EngMaterialPurchasing engMaterialPurchasing);

    /**
     * 修改物料采购维
     * 
     * @param engMaterialPurchasing 物料采购维
     * @return 结果
     */
    public int updateEngMaterialPurchasing(EngMaterialPurchasing engMaterialPurchasing);

    /**
     * 删除物料采购维
     * 
     * @param id 物料采购维主键
     * @return 结果
     */
    public int deleteEngMaterialPurchasingById(Long id);

    /**
     * 批量删除物料采购维
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialPurchasingByIds(Long[] ids);

    /**
     * 查询物料采购维列表
     *
     * @param materialId 物料编码
     * @return 物料采购维集合
     */
    public int deleteEngMaterialPurchasingByMaterialId(Long materialId);
}
