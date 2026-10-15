package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.exe.ExeMlSplitAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlSplitMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlSplit;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlSplitService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制造批次分拆Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class ExeMlSplitServiceImpl implements IExeMlSplitService 
{
    @Autowired(required = false)
    private ExeMlSplitMapper exeMlSplitMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private ExeMlSplitAlgorithm exeMlSplitAlgorithm;

    /**
     * 查询制造批次分拆
     * 
     * @param id 制造批次分拆主键
     * @return 制造批次分拆
     */
    @Override
    public ExeMlSplit selectExeMlSplitById(Long id)
    {
        return exeMlSplitMapper.selectExeMlSplitById(id);
    }

    /**
     * 查询制造批次分拆列表
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 制造批次分拆
     */
    @Override
    public List<ExeMlSplit> selectExeMlSplitList(ExeMlSplit exeMlSplit)
    {
        return exeMlSplitMapper.selectExeMlSplitList(exeMlSplit);
    }

    /**
     * 新增制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    @Override
    public int insertExeMlSplit(ExeMlSplit exeMlSplit)
    {
        exeMlSplit.setCreateTime(DateUtils.getNowDate());
        return exeMlSplitMapper.insertExeMlSplit(exeMlSplit);
    }

    /**
     * 修改制造批次分拆
     * 
     * @param exeMlSplit 制造批次分拆
     * @return 结果
     */
    @Override
    public int updateExeMlSplit(ExeMlSplit exeMlSplit)
    {
        exeMlSplit.setUpdateTime(DateUtils.getNowDate());
        return exeMlSplitMapper.updateExeMlSplit(exeMlSplit);
    }

    /**
     * 批量删除制造批次分拆
     * 
     * @param ids 需要删除的制造批次分拆主键
     * @return 结果
     */
    @Override
    public int deleteExeMlSplitByIds(Long[] ids)
    {
        return exeMlSplitMapper.deleteExeMlSplitByIds(ids);
    }

    /**
     * 删除制造批次分拆信息
     * 
     * @param id 制造批次分拆主键
     * @return 结果
     */
    @Override
    public int deleteExeMlSplitById(Long id)
    {
        return exeMlSplitMapper.deleteExeMlSplitById(id);
    }

    /**
     * 分批
     *
     * @param exeMlSplit 分批
     * @return 结果
     */
    @Override
    public int splitMl(ExeMlSplit exeMlSplit) throws Exception{
        try{
            return exeMlSplitAlgorithm.splitMl(exeMlSplit);
        }catch (Exception ex){
            throw ex;
        }
    }

}
