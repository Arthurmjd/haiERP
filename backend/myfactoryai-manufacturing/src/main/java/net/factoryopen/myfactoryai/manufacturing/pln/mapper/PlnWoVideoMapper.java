package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoVideo;

/**
 * 工单作业视频Mapper接口
 * 
 * @author admin
 * @date 2024-07-12
 */
public interface PlnWoVideoMapper 
{
    /**
     * 查询工单作业视频
     * 
     * @param id 工单作业视频主键
     * @return 工单作业视频
     */
    public PlnWoVideo selectPlnWoVideoById(Long id);

    /**
     * 查询工单作业视频列表
     * 
     * @param plnWoVideo 工单作业视频
     * @return 工单作业视频集合
     */
    public List<PlnWoVideo> selectPlnWoVideoList(PlnWoVideo plnWoVideo);

    /**
     * 查询工单作业视频列表
     *
     * @param woId 工单ID
     * @return 工单作业视频集合
     */
    public List<PlnWoVideo> selectPlnWoVideoListByWoId(Long woId);

    /**
     * 新增工单作业视频
     * 
     * @param plnWoVideo 工单作业视频
     * @return 结果
     */
    public int insertPlnWoVideo(PlnWoVideo plnWoVideo);

    /**
     * 修改工单作业视频
     * 
     * @param plnWoVideo 工单作业视频
     * @return 结果
     */
    public int updatePlnWoVideo(PlnWoVideo plnWoVideo);

    /**
     * 删除工单作业视频
     * 
     * @param id 工单作业视频主键
     * @return 结果
     */
    public int deletePlnWoVideoById(Long id);

    /**
     * 批量删除工单作业视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoVideoByIds(Long[] ids);

    /**
     * 查询工单作业视频列表
     *
     * @param woId 工单ID
     * @return 工单作业视频集合
     */
    public int deletePlnWoVideoByWoId(Long woId);
}
