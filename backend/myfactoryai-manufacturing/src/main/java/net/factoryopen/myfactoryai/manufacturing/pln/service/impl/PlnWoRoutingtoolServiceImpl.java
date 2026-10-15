package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingtoolMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingtoolService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单工具Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class PlnWoRoutingtoolServiceImpl implements IPlnWoRoutingtoolService 
{
    @Autowired(required = false)
    private PlnWoRoutingtoolMapper plnWoRoutingtoolMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单工具
     * 
     * @param id 工单工具主键
     * @return 工单工具
     */
    @Override
    public PlnWoRoutingtool selectPlnWoRoutingtoolById(Long id)
    {
        return plnWoRoutingtoolMapper.selectPlnWoRoutingtoolById(id);
    }

    /**
     * 查询工单工具列表
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 工单工具
     */
    @Override
    public List<PlnWoRoutingtool> selectPlnWoRoutingtoolList(PlnWoRoutingtool plnWoRoutingtool)
    {
        return plnWoRoutingtoolMapper.selectPlnWoRoutingtoolList(plnWoRoutingtool);
    }

    /**
     * 新增工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    @Override
    public int insertPlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool)
    {
        plnWoRoutingtool.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingtoolMapper.insertPlnWoRoutingtool(plnWoRoutingtool);
    }

    /**
     * 修改工单工具
     * 
     * @param plnWoRoutingtool 工单工具
     * @return 结果
     */
    @Override
    public int updatePlnWoRoutingtool(PlnWoRoutingtool plnWoRoutingtool)
    {
        plnWoRoutingtool.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingtoolMapper.updatePlnWoRoutingtool(plnWoRoutingtool);
    }

    /**
     * 批量删除工单工具
     * 
     * @param ids 需要删除的工单工具主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingtoolByIds(Long[] ids)
    {
        return plnWoRoutingtoolMapper.deletePlnWoRoutingtoolByIds(ids);
    }

    /**
     * 删除工单工具信息
     * 
     * @param id 工单工具主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingtoolById(Long id)
    {
        return plnWoRoutingtoolMapper.deletePlnWoRoutingtoolById(id);
    }


}
