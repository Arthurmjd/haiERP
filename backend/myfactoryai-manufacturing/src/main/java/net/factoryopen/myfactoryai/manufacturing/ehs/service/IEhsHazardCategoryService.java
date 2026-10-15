package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardCategory;

/**
 * 安全风险分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsHazardCategoryService 
{
    /**
     * 查询安全风险分类
     * 
     * @param id 安全风险分类主键
     * @return 安全风险分类
     */
    public EhsHazardCategory selectEhsHazardCategoryById(Long id);

    /**
     * 查询安全风险分类列表
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 安全风险分类集合
     */
    public List<EhsHazardCategory> selectEhsHazardCategoryList(EhsHazardCategory ehsHazardCategory);

    /**
     * 新增安全风险分类
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 结果
     */
    public int insertEhsHazardCategory(EhsHazardCategory ehsHazardCategory);

    /**
     * 修改安全风险分类
     * 
     * @param ehsHazardCategory 安全风险分类
     * @return 结果
     */
    public int updateEhsHazardCategory(EhsHazardCategory ehsHazardCategory);

    /**
     * 批量删除安全风险分类
     * 
     * @param ids 需要删除的安全风险分类主键集合
     * @return 结果
     */
    public int deleteEhsHazardCategoryByIds(Long[] ids);

    /**
     * 删除安全风险分类信息
     * 
     * @param id 安全风险分类主键
     * @return 结果
     */
    public int deleteEhsHazardCategoryById(Long id);


}
