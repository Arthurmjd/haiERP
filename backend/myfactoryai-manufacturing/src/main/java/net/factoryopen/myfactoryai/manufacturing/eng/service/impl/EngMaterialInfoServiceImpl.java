package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialInfoMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInfo;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialInfoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料信息Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-09-17
 */
@Service
public class EngMaterialInfoServiceImpl implements IEngMaterialInfoService 
{
    @Autowired(required = false)
    private EngMaterialInfoMapper engMaterialInfoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料信息
     * 
     * @param id 物料信息主键
     * @return 物料信息
     */
    @Override
    public EngMaterialInfo selectEngMaterialInfoById(Long id)
    {
        return engMaterialInfoMapper.selectEngMaterialInfoById(id);
    }

    /**
     * 查询物料信息列表
     * 
     * @param engMaterialInfo 物料信息
     * @return 物料信息
     */
    @Override
    public List<EngMaterialInfo> selectEngMaterialInfoList(EngMaterialInfo engMaterialInfo)
    {
        return engMaterialInfoMapper.selectEngMaterialInfoList(engMaterialInfo);
    }

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息
     */
    @Override
    public List<EngMaterialInfo> selectPurchasingMaterialInfoList(EngMaterialInfo engMaterialInfo)
    {
        return engMaterialInfoMapper.selectPurchasingMaterialInfoList(engMaterialInfo);
    }

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息
     */
    @Override
    public List<EngMaterialInfo> selectProductingMaterialInfoList(EngMaterialInfo engMaterialInfo)
    {
        return engMaterialInfoMapper.selectProductingMaterialInfoList(engMaterialInfo);
    }

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息
     */
    @Override
    public List<EngMaterialInfo> filterMaterialList(EngMaterialInfo engMaterialInfo)
    {
        return engMaterialInfoMapper.filterMaterialList(engMaterialInfo);
    }

    /**
     * 新增物料信息
     * 
     * @param engMaterialInfo 物料信息
     * @return 结果
     */
    @Override
    public int insertEngMaterialInfo(EngMaterialInfo engMaterialInfo)
    {
        engMaterialInfo.setCreateTime(DateUtils.getNowDate());
        return engMaterialInfoMapper.insertEngMaterialInfo(engMaterialInfo);
    }

    /**
     * 修改物料信息
     * 
     * @param engMaterialInfo 物料信息
     * @return 结果
     */
    @Override
    public int updateEngMaterialInfo(EngMaterialInfo engMaterialInfo)
    {
        engMaterialInfo.setUpdateTime(DateUtils.getNowDate());
        return engMaterialInfoMapper.updateEngMaterialInfo(engMaterialInfo);
    }

    /**
     * 批量删除物料信息
     * 
     * @param ids 需要删除的物料信息主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialInfoByIds(Long[] ids)
    {
        return engMaterialInfoMapper.deleteEngMaterialInfoByIds(ids);
    }

    /**
     * 删除物料信息信息
     * 
     * @param id 物料信息主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialInfoById(Long id)
    {
        return engMaterialInfoMapper.deleteEngMaterialInfoById(id);
    }


}
