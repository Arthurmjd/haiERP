package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardCategory;

/**
 * 安全风险分类Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsHazardCategoryMapper 
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
     * 删除安全风险分类
     * 
     * @param id 安全风险分类主键
     * @return 结果
     */
    public int deleteEhsHazardCategoryById(Long id);

    /**
     * 批量删除安全风险分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsHazardCategoryByIds(Long[] ids);
}
