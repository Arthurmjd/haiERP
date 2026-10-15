package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductVideo;

/**
 * 产品作业视频Mapper接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface EngPruductVideoMapper 
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
     * 查询产品作业视频列表
     *
     * @param productId 产品ID
     * @return 产品作业视频集合
     */
    public List<EngPruductVideo> selectEngPruductVideoListByProductId(Long productId);

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
     * 删除产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    public int deleteEngPruductVideoById(Long id);

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngPruductVideoByIds(Long[] ids);

    /**
     * 查询产品作业视频列表
     *
     * @param productId 产品ID
     * @return 产品作业视频集合
     */
    public int deleteEngPruductVideoByProductId(Long productId);
}
