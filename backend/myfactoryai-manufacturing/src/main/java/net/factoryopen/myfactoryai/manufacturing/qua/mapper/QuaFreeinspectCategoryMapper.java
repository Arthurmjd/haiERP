package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectCategory;

/**
 * 免检品类Mapper接口
 * 
 * @author admin
 * @date 2024-11-12
 */
public interface QuaFreeinspectCategoryMapper 
{
    /**
     * 查询免检品类
     * 
     * @param id 免检品类主键
     * @return 免检品类
     */
    public QuaFreeinspectCategory selectQuaFreeinspectCategoryById(Long id);

    /**
     * 查询免检品类列表
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 免检品类集合
     */
    public List<QuaFreeinspectCategory> selectQuaFreeinspectCategoryList(QuaFreeinspectCategory quaFreeinspectCategory);

    /**
     * 查询免检品类列表
     *
     * @param policyId 免检策略ID
     * @return 免检品类集合
     */
    public List<QuaFreeinspectCategory> selectQuaFreeinspectCategoryListByPolicyId(Long policyId);

    /**
     * 查询免检品类列表
     *
     * @param categoryId 免检品类ID
     * @return 免检品类集合
     */
    public List<QuaFreeinspectCategory> selectQuaFreeinspectCategoryListByCategoryId(Long categoryId);

    /**
     * 新增免检品类
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 结果
     */
    public int insertQuaFreeinspectCategory(QuaFreeinspectCategory quaFreeinspectCategory);

    /**
     * 修改免检品类
     * 
     * @param quaFreeinspectCategory 免检品类
     * @return 结果
     */
    public int updateQuaFreeinspectCategory(QuaFreeinspectCategory quaFreeinspectCategory);

    /**
     * 删除免检品类
     * 
     * @param id 免检品类主键
     * @return 结果
     */
    public int deleteQuaFreeinspectCategoryById(Long id);

    /**
     * 批量删除免检品类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaFreeinspectCategoryByIds(Long[] ids);

    /**
     * 查询免检品类列表
     *
     * @param policyId 免检策略ID
     * @return 免检品类集合
     */
    public int deleteQuaFreeinspectCategoryByPolicyId(Long policyId);

    /**
     * 查询免检品类列表
     *
     * @param categoryId 免检品类ID
     * @return 免检品类集合
     */
    public int deleteQuaFreeinspectCategoryByCategoryId(Long categoryId);
}
