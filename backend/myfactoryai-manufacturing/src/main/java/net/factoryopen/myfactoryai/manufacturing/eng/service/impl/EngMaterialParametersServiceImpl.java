package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialParametersMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialParameters;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialParametersService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 物料参数Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngMaterialParametersServiceImpl implements IEngMaterialParametersService 
{
    @Autowired(required = false)
    private EngMaterialParametersMapper engMaterialParametersMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询物料参数
     * 
     * @param id 物料参数主键
     * @return 物料参数
     */
    @Override
    public EngMaterialParameters selectEngMaterialParametersById(Long id)
    {
        return engMaterialParametersMapper.selectEngMaterialParametersById(id);
    }

    /**
     * 查询物料参数列表
     * 
     * @param engMaterialParameters 物料参数
     * @return 物料参数
     */
    @Override
    public List<EngMaterialParameters> selectEngMaterialParametersList(EngMaterialParameters engMaterialParameters)
    {
        return engMaterialParametersMapper.selectEngMaterialParametersList(engMaterialParameters);
    }

    /**
     * 新增物料参数
     * 
     * @param engMaterialParameters 物料参数
     * @return 结果
     */
    @Override
    public int insertEngMaterialParameters(EngMaterialParameters engMaterialParameters)
    {
        //创建戳
        engMaterialParameters.setCreateBy(SecurityUtils.getUsername());
        engMaterialParameters.setCreateTime(DateUtils.getNowDate());
        engMaterialParameters.setUpdateBy(SecurityUtils.getUsername());
        engMaterialParameters.setUpdateTime(DateUtils.getNowDate());
        return engMaterialParametersMapper.insertEngMaterialParameters(engMaterialParameters);
    }

    /**
     * 修改物料参数
     * 
     * @param engMaterialParameters 物料参数
     * @return 结果
     */
    @Override
    public int updateEngMaterialParameters(EngMaterialParameters engMaterialParameters)
    {
        engMaterialParameters.setUpdateTime(DateUtils.getNowDate());
        return engMaterialParametersMapper.updateEngMaterialParameters(engMaterialParameters);
    }

    /**
     * 批量删除物料参数
     * 
     * @param ids 需要删除的物料参数主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialParametersByIds(Long[] ids)
    {
        return engMaterialParametersMapper.deleteEngMaterialParametersByIds(ids);
    }

    /**
     * 删除物料参数信息
     * 
     * @param id 物料参数主键
     * @return 结果
     */
    @Override
    public int deleteEngMaterialParametersById(Long id)
    {
        return engMaterialParametersMapper.deleteEngMaterialParametersById(id);
    }


}
