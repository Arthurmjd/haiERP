package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdPackagePatternMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdPackagePattern;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdPackagePatternService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 包装方式预配Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdPackagePatternServiceImpl implements IOrdPackagePatternService 
{
    @Autowired(required = false)
    private OrdPackagePatternMapper ordPackagePatternMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询包装方式预配
     * 
     * @param id 包装方式预配主键
     * @return 包装方式预配
     */
    @Override
    public OrdPackagePattern selectOrdPackagePatternById(Long id)
    {
        return ordPackagePatternMapper.selectOrdPackagePatternById(id);
    }

    /**
     * 查询包装方式预配列表
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 包装方式预配
     */
    @Override
    public List<OrdPackagePattern> selectOrdPackagePatternList(OrdPackagePattern ordPackagePattern)
    {
        return ordPackagePatternMapper.selectOrdPackagePatternList(ordPackagePattern);
    }

    /**
     * 新增包装方式预配
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 结果
     */
    @Override
    public int insertOrdPackagePattern(OrdPackagePattern ordPackagePattern)
    {
        ordPackagePattern.setCreateTime(DateUtils.getNowDate());
        return ordPackagePatternMapper.insertOrdPackagePattern(ordPackagePattern);
    }

    /**
     * 修改包装方式预配
     * 
     * @param ordPackagePattern 包装方式预配
     * @return 结果
     */
    @Override
    public int updateOrdPackagePattern(OrdPackagePattern ordPackagePattern)
    {
        ordPackagePattern.refreshUpdatingStamp();
        return ordPackagePatternMapper.updateOrdPackagePattern(ordPackagePattern);
    }

    /**
     * 批量删除包装方式预配
     * 
     * @param ids 需要删除的包装方式预配主键
     * @return 结果
     */
    @Override
    public int deleteOrdPackagePatternByIds(Long[] ids)
    {
        return ordPackagePatternMapper.deleteOrdPackagePatternByIds(ids);
    }

    /**
     * 删除包装方式预配信息
     * 
     * @param id 包装方式预配主键
     * @return 结果
     */
    @Override
    public int deleteOrdPackagePatternById(Long id)
    {
        return ordPackagePatternMapper.deleteOrdPackagePatternById(id);
    }


}
