package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;

/**
 * 物料计算Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface PlnMaterialComputingMapper 
{
    /**
     * 查询物料计算
     * 
     * @param id 物料计算主键
     * @return 物料计算
     */
    public PlnMaterialComputing selectPlnMaterialComputingById(Long id);

    /**
     * 查询物料计算列表
     * 
     * @param plnMaterialComputing 物料计算
     * @return 物料计算集合
     */
    public List<PlnMaterialComputing> selectPlnMaterialComputingList(PlnMaterialComputing plnMaterialComputing);

    /**
     * 查询物料计算列表
     *
     * @param productId 产品编码
     * @return 物料计算集合
     */
    public List<PlnMaterialComputing> selectPlnMaterialComputingListByProductId(Long productId);

    /**
     * 查询物料计算列表
     *
     * @param customerId 客户ID
     * @return 物料计算集合
     */
    public List<PlnMaterialComputing> selectPlnMaterialComputingListByCustomerId(Long customerId);

    /**
     * 查询物料计算列表
     *
     * @param soId 销售订单
     * @return 物料计算集合
     */
    public List<PlnMaterialComputing> selectPlnMaterialComputingListBySoId(Long soId);

    /**
     * 查询物料计算列表
     *
     * @param soItemId 订单项
     * @return 物料计算集合
     */
    public List<PlnMaterialComputing> selectPlnMaterialComputingListBySoItemId(Long soItemId);

    /**
     * 新增物料计算
     * 
     * @param plnMaterialComputing 物料计算
     * @return 结果
     */
    public int insertPlnMaterialComputing(PlnMaterialComputing plnMaterialComputing);

    /**
     * 修改物料计算
     * 
     * @param plnMaterialComputing 物料计算
     * @return 结果
     */
    public int updatePlnMaterialComputing(PlnMaterialComputing plnMaterialComputing);

    /**
     * 删除物料计算
     * 
     * @param id 物料计算主键
     * @return 结果
     */
    public int deletePlnMaterialComputingById(Long id);

    /**
     * 批量删除物料计算
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMaterialComputingByIds(Long[] ids);

    /**
     * 查询物料计算列表
     *
     * @param productId 产品编码
     * @return 物料计算集合
     */
    public int deletePlnMaterialComputingByProductId(Long productId);

    /**
     * 查询物料计算列表
     *
     * @param customerId 客户ID
     * @return 物料计算集合
     */
    public int deletePlnMaterialComputingByCustomerId(Long customerId);

    /**
     * 查询物料计算列表
     *
     * @param soId 销售订单
     * @return 物料计算集合
     */
    public int deletePlnMaterialComputingBySoId(Long soId);

    /**
     * 查询物料计算列表
     *
     * @param soItemId 订单项
     * @return 物料计算集合
     */
    public int deletePlnMaterialComputingBySoItemId(Long soItemId);
}
