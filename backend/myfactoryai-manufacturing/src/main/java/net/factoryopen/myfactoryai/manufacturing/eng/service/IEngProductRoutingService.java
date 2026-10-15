package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRouting;

/**
 * 制造工艺管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IEngProductRoutingService 
{
    /**
     * 查询制造工艺管理
     * 
     * @param id 制造工艺管理主键
     * @return 制造工艺管理
     */
    public EngProductRouting selectEngProductRoutingById(Long id);

    /**
     * 查询制造工艺管理列表
     * 
     * @param engProductRouting 制造工艺管理
     * @return 制造工艺管理集合
     */
    public List<EngProductRouting> selectEngProductRoutingList(EngProductRouting engProductRouting);

    /**
     * 新增制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    public int insertEngProductRouting(EngProductRouting engProductRouting);

    /**
     * 修改制造工艺管理
     * 
     * @param engProductRouting 制造工艺管理
     * @return 结果
     */
    public int updateEngProductRouting(EngProductRouting engProductRouting);

    /**
     * 批量删除制造工艺管理
     * 
     * @param ids 需要删除的制造工艺管理主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingByIds(Long[] ids);

    /**
     * 删除制造工艺管理信息
     * 
     * @param id 制造工艺管理主键
     * @return 结果
     */
    public int deleteEngProductRoutingById(Long id);


}
