package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResTool;

/**
 * 工具管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface ResToolMapper 
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
     * 查询工具管理列表
     *
     * @param lineId 产线
     * @return 工具管理集合
     */
    public List<ResTool> selectResToolListByLineId(Long lineId);

    /**
     * 查询工具管理列表
     *
     * @param processId 工序ID
     * @return 工具管理集合
     */
    public List<ResTool> selectResToolListByProcessId(Long processId);

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
     * 删除工具管理
     * 
     * @param id 工具管理主键
     * @return 结果
     */
    public int deleteResToolById(Long id);

    /**
     * 批量删除工具管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResToolByIds(Long[] ids);

    /**
     * 查询工具管理列表
     *
     * @param lineId 产线
     * @return 工具管理集合
     */
    public int deleteResToolByLineId(Long lineId);

    /**
     * 查询工具管理列表
     *
     * @param processId 工序ID
     * @return 工具管理集合
     */
    public int deleteResToolByProcessId(Long processId);
}
