package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMaterialComputing;

/**
 * 物料计算Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IPlnMaterialComputingService 
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
     * 批量删除物料计算
     * 
     * @param ids 需要删除的物料计算主键集合
     * @return 结果
     */
    public int deletePlnMaterialComputingByIds(Long[] ids);

    /**
     * 删除物料计算信息
     * 
     * @param id 物料计算主键
     * @return 结果
     */
    public int deletePlnMaterialComputingById(Long id);

    /**
     * 算料
     *
     * @param ids id集合
     * @return 结果
     */
    public int runMrp(Long[] ids) throws Exception;


}
