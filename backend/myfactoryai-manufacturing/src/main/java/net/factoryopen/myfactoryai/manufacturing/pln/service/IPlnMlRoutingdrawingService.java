package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingdrawing;

/**
 * 批次工序图Service接口
 * 
 * @author admin
 * @date 2024-07-13
 */
public interface IPlnMlRoutingdrawingService 
{
    /**
     * 查询批次工序图
     * 
     * @param id 批次工序图主键
     * @return 批次工序图
     */
    public PlnMlRoutingdrawing selectPlnMlRoutingdrawingById(Long id);

    /**
     * 查询批次工序图列表
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 批次工序图集合
     */
    public List<PlnMlRoutingdrawing> selectPlnMlRoutingdrawingList(PlnMlRoutingdrawing plnMlRoutingdrawing);

    /**
     * 新增批次工序图
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 结果
     */
    public int insertPlnMlRoutingdrawing(PlnMlRoutingdrawing plnMlRoutingdrawing);

    /**
     * 修改批次工序图
     * 
     * @param plnMlRoutingdrawing 批次工序图
     * @return 结果
     */
    public int updatePlnMlRoutingdrawing(PlnMlRoutingdrawing plnMlRoutingdrawing);

    /**
     * 批量删除批次工序图
     * 
     * @param ids 需要删除的批次工序图主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingdrawingByIds(Long[] ids);

    /**
     * 删除批次工序图信息
     * 
     * @param id 批次工序图主键
     * @return 结果
     */
    public int deletePlnMlRoutingdrawingById(Long id);


}
