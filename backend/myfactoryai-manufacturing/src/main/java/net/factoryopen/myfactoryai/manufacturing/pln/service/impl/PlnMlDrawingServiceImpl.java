package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlDrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDrawing;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlDrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次图纸Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlDrawingServiceImpl implements IPlnMlDrawingService 
{
    @Autowired(required = false)
    private PlnMlDrawingMapper plnMlDrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次图纸
     * 
     * @param id 批次图纸主键
     * @return 批次图纸
     */
    @Override
    public PlnMlDrawing selectPlnMlDrawingById(Long id)
    {
        return plnMlDrawingMapper.selectPlnMlDrawingById(id);
    }

    /**
     * 查询批次图纸列表
     * 
     * @param plnMlDrawing 批次图纸
     * @return 批次图纸
     */
    @Override
    public List<PlnMlDrawing> selectPlnMlDrawingList(PlnMlDrawing plnMlDrawing)
    {
        return plnMlDrawingMapper.selectPlnMlDrawingList(plnMlDrawing);
    }

    /**
     * 新增批次图纸
     * 
     * @param plnMlDrawing 批次图纸
     * @return 结果
     */
    @Override
    public int insertPlnMlDrawing(PlnMlDrawing plnMlDrawing)
    {
        plnMlDrawing.setCreateTime(DateUtils.getNowDate());
        return plnMlDrawingMapper.insertPlnMlDrawing(plnMlDrawing);
    }

    /**
     * 修改批次图纸
     * 
     * @param plnMlDrawing 批次图纸
     * @return 结果
     */
    @Override
    public int updatePlnMlDrawing(PlnMlDrawing plnMlDrawing)
    {
        plnMlDrawing.refreshUpdatingStamp();
        return plnMlDrawingMapper.updatePlnMlDrawing(plnMlDrawing);
    }

    /**
     * 批量删除批次图纸
     * 
     * @param ids 需要删除的批次图纸主键
     * @return 结果
     */
    @Override
    public int deletePlnMlDrawingByIds(Long[] ids)
    {
        return plnMlDrawingMapper.deletePlnMlDrawingByIds(ids);
    }

    /**
     * 删除批次图纸信息
     * 
     * @param id 批次图纸主键
     * @return 结果
     */
    @Override
    public int deletePlnMlDrawingById(Long id)
    {
        return plnMlDrawingMapper.deletePlnMlDrawingById(id);
    }


}
