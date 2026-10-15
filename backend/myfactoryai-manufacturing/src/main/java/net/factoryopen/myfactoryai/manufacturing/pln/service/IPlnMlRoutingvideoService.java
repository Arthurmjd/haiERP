package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingvideo;

/**
 * 批次作业视频Service接口
 * 
 * @author admin
 * @date 2024-07-13
 */
public interface IPlnMlRoutingvideoService 
{
    /**
     * 查询批次作业视频
     * 
     * @param id 批次作业视频主键
     * @return 批次作业视频
     */
    public PlnMlRoutingvideo selectPlnMlRoutingvideoById(Long id);

    /**
     * 查询批次作业视频列表
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 批次作业视频集合
     */
    public List<PlnMlRoutingvideo> selectPlnMlRoutingvideoList(PlnMlRoutingvideo plnMlRoutingvideo);

    /**
     * 新增批次作业视频
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 结果
     */
    public int insertPlnMlRoutingvideo(PlnMlRoutingvideo plnMlRoutingvideo);

    /**
     * 修改批次作业视频
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 结果
     */
    public int updatePlnMlRoutingvideo(PlnMlRoutingvideo plnMlRoutingvideo);

    /**
     * 批量删除批次作业视频
     * 
     * @param ids 需要删除的批次作业视频主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingvideoByIds(Long[] ids);

    /**
     * 删除批次作业视频信息
     * 
     * @param id 批次作业视频主键
     * @return 结果
     */
    public int deletePlnMlRoutingvideoById(Long id);


}
