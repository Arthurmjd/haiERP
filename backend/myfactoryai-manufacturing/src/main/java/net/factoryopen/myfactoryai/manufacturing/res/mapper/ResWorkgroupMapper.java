package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;
import org.springframework.stereotype.Service;

/**
 * 班组管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public interface ResWorkgroupMapper 
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
     * 查询班组管理列表
     *
     * @param shiftsysId 班制
     * @return 班组管理集合
     */
    public List<ResWorkgroup> selectResWorkgroupListByShiftsysId(Long shiftsysId);

    /**
     * 查询班组管理列表
     *
     * @param lineId 产线
     * @return 班组管理集合
     */
    public List<ResWorkgroup> selectResWorkgroupListByLineId(Long lineId);

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
     * 删除班组管理
     * 
     * @param id 班组管理主键
     * @return 结果
     */
    public int deleteResWorkgroupById(Long id);

    /**
     * 批量删除班组管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResWorkgroupByIds(Long[] ids);

    /**
     * 查询班组管理列表
     *
     * @param shiftsysId 班制
     * @return 班组管理集合
     */
    public int deleteResWorkgroupByShiftsysId(Long shiftsysId);

    /**
     * 查询班组管理列表
     *
     * @param lineId 产线
     * @return 班组管理集合
     */
    public int deleteResWorkgroupByLineId(Long lineId);
}
