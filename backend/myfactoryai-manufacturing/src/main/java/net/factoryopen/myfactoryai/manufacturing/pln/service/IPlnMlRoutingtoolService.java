package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingtool;

/**
 * 批次工具Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlRoutingtoolService 
{
    /**
     * 查询批次工具
     * 
     * @param id 批次工具主键
     * @return 批次工具
     */
    public PlnMlRoutingtool selectPlnMlRoutingtoolById(Long id);

    /**
     * 查询批次工具列表
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 批次工具集合
     */
    public List<PlnMlRoutingtool> selectPlnMlRoutingtoolList(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 新增批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    public int insertPlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 修改批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    public int updatePlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool);

    /**
     * 批量删除批次工具
     * 
     * @param ids 需要删除的批次工具主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingtoolByIds(Long[] ids);

    /**
     * 删除批次工具信息
     * 
     * @param id 批次工具主键
     * @return 结果
     */
    public int deletePlnMlRoutingtoolById(Long id);


}
