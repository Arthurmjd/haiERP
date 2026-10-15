package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlVideo;

/**
 * 批次作业视频Service接口
 * 
 * @author admin
 * @date 2024-07-13
 */
public interface IPlnMlVideoService 
{
    /**
     * 查询批次作业视频
     * 
     * @param id 批次作业视频主键
     * @return 批次作业视频
     */
    public PlnMlVideo selectPlnMlVideoById(Long id);

    /**
     * 查询批次作业视频列表
     * 
     * @param plnMlVideo 批次作业视频
     * @return 批次作业视频集合
     */
    public List<PlnMlVideo> selectPlnMlVideoList(PlnMlVideo plnMlVideo);

    /**
     * 新增批次作业视频
     * 
     * @param plnMlVideo 批次作业视频
     * @return 结果
     */
    public int insertPlnMlVideo(PlnMlVideo plnMlVideo);

    /**
     * 修改批次作业视频
     * 
     * @param plnMlVideo 批次作业视频
     * @return 结果
     */
    public int updatePlnMlVideo(PlnMlVideo plnMlVideo);

    /**
     * 批量删除批次作业视频
     * 
     * @param ids 需要删除的批次作业视频主键集合
     * @return 结果
     */
    public int deletePlnMlVideoByIds(Long[] ids);

    /**
     * 删除批次作业视频信息
     * 
     * @param id 批次作业视频主键
     * @return 结果
     */
    public int deletePlnMlVideoById(Long id);


}
