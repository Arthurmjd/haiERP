package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingtoolMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingtoolService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次工具Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingtoolServiceImpl implements IPlnMlRoutingtoolService 
{
    @Autowired(required = false)
    private PlnMlRoutingtoolMapper plnMlRoutingtoolMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次工具
     * 
     * @param id 批次工具主键
     * @return 批次工具
     */
    @Override
    public PlnMlRoutingtool selectPlnMlRoutingtoolById(Long id)
    {
        return plnMlRoutingtoolMapper.selectPlnMlRoutingtoolById(id);
    }

    /**
     * 查询批次工具列表
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 批次工具
     */
    @Override
    public List<PlnMlRoutingtool> selectPlnMlRoutingtoolList(PlnMlRoutingtool plnMlRoutingtool)
    {
        return plnMlRoutingtoolMapper.selectPlnMlRoutingtoolList(plnMlRoutingtool);
    }

    /**
     * 新增批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    @Override
    public int insertPlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool)
    {
        plnMlRoutingtool.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingtoolMapper.insertPlnMlRoutingtool(plnMlRoutingtool);
    }

    /**
     * 修改批次工具
     * 
     * @param plnMlRoutingtool 批次工具
     * @return 结果
     */
    @Override
    public int updatePlnMlRoutingtool(PlnMlRoutingtool plnMlRoutingtool)
    {
        plnMlRoutingtool.refreshUpdatingStamp();
        return plnMlRoutingtoolMapper.updatePlnMlRoutingtool(plnMlRoutingtool);
    }

    /**
     * 批量删除批次工具
     * 
     * @param ids 需要删除的批次工具主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingtoolByIds(Long[] ids)
    {
        return plnMlRoutingtoolMapper.deletePlnMlRoutingtoolByIds(ids);
    }

    /**
     * 删除批次工具信息
     * 
     * @param id 批次工具主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingtoolById(Long id)
    {
        return plnMlRoutingtoolMapper.deletePlnMlRoutingtoolById(id);
    }


}
