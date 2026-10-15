package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingdrawing;

/**
 * 工单工序图Mapper接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface PlnWoRoutingdrawingMapper 
{
    /**
     * 查询工单工序图
     * 
     * @param id 工单工序图主键
     * @return 工单工序图
     */
    public PlnWoRoutingdrawing selectPlnWoRoutingdrawingById(Long id);

    /**
     * 查询工单工序图列表
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 工单工序图集合
     */
    public List<PlnWoRoutingdrawing> selectPlnWoRoutingdrawingList(PlnWoRoutingdrawing plnWoRoutingdrawing);

    /**
     * 查询工单工序图列表
     *
     * @param routingId 产品路线ID
     * @return 工单工序图集合
     */
    public List<PlnWoRoutingdrawing> selectPlnWoRoutingdrawingListByRoutingId(Long routingId);

    /**
     * 新增工单工序图
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 结果
     */
    public int insertPlnWoRoutingdrawing(PlnWoRoutingdrawing plnWoRoutingdrawing);

    /**
     * 修改工单工序图
     * 
     * @param plnWoRoutingdrawing 工单工序图
     * @return 结果
     */
    public int updatePlnWoRoutingdrawing(PlnWoRoutingdrawing plnWoRoutingdrawing);

    /**
     * 删除工单工序图
     * 
     * @param id 工单工序图主键
     * @return 结果
     */
    public int deletePlnWoRoutingdrawingById(Long id);

    /**
     * 批量删除工单工序图
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingdrawingByIds(Long[] ids);

    /**
     * 查询工单工序图列表
     *
     * @param routingId 产品路线ID
     * @return 工单工序图集合
     */
    public int deletePlnWoRoutingdrawingByRoutingId(Long routingId);
}
