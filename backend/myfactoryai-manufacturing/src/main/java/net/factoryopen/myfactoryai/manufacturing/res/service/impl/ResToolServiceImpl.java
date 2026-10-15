package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResToolMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResTool;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResToolService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工具管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public class ResToolServiceImpl implements IResToolService 
{
    @Autowired(required = false)
    private ResToolMapper resToolMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工具管理
     * 
     * @param id 工具管理主键
     * @return 工具管理
     */
    @Override
    public ResTool selectResToolById(Long id)
    {
        return resToolMapper.selectResToolById(id);
    }

    /**
     * 查询工具管理列表
     * 
     * @param resTool 工具管理
     * @return 工具管理
     */
    @Override
    public List<ResTool> selectResToolList(ResTool resTool)
    {
        return resToolMapper.selectResToolList(resTool);
    }

    /**
     * 新增工具管理
     * 
     * @param resTool 工具管理
     * @return 结果
     */
    @Override
    public int insertResTool(ResTool resTool)
    {
        resTool.setCreateTime(DateUtils.getNowDate());
        return resToolMapper.insertResTool(resTool);
    }

    /**
     * 修改工具管理
     * 
     * @param resTool 工具管理
     * @return 结果
     */
    @Override
    public int updateResTool(ResTool resTool)
    {
        resTool.setUpdateTime(DateUtils.getNowDate());
        return resToolMapper.updateResTool(resTool);
    }

    /**
     * 批量删除工具管理
     * 
     * @param ids 需要删除的工具管理主键
     * @return 结果
     */
    @Override
    public int deleteResToolByIds(Long[] ids)
    {
        return resToolMapper.deleteResToolByIds(ids);
    }

    /**
     * 删除工具管理信息
     * 
     * @param id 工具管理主键
     * @return 结果
     */
    @Override
    public int deleteResToolById(Long id)
    {
        return resToolMapper.deleteResToolById(id);
    }


}
