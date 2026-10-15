package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingvideo;

/**
 * 产品作业视频Service接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface IEngPruductRoutingvideoService 
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
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的产品作业视频主键集合
     * @return 结果
     */
    public int deleteEngPruductRoutingvideoByIds(Long[] ids);

    /**
     * 删除产品作业视频信息
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    public int deleteEngPruductRoutingvideoById(Long id);


}
