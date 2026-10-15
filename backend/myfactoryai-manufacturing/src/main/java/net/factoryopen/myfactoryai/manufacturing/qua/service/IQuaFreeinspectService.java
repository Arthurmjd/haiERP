package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspect;

/**
 * 免检策略管理Service接口
 * 
 * @author admin
 * @date 2024-11-12
 */
public interface IQuaFreeinspectService 
{
    /**
     * 查询免检策略管理
     * 
     * @param id 免检策略管理主键
     * @return 免检策略管理
     */
    public QuaFreeinspect selectQuaFreeinspectById(Long id);

    /**
     * 查询免检策略管理列表
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 免检策略管理集合
     */
    public List<QuaFreeinspect> selectQuaFreeinspectList(QuaFreeinspect quaFreeinspect);

    /**
     * 新增免检策略管理
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 结果
     */
    public int insertQuaFreeinspect(QuaFreeinspect quaFreeinspect);

    /**
     * 修改免检策略管理
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 结果
     */
    public int updateQuaFreeinspect(QuaFreeinspect quaFreeinspect);

    /**
     * 批量删除免检策略管理
     * 
     * @param ids 需要删除的免检策略管理主键集合
     * @return 结果
     */
    public int deleteQuaFreeinspectByIds(Long[] ids);

    /**
     * 删除免检策略管理信息
     * 
     * @param id 免检策略管理主键
     * @return 结果
     */
    public int deleteQuaFreeinspectById(Long id);


}
