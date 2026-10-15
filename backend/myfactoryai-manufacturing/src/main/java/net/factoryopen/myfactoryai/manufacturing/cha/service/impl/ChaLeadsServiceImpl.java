package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaLeadsMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeads;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaLeadsService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 线索备案Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaLeadsServiceImpl implements IChaLeadsService 
{
    @Autowired(required = false)
    private ChaLeadsMapper chaLeadsMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询线索备案
     * 
     * @param id 线索备案主键
     * @return 线索备案
     */
    @Override
    public ChaLeads selectChaLeadsById(Long id)
    {
        return chaLeadsMapper.selectChaLeadsById(id);
    }

    /**
     * 查询线索备案列表
     * 
     * @param chaLeads 线索备案
     * @return 线索备案
     */
    @Override
    public List<ChaLeads> selectChaLeadsList(ChaLeads chaLeads)
    {
        return chaLeadsMapper.selectChaLeadsList(chaLeads);
    }

    /**
     * 新增线索备案
     * 
     * @param chaLeads 线索备案
     * @return 结果
     */
    @Override
    public int insertChaLeads(ChaLeads chaLeads)
    {
        //创建戳
        chaLeads.setCreateBy(SecurityUtils.getUsername());
        chaLeads.setCreateTime(DateUtils.getNowDate());
        chaLeads.setUpdateBy(SecurityUtils.getUsername());
        chaLeads.setUpdateTime(DateUtils.getNowDate());
        return chaLeadsMapper.insertChaLeads(chaLeads);
    }

    /**
     * 修改线索备案
     * 
     * @param chaLeads 线索备案
     * @return 结果
     */
    @Override
    public int updateChaLeads(ChaLeads chaLeads)
    {
        chaLeads.setUpdateBy(SecurityUtils.getUsername());
        chaLeads.setUpdateTime(DateUtils.getNowDate());
        return chaLeadsMapper.updateChaLeads(chaLeads);
    }

    /**
     * 批量删除线索备案
     * 
     * @param ids 需要删除的线索备案主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsByIds(Long[] ids)
    {
        return chaLeadsMapper.deleteChaLeadsByIds(ids);
    }

    /**
     * 删除线索备案信息
     * 
     * @param id 线索备案主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsById(Long id)
    {
        return chaLeadsMapper.deleteChaLeadsById(id);
    }


}
