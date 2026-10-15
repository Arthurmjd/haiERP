package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachineType;

/**
 * 机型Mapper接口
 * 
 * @author admin
 * @date 2024-08-11
 */
public interface ResMachineTypeMapper 
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
     * 删除机型
     * 
     * @param id 机型主键
     * @return 结果
     */
    public int deleteResMachineTypeById(Long id);

    /**
     * 批量删除机型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResMachineTypeByIds(Long[] ids);
}
