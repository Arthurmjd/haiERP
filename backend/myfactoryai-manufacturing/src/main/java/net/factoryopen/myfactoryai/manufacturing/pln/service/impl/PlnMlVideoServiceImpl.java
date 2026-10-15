package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlVideoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlVideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlVideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public class PlnMlVideoServiceImpl implements IPlnMlVideoService 
{
    @Autowired(required = false)
    private PlnMlVideoMapper plnMlVideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次作业视频
     * 
     * @param id 批次作业视频主键
     * @return 批次作业视频
     */
    @Override
    public PlnMlVideo selectPlnMlVideoById(Long id)
    {
        return plnMlVideoMapper.selectPlnMlVideoById(id);
    }

    /**
     * 查询批次作业视频列表
     * 
     * @param plnMlVideo 批次作业视频
     * @return 批次作业视频
     */
    @Override
    public List<PlnMlVideo> selectPlnMlVideoList(PlnMlVideo plnMlVideo)
    {
        return plnMlVideoMapper.selectPlnMlVideoList(plnMlVideo);
    }

    /**
     * 新增批次作业视频
     * 
     * @param plnMlVideo 批次作业视频
     * @return 结果
     */
    @Override
    public int insertPlnMlVideo(PlnMlVideo plnMlVideo)
    {
        plnMlVideo.setCreateTime(DateUtils.getNowDate());
        return plnMlVideoMapper.insertPlnMlVideo(plnMlVideo);
    }

    /**
     * 修改批次作业视频
     * 
     * @param plnMlVideo 批次作业视频
     * @return 结果
     */
    @Override
    public int updatePlnMlVideo(PlnMlVideo plnMlVideo)
    {
        plnMlVideo.setUpdateTime(DateUtils.getNowDate());
        return plnMlVideoMapper.updatePlnMlVideo(plnMlVideo);
    }

    /**
     * 批量删除批次作业视频
     * 
     * @param ids 需要删除的批次作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnMlVideoByIds(Long[] ids)
    {
        return plnMlVideoMapper.deletePlnMlVideoByIds(ids);
    }

    /**
     * 删除批次作业视频信息
     * 
     * @param id 批次作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnMlVideoById(Long id)
    {
        return plnMlVideoMapper.deletePlnMlVideoById(id);
    }


}
