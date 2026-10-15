package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingtool;

/**
 * 产品工具Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IEngProductRoutingtoolService 
{
    /**
     * 查询产品工具
     * 
     * @param id 产品工具主键
     * @return 产品工具
     */
    public EngProductRoutingtool selectEngProductRoutingtoolById(Long id);

    /**
     * 查询产品工具列表
     * 
     * @param engProductRoutingtool 产品工具
     * @return 产品工具集合
     */
    public List<EngProductRoutingtool> selectEngProductRoutingtoolList(EngProductRoutingtool engProductRoutingtool);

    /**
     * 新增产品工具
     * 
     * @param engProductRoutingtool 产品工具
     * @return 结果
     */
    public int insertEngProductRoutingtool(EngProductRoutingtool engProductRoutingtool);

    /**
     * 修改产品工具
     * 
     * @param engProductRoutingtool 产品工具
     * @return 结果
     */
    public int updateEngProductRoutingtool(EngProductRoutingtool engProductRoutingtool);

    /**
     * 批量删除产品工具
     * 
     * @param ids 需要删除的产品工具主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingtoolByIds(Long[] ids);

    /**
     * 删除产品工具信息
     * 
     * @param id 产品工具主键
     * @return 结果
     */
    public int deleteEngProductRoutingtoolById(Long id);


}
