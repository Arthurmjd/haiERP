package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMaterial;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlMaterialService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次备料单Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlMaterialServiceImpl implements IPlnMlMaterialService 
{
    @Autowired(required = false)
    private PlnMlMaterialMapper plnMlMaterialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次备料单
     * 
     * @param id 批次备料单主键
     * @return 批次备料单
     */
    @Override
    public PlnMlMaterial selectPlnMlMaterialById(Long id)
    {
        return plnMlMaterialMapper.selectPlnMlMaterialById(id);
    }

    /**
     * 查询批次备料单列表
     * 
     * @param plnMlMaterial 批次备料单
     * @return 批次备料单
     */
    @Override
    public List<PlnMlMaterial> selectPlnMlMaterialList(PlnMlMaterial plnMlMaterial)
    {
        return plnMlMaterialMapper.selectPlnMlMaterialList(plnMlMaterial);
    }

    /**
     * 新增批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    @Override
    public int insertPlnMlMaterial(PlnMlMaterial plnMlMaterial)
    {
        plnMlMaterial.setCreateTime(DateUtils.getNowDate());
        return plnMlMaterialMapper.insertPlnMlMaterial(plnMlMaterial);
    }

    /**
     * 修改批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    @Override
    public int updatePlnMlMaterial(PlnMlMaterial plnMlMaterial)
    {
        plnMlMaterial.refreshUpdatingStamp();
        return plnMlMaterialMapper.updatePlnMlMaterial(plnMlMaterial);
    }

    /**
     * 批量删除批次备料单
     * 
     * @param ids 需要删除的批次备料单主键
     * @return 结果
     */
    @Override
    public int deletePlnMlMaterialByIds(Long[] ids)
    {
        return plnMlMaterialMapper.deletePlnMlMaterialByIds(ids);
    }

    /**
     * 删除批次备料单信息
     * 
     * @param id 批次备料单主键
     * @return 结果
     */
    @Override
    public int deletePlnMlMaterialById(Long id)
    {
        return plnMlMaterialMapper.deletePlnMlMaterialById(id);
    }


}
