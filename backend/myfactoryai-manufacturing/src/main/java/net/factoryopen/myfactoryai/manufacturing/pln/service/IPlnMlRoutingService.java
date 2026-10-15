package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRouting;

/**
 * 批次工艺管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlRoutingService 
{
    /**
     * 查询批次工艺管理
     * 
     * @param id 批次工艺管理主键
     * @return 批次工艺管理
     */
    public PlnMlRouting selectPlnMlRoutingById(Long id);

    /**
     * 查询批次工艺管理列表
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 批次工艺管理集合
     */
    public List<PlnMlRouting> selectPlnMlRoutingList(PlnMlRouting plnMlRouting);

    /**
     * 新增批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    public int insertPlnMlRouting(PlnMlRouting plnMlRouting);

    /**
     * 修改批次工艺管理
     * 
     * @param plnMlRouting 批次工艺管理
     * @return 结果
     */
    public int updatePlnMlRouting(PlnMlRouting plnMlRouting);

    /**
     * 批量删除批次工艺管理
     * 
     * @param ids 需要删除的批次工艺管理主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingByIds(Long[] ids);

    /**
     * 删除批次工艺管理信息
     * 
     * @param id 批次工艺管理主键
     * @return 结果
     */
    public int deletePlnMlRoutingById(Long id);


}
