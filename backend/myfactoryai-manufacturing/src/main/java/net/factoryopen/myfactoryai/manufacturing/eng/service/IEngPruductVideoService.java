package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductVideo;

/**
 * 产品作业视频Service接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface IEngPruductVideoService 
{
    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    public EngPruductVideo selectEngPruductVideoById(Long id);

    /**
     * 查询产品作业视频列表
     * 
     * @param engPruductVideo 产品作业视频
     * @return 产品作业视频集合
     */
    public List<EngPruductVideo> selectEngPruductVideoList(EngPruductVideo engPruductVideo);

    /**
     * 新增产品作业视频
     * 
     * @param engPruductVideo 产品作业视频
     * @return 结果
     */
    public int insertEngPruductVideo(EngPruductVideo engPruductVideo);

    /**
     * 修改产品作业视频
     * 
     * @param engPruductVideo 产品作业视频
     * @return 结果
     */
    public int updateEngPruductVideo(EngPruductVideo engPruductVideo);

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的产品作业视频主键集合
     * @return 结果
     */
    public int deleteEngPruductVideoByIds(Long[] ids);

    /**
     * 删除产品作业视频信息
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    public int deleteEngPruductVideoById(Long id);


}
