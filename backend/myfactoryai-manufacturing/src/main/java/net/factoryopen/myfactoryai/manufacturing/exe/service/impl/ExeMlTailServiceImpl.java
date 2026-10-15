package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlTailMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTail;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlTailService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 制造批次尾情Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class ExeMlTailServiceImpl implements IExeMlTailService 
{
    @Autowired(required = false)
    private ExeMlTailMapper exeMlTailMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询制造批次尾情
     * 
     * @param id 制造批次尾情主键
     * @return 制造批次尾情
     */
    @Override
    public ExeMlTail selectExeMlTailById(Long id)
    {
        return exeMlTailMapper.selectExeMlTailById(id);
    }

    /**
     * 查询制造批次尾情列表
     * 
     * @param exeMlTail 制造批次尾情
     * @return 制造批次尾情
     */
    @Override
    public List<ExeMlTail> selectExeMlTailList(ExeMlTail exeMlTail)
    {
        return exeMlTailMapper.selectExeMlTailList(exeMlTail);
    }

    /**
     * 新增制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    @Override
    public int insertExeMlTail(ExeMlTail exeMlTail)
    {
        exeMlTail.setCreateTime(DateUtils.getNowDate());
        return exeMlTailMapper.insertExeMlTail(exeMlTail);
    }

    /**
     * 修改制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    @Override
    public int updateExeMlTail(ExeMlTail exeMlTail)
    {
        exeMlTail.setUpdateTime(DateUtils.getNowDate());
        return exeMlTailMapper.updateExeMlTail(exeMlTail);
    }

    /**
     * 批量删除制造批次尾情
     * 
     * @param ids 需要删除的制造批次尾情主键
     * @return 结果
     */
    @Override
    public int deleteExeMlTailByIds(Long[] ids)
    {
        return exeMlTailMapper.deleteExeMlTailByIds(ids);
    }

    /**
     * 删除制造批次尾情信息
     * 
     * @param id 制造批次尾情主键
     * @return 结果
     */
    @Override
    public int deleteExeMlTailById(Long id)
    {
        return exeMlTailMapper.deleteExeMlTailById(id);
    }


}
