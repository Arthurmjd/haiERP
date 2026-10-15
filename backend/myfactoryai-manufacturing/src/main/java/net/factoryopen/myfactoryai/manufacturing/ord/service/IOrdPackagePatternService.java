package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdPackagePattern;

/**
 * 包装方式预配Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdPackagePatternService 
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
     * 批量删除包装方式预配
     * 
     * @param ids 需要删除的包装方式预配主键集合
     * @return 结果
     */
    public int deleteOrdPackagePatternByIds(Long[] ids);

    /**
     * 删除包装方式预配信息
     * 
     * @param id 包装方式预配主键
     * @return 结果
     */
    public int deleteOrdPackagePatternById(Long id);


}
