package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCompound;

/**
 * 材质数据Mapper接口
 * 
 * @author admin
 * @date 2024-11-29
 */
public interface EngCompoundMapper 
{
    /**
     * 查询材质数据
     * 
     * @param id 材质数据主键
     * @return 材质数据
     */
    public EngCompound selectEngCompoundById(Long id);

    /**
     * 查询材质数据列表
     * 
     * @param engCompound 材质数据
     * @return 材质数据集合
     */
    public List<EngCompound> selectEngCompoundList(EngCompound engCompound);

    /**
     * 查询材质数据列表
     *
     * @param parentId 父材质ID
     * @return 材质数据集合
     */
    public List<EngCompound> selectEngCompoundListByParentId(Long parentId);

    /**
     * 新增材质数据
     * 
     * @param engCompound 材质数据
     * @return 结果
     */
    public int insertEngCompound(EngCompound engCompound);

    /**
     * 修改材质数据
     * 
     * @param engCompound 材质数据
     * @return 结果
     */
    public int updateEngCompound(EngCompound engCompound);

    /**
     * 删除材质数据
     * 
     * @param id 材质数据主键
     * @return 结果
     */
    public int deleteEngCompoundById(Long id);

    /**
     * 批量删除材质数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngCompoundByIds(Long[] ids);

    /**
     * 查询材质数据列表
     *
     * @param parentId 父材质ID
     * @return 材质数据集合
     */
    public int deleteEngCompoundByParentId(Long parentId);
}
