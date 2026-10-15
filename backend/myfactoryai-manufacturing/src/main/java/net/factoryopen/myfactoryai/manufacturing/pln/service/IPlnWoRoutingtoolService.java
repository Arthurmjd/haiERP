package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingtool;

/**
 * 工单工具Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IPlnWoRoutingtoolService 
{
    /**
     * 查询工单工具
     * 
     * @param id 工单工具主键
     * @return 工单工具
     */
    public PlnWoRoutingtool selectPlnWoRoutingtoolById(Long id);

    /**
     * 查询工单工具列表
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 工单工具集合
     */
    public List<PlnWoRoutingtool> selectPlnWoRoutingtoolList(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 新增工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    public int insertPlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 修改工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    public int updatePlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool);

    /**
     * 批量删除工单工具
     * 
     * @param ids 需要删除的工单工具主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingtoolByIds(Long[] ids);

    /**
     * 删除工单工具信息
     * 
     * @param id 工单工具主键
     * @return 结果
     */
    public int deletePlnWoRoutingtoolById(Long id);


}
