package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBomService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产品BOMService业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class EngProductBomServiceImpl implements IEngProductBomService 
{
    @Autowired(required = false)
    private EngProductBomMapper engProductBomMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询产品BOM
     * 
     * @param id 产品BOM主键
     * @return 产品BOM
     */
    @Override
    public EngProductBom selectEngProductBomById(Long id)
    {
        return engProductBomMapper.selectEngProductBomById(id);
    }

    /**
     * 查询产品BOM列表
     * 
     * @param engProductBom 产品BOM
     * @return 产品BOM
     */
    @Override
    public List<EngProductBom> selectEngProductBomList(EngProductBom engProductBom)
    {
        return engProductBomMapper.selectEngProductBomList(engProductBom);
    }

    /**
     * 新增产品BOM
     * 
     * @param engProductBom 产品BOM
     * @return 结果
     */
    @Override
    public int insertEngProductBom(EngProductBom engProductBom)
    {
        //创建戳
        engProductBom.setCreateBy(SecurityUtils.getUsername());
        engProductBom.setCreateTime(DateUtils.getNowDate());
        engProductBom.setUpdateBy(SecurityUtils.getUsername());
        engProductBom.setUpdateTime(DateUtils.getNowDate());
        return engProductBomMapper.insertEngProductBom(engProductBom);
    }

    /**
     * 修改产品BOM
     * 
     * @param engProductBom 产品BOM
     * @return 结果
     */
    @Override
    public int updateEngProductBom(EngProductBom engProductBom)
    {
        engProductBom.setUpdateTime(DateUtils.getNowDate());
        return engProductBomMapper.updateEngProductBom(engProductBom);
    }

    /**
     * 批量删除产品BOM
     * 
     * @param ids 需要删除的产品BOM主键
     * @return 结果
     */
    @Override
    public int deleteEngProductBomByIds(Long[] ids)
    {
        return engProductBomMapper.deleteEngProductBomByIds(ids);
    }

    /**
     * 删除产品BOM信息
     * 
     * @param id 产品BOM主键
     * @return 结果
     */
    @Override
    public int deleteEngProductBomById(Long id)
    {
        return engProductBomMapper.deleteEngProductBomById(id);
    }


}
