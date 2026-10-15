package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoMaterial;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoMaterialService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单发料Service业务层处理
 * 
 * @author admin
 * @date 2024-07-12
 */
@Service
public class PlnWoMaterialServiceImpl implements IPlnWoMaterialService 
{
    @Autowired(required = false)
    private PlnWoMaterialMapper plnWoMaterialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单发料
     * 
     * @param id 工单发料主键
     * @return 工单发料
     */
    @Override
    public PlnWoMaterial selectPlnWoMaterialById(Long id)
    {
        return plnWoMaterialMapper.selectPlnWoMaterialById(id);
    }

    /**
     * 查询工单发料列表
     * 
     * @param plnWoMaterial 工单发料
     * @return 工单发料
     */
    @Override
    public List<PlnWoMaterial> selectPlnWoMaterialList(PlnWoMaterial plnWoMaterial)
    {
        return plnWoMaterialMapper.selectPlnWoMaterialList(plnWoMaterial);
    }

    /**
     * 新增工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    @Override
    public int insertPlnWoMaterial(PlnWoMaterial plnWoMaterial)
    {
        plnWoMaterial.setCreateTime(DateUtils.getNowDate());
        return plnWoMaterialMapper.insertPlnWoMaterial(plnWoMaterial);
    }

    /**
     * 修改工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    @Override
    public int updatePlnWoMaterial(PlnWoMaterial plnWoMaterial)
    {
        plnWoMaterial.setUpdateTime(DateUtils.getNowDate());
        return plnWoMaterialMapper.updatePlnWoMaterial(plnWoMaterial);
    }

    /**
     * 批量删除工单发料
     * 
     * @param ids 需要删除的工单发料主键
     * @return 结果
     */
    @Override
    public int deletePlnWoMaterialByIds(Long[] ids)
    {
        return plnWoMaterialMapper.deletePlnWoMaterialByIds(ids);
    }

    /**
     * 删除工单发料信息
     * 
     * @param id 工单发料主键
     * @return 结果
     */
    @Override
    public int deletePlnWoMaterialById(Long id)
    {
        return plnWoMaterialMapper.deletePlnWoMaterialById(id);
    }


}
