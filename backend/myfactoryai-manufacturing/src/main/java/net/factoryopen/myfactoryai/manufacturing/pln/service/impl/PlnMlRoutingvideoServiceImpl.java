package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingvideoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingvideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingvideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingvideoServiceImpl implements IPlnMlRoutingvideoService 
{
    @Autowired(required = false)
    private PlnMlRoutingvideoMapper plnMlRoutingvideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次作业视频
     * 
     * @param id 批次作业视频主键
     * @return 批次作业视频
     */
    @Override
    public PlnMlRoutingvideo selectPlnMlRoutingvideoById(Long id)
    {
        return plnMlRoutingvideoMapper.selectPlnMlRoutingvideoById(id);
    }

    /**
     * 查询批次作业视频列表
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 批次作业视频
     */
    @Override
    public List<PlnMlRoutingvideo> selectPlnMlRoutingvideoList(PlnMlRoutingvideo plnMlRoutingvideo)
    {
        return plnMlRoutingvideoMapper.selectPlnMlRoutingvideoList(plnMlRoutingvideo);
    }

    /**
     * 新增批次作业视频
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 结果
     */
    @Override
    public int insertPlnMlRoutingvideo(PlnMlRoutingvideo plnMlRoutingvideo)
    {
        plnMlRoutingvideo.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingvideoMapper.insertPlnMlRoutingvideo(plnMlRoutingvideo);
    }

    /**
     * 修改批次作业视频
     * 
     * @param plnMlRoutingvideo 批次作业视频
     * @return 结果
     */
    @Override
    public int updatePlnMlRoutingvideo(PlnMlRoutingvideo plnMlRoutingvideo)
    {
        plnMlRoutingvideo.setUpdateTime(DateUtils.getNowDate());
        return plnMlRoutingvideoMapper.updatePlnMlRoutingvideo(plnMlRoutingvideo);
    }

    /**
     * 批量删除批次作业视频
     * 
     * @param ids 需要删除的批次作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingvideoByIds(Long[] ids)
    {
        return plnMlRoutingvideoMapper.deletePlnMlRoutingvideoByIds(ids);
    }

    /**
     * 删除批次作业视频信息
     * 
     * @param id 批次作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingvideoById(Long id)
    {
        return plnMlRoutingvideoMapper.deletePlnMlRoutingvideoById(id);
    }


}
