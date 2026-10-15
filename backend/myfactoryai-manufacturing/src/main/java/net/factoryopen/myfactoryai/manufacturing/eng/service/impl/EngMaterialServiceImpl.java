package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 外购料管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialServiceImpl implements IEngMaterialService 
{
    @Autowired(required = false)
    private EngMaterialMapper engMaterialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询外购料管理
     * 
     * @param id 外购料管理主键
     * @return 外购料管理
     */
    @Override
    public EngMaterial selectEngMaterialById(Long id)
    {
        return engMaterialMapper.selectEngMaterialById(id);
    }

    /**
     * 查询前缀物料列表
     *
     * @param prefixCode 料名前缀
     * @return 外购料管理集合
     */
    @Override
    public List<EngMaterial> selectEngMaterialByPrefixCode(String prefixCode)
    {
        return engMaterialMapper.selectEngMaterialByPrefixCode(prefixCode);
    }

    /**
     * 查询外购料管理列表
     * 
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngProductList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngProductList(engMaterial);
    }

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngPureProductList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngPureProductList(engMaterial);
    }

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngMaterialList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngMaterialList(engMaterial);
    }

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngPureMaterialOrProductList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngPureMaterialOrProductList(engMaterial);
    }

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngUtilityList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngUtilityList(engMaterial);
    }

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理
     */
    @Override
    public List<EngMaterial> selectEngInventoryList(EngMaterial engMaterial)
    {
        return engMaterialMapper.selectEngInventoryList(engMaterial);
    }

    /**
     * 新增外购料管理
     * 
     * @param engMaterial 外购料管理
     * @return 结果
     */
    @Override
    public int insertEngMaterial(EngMaterial engMaterial)
    {
        //创建戳
        engMaterial.setCreateBy(SecurityUtils.getUsername());
        engMaterial.setCreateTime(DateUtils.getNowDate());
        engMaterial.setUpdateBy(SecurityUtils.getUsername());
        engMaterial.setUpdateTime(DateUtils.getNowDate());
        return engMaterialMapper.insertEngMaterial(engMaterial);
    }

    /**
     * 修改外购料管理
     * 
     * @param engMaterial 外购料管理
     * @return 结果
     */
    @Override
    public int updateEngMaterial(EngMaterial engMaterial)
    {
        engMaterial.setUpdateTime(DateUtils.getNowDate());
        return engMaterialMapper.updateEngMaterial(engMaterial);
    }

    /**
     * 批量删除外购料管理
     * 
     * @param ids 需要删除的外购料管理主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialByIds(Long[] ids)
    {
        return engMaterialMapper.deleteEngMaterialByIds(ids);
    }

    /**
     * 删除外购料管理信息
     * 
     * @param id 外购料管理主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialById(Long id)
    {
        return engMaterialMapper.deleteEngMaterialById(id);
    }


}
