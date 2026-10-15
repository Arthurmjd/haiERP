package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResShiftsys;

/**
 * 班制管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-12-28
 */
public interface IResShiftsysService 
{
    /**
     * 查询班制管理
     * 
     * @param id 班制管理主键
     * @return 班制管理
     */
    public ResShiftsys selectResShiftsysById(Long id);

    /**
     * 查询班制管理列表
     * 
     * @param resShiftsys 班制管理
     * @return 班制管理集合
     */
    public List<ResShiftsys> selectResShiftsysList(ResShiftsys resShiftsys);

    /**
     * 新增班制管理
     * 
     * @param resShiftsys 班制管理
     * @return 结果
     */
    public int insertResShiftsys(ResShiftsys resShiftsys);

    /**
     * 修改班制管理
     * 
     * @param resShiftsys 班制管理
     * @return 结果
     */
    public int updateResShiftsys(ResShiftsys resShiftsys);

    /**
     * 批量删除班制管理
     * 
     * @param ids 需要删除的班制管理主键集合
     * @return 结果
     */
    public int deleteResShiftsysByIds(Long[] ids);

    /**
     * 删除班制管理信息
     * 
     * @param id 班制管理主键
     * @return 结果
     */
    public int deleteResShiftsysById(Long id);


}
