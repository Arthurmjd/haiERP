package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShift;

/**
 * 班别Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
public interface IResShiftService 
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
     * 批量删除班别
     * 
     * @param ids 需要删除的班别主键集合
     * @return 结果
     */
    public int deleteResShiftByIds(Long[] ids);

    /**
     * 删除班别信息
     * 
     * @param id 班别主键
     * @return 结果
     */
    public int deleteResShiftById(Long id);


}
