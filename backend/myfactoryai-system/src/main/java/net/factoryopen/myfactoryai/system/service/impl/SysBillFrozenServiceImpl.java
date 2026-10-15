package net.factoryopen.myfactoryai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;
import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.service.ISysBillFrozenService;

/**
 * 单对象冻结Service业务层处理
 * 
 * @author admin
 * @date 2024-06-18
 */
@Service
public class SysBillFrozenServiceImpl implements ISysBillFrozenService 
{
    @Autowired
    private SysBillFrozenMapper sysBillFrozenMapper;

    /**
     * 查询单对象冻结
     * 
     * @param id 单对象冻结主键
     * @return 单对象冻结
     */
    @Override
    public SysBillFrozen selectSysBillFrozenById(Long id)
    {
        return sysBillFrozenMapper.selectSysBillFrozenById(id);
    }

    /**
     * 查询单对象冻结列表
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 单对象冻结
     */
    @Override
    public List<SysBillFrozen> selectSysBillFrozenList(SysBillFrozen sysBillFrozen)
    {
        return sysBillFrozenMapper.selectSysBillFrozenList(sysBillFrozen);
    }

    /**
     * 新增单对象冻结
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 结果
     */
    @Override
    public int insertSysBillFrozen(SysBillFrozen sysBillFrozen)
    {
        return sysBillFrozenMapper.insertSysBillFrozen(sysBillFrozen);
    }

    /**
     * 修改单对象冻结
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 结果
     */
    @Override
    public int updateSysBillFrozen(SysBillFrozen sysBillFrozen)
    {
        return sysBillFrozenMapper.updateSysBillFrozen(sysBillFrozen);
    }

    /**
     * 批量删除单对象冻结
     * 
     * @param ids 需要删除的单对象冻结主键
     * @return 结果
     */
    @Override
    public int deleteSysBillFrozenByIds(Long[] ids)
    {
        return sysBillFrozenMapper.deleteSysBillFrozenByIds(ids);
    }

    /**
     * 删除单对象冻结信息
     * 
     * @param id 单对象冻结主键
     * @return 结果
     */
    @Override
    public int deleteSysBillFrozenById(Long id)
    {
        return sysBillFrozenMapper.deleteSysBillFrozenById(id);
    }



}
