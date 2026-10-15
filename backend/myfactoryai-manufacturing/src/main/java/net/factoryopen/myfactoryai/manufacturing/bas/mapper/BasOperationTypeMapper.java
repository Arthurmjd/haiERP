package net.factoryopen.myfactoryai.manufacturing.bas.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasOperationType;

/**
 * 作业类型Mapper接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface BasOperationTypeMapper 
{
    /**
     * 查询作业类型
     * 
     * @param id 作业类型主键
     * @return 作业类型
     */
    public BasOperationType selectBasOperationTypeById(Long id);

    /**
     * 查询作业类型列表
     * 
     * @param basOperationType 作业类型
     * @return 作业类型集合
     */
    public List<BasOperationType> selectBasOperationTypeList(BasOperationType basOperationType);

    /**
     * 新增作业类型
     * 
     * @param basOperationType 作业类型
     * @return 结果
     */
    public int insertBasOperationType(BasOperationType basOperationType);

    /**
     * 修改作业类型
     * 
     * @param basOperationType 作业类型
     * @return 结果
     */
    public int updateBasOperationType(BasOperationType basOperationType);

    /**
     * 删除作业类型
     * 
     * @param id 作业类型主键
     * @return 结果
     */
    public int deleteBasOperationTypeById(Long id);

    /**
     * 批量删除作业类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasOperationTypeByIds(Long[] ids);
}
