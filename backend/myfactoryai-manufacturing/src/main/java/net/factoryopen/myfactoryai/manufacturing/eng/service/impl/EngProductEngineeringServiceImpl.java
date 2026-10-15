package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;

import net.factoryopen.core.myfactoryai.manufacturing.eng.EngProductEngineeringAlgorithm;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductEngineeringMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductEngineeringService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品工艺管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductEngineeringServiceImpl implements IEngProductEngineeringService 
{
    @Autowired(required = false)
    private EngProductEngineeringMapper engProductEngineeringMapper;

    @Autowired(required = false)
    private EngProductEngineeringAlgorithm engProductEngineeringAlgorithm;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品工艺管理
     * 
     * @param id 产品工艺管理主键
     * @return 产品工艺管理
     */
    @Override
    public EngProductEngineering selectEngProductEngineeringById(Long id)
    {
        return engProductEngineeringMapper.selectEngProductEngineeringById(id);
    }

    /**
     * 查询产品工艺管理列表
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 产品工艺管理
     */
    @Override
    public List<EngProductEngineering> selectEngProductEngineeringList(EngProductEngineering engProductEngineering)
    {
        return engProductEngineeringMapper.selectEngProductEngineeringList(engProductEngineering);
    }

    /**
     * 新增产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    @Override
    public int insertEngProductEngineering(EngProductEngineering engProductEngineering)
    {
        //创建戳
        engProductEngineering.setCreateBy(SecurityUtils.getUsername());
        engProductEngineering.setCreateTime(DateUtils.getNowDate());
        engProductEngineering.setUpdateBy(SecurityUtils.getUsername());
        engProductEngineering.setUpdateTime(DateUtils.getNowDate());
        return engProductEngineeringMapper.insertEngProductEngineering(engProductEngineering);
    }

    /**
     * 修改产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    @Override
    public int updateEngProductEngineering(EngProductEngineering engProductEngineering)
    {
        engProductEngineering.setUpdateTime(DateUtils.getNowDate());
        return engProductEngineeringMapper.updateEngProductEngineering(engProductEngineering);
    }

    /**
     * 批量删除产品工艺管理
     * 
     * @param ids 需要删除的产品工艺管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductEngineeringByIds(Long[] ids)
    {
        return engProductEngineeringMapper.deleteEngProductEngineeringByIds(ids);
    }

    /**
     * 删除产品工艺管理信息
     * 
     * @param id 产品工艺管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductEngineeringById(Long id)
    {
        return engProductEngineeringMapper.deleteEngProductEngineeringById(id);
    }

    /**
     * 继承工艺
     *
     * @param productId 产成品Id
     * @return 结果
     */
    public int inheritEngineering(Long productId) throws Exception{
        try{
            return engProductEngineeringAlgorithm.inheritEngineering(productId);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 生成采购料号
     *
     * @param bom 物料清单项
     * @return 结果
     */
    @Override
    public int generateCode(EngProductBom bom) throws Exception{
        try{
            return engProductEngineeringAlgorithm.generateCode(bom);
        }catch (Exception ex){
            throw ex;
        }
    }
}
