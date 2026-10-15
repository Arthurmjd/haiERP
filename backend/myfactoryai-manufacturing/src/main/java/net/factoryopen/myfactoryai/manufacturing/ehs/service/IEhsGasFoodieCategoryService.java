package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodieCategory;

/**
 * 气耗设备分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsGasFoodieCategoryService 
{
    /**
     * 查询气耗设备分类
     * 
     * @param id 气耗设备分类主键
     * @return 气耗设备分类
     */
    public EhsGasFoodieCategory selectEhsGasFoodieCategoryById(Long id);

    /**
     * 查询气耗设备分类列表
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 气耗设备分类集合
     */
    public List<EhsGasFoodieCategory> selectEhsGasFoodieCategoryList(EhsGasFoodieCategory ehsGasFoodieCategory);

    /**
     * 新增气耗设备分类
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 结果
     */
    public int insertEhsGasFoodieCategory(EhsGasFoodieCategory ehsGasFoodieCategory);

    /**
     * 修改气耗设备分类
     * 
     * @param ehsGasFoodieCategory 气耗设备分类
     * @return 结果
     */
    public int updateEhsGasFoodieCategory(EhsGasFoodieCategory ehsGasFoodieCategory);

    /**
     * 批量删除气耗设备分类
     * 
     * @param ids 需要删除的气耗设备分类主键集合
     * @return 结果
     */
    public int deleteEhsGasFoodieCategoryByIds(Long[] ids);

    /**
     * 删除气耗设备分类信息
     * 
     * @param id 气耗设备分类主键
     * @return 结果
     */
    public int deleteEhsGasFoodieCategoryById(Long id);


}
