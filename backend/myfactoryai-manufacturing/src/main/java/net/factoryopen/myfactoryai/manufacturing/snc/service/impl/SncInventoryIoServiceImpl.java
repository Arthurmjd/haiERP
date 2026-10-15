package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncInventoryIoMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventoryIo;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncInventoryIoService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 仓库存货流水Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
@Service
public class SncInventoryIoServiceImpl implements ISncInventoryIoService 
{
    @Autowired(required = false)
    private SncInventoryIoMapper sncInventoryIoMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询仓库存货流水
     * 
     * @param id 仓库存货流水主键
     * @return 仓库存货流水
     */
    @Override
    public SncInventoryIo selectSncInventoryIoById(Long id)
    {
        return sncInventoryIoMapper.selectSncInventoryIoById(id);
    }

    /**
     * 查询仓库存货流水列表
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 仓库存货流水
     */
    @Override
    public List<SncInventoryIo> selectSncInventoryIoList(SncInventoryIo sncInventoryIo)
    {
        return sncInventoryIoMapper.selectSncInventoryIoList(sncInventoryIo);
    }

    /**
     * 新增仓库存货流水
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 结果
     */
    @Override
    public int insertSncInventoryIo(SncInventoryIo sncInventoryIo)
    {
        sncInventoryIo.setCreateTime(DateUtils.getNowDate());
        return sncInventoryIoMapper.insertSncInventoryIo(sncInventoryIo);
    }

    /**
     * 修改仓库存货流水
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 结果
     */
    @Override
    public int updateSncInventoryIo(SncInventoryIo sncInventoryIo)
    {
        sncInventoryIo.setUpdateTime(DateUtils.getNowDate());
        return sncInventoryIoMapper.updateSncInventoryIo(sncInventoryIo);
    }

    /**
     * 批量删除仓库存货流水
     * 
     * @param ids 需要删除的仓库存货流水主键
     * @return 结果
     */
    @Override
    public int deleteSncInventoryIoByIds(Long[] ids)
    {
        return sncInventoryIoMapper.deleteSncInventoryIoByIds(ids);
    }

    /**
     * 删除仓库存货流水信息
     * 
     * @param id 仓库存货流水主键
     * @return 结果
     */
    @Override
    public int deleteSncInventoryIoById(Long id)
    {
        return sncInventoryIoMapper.deleteSncInventoryIoById(id);
    }


}
