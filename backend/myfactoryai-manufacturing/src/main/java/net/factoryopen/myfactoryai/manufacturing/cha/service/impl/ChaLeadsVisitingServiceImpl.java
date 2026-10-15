package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaLeadsVisitingMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisiting;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaLeadsVisitingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 线索拜访Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaLeadsVisitingServiceImpl implements IChaLeadsVisitingService 
{
    @Autowired(required = false)
    private ChaLeadsVisitingMapper chaLeadsVisitingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询线索拜访
     * 
     * @param id 线索拜访主键
     * @return 线索拜访
     */
    @Override
    public ChaLeadsVisiting selectChaLeadsVisitingById(Long id)
    {
        return chaLeadsVisitingMapper.selectChaLeadsVisitingById(id);
    }

    /**
     * 查询线索拜访列表
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 线索拜访
     */
    @Override
    public List<ChaLeadsVisiting> selectChaLeadsVisitingList(ChaLeadsVisiting chaLeadsVisiting)
    {
        return chaLeadsVisitingMapper.selectChaLeadsVisitingList(chaLeadsVisiting);
    }

    /**
     * 新增线索拜访
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 结果
     */
    @Override
    public int insertChaLeadsVisiting(ChaLeadsVisiting chaLeadsVisiting)
    {
        //创建戳
        chaLeadsVisiting.setCreateBy(SecurityUtils.getUsername());
        chaLeadsVisiting.setCreateTime(DateUtils.getNowDate());
        chaLeadsVisiting.setUpdateBy(SecurityUtils.getUsername());
        chaLeadsVisiting.setUpdateTime(DateUtils.getNowDate());
        return chaLeadsVisitingMapper.insertChaLeadsVisiting(chaLeadsVisiting);
    }

    /**
     * 修改线索拜访
     * 
     * @param chaLeadsVisiting 线索拜访
     * @return 结果
     */
    @Override
    public int updateChaLeadsVisiting(ChaLeadsVisiting chaLeadsVisiting)
    {
        chaLeadsVisiting.setUpdateBy(SecurityUtils.getUsername());
        chaLeadsVisiting.setUpdateTime(DateUtils.getNowDate());
        return chaLeadsVisitingMapper.updateChaLeadsVisiting(chaLeadsVisiting);
    }

    /**
     * 批量删除线索拜访
     * 
     * @param ids 需要删除的线索拜访主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsVisitingByIds(Long[] ids)
    {
        return chaLeadsVisitingMapper.deleteChaLeadsVisitingByIds(ids);
    }

    /**
     * 删除线索拜访信息
     * 
     * @param id 线索拜访主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsVisitingById(Long id)
    {
        return chaLeadsVisitingMapper.deleteChaLeadsVisitingById(id);
    }


}
