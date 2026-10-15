package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductVideoMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductVideo;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductVideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class EngPruductVideoServiceImpl implements IEngPruductVideoService 
{
    @Autowired(required = false)
    private EngPruductVideoMapper engPruductVideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    @Override
    public EngPruductVideo selectEngPruductVideoById(Long id)
    {
        return engPruductVideoMapper.selectEngPruductVideoById(id);
    }

    /**
     * 查询产品作业视频列表
     * 
     * @param engPruductVideo 产品作业视频
     * @return 产品作业视频
     */
    @Override
    public List<EngPruductVideo> selectEngPruductVideoList(EngPruductVideo engPruductVideo)
    {
        return engPruductVideoMapper.selectEngPruductVideoList(engPruductVideo);
    }

    /**
     * 新增产品作业视频
     * 
     * @param engPruductVideo 产品作业视频
     * @return 结果
     */
    @Override
    public int insertEngPruductVideo(EngPruductVideo engPruductVideo)
    {
        engPruductVideo.setCreateTime(DateUtils.getNowDate());
        return engPruductVideoMapper.insertEngPruductVideo(engPruductVideo);
    }

    /**
     * 修改产品作业视频
     * 
     * @param engPruductVideo 产品作业视频
     * @return 结果
     */
    @Override
    public int updateEngPruductVideo(EngPruductVideo engPruductVideo)
    {
        engPruductVideo.setUpdateTime(DateUtils.getNowDate());
        return engPruductVideoMapper.updateEngPruductVideo(engPruductVideo);
    }

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的产品作业视频主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductVideoByIds(Long[] ids)
    {
        return engPruductVideoMapper.deleteEngPruductVideoByIds(ids);
    }

    /**
     * 删除产品作业视频信息
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductVideoById(Long id)
    {
        return engPruductVideoMapper.deleteEngPruductVideoById(id);
    }


}
