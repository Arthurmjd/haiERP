package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlVideo;
import org.springframework.stereotype.Service;

/**
 * 批次作业视频Mapper接口
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public interface PlnMlVideoMapper 
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
     * 查询批次作业视频列表
     *
     * @param mlId 批次ID
     * @return 批次作业视频集合
     */
    public List<PlnMlVideo> selectPlnMlVideoListByMlId(Long mlId);

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
     * 删除批次作业视频
     * 
     * @param id 批次作业视频主键
     * @return 结果
     */
    public int deletePlnMlVideoById(Long id);

    /**
     * 批量删除批次作业视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlVideoByIds(Long[] ids);

    /**
     * 查询批次作业视频列表
     *
     * @param mlId 批次ID
     * @return 批次作业视频集合
     */
    public int deletePlnMlVideoByMlId(Long mlId);
}
