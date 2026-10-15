package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPerspectivityTailMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPerspectivityTail;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelPerspectivityTailService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 订单透视尾情Service业务层处理
 * 
 * @author admin
 * @date 2024-07-23
 */
@Service
public class DelPerspectivityTailServiceImpl implements IDelPerspectivityTailService 
{
    @Autowired(required = false)
    private DelPerspectivityTailMapper delPerspectivityTailMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询订单透视尾情
     * 
     * @param id 订单透视尾情主键
     * @return 订单透视尾情
     */
    @Override
    public DelPerspectivityTail selectDelPerspectivityTailById(Long id)
    {
        return delPerspectivityTailMapper.selectDelPerspectivityTailById(id);
    }

    /**
     * 查询订单透视尾情列表
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 订单透视尾情
     */
    @Override
    public List<DelPerspectivityTail> selectDelPerspectivityTailList(DelPerspectivityTail delPerspectivityTail)
    {
        return delPerspectivityTailMapper.selectDelPerspectivityTailList(delPerspectivityTail);
    }

    /**
     * 新增订单透视尾情
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 结果
     */
    @Override
    public int insertDelPerspectivityTail(DelPerspectivityTail delPerspectivityTail)
    {
        return delPerspectivityTailMapper.insertDelPerspectivityTail(delPerspectivityTail);
    }

    /**
     * 修改订单透视尾情
     * 
     * @param delPerspectivityTail 订单透视尾情
     * @return 结果
     */
    @Override
    public int updateDelPerspectivityTail(DelPerspectivityTail delPerspectivityTail)
    {
        return delPerspectivityTailMapper.updateDelPerspectivityTail(delPerspectivityTail);
    }

    /**
     * 批量删除订单透视尾情
     * 
     * @param ids 需要删除的订单透视尾情主键
     * @return 结果
     */
    @Override
    public int deleteDelPerspectivityTailByIds(Long[] ids)
    {
        return delPerspectivityTailMapper.deleteDelPerspectivityTailByIds(ids);
    }

    /**
     * 删除订单透视尾情信息
     * 
     * @param id 订单透视尾情主键
     * @return 结果
     */
    @Override
    public int deleteDelPerspectivityTailById(Long id)
    {
        return delPerspectivityTailMapper.deleteDelPerspectivityTailById(id);
    }


}
