package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaFreeinspectMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspect;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 免检策略管理Service业务层处理
 * 
 * @author admin
 * @date 2024-11-12
 */
@Service
public class QuaFreeinspectServiceImpl implements IQuaFreeinspectService 
{
    @Autowired(required = false)
    private QuaFreeinspectMapper quaFreeinspectMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询免检策略管理
     * 
     * @param id 免检策略管理主键
     * @return 免检策略管理
     */
    @Override
    public QuaFreeinspect selectQuaFreeinspectById(Long id)
    {
        return quaFreeinspectMapper.selectQuaFreeinspectById(id);
    }

    /**
     * 查询免检策略管理列表
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 免检策略管理
     */
    @Override
    public List<QuaFreeinspect> selectQuaFreeinspectList(QuaFreeinspect quaFreeinspect)
    {
        return quaFreeinspectMapper.selectQuaFreeinspectList(quaFreeinspect);
    }

    /**
     * 新增免检策略管理
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 结果
     */
    @Override
    public int insertQuaFreeinspect(QuaFreeinspect quaFreeinspect)
    {
        quaFreeinspect.setCreateTime(DateUtils.getNowDate());
        return quaFreeinspectMapper.insertQuaFreeinspect(quaFreeinspect);
    }

    /**
     * 修改免检策略管理
     * 
     * @param quaFreeinspect 免检策略管理
     * @return 结果
     */
    @Override
    public int updateQuaFreeinspect(QuaFreeinspect quaFreeinspect)
    {
        quaFreeinspect.setUpdateTime(DateUtils.getNowDate());
        return quaFreeinspectMapper.updateQuaFreeinspect(quaFreeinspect);
    }

    /**
     * 批量删除免检策略管理
     * 
     * @param ids 需要删除的免检策略管理主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectByIds(Long[] ids)
    {
        return quaFreeinspectMapper.deleteQuaFreeinspectByIds(ids);
    }

    /**
     * 删除免检策略管理信息
     * 
     * @param id 免检策略管理主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectById(Long id)
    {
        return quaFreeinspectMapper.deleteQuaFreeinspectById(id);
    }


}
