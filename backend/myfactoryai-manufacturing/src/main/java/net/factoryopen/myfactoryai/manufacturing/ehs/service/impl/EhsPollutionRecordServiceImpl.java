package net.factoryopen.myfactoryai.manufacturing.ehs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ehs.mapper.EhsPollutionRecordMapper;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionRecord;
import net.factoryopen.myfactoryai.manufacturing.ehs.service.IEhsPollutionRecordService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 污染物排放登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EhsPollutionRecordServiceImpl implements IEhsPollutionRecordService 
{
    @Autowired(required = false)
    private EhsPollutionRecordMapper ehsPollutionRecordMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询污染物排放登记
     * 
     * @param id 污染物排放登记主键
     * @return 污染物排放登记
     */
    @Override
    public EhsPollutionRecord selectEhsPollutionRecordById(Long id)
    {
        return ehsPollutionRecordMapper.selectEhsPollutionRecordById(id);
    }

    /**
     * 查询污染物排放登记列表
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 污染物排放登记
     */
    @Override
    public List<EhsPollutionRecord> selectEhsPollutionRecordList(EhsPollutionRecord ehsPollutionRecord)
    {
        return ehsPollutionRecordMapper.selectEhsPollutionRecordList(ehsPollutionRecord);
    }

    /**
     * 新增污染物排放登记
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 结果
     */
    @Override
    public int insertEhsPollutionRecord(EhsPollutionRecord ehsPollutionRecord)
    {
        ehsPollutionRecord.setCreateTime(DateUtils.getNowDate());
        return ehsPollutionRecordMapper.insertEhsPollutionRecord(ehsPollutionRecord);
    }

    /**
     * 修改污染物排放登记
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 结果
     */
    @Override
    public int updateEhsPollutionRecord(EhsPollutionRecord ehsPollutionRecord)
    {
        ehsPollutionRecord.setUpdateTime(DateUtils.getNowDate());
        return ehsPollutionRecordMapper.updateEhsPollutionRecord(ehsPollutionRecord);
    }

    /**
     * 批量删除污染物排放登记
     * 
     * @param ids 需要删除的污染物排放登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionRecordByIds(Long[] ids)
    {
        return ehsPollutionRecordMapper.deleteEhsPollutionRecordByIds(ids);
    }

    /**
     * 删除污染物排放登记信息
     * 
     * @param id 污染物排放登记主键
     * @return 结果
     */
    @Override
    public int deleteEhsPollutionRecordById(Long id)
    {
        return ehsPollutionRecordMapper.deleteEhsPollutionRecordById(id);
    }


}
