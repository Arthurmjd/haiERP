package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingvideo;

/**
 * 产品作业视频Mapper接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface EngPruductRoutingvideoMapper 
{
    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    public EngPruductRoutingvideo selectEngPruductRoutingvideoById(Long id);

    /**
     * 查询产品作业视频列表
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 产品作业视频集合
     */
    public List<EngPruductRoutingvideo> selectEngPruductRoutingvideoList(EngPruductRoutingvideo engPruductRoutingvideo);

    /**
     * 查询产品作业视频列表
     *
     * @param routingId 产品路线ID
     * @return 产品作业视频集合
     */
    public List<EngPruductRoutingvideo> selectEngPruductRoutingvideoListByRoutingId(Long routingId);

    /**
     * 新增产品作业视频
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 结果
     */
    public int insertEngPruductRoutingvideo(EngPruductRoutingvideo engPruductRoutingvideo);

    /**
     * 修改产品作业视频
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 结果
     */
    public int updateEngPruductRoutingvideo(EngPruductRoutingvideo engPruductRoutingvideo);

    /**
     * 删除产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    public int deleteEngPruductRoutingvideoById(Long id);

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngPruductRoutingvideoByIds(Long[] ids);

    /**
     * 查询产品作业视频列表
     *
     * @param routingId 产品路线ID
     * @return 产品作业视频集合
     */
    public int deleteEngPruductRoutingvideoByRoutingId(Long routingId);
}
