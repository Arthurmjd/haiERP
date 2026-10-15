package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;

/**
 * 产成品管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngProductBasisService 
{
    /**
     * 查询产成品管理
     * 
     * @param id 产成品管理主键
     * @return 产成品管理
     */
    public EngProductBasis selectEngProductBasisById(Long id);

    /**
     * 查询产成品管理
     *
     * @param materialCode 产品编码
     * @return 产成品管理
     */
    public EngProductBasis selectEngProductBasisByCode(String materialCode);

    /**
     * 查询产成品管理列表
     * 
     * @param engProductBasis 产成品管理
     * @return 产成品管理集合
     */
    public List<EngProductBasis> selectEngProductBasisList(EngProductBasis engProductBasis);

    /**
     * 新增产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    public int insertEngProductBasis(EngProductBasis engProductBasis);

    /**
     * 修改产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    public int updateEngProductBasis(EngProductBasis engProductBasis);

    /**
     * 批量删除产成品管理
     * 
     * @param ids 需要删除的产成品管理主键集合
     * @return 结果
     */
    public int deleteEngProductBasisByIds(Long[] ids);

    /**
     * 删除产成品管理信息
     * 
     * @param id 产成品管理主键
     * @return 结果
     */
    public int deleteEngProductBasisById(Long id);


}
