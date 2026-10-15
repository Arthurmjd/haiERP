package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoDrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoDrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoDrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单图纸Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@Service
public class PlnWoDrawingServiceImpl implements IPlnWoDrawingService 
{
    @Autowired(required = false)
    private PlnWoDrawingMapper plnWoDrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单图纸
     * 
     * @param id 工单图纸主键
     * @return 工单图纸
     */
    @Override
    public PlnWoDrawing selectPlnWoDrawingById(Long id)
    {
        return plnWoDrawingMapper.selectPlnWoDrawingById(id);
    }

    /**
     * 查询工单图纸列表
     * 
     * @param plnWoDrawing 工单图纸
     * @return 工单图纸
     */
    @Override
    public List<PlnWoDrawing> selectPlnWoDrawingList(PlnWoDrawing plnWoDrawing)
    {
        return plnWoDrawingMapper.selectPlnWoDrawingList(plnWoDrawing);
    }

    /**
     * 新增工单图纸
     * 
     * @param plnWoDrawing 工单图纸
     * @return 结果
     */
    @Override
    public int insertPlnWoDrawing(PlnWoDrawing plnWoDrawing)
    {
        plnWoDrawing.setCreateTime(DateUtils.getNowDate());
        return plnWoDrawingMapper.insertPlnWoDrawing(plnWoDrawing);
    }

    /**
     * 修改工单图纸
     * 
     * @param plnWoDrawing 工单图纸
     * @return 结果
     */
    @Override
    public int updatePlnWoDrawing(PlnWoDrawing plnWoDrawing)
    {
        plnWoDrawing.refreshUpdatingStamp();
        return plnWoDrawingMapper.updatePlnWoDrawing(plnWoDrawing);
    }

    /**
     * 批量删除工单图纸
     * 
     * @param ids 需要删除的工单图纸主键
     * @return 结果
     */
    @Override
    public int deletePlnWoDrawingByIds(Long[] ids)
    {
        return plnWoDrawingMapper.deletePlnWoDrawingByIds(ids);
    }

    /**
     * 删除工单图纸信息
     * 
     * @param id 工单图纸主键
     * @return 结果
     */
    @Override
    public int deletePlnWoDrawingById(Long id)
    {
        return plnWoDrawingMapper.deletePlnWoDrawingById(id);
    }


}
