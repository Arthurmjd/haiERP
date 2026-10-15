package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPollutionIndexMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionIndex;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionIndexService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 污染排放指标Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPollutionIndexServiceImpl implements IEhsPollutionIndexService 
{
    @Autowired(required = false)
    private EhsPollutionIndexMapper ehsPollutionIndexMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询污染排放指标
     * 
     * @param id 污染排放指标主键
     * @return 污染排放指标
     */
    @Override
    public EhsPollutionIndex selectEhsPollutionIndexById(Long id)
    {
        return ehsPollutionIndexMapper.selectEhsPollutionIndexById(id);
    }

    /**
     * 查询污染排放指标列表
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 污染排放指标
     */
    @Override
    public List<EhsPollutionIndex> selectEhsPollutionIndexList(EhsPollutionIndex ehsPollutionIndex)
    {
        return ehsPollutionIndexMapper.selectEhsPollutionIndexList(ehsPollutionIndex);
    }

    /**
     * 新增污染排放指标
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 结果
     */
    @Override
    public int insertEhsPollutionIndex(EhsPollutionIndex ehsPollutionIndex)
    {
        ehsPollutionIndex.setCreateTime(DateUtils.getNowDate());
        return ehsPollutionIndexMapper.insertEhsPollutionIndex(ehsPollutionIndex);
    }

    /**
     * 修改污染排放指标
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 结果
     */
    @Override
    public int updateEhsPollutionIndex(EhsPollutionIndex ehsPollutionIndex)
    {
        ehsPollutionIndex.setUpdateTime(DateUtils.getNowDate());
        return ehsPollutionIndexMapper.updateEhsPollutionIndex(ehsPollutionIndex);
    }

    /**
     * 批量删除污染排放指标
     * 
     * @param ids 需要删除的污染排放指标主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionIndexByIds(Long[] ids)
    {
        return ehsPollutionIndexMapper.deleteEhsPollutionIndexByIds(ids);
    }

    /**
     * 删除污染排放指标信息
     * 
     * @param id 污染排放指标主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionIndexById(Long id)
    {
        return ehsPollutionIndexMapper.deleteEhsPollutionIndexById(id);
    }


}
