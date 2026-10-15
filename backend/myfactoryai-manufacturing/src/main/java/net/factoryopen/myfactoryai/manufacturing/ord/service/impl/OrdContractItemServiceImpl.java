package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;

import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdContractItemAlgorithm;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdContractItemMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdContractItemService;

import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 合同明细Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdContractItemServiceImpl implements IOrdContractItemService 
{
    @Autowired(required = false)
    private OrdContractItemMapper ordContractItemMapper;

    @Autowired(required = false)
    private OrdContractItemAlgorithm contractItemAlgorithm;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询合同明细
     * 
     * @param id 合同明细主键
     * @return 合同明细
     */
    @Override
    public OrdContractItem selectOrdContractItemById(Long id)
    {
        return ordContractItemMapper.selectOrdContractItemById(id);
    }

    /**
     * 查询合同明细列表
     * 
     * @param ordContractItem 合同明细
     * @return 合同明细
     */
    @Override
    public List<OrdContractItem> selectOrdContractItemList(OrdContractItem ordContractItem)
    {
        return ordContractItemMapper.selectOrdContractItemList(ordContractItem);
    }

    /**
     * 查询合同登记发布列表
     *
     * @param soId 合同ID
     * @return 合同登记发布
     */
    @Override
    public List<OrdContractItem> selectOrdContractItemListByContractId(Long soId) { return ordContractItemMapper.selectOrdContractItemListByContractId(soId); }

    /**
     * 新增合同明细
     * 
     * @param ordContractItem 合同明细
     * @return 结果
     */
    @Override
    public int insertOrdContractItem(OrdContractItem ordContractItem)
    {
        ordContractItem.setCreateTime(DateUtils.getNowDate());
        return ordContractItemMapper.insertOrdContractItem(ordContractItem);
    }

    /**
     * 修改合同明细
     * 
     * @param ordContractItem 合同明细
     * @return 结果
     */
    @Override
    public int updateOrdContractItem(OrdContractItem ordContractItem)
    {
        ordContractItem.refreshUpdatingStamp();
        return ordContractItemMapper.updateOrdContractItem(ordContractItem);
    }

    /**
     * 批量删除合同明细
     * 
     * @param ids 需要删除的合同明细主键
     * @return 结果
     */
    @Override
    public int deleteOrdContractItemByIds(Long[] ids)
    {
        return ordContractItemMapper.deleteOrdContractItemByIds(ids);
    }

    /**
     * 删除合同明细信息
     * 
     * @param id 合同明细主键
     * @return 结果
     */
    @Override
    public int deleteOrdContractItemById(Long id)
    {
        return ordContractItemMapper.deleteOrdContractItemById(id);
    }

    /**
     * 请求临时料号
     *
     * @param ids 合同明细ID集合
     * @return 结果
     */
    @Override
    public int requestTempProduct(Long[] ids) throws Exception{
        return contractItemAlgorithm.requestTempProduct(ids);
    }
}
