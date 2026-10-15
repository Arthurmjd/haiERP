package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngCategoryBomMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryBom;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngCategoryBomService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 品类BOMService业务层处理
 * 
 * @author admin
 * @date 2024-11-09
 */
@Service
public class EngCategoryBomServiceImpl implements IEngCategoryBomService 
{
    @Autowired(required = false)
    private EngCategoryBomMapper engCategoryBomMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询品类BOM
     * 
     * @param id 品类BOM主键
     * @return 品类BOM
     */
    @Override
    public EngCategoryBom selectEngCategoryBomById(Long id)
    {
        return engCategoryBomMapper.selectEngCategoryBomById(id);
    }

    /**
     * 查询品类BOM列表
     * 
     * @param engCategoryBom 品类BOM
     * @return 品类BOM
     */
    @Override
    public List<EngCategoryBom> selectEngCategoryBomList(EngCategoryBom engCategoryBom)
    {
        return engCategoryBomMapper.selectEngCategoryBomList(engCategoryBom);
    }

    /**
     * 新增品类BOM
     * 
     * @param engCategoryBom 品类BOM
     * @return 结果
     */
    @Override
    public int insertEngCategoryBom(EngCategoryBom engCategoryBom)
    {
        engCategoryBom.setCreateTime(DateUtils.getNowDate());
        return engCategoryBomMapper.insertEngCategoryBom(engCategoryBom);
    }

    /**
     * 修改品类BOM
     * 
     * @param engCategoryBom 品类BOM
     * @return 结果
     */
    @Override
    public int updateEngCategoryBom(EngCategoryBom engCategoryBom)
    {
        engCategoryBom.setUpdateTime(DateUtils.getNowDate());
        return engCategoryBomMapper.updateEngCategoryBom(engCategoryBom);
    }

    /**
     * 批量删除品类BOM
     * 
     * @param ids 需要删除的品类BOM主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryBomByIds(Long[] ids)
    {
        return engCategoryBomMapper.deleteEngCategoryBomByIds(ids);
    }

    /**
     * 删除品类BOM信息
     * 
     * @param id 品类BOM主键
     * @return 结果
     */
    @Override
    public int deleteEngCategoryBomById(Long id)
    {
        return engCategoryBomMapper.deleteEngCategoryBomById(id);
    }


}
