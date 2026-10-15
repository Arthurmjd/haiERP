package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngPruductRoutingvideoMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingvideo;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngPruductRoutingvideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class EngPruductRoutingvideoServiceImpl implements IEngPruductRoutingvideoService 
{
    @Autowired(required = false)
    private EngPruductRoutingvideoMapper engPruductRoutingvideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    @Override
    public EngPruductRoutingvideo selectEngPruductRoutingvideoById(Long id)
    {
        return engPruductRoutingvideoMapper.selectEngPruductRoutingvideoById(id);
    }

    /**
     * 查询产品作业视频列表
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 产品作业视频
     */
    @Override
    public List<EngPruductRoutingvideo> selectEngPruductRoutingvideoList(EngPruductRoutingvideo engPruductRoutingvideo)
    {
        return engPruductRoutingvideoMapper.selectEngPruductRoutingvideoList(engPruductRoutingvideo);
    }

    /**
     * 新增产品作业视频
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 结果
     */
    @Override
    public int insertEngPruductRoutingvideo(EngPruductRoutingvideo engPruductRoutingvideo)
    {
        engPruductRoutingvideo.setCreateTime(DateUtils.getNowDate());
        return engPruductRoutingvideoMapper.insertEngPruductRoutingvideo(engPruductRoutingvideo);
    }

    /**
     * 修改产品作业视频
     * 
     * @param engPruductRoutingvideo 产品作业视频
     * @return 结果
     */
    @Override
    public int updateEngPruductRoutingvideo(EngPruductRoutingvideo engPruductRoutingvideo)
    {
        engPruductRoutingvideo.setUpdateTime(DateUtils.getNowDate());
        return engPruductRoutingvideoMapper.updateEngPruductRoutingvideo(engPruductRoutingvideo);
    }

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的产品作业视频主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductRoutingvideoByIds(Long[] ids)
    {
        return engPruductRoutingvideoMapper.deleteEngPruductRoutingvideoByIds(ids);
    }

    /**
     * 删除产品作业视频信息
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    @Override
    public int deleteEngPruductRoutingvideoById(Long id)
    {
        return engPruductRoutingvideoMapper.deleteEngPruductRoutingvideoById(id);
    }


}
