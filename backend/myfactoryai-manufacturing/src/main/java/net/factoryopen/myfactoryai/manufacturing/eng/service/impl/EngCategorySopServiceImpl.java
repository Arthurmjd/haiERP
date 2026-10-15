package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCategorySopMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategorySop;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategorySopService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 品类SOPService业务层处理
 * 
 * @author admin
 * @date 2024-11-09
 */
@Service
public class EngCategorySopServiceImpl implements IEngCategorySopService 
{
    @Autowired(required = false)
    private EngCategorySopMapper engCategorySopMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询品类SOP
     * 
     * @param id 品类SOP主键
     * @return 品类SOP
     */
    @Override
    public EngCategorySop selectEngCategorySopById(Long id)
    {
        return engCategorySopMapper.selectEngCategorySopById(id);
    }

    /**
     * 查询品类SOP列表
     * 
     * @param engCategorySop 品类SOP
     * @return 品类SOP
     */
    @Override
    public List<EngCategorySop> selectEngCategorySopList(EngCategorySop engCategorySop)
    {
        return engCategorySopMapper.selectEngCategorySopList(engCategorySop);
    }

    /**
     * 新增品类SOP
     * 
     * @param engCategorySop 品类SOP
     * @return 结果
     */
    @Override
    public int insertEngCategorySop(EngCategorySop engCategorySop)
    {
        engCategorySop.setCreateTime(DateUtils.getNowDate());
        return engCategorySopMapper.insertEngCategorySop(engCategorySop);
    }

    /**
     * 修改品类SOP
     * 
     * @param engCategorySop 品类SOP
     * @return 结果
     */
    @Override
    public int updateEngCategorySop(EngCategorySop engCategorySop)
    {
        engCategorySop.setUpdateTime(DateUtils.getNowDate());
        return engCategorySopMapper.updateEngCategorySop(engCategorySop);
    }

    /**
     * 批量删除品类SOP
     * 
     * @param ids 需要删除的品类SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngCategorySopByIds(Long[] ids)
    {
        return engCategorySopMapper.deleteEngCategorySopByIds(ids);
    }

    /**
     * 删除品类SOP信息
     * 
     * @param id 品类SOP主键
     * @return 结果
     */
    @Override
    public int deleteEngCategorySopById(Long id)
    {
        return engCategorySopMapper.deleteEngCategorySopById(id);
    }


}
