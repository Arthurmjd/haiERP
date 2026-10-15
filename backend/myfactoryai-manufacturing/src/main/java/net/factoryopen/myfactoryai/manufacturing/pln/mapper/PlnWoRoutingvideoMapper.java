package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingvideo;

/**
 * 产品作业视频Mapper接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface PlnWoRoutingvideoMapper 
{
    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    public PlnWoRoutingvideo selectPlnWoRoutingvideoById(Long id);

    /**
     * 查询产品作业视频列表
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 产品作业视频集合
     */
    public List<PlnWoRoutingvideo> selectPlnWoRoutingvideoList(PlnWoRoutingvideo plnWoRoutingvideo);

    /**
     * 查询产品作业视频列表
     *
     * @param routingId 产品路线ID
     * @return 产品作业视频集合
     */
    public List<PlnWoRoutingvideo> selectPlnWoRoutingvideoListByRoutingId(Long routingId);

    /**
     * 新增产品作业视频
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 结果
     */
    public int insertPlnWoRoutingvideo(PlnWoRoutingvideo plnWoRoutingvideo);

    /**
     * 修改产品作业视频
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 结果
     */
    public int updatePlnWoRoutingvideo(PlnWoRoutingvideo plnWoRoutingvideo);

    /**
     * 删除产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    public int deletePlnWoRoutingvideoById(Long id);

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingvideoByIds(Long[] ids);

    /**
     * 查询产品作业视频列表
     *
     * @param routingId 产品路线ID
     * @return 产品作业视频集合
     */
    public int deletePlnWoRoutingvideoByRoutingId(Long routingId);
}
