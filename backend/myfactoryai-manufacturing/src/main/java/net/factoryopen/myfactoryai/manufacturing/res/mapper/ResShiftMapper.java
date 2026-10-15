package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShift;

/**
 * 班别Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
public interface ResShiftMapper 
{
    /**
     * 查询班别
     * 
     * @param id 班别主键
     * @return 班别
     */
    public ResShift selectResShiftById(Long id);

    /**
     * 查询班别列表
     * 
     * @param resShift 班别
     * @return 班别集合
     */
    public List<ResShift> selectResShiftList(ResShift resShift);

    /**
     * 查询班别列表
     *
     * @param shiftsysId 班制
     * @return 班别集合
     */
    public List<ResShift> selectResShiftListByShiftsysId(Long shiftsysId);

    /**
     * 新增班别
     * 
     * @param resShift 班别
     * @return 结果
     */
    public int insertResShift(ResShift resShift);

    /**
     * 修改班别
     * 
     * @param resShift 班别
     * @return 结果
     */
    public int updateResShift(ResShift resShift);

    /**
     * 删除班别
     * 
     * @param id 班别主键
     * @return 结果
     */
    public int deleteResShiftById(Long id);

    /**
     * 批量删除班别
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResShiftByIds(Long[] ids);

    /**
     * 查询班别列表
     *
     * @param shiftsysId 班制
     * @return 班别集合
     */
    public int deleteResShiftByShiftsysId(Long shiftsysId);
}
