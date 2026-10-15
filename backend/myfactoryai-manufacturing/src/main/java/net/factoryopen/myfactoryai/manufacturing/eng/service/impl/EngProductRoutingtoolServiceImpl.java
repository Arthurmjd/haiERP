package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingtoolMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingtool;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingtoolService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品工具Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductRoutingtoolServiceImpl implements IEngProductRoutingtoolService 
{
    @Autowired(required = false)
    private EngProductRoutingtoolMapper engProductRoutingtoolMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品工具
     * 
     * @param id 产品工具主键
     * @return 产品工具
     */
    @Override
    public EngProductRoutingtool selectEngProductRoutingtoolById(Long id)
    {
        return engProductRoutingtoolMapper.selectEngProductRoutingtoolById(id);
    }

    /**
     * 查询产品工具列表
     * 
     * @param engProductRoutingtool 产品工具
     * @return 产品工具
     */
    @Override
    public List<EngProductRoutingtool> selectEngProductRoutingtoolList(EngProductRoutingtool engProductRoutingtool)
    {
        return engProductRoutingtoolMapper.selectEngProductRoutingtoolList(engProductRoutingtool);
    }

    /**
     * 新增产品工具
     * 
     * @param engProductRoutingtool 产品工具
     * @return 结果
     */
    @Override
    public int insertEngProductRoutingtool(EngProductRoutingtool engProductRoutingtool)
    {
        //创建戳
        engProductRoutingtool.setCreateBy(SecurityUtils.getUsername());
        engProductRoutingtool.setCreateTime(DateUtils.getNowDate());
        engProductRoutingtool.setUpdateBy(SecurityUtils.getUsername());
        engProductRoutingtool.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingtoolMapper.insertEngProductRoutingtool(engProductRoutingtool);
    }

    /**
     * 修改产品工具
     * 
     * @param engProductRoutingtool 产品工具
     * @return 结果
     */
    @Override
    public int updateEngProductRoutingtool(EngProductRoutingtool engProductRoutingtool)
    {
        engProductRoutingtool.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingtoolMapper.updateEngProductRoutingtool(engProductRoutingtool);
    }

    /**
     * 批量删除产品工具
     * 
     * @param ids 需要删除的产品工具主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingtoolByIds(Long[] ids)
    {
        return engProductRoutingtoolMapper.deleteEngProductRoutingtoolByIds(ids);
    }

    /**
     * 删除产品工具信息
     * 
     * @param id 产品工具主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingtoolById(Long id)
    {
        return engProductRoutingtoolMapper.deleteEngProductRoutingtoolById(id);
    }


}
