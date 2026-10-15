package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;

/**
 * 班组管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface IResWorkgroupService 
{
    /**
     * 查询班组管理
     * 
     * @param id 班组管理主键
     * @return 班组管理
     */
    public ResWorkgroup selectResWorkgroupById(Long id);

    /**
     * 查询班组管理列表
     * 
     * @param resWorkgroup 班组管理
     * @return 班组管理集合
     */
    public List<ResWorkgroup> selectResWorkgroupList(ResWorkgroup resWorkgroup);

    /**
     * 新增班组管理
     * 
     * @param resWorkgroup 班组管理
     * @return 结果
     */
    public int insertResWorkgroup(ResWorkgroup resWorkgroup);

    /**
     * 修改班组管理
     * 
     * @param resWorkgroup 班组管理
     * @return 结果
     */
    public int updateResWorkgroup(ResWorkgroup resWorkgroup);

    /**
     * 批量删除班组管理
     * 
     * @param ids 需要删除的班组管理主键集合
     * @return 结果
     */
    public int deleteResWorkgroupByIds(Long[] ids);

    /**
     * 删除班组管理信息
     * 
     * @param id 班组管理主键
     * @return 结果
     */
    public int deleteResWorkgroupById(Long id);


}
