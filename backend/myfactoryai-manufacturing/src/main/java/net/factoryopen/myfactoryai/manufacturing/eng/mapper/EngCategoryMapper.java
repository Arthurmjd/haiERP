package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategory;
import org.springframework.stereotype.Service;

/**
 * 物料分类管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public interface EngCategoryMapper 
{
    /**
     * 查询物料分类管理
     * 
     * @param id 物料分类管理主键
     * @return 物料分类管理
     */
    public EngCategory selectEngCategoryById(Long id);

    /**
     * 查询物料分类管理列表
     * 
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngProductCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngPureProductCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngMaterialCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngPureMaterialOrProductCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngUtilityCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param engCategory 物料分类管理
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngCategoryList(EngCategory engCategory);

    /**
     * 查询物料分类管理列表
     *
     * @param parentId 父品类
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngCategoryListByParentId(Long parentId);

    /**
     * 查询物料分类管理列表
     *
     * @param lineId 产线ID
     * @return 物料分类管理集合
     */
    public List<EngCategory> selectEngCategoryListByLineId(Long lineId);

    /**
     * 新增物料分类管理
     * 
     * @param engCategory 物料分类管理
     * @return 结果
     */
    public int insertEngCategory(EngCategory engCategory);

    /**
     * 修改物料分类管理
     * 
     * @param engCategory 物料分类管理
     * @return 结果
     */
    public int updateEngCategory(EngCategory engCategory);

    /**
     * 删除物料分类管理
     * 
     * @param id 物料分类管理主键
     * @return 结果
     */
    public int deleteEngCategoryById(Long id);

    /**
     * 批量删除物料分类管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngCategoryByIds(Long[] ids);

    /**
     * 查询物料分类管理列表
     *
     * @param parentId 父品类
     * @return 物料分类管理集合
     */
    public int deleteEngCategoryByParentId(Long parentId);

    /**
     * 查询物料分类管理列表
     *
     * @param lineId 产线ID
     * @return 物料分类管理集合
     */
    public int deleteEngCategoryByLineId(Long lineId);
}
