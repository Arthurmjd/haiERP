package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductDrawingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductDrawing;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductDrawingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品图纸Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductDrawingServiceImpl implements IEngProductDrawingService 
{
    @Autowired(required = false)
    private EngProductDrawingMapper engProductDrawingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品图纸
     * 
     * @param id 产品图纸主键
     * @return 产品图纸
     */
    @Override
    public EngProductDrawing selectEngProductDrawingById(Long id)
    {
        return engProductDrawingMapper.selectEngProductDrawingById(id);
    }

    /**
     * 查询产品图纸列表
     * 
     * @param engProductDrawing 产品图纸
     * @return 产品图纸
     */
    @Override
    public List<EngProductDrawing> selectEngProductDrawingList(EngProductDrawing engProductDrawing)
    {
        return engProductDrawingMapper.selectEngProductDrawingList(engProductDrawing);
    }

    /**
     * 新增产品图纸
     * 
     * @param engProductDrawing 产品图纸
     * @return 结果
     */
    @Override
    public int insertEngProductDrawing(EngProductDrawing engProductDrawing)
    {
        //创建戳
        engProductDrawing.setCreateBy(SecurityUtils.getUsername());
        engProductDrawing.setCreateTime(DateUtils.getNowDate());
        engProductDrawing.setUpdateBy(SecurityUtils.getUsername());
        engProductDrawing.setUpdateTime(DateUtils.getNowDate());
        return engProductDrawingMapper.insertEngProductDrawing(engProductDrawing);
    }

    /**
     * 修改产品图纸
     * 
     * @param engProductDrawing 产品图纸
     * @return 结果
     */
    @Override
    public int updateEngProductDrawing(EngProductDrawing engProductDrawing)
    {
        engProductDrawing.setUpdateTime(DateUtils.getNowDate());
        return engProductDrawingMapper.updateEngProductDrawing(engProductDrawing);
    }

    /**
     * 批量删除产品图纸
     * 
     * @param ids 需要删除的产品图纸主键
     * @return 结果
     */
    @Override
    public int deleteEngProductDrawingByIds(Long[] ids)
    {
        return engProductDrawingMapper.deleteEngProductDrawingByIds(ids);
    }

    /**
     * 删除产品图纸信息
     * 
     * @param id 产品图纸主键
     * @return 结果
     */
    @Override
    public int deleteEngProductDrawingById(Long id)
    {
        return engProductDrawingMapper.deleteEngProductDrawingById(id);
    }


}
