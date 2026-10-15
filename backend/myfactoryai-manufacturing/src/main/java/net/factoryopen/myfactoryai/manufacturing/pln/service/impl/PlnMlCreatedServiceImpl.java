package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlCreatedMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlCreated;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlCreatedService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 新派批次Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnMlCreatedServiceImpl implements IPlnMlCreatedService 
{
    @Autowired(required = false)
    private PlnMlCreatedMapper plnMlCreatedMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询新派批次
     * 
     * @param id 新派批次主键
     * @return 新派批次
     */
    @Override
    public PlnMlCreated selectPlnMlCreatedById(Long id)
    {
        return plnMlCreatedMapper.selectPlnMlCreatedById(id);
    }

    /**
     * 查询新派批次列表
     * 
     * @param plnMlCreated 新派批次
     * @return 新派批次
     */
    @Override
    public List<PlnMlCreated> selectPlnMlCreatedList(PlnMlCreated plnMlCreated)
    {
        return plnMlCreatedMapper.selectPlnMlCreatedList(plnMlCreated);
    }

    /**
     * 新增新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    @Override
    public int insertPlnMlCreated(PlnMlCreated plnMlCreated)
    {
        plnMlCreated.setCreateTime(DateUtils.getNowDate());
        return plnMlCreatedMapper.insertPlnMlCreated(plnMlCreated);
    }

    /**
     * 修改新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    @Override
    public int updatePlnMlCreated(PlnMlCreated plnMlCreated)
    {
        plnMlCreated.setUpdateTime(DateUtils.getNowDate());
        return plnMlCreatedMapper.updatePlnMlCreated(plnMlCreated);
    }

    /**
     * 批量删除新派批次
     * 
     * @param ids 需要删除的新派批次主键
     * @return 结果
     */
    @Override
    public int deletePlnMlCreatedByIds(Long[] ids)
    {
        return plnMlCreatedMapper.deletePlnMlCreatedByIds(ids);
    }

    /**
     * 删除新派批次信息
     * 
     * @param id 新派批次主键
     * @return 结果
     */
    @Override
    public int deletePlnMlCreatedById(Long id)
    {
        return plnMlCreatedMapper.deletePlnMlCreatedById(id);
    }


}
