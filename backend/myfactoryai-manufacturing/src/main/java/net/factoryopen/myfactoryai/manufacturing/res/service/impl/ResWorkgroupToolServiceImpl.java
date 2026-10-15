package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupToolMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroupTool;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWorkgroupToolService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 班组工序Service业务层处理
 * 
 * @author admin
 * @date 2025-01-02
 */
@Service
public class ResWorkgroupToolServiceImpl implements IResWorkgroupToolService 
{
    @Autowired(required = false)
    private ResWorkgroupToolMapper resWorkgroupToolMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询班组工序
     * 
     * @param id 班组工序主键
     * @return 班组工序
     */
    @Override
    public ResWorkgroupTool selectResWorkgroupToolById(Long id)
    {
        return resWorkgroupToolMapper.selectResWorkgroupToolById(id);
    }

    /**
     * 查询班组工序列表
     * 
     * @param resWorkgroupTool 班组工序
     * @return 班组工序
     */
    @Override
    public List<ResWorkgroupTool> selectResWorkgroupToolList(ResWorkgroupTool resWorkgroupTool)
    {
        return resWorkgroupToolMapper.selectResWorkgroupToolList(resWorkgroupTool);
    }

    /**
     * 新增班组工序
     * 
     * @param resWorkgroupTool 班组工序
     * @return 结果
     */
    @Override
    public int insertResWorkgroupTool(ResWorkgroupTool resWorkgroupTool)
    {
        resWorkgroupTool.setCreateTime(DateUtils.getNowDate());
        return resWorkgroupToolMapper.insertResWorkgroupTool(resWorkgroupTool);
    }

    /**
     * 修改班组工序
     * 
     * @param resWorkgroupTool 班组工序
     * @return 结果
     */
    @Override
    public int updateResWorkgroupTool(ResWorkgroupTool resWorkgroupTool)
    {
        resWorkgroupTool.setUpdateTime(DateUtils.getNowDate());
        return resWorkgroupToolMapper.updateResWorkgroupTool(resWorkgroupTool);
    }

    /**
     * 批量删除班组工序
     * 
     * @param ids 需要删除的班组工序主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupToolByIds(Long[] ids)
    {
        return resWorkgroupToolMapper.deleteResWorkgroupToolByIds(ids);
    }

    /**
     * 删除班组工序信息
     * 
     * @param id 班组工序主键
     * @return 结果
     */
    @Override
    public int deleteResWorkgroupToolById(Long id)
    {
        return resWorkgroupToolMapper.deleteResWorkgroupToolById(id);
    }


}
