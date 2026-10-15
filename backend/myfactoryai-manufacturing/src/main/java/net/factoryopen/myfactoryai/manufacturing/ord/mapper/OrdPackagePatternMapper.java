package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdPackagePattern;

/**
 * 包装方式预配Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface OrdPackagePatternMapper 
{
    /**
     * 查询包装方式预配
     * 
     * @param id 包装方式预配主键
     * @return 包装方式预配
     */
    public OrdPackagePattern selectOrdPackagePatternById(Long id);

    /**
     * 查询包装方式预配列表
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 包装方式预配集合
     */
    public List<OrdPackagePattern> selectOrdPackagePatternList(OrdPackagePattern ordPackagePattern);

    /**
     * 查询包装方式预配列表
     *
     * @param weightUnitId 重量单位
     * @return 包装方式预配集合
     */
    public List<OrdPackagePattern> selectOrdPackagePatternListByWeightUnitId(Long weightUnitId);

    /**
     * 查询包装方式预配列表
     *
     * @param childUnitId 内装单位
     * @return 包装方式预配集合
     */
    public List<OrdPackagePattern> selectOrdPackagePatternListByChildUnitId(Long childUnitId);

    /**
     * 新增包装方式预配
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 结果
     */
    public int insertOrdPackagePattern(OrdPackagePattern ordPackagePattern);

    /**
     * 修改包装方式预配
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 结果
     */
    public int updateOrdPackagePattern(OrdPackagePattern ordPackagePattern);

    /**
     * 删除包装方式预配
     * 
     * @param id 包装方式预配主键
     * @return 结果
     */
    public int deleteOrdPackagePatternById(Long id);

    /**
     * 批量删除包装方式预配
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdPackagePatternByIds(Long[] ids);

    /**
     * 查询包装方式预配列表
     *
     * @param weightUnitId 重量单位
     * @return 包装方式预配集合
     */
    public int deleteOrdPackagePatternByWeightUnitId(Long weightUnitId);

    /**
     * 查询包装方式预配列表
     *
     * @param childUnitId 内装单位
     * @return 包装方式预配集合
     */
    public int deleteOrdPackagePatternByChildUnitId(Long childUnitId);
}
