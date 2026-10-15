package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingvideoMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingvideo;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingvideoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品作业视频Service业务层处理
 * 
 * @author admin
 * @date 2024-07-11
 */
@Service
public class PlnWoRoutingvideoServiceImpl implements IPlnWoRoutingvideoService 
{
    @Autowired(required = false)
    private PlnWoRoutingvideoMapper plnWoRoutingvideoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品作业视频
     * 
     * @param id 产品作业视频主键
     * @return 产品作业视频
     */
    @Override
    public PlnWoRoutingvideo selectPlnWoRoutingvideoById(Long id)
    {
        return plnWoRoutingvideoMapper.selectPlnWoRoutingvideoById(id);
    }

    /**
     * 查询产品作业视频列表
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 产品作业视频
     */
    @Override
    public List<PlnWoRoutingvideo> selectPlnWoRoutingvideoList(PlnWoRoutingvideo plnWoRoutingvideo)
    {
        return plnWoRoutingvideoMapper.selectPlnWoRoutingvideoList(plnWoRoutingvideo);
    }

    /**
     * 新增产品作业视频
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 结果
     */
    @Override
    public int insertPlnWoRoutingvideo(PlnWoRoutingvideo plnWoRoutingvideo)
    {
        plnWoRoutingvideo.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingvideoMapper.insertPlnWoRoutingvideo(plnWoRoutingvideo);
    }

    /**
     * 修改产品作业视频
     * 
     * @param plnWoRoutingvideo 产品作业视频
     * @return 结果
     */
    @Override
    public int updatePlnWoRoutingvideo(PlnWoRoutingvideo plnWoRoutingvideo)
    {
        plnWoRoutingvideo.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingvideoMapper.updatePlnWoRoutingvideo(plnWoRoutingvideo);
    }

    /**
     * 批量删除产品作业视频
     * 
     * @param ids 需要删除的产品作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingvideoByIds(Long[] ids)
    {
        return plnWoRoutingvideoMapper.deletePlnWoRoutingvideoByIds(ids);
    }

    /**
     * 删除产品作业视频信息
     * 
     * @param id 产品作业视频主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingvideoById(Long id)
    {
        return plnWoRoutingvideoMapper.deletePlnWoRoutingvideoById(id);
    }


}
