package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingdrawing;

/**
 * 工单工序图Service接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface IPlnWoRoutingdrawingService 
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
     * 批量删除工单工序图
     * 
     * @param ids 需要删除的工单工序图主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingdrawingByIds(Long[] ids);

    /**
     * 删除工单工序图信息
     * 
     * @param id 工单工序图主键
     * @return 结果
     */
    public int deletePlnWoRoutingdrawingById(Long id);


}
