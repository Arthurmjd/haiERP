package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachineType;

/**
 * 机型Service接口
 * 
 * @author admin
 * @date 2024-08-11
 */
public interface IResMachineTypeService 
{
    /**
     * 查询机型
     * 
     * @param id 机型主键
     * @return 机型
     */
    public ResMachineType selectResMachineTypeById(Long id);

    /**
     * 查询机型列表
     * 
     * @param resMachineType 机型
     * @return 机型集合
     */
    public List<ResMachineType> selectResMachineTypeList(ResMachineType resMachineType);

    /**
     * 新增机型
     * 
     * @param resMachineType 机型
     * @return 结果
     */
    public int insertResMachineType(ResMachineType resMachineType);

    /**
     * 修改机型
     * 
     * @param resMachineType 机型
     * @return 结果
     */
    public int updateResMachineType(ResMachineType resMachineType);

    /**
     * 批量删除机型
     * 
     * @param ids 需要删除的机型主键集合
     * @return 结果
     */
    public int deleteResMachineTypeByIds(Long[] ids);

    /**
     * 删除机型信息
     * 
     * @param id 机型主键
     * @return 结果
     */
    public int deleteResMachineTypeById(Long id);


}
