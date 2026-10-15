package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialPurchasing;

/**
 * 物料采购维Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialPurchasingService 
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
     * 批量删除物料采购维
     * 
     * @param ids 需要删除的物料采购维主键集合
     * @return 结果
     */
    public int deleteEngMaterialPurchasingByIds(Long[] ids);

    /**
     * 删除物料采购维信息
     * 
     * @param id 物料采购维主键
     * @return 结果
     */
    public int deleteEngMaterialPurchasingById(Long id);


}
