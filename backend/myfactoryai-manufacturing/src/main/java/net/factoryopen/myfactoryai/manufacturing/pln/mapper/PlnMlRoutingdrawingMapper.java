package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingdrawing;
import org.springframework.stereotype.Service;

/**
 * 批次工序图Mapper接口
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public interface PlnMlRoutingdrawingMapper 
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
     * 查询批次工序图列表
     *
     * @param routingId 产品路线ID
     * @return 批次工序图集合
     */
    public List<PlnMlRoutingdrawing> selectPlnMlRoutingdrawingListByRoutingId(Long routingId);

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
     * 删除批次工序图
     * 
     * @param id 批次工序图主键
     * @return 结果
     */
    public int deletePlnMlRoutingdrawingById(Long id);

    /**
     * 批量删除批次工序图
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingdrawingByIds(Long[] ids);

    /**
     * 查询批次工序图列表
     *
     * @param routingId 产品路线ID
     * @return 批次工序图集合
     */
    public int deletePlnMlRoutingdrawingByRoutingId(Long routingId);
}
