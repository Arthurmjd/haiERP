package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialSales;

/**
 * 物料销售维Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialSalesService 
{
    /**
     * 查询物料销售维
     * 
     * @param id 物料销售维主键
     * @return 物料销售维
     */
    public EngMaterialSales selectEngMaterialSalesById(Long id);

    /**
     * 查询物料销售维列表
     * 
     * @param engMaterialSales 物料销售维
     * @return 物料销售维集合
     */
    public List<EngMaterialSales> selectEngMaterialSalesList(EngMaterialSales engMaterialSales);

    /**
     * 新增物料销售维
     * 
     * @param engMaterialSales 物料销售维
     * @return 结果
     */
    public int insertEngMaterialSales(EngMaterialSales engMaterialSales);

    /**
     * 修改物料销售维
     * 
     * @param engMaterialSales 物料销售维
     * @return 结果
     */
    public int updateEngMaterialSales(EngMaterialSales engMaterialSales);

    /**
     * 批量删除物料销售维
     * 
     * @param ids 需要删除的物料销售维主键集合
     * @return 结果
     */
    public int deleteEngMaterialSalesByIds(Long[] ids);

    /**
     * 删除物料销售维信息
     * 
     * @param id 物料销售维主键
     * @return 结果
     */
    public int deleteEngMaterialSalesById(Long id);


}
