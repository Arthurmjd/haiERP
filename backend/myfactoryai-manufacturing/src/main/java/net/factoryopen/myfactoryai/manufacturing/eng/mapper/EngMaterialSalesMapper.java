package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialSales;

/**
 * 物料销售维Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface EngMaterialSalesMapper 
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
     * 查询物料销售维列表
     *
     * @param materialId 物料编码
     * @return 物料销售维集合
     */
    public List<EngMaterialSales> selectEngMaterialSalesListByMaterialId(Long materialId);

    /**
     * 查询物料销售维列表
     *
     * @param projectId 项目
     * @return 物料销售维集合
     */
    public List<EngMaterialSales> selectEngMaterialSalesListByProjectId(Long projectId);

    /**
     * 查询物料销售维列表
     *
     * @param customerId 客户
     * @return 物料销售维集合
     */
    public List<EngMaterialSales> selectEngMaterialSalesListByCustomerId(Long customerId);

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
     * 删除物料销售维
     * 
     * @param id 物料销售维主键
     * @return 结果
     */
    public int deleteEngMaterialSalesById(Long id);

    /**
     * 批量删除物料销售维
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialSalesByIds(Long[] ids);

    /**
     * 查询物料销售维列表
     *
     * @param materialId 物料编码
     * @return 物料销售维集合
     */
    public int deleteEngMaterialSalesByMaterialId(Long materialId);

    /**
     * 查询物料销售维列表
     *
     * @param projectId 项目
     * @return 物料销售维集合
     */
    public int deleteEngMaterialSalesByProjectId(Long projectId);

    /**
     * 查询物料销售维列表
     *
     * @param customerId 客户
     * @return 物料销售维集合
     */
    public int deleteEngMaterialSalesByCustomerId(Long customerId);
}
