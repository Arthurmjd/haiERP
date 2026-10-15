package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCategoryRoutingMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryRouting;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryRoutingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 品类工艺路线Service业务层处理
 * 
 * @author admin
 * @date 2024-11-09
 */
@Service
public class EngCategoryRoutingServiceImpl implements IEngCategoryRoutingService 
{
    @Autowired(required = false)
    private EngCategoryRoutingMapper engCategoryRoutingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询品类工艺路线
     * 
     * @param id 品类工艺路线主键
     * @return 品类工艺路线
     */
    @Override
    public EngCategoryRouting selectEngCategoryRoutingById(Long id)
    {
        return engCategoryRoutingMapper.selectEngCategoryRoutingById(id);
    }

    /**
     * 查询品类工艺路线列表
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 品类工艺路线
     */
    @Override
    public List<EngCategoryRouting> selectEngCategoryRoutingList(EngCategoryRouting engCategoryRouting)
    {
        return engCategoryRoutingMapper.selectEngCategoryRoutingList(engCategoryRouting);
    }

    /**
     * 新增品类工艺路线
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 结果
     */
    @Override
    public int insertEngCategoryRouting(EngCategoryRouting engCategoryRouting)
    {
        engCategoryRouting.setCreateTime(DateUtils.getNowDate());
        return engCategoryRoutingMapper.insertEngCategoryRouting(engCategoryRouting);
    }

    /**
     * 修改品类工艺路线
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 结果
     */
    @Override
    public int updateEngCategoryRouting(EngCategoryRouting engCategoryRouting)
    {
        engCategoryRouting.setUpdateTime(DateUtils.getNowDate());
        return engCategoryRoutingMapper.updateEngCategoryRouting(engCategoryRouting);
    }

    /**
     * 批量删除品类工艺路线
     * 
     * @param ids 需要删除的品类工艺路线主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryRoutingByIds(Long[] ids)
    {
        return engCategoryRoutingMapper.deleteEngCategoryRoutingByIds(ids);
    }

    /**
     * 删除品类工艺路线信息
     * 
     * @param id 品类工艺路线主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryRoutingById(Long id)
    {
        return engCategoryRoutingMapper.deleteEngCategoryRoutingById(id);
    }


}
