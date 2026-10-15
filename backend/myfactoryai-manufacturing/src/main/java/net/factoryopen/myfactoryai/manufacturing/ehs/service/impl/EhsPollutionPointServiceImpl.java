package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPollutionPointMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionPoint;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionPointService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 污染物排放点Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPollutionPointServiceImpl implements IEhsPollutionPointService 
{
    @Autowired(required = false)
    private EhsPollutionPointMapper ehsPollutionPointMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询污染物排放点
     * 
     * @param id 污染物排放点主键
     * @return 污染物排放点
     */
    @Override
    public EhsPollutionPoint selectEhsPollutionPointById(Long id)
    {
        return ehsPollutionPointMapper.selectEhsPollutionPointById(id);
    }

    /**
     * 查询污染物排放点列表
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 污染物排放点
     */
    @Override
    public List<EhsPollutionPoint> selectEhsPollutionPointList(EhsPollutionPoint ehsPollutionPoint)
    {
        return ehsPollutionPointMapper.selectEhsPollutionPointList(ehsPollutionPoint);
    }

    /**
     * 新增污染物排放点
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 结果
     */
    @Override
    public int insertEhsPollutionPoint(EhsPollutionPoint ehsPollutionPoint)
    {
        ehsPollutionPoint.setCreateTime(DateUtils.getNowDate());
        return ehsPollutionPointMapper.insertEhsPollutionPoint(ehsPollutionPoint);
    }

    /**
     * 修改污染物排放点
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 结果
     */
    @Override
    public int updateEhsPollutionPoint(EhsPollutionPoint ehsPollutionPoint)
    {
        ehsPollutionPoint.setUpdateTime(DateUtils.getNowDate());
        return ehsPollutionPointMapper.updateEhsPollutionPoint(ehsPollutionPoint);
    }

    /**
     * 批量删除污染物排放点
     * 
     * @param ids 需要删除的污染物排放点主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionPointByIds(Long[] ids)
    {
        return ehsPollutionPointMapper.deleteEhsPollutionPointByIds(ids);
    }

    /**
     * 删除污染物排放点信息
     * 
     * @param id 污染物排放点主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionPointById(Long id)
    {
        return ehsPollutionPointMapper.deleteEhsPollutionPointById(id);
    }


}
