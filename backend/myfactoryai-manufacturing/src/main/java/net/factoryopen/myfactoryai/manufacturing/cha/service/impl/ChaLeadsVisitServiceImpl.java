package net.factoryopen.myfactoryai.manufacturing.cha.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.cha.mapper.ChaLeadsVisitMapper;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaLeadsVisit;
import net.factoryopen.myfactoryai.manufacturing.cha.service.IChaLeadsVisitService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客访登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ChaLeadsVisitServiceImpl implements IChaLeadsVisitService 
{
    @Autowired(required = false)
    private ChaLeadsVisitMapper chaLeadsVisitMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客访登记
     * 
     * @param id 客访登记主键
     * @return 客访登记
     */
    @Override
    public ChaLeadsVisit selectChaLeadsVisitById(Long id)
    {
        return chaLeadsVisitMapper.selectChaLeadsVisitById(id);
    }

    /**
     * 查询客访登记列表
     * 
     * @param chaLeadsVisit 客访登记
     * @return 客访登记
     */
    @Override
    public List<ChaLeadsVisit> selectChaLeadsVisitList(ChaLeadsVisit chaLeadsVisit)
    {
        return chaLeadsVisitMapper.selectChaLeadsVisitList(chaLeadsVisit);
    }

    /**
     * 新增客访登记
     * 
     * @param chaLeadsVisit 客访登记
     * @return 结果
     */
    @Override
    public int insertChaLeadsVisit(ChaLeadsVisit chaLeadsVisit)
    {
        //创建戳
        chaLeadsVisit.setCreateBy(SecurityUtils.getUsername());
        chaLeadsVisit.setCreateTime(DateUtils.getNowDate());
        chaLeadsVisit.setUpdateBy(SecurityUtils.getUsername());
        chaLeadsVisit.setUpdateTime(DateUtils.getNowDate());
        return chaLeadsVisitMapper.insertChaLeadsVisit(chaLeadsVisit);
    }

    /**
     * 修改客访登记
     * 
     * @param chaLeadsVisit 客访登记
     * @return 结果
     */
    @Override
    public int updateChaLeadsVisit(ChaLeadsVisit chaLeadsVisit)
    {
        chaLeadsVisit.refreshUpdatingStamp();
        return chaLeadsVisitMapper.updateChaLeadsVisit(chaLeadsVisit);
    }

    /**
     * 批量删除客访登记
     * 
     * @param ids 需要删除的客访登记主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsVisitByIds(Long[] ids)
    {
        return chaLeadsVisitMapper.deleteChaLeadsVisitByIds(ids);
    }

    /**
     * 删除客访登记信息
     * 
     * @param id 客访登记主键
     * @return 结果
     */
    @Override
    public int deleteChaLeadsVisitById(Long id)
    {
        return chaLeadsVisitMapper.deleteChaLeadsVisitById(id);
    }


}
