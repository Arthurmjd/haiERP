package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsHazardRecordMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardRecord;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsHazardRecordService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 安全风险登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsHazardRecordServiceImpl implements IEhsHazardRecordService 
{
    @Autowired(required = false)
    private EhsHazardRecordMapper ehsHazardRecordMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询安全风险登记
     * 
     * @param id 安全风险登记主键
     * @return 安全风险登记
     */
    @Override
    public EhsHazardRecord selectEhsHazardRecordById(Long id)
    {
        return ehsHazardRecordMapper.selectEhsHazardRecordById(id);
    }

    /**
     * 查询安全风险登记列表
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 安全风险登记
     */
    @Override
    public List<EhsHazardRecord> selectEhsHazardRecordList(EhsHazardRecord ehsHazardRecord)
    {
        return ehsHazardRecordMapper.selectEhsHazardRecordList(ehsHazardRecord);
    }

    /**
     * 新增安全风险登记
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 结果
     */
    @Override
    public int insertEhsHazardRecord(EhsHazardRecord ehsHazardRecord)
    {
        ehsHazardRecord.setCreateTime(DateUtils.getNowDate());
        return ehsHazardRecordMapper.insertEhsHazardRecord(ehsHazardRecord);
    }

    /**
     * 修改安全风险登记
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 结果
     */
    @Override
    public int updateEhsHazardRecord(EhsHazardRecord ehsHazardRecord)
    {
        ehsHazardRecord.setUpdateTime(DateUtils.getNowDate());
        return ehsHazardRecordMapper.updateEhsHazardRecord(ehsHazardRecord);
    }

    /**
     * 批量删除安全风险登记
     * 
     * @param ids 需要删除的安全风险登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardRecordByIds(Long[] ids)
    {
        return ehsHazardRecordMapper.deleteEhsHazardRecordByIds(ids);
    }

    /**
     * 删除安全风险登记信息
     * 
     * @param id 安全风险登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsHazardRecordById(Long id)
    {
        return ehsHazardRecordMapper.deleteEhsHazardRecordById(id);
    }


}
