package net.factoryopen.myfactoryai.manufacturing.del.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelGdItemMapper;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelGdItem;
import net.factoryopen.myfactoryai.manufacturing.del.service.IDelGdItemService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 发货单明细Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class DelGdItemServiceImpl implements IDelGdItemService 
{
    @Autowired(required = false)
    private DelGdItemMapper delGdItemMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询发货单明细
     * 
     * @param id 发货单明细主键
     * @return 发货单明细
     */
    @Override
    public DelGdItem selectDelGdItemById(Long id)
    {
        return delGdItemMapper.selectDelGdItemById(id);
    }

    /**
     * 查询发货单明细列表
     * 
     * @param delGdItem 发货单明细
     * @return 发货单明细
     */
    @Override
    public List<DelGdItem> selectDelGdItemList(DelGdItem delGdItem)
    {
        return delGdItemMapper.selectDelGdItemList(delGdItem);
    }

    /**
     * 新增发货单明细
     * 
     * @param delGdItem 发货单明细
     * @return 结果
     */
    @Override
    public int insertDelGdItem(DelGdItem delGdItem)
    {
        //创建戳
        delGdItem.setCreateBy(SecurityUtils.getUsername());
        delGdItem.setCreateTime(DateUtils.getNowDate());
        delGdItem.setUpdateBy(SecurityUtils.getUsername());
        delGdItem.setUpdateTime(DateUtils.getNowDate());
        return delGdItemMapper.insertDelGdItem(delGdItem);
    }

    /**
     * 修改发货单明细
     * 
     * @param delGdItem 发货单明细
     * @return 结果
     */
    @Override
    public int updateDelGdItem(DelGdItem delGdItem)
    {
        delGdItem.refreshUpdatingStamp();
        return delGdItemMapper.updateDelGdItem(delGdItem);
    }

    /**
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的发货单明细主键
     * @return 结果
     */
    @Override
    public int deleteDelGdItemByIds(Long[] ids)
    {
        return delGdItemMapper.deleteDelGdItemByIds(ids);
    }

    /**
     * 删除发货单明细信息
     * 
     * @param id 发货单明细主键
     * @return 结果
     */
    @Override
    public int deleteDelGdItemById(Long id)
    {
        return delGdItemMapper.deleteDelGdItemById(id);
    }


}
