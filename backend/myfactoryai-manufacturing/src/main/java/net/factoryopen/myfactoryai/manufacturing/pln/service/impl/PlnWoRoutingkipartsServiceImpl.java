package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoRoutingkipartsMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoRoutingkipartsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单关重料Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class PlnWoRoutingkipartsServiceImpl implements IPlnWoRoutingkipartsService 
{
    @Autowired(required = false)
    private PlnWoRoutingkipartsMapper plnWoRoutingkipartsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单关重料
     * 
     * @param id 工单关重料主键
     * @return 工单关重料
     */
    @Override
    public PlnWoRoutingkiparts selectPlnWoRoutingkipartsById(Long id)
    {
        return plnWoRoutingkipartsMapper.selectPlnWoRoutingkipartsById(id);
    }

    /**
     * 查询工单关重料列表
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 工单关重料
     */
    @Override
    public List<PlnWoRoutingkiparts> selectPlnWoRoutingkipartsList(PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        return plnWoRoutingkipartsMapper.selectPlnWoRoutingkipartsList(plnWoRoutingkiparts);
    }

    /**
     * 新增工单关重料
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 结果
     */
    @Override
    public int insertPlnWoRoutingkiparts(PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        plnWoRoutingkiparts.setCreateTime(DateUtils.getNowDate());
        return plnWoRoutingkipartsMapper.insertPlnWoRoutingkiparts(plnWoRoutingkiparts);
    }

    /**
     * 修改工单关重料
     * 
     * @param plnWoRoutingkiparts 工单关重料
     * @return 结果
     */
    @Override
    public int updatePlnWoRoutingkiparts(PlnWoRoutingkiparts plnWoRoutingkiparts)
    {
        plnWoRoutingkiparts.setUpdateTime(DateUtils.getNowDate());
        return plnWoRoutingkipartsMapper.updatePlnWoRoutingkiparts(plnWoRoutingkiparts);
    }

    /**
     * 批量删除工单关重料
     * 
     * @param ids 需要删除的工单关重料主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingkipartsByIds(Long[] ids)
    {
        return plnWoRoutingkipartsMapper.deletePlnWoRoutingkipartsByIds(ids);
    }

    /**
     * 删除工单关重料信息
     * 
     * @param id 工单关重料主键
     * @return 结果
     */
    @Override
    public int deletePlnWoRoutingkipartsById(Long id)
    {
        return plnWoRoutingkipartsMapper.deletePlnWoRoutingkipartsById(id);
    }


}
