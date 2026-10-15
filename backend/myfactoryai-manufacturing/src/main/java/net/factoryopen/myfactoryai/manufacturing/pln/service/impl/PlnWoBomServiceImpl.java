package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnWoBomMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoBom;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnWoBomService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工单BOMService业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
@Service
public class PlnWoBomServiceImpl implements IPlnWoBomService 
{
    @Autowired(required = false)
    private PlnWoBomMapper plnWoBomMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工单BOM
     * 
     * @param id 工单BOM主键
     * @return 工单BOM
     */
    @Override
    public PlnWoBom selectPlnWoBomById(Long id)
    {
        return plnWoBomMapper.selectPlnWoBomById(id);
    }

    /**
     * 查询工单BOM列表
     * 
     * @param plnWoBom 工单BOM
     * @return 工单BOM
     */
    @Override
    public List<PlnWoBom> selectPlnWoBomList(PlnWoBom plnWoBom)
    {
        return plnWoBomMapper.selectPlnWoBomList(plnWoBom);
    }

    /**
     * 新增工单BOM
     * 
     * @param plnWoBom 工单BOM
     * @return 结果
     */
    @Override
    public int insertPlnWoBom(PlnWoBom plnWoBom)
    {
        plnWoBom.setCreateTime(DateUtils.getNowDate());
        return plnWoBomMapper.insertPlnWoBom(plnWoBom);
    }

    /**
     * 修改工单BOM
     * 
     * @param plnWoBom 工单BOM
     * @return 结果
     */
    @Override
    public int updatePlnWoBom(PlnWoBom plnWoBom)
    {
        plnWoBom.refreshUpdatingStamp();
        return plnWoBomMapper.updatePlnWoBom(plnWoBom);
    }

    /**
     * 批量删除工单BOM
     * 
     * @param ids 需要删除的工单BOM主键
     * @return 结果
     */
    @Override
    public int deletePlnWoBomByIds(Long[] ids)
    {
        return plnWoBomMapper.deletePlnWoBomByIds(ids);
    }

    /**
     * 删除工单BOM信息
     * 
     * @param id 工单BOM主键
     * @return 结果
     */
    @Override
    public int deletePlnWoBomById(Long id)
    {
        return plnWoBomMapper.deletePlnWoBomById(id);
    }


}
