package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductRoutingkipartsMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductRoutingkiparts;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductRoutingkipartsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品关重料Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductRoutingkipartsServiceImpl implements IEngProductRoutingkipartsService 
{
    @Autowired(required = false)
    private EngProductRoutingkipartsMapper engProductRoutingkipartsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品关重料
     * 
     * @param id 产品关重料主键
     * @return 产品关重料
     */
    @Override
    public EngProductRoutingkiparts selectEngProductRoutingkipartsById(Long id)
    {
        return engProductRoutingkipartsMapper.selectEngProductRoutingkipartsById(id);
    }

    /**
     * 查询产品关重料列表
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 产品关重料
     */
    @Override
    public List<EngProductRoutingkiparts> selectEngProductRoutingkipartsList(EngProductRoutingkiparts engProductRoutingkiparts)
    {
        return engProductRoutingkipartsMapper.selectEngProductRoutingkipartsList(engProductRoutingkiparts);
    }

    /**
     * 新增产品关重料
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 结果
     */
    @Override
    public int insertEngProductRoutingkiparts(EngProductRoutingkiparts engProductRoutingkiparts)
    {
        //创建戳
        engProductRoutingkiparts.setCreateBy(SecurityUtils.getUsername());
        engProductRoutingkiparts.setCreateTime(DateUtils.getNowDate());
        engProductRoutingkiparts.setUpdateBy(SecurityUtils.getUsername());
        engProductRoutingkiparts.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingkipartsMapper.insertEngProductRoutingkiparts(engProductRoutingkiparts);
    }

    /**
     * 修改产品关重料
     * 
     * @param engProductRoutingkiparts 产品关重料
     * @return 结果
     */
    @Override
    public int updateEngProductRoutingkiparts(EngProductRoutingkiparts engProductRoutingkiparts)
    {
        engProductRoutingkiparts.setUpdateTime(DateUtils.getNowDate());
        return engProductRoutingkipartsMapper.updateEngProductRoutingkiparts(engProductRoutingkiparts);
    }

    /**
     * 批量删除产品关重料
     * 
     * @param ids 需要删除的产品关重料主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingkipartsByIds(Long[] ids)
    {
        return engProductRoutingkipartsMapper.deleteEngProductRoutingkipartsByIds(ids);
    }

    /**
     * 删除产品关重料信息
     * 
     * @param id 产品关重料主键
     * @return 结果
     */
    @Override
    public int deleteEngProductRoutingkipartsById(Long id)
    {
        return engProductRoutingkipartsMapper.deleteEngProductRoutingkipartsById(id);
    }


}
