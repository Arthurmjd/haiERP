package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionCategory;

/**
 * 污染排放分类Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPollutionCategoryService 
{
    /**
     * 查询污染排放分类
     * 
     * @param id 污染排放分类主键
     * @return 污染排放分类
     */
    public EhsPollutionCategory selectEhsPollutionCategoryById(Long id);

    /**
     * 查询污染排放分类列表
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 污染排放分类集合
     */
    public List<EhsPollutionCategory> selectEhsPollutionCategoryList(EhsPollutionCategory ehsPollutionCategory);

    /**
     * 新增污染排放分类
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 结果
     */
    public int insertEhsPollutionCategory(EhsPollutionCategory ehsPollutionCategory);

    /**
     * 修改污染排放分类
     * 
     * @param ehsPollutionCategory 污染排放分类
     * @return 结果
     */
    public int updateEhsPollutionCategory(EhsPollutionCategory ehsPollutionCategory);

    /**
     * 批量删除污染排放分类
     * 
     * @param ids 需要删除的污染排放分类主键集合
     * @return 结果
     */
    public int deleteEhsPollutionCategoryByIds(Long[] ids);

    /**
     * 删除污染排放分类信息
     * 
     * @param id 污染排放分类主键
     * @return 结果
     */
    public int deleteEhsPollutionCategoryById(Long id);


}
