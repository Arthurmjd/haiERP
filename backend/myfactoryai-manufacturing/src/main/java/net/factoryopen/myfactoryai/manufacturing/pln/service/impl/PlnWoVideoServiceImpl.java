package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoVideoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoVideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoVideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-12
 */
@Service
public class PlnWoVideoServiceImpl implements IPlnWoVideoService 
{
    @Autowired(required = false)
    private PlnWoVideoMapper plnWoVideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单作业视频
     * 
     * @param id 工单作业视频主键
     * @return 工单作业视频
     */
    @Override
    public PlnWoVideo selectPlnWoVideoById(Long id)
    {
        return plnWoVideoMapper.selectPlnWoVideoById(id);
    }

    /**
     * 查询工单作业视频列表
     * 
     * @param plnWoVideo 工单作业视频
     * @return 工单作业视频
     */
    @Override
    public List<PlnWoVideo> selectPlnWoVideoList(PlnWoVideo plnWoVideo)
    {
        return plnWoVideoMapper.selectPlnWoVideoList(plnWoVideo);
    }

    /**
     * 新增工单作业视频
     * 
     * @param plnWoVideo 工单作业视频
     * @return 结果
     */
    @Override
    public int insertPlnWoVideo(PlnWoVideo plnWoVideo)
    {
        plnWoVideo.setCreateTime(DateUtils.getNowDate());
        return plnWoVideoMapper.insertPlnWoVideo(plnWoVideo);
    }

    /**
     * 修改工单作业视频
     * 
     * @param plnWoVideo 工单作业视频
     * @return 结果
     */
    @Override
    public int updatePlnWoVideo(PlnWoVideo plnWoVideo)
    {
        plnWoVideo.setUpdateTime(DateUtils.getNowDate());
        return plnWoVideoMapper.updatePlnWoVideo(plnWoVideo);
    }

    /**
     * 批量删除工单作业视频
     * 
     * @param ids 需要删除的工单作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnWoVideoByIds(Long[] ids)
    {
        return plnWoVideoMapper.deletePlnWoVideoByIds(ids);
    }

    /**
     * 删除工单作业视频信息
     * 
     * @param id 工单作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnWoVideoById(Long id)
    {
        return plnWoVideoMapper.deletePlnWoVideoById(id);
    }


}
