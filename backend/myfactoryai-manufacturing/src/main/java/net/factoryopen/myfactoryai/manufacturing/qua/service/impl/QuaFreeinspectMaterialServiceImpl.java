package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaFreeinspectMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectMaterial;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaFreeinspectMaterialService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 免检物料Service业务层处理
 * 
 * @author admin
 * @date 2024-11-12
 */
@Service
public class QuaFreeinspectMaterialServiceImpl implements IQuaFreeinspectMaterialService 
{
    @Autowired(required = false)
    private QuaFreeinspectMaterialMapper quaFreeinspectMaterialMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询免检物料
     * 
     * @param id 免检物料主键
     * @return 免检物料
     */
    @Override
    public QuaFreeinspectMaterial selectQuaFreeinspectMaterialById(Long id)
    {
        return quaFreeinspectMaterialMapper.selectQuaFreeinspectMaterialById(id);
    }

    /**
     * 查询免检物料列表
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 免检物料
     */
    @Override
    public List<QuaFreeinspectMaterial> selectQuaFreeinspectMaterialList(QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        return quaFreeinspectMaterialMapper.selectQuaFreeinspectMaterialList(quaFreeinspectMaterial);
    }

    /**
     * 新增免检物料
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 结果
     */
    @Override
    public int insertQuaFreeinspectMaterial(QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        quaFreeinspectMaterial.setCreateTime(DateUtils.getNowDate());
        return quaFreeinspectMaterialMapper.insertQuaFreeinspectMaterial(quaFreeinspectMaterial);
    }

    /**
     * 修改免检物料
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 结果
     */
    @Override
    public int updateQuaFreeinspectMaterial(QuaFreeinspectMaterial quaFreeinspectMaterial)
    {
        quaFreeinspectMaterial.setUpdateTime(DateUtils.getNowDate());
        return quaFreeinspectMaterialMapper.updateQuaFreeinspectMaterial(quaFreeinspectMaterial);
    }

    /**
     * 批量删除免检物料
     * 
     * @param ids 需要删除的免检物料主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectMaterialByIds(Long[] ids)
    {
        return quaFreeinspectMaterialMapper.deleteQuaFreeinspectMaterialByIds(ids);
    }

    /**
     * 删除免检物料信息
     * 
     * @param id 免检物料主键
     * @return 结果
     */
    @Override
    public int deleteQuaFreeinspectMaterialById(Long id)
    {
        return quaFreeinspectMaterialMapper.deleteQuaFreeinspectMaterialById(id);
    }


}
