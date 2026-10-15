package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingdrawing;

/**
 * 产品工序图Service接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface IEngProductRoutingdrawingService 
{
    /**
     * 查询产品工序图
     * 
     * @param id 产品工序图主键
     * @return 产品工序图
     */
    public EngProductRoutingdrawing selectEngProductRoutingdrawingById(Long id);

    /**
     * 查询产品工序图列表
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 产品工序图集合
     */
    public List<EngProductRoutingdrawing> selectEngProductRoutingdrawingList(EngProductRoutingdrawing engProductRoutingdrawing);

    /**
     * 新增产品工序图
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 结果
     */
    public int insertEngProductRoutingdrawing(EngProductRoutingdrawing engProductRoutingdrawing);

    /**
     * 修改产品工序图
     * 
     * @param engProductRoutingdrawing 产品工序图
     * @return 结果
     */
    public int updateEngProductRoutingdrawing(EngProductRoutingdrawing engProductRoutingdrawing);

    /**
     * 批量删除产品工序图
     * 
     * @param ids 需要删除的产品工序图主键集合
     * @return 结果
     */
    public int deleteEngProductRoutingdrawingByIds(Long[] ids);

    /**
     * 删除产品工序图信息
     * 
     * @param id 产品工序图主键
     * @return 结果
     */
    public int deleteEngProductRoutingdrawingById(Long id);


}
