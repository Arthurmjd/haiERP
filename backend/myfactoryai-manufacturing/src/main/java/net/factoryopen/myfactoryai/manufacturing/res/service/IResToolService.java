package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResTool;

/**
 * 工具管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface IResToolService 
{
    /**
     * 查询工具管理
     * 
     * @param id 工具管理主键
     * @return 工具管理
     */
    public ResTool selectResToolById(Long id);

    /**
     * 查询工具管理列表
     * 
     * @param resTool 工具管理
     * @return 工具管理集合
     */
    public List<ResTool> selectResToolList(ResTool resTool);

    /**
     * 新增工具管理
     * 
     * @param resTool 工具管理
     * @return 结果
     */
    public int insertResTool(ResTool resTool);

    /**
     * 修改工具管理
     * 
     * @param resTool 工具管理
     * @return 结果
     */
    public int updateResTool(ResTool resTool);

    /**
     * 批量删除工具管理
     * 
     * @param ids 需要删除的工具管理主键集合
     * @return 结果
     */
    public int deleteResToolByIds(Long[] ids);

    /**
     * 删除工具管理信息
     * 
     * @param id 工具管理主键
     * @return 结果
     */
    public int deleteResToolById(Long id);


}
