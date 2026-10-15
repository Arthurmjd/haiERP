package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLineMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLine;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResLineService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产线管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public class ResLineServiceImpl implements IResLineService 
{
    @Autowired(required = false)
    private ResLineMapper resLineMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产线管理
     * 
     * @param id 产线管理主键
     * @return 产线管理
     */
    @Override
    public ResLine selectResLineById(Long id)
    {
        return resLineMapper.selectResLineById(id);
    }

    /**
     * 查询产线管理列表
     * 
     * @param resLine 产线管理
     * @return 产线管理
     */
    @Override
    public List<ResLine> selectResLineList(ResLine resLine)
    {
        return resLineMapper.selectResLineList(resLine);
    }

    /**
     * 新增产线管理
     * 
     * @param resLine 产线管理
     * @return 结果
     */
    @Override
    public int insertResLine(ResLine resLine)
    {
        resLine.setCreateTime(DateUtils.getNowDate());
        return resLineMapper.insertResLine(resLine);
    }

    /**
     * 修改产线管理
     * 
     * @param resLine 产线管理
     * @return 结果
     */
    @Override
    public int updateResLine(ResLine resLine)
    {
        resLine.setUpdateTime(DateUtils.getNowDate());
        return resLineMapper.updateResLine(resLine);
    }

    /**
     * 批量删除产线管理
     * 
     * @param ids 需要删除的产线管理主键
     * @return 结果
     */
    @Override
    public int deleteResLineByIds(Long[] ids)
    {
        return resLineMapper.deleteResLineByIds(ids);
    }

    /**
     * 删除产线管理信息
     * 
     * @param id 产线管理主键
     * @return 结果
     */
    @Override
    public int deleteResLineById(Long id)
    {
        return resLineMapper.deleteResLineById(id);
    }


}
