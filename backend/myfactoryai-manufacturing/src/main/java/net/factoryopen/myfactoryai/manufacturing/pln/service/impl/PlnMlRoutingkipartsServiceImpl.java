package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlRoutingkipartsMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlRoutingkipartsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次关重料Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlRoutingkipartsServiceImpl implements IPlnMlRoutingkipartsService 
{
    @Autowired(required = false)
    private PlnMlRoutingkipartsMapper plnMlRoutingkipartsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次关重料
     * 
     * @param id 批次关重料主键
     * @return 批次关重料
     */
    @Override
    public PlnMlRoutingkiparts selectPlnMlRoutingkipartsById(Long id)
    {
        return plnMlRoutingkipartsMapper.selectPlnMlRoutingkipartsById(id);
    }

    /**
     * 查询批次关重料列表
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 批次关重料
     */
    @Override
    public List<PlnMlRoutingkiparts> selectPlnMlRoutingkipartsList(PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        return plnMlRoutingkipartsMapper.selectPlnMlRoutingkipartsList(plnMlRoutingkiparts);
    }

    /**
     * 新增批次关重料
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 结果
     */
    @Override
    public int insertPlnMlRoutingkiparts(PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        plnMlRoutingkiparts.setCreateTime(DateUtils.getNowDate());
        return plnMlRoutingkipartsMapper.insertPlnMlRoutingkiparts(plnMlRoutingkiparts);
    }

    /**
     * 修改批次关重料
     * 
     * @param plnMlRoutingkiparts 批次关重料
     * @return 结果
     */
    @Override
    public int updatePlnMlRoutingkiparts(PlnMlRoutingkiparts plnMlRoutingkiparts)
    {
        plnMlRoutingkiparts.refreshUpdatingStamp();
        return plnMlRoutingkipartsMapper.updatePlnMlRoutingkiparts(plnMlRoutingkiparts);
    }

    /**
     * 批量删除批次关重料
     * 
     * @param ids 需要删除的批次关重料主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingkipartsByIds(Long[] ids)
    {
        return plnMlRoutingkipartsMapper.deletePlnMlRoutingkipartsByIds(ids);
    }

    /**
     * 删除批次关重料信息
     * 
     * @param id 批次关重料主键
     * @return 结果
     */
    @Override
    public int deletePlnMlRoutingkipartsById(Long id)
    {
        return plnMlRoutingkipartsMapper.deletePlnMlRoutingkipartsById(id);
    }


}
