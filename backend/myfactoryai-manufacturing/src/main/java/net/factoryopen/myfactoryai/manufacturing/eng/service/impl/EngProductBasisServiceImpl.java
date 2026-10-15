package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 产成品管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public class EngProductBasisServiceImpl implements IEngProductBasisService 
{
    @Autowired(required = false)
    private EngProductBasisMapper engProductBasisMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    /**
     * 查询产成品管理
     * 
     * @param id 产成品管理主键
     * @return 产成品管理
     */
    @Override
    public EngProductBasis selectEngProductBasisById(Long id)
    {
        return engProductBasisMapper.selectEngProductBasisById(id);
    }

    /**
     * 查询产成品管理
     *
     * @param materialCode 产品编码
     * @return 产成品管理
     */
    @Override
    public EngProductBasis selectEngProductBasisByCode(String materialCode)
    {
        return engProductBasisMapper.selectEngProductBasisByCode(materialCode);
    }

    /**
     * 查询产成品管理列表
     * 
     * @param engProductBasis 产成品管理
     * @return 产成品管理
     */
    @Override
    public List<EngProductBasis> selectEngProductBasisList(EngProductBasis engProductBasis)
    {
        return engProductBasisMapper.selectEngProductBasisList(engProductBasis);
    }

    /**
     * 新增产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    @Override
    public int insertEngProductBasis(EngProductBasis engProductBasis)
    {
        //创建戳
        engProductBasis.setCreateBy(SecurityUtils.getUsername());
        engProductBasis.setCreateTime(DateUtils.getNowDate());
        engProductBasis.setUpdateBy(SecurityUtils.getUsername());
        engProductBasis.setUpdateTime(DateUtils.getNowDate());
        return engProductBasisMapper.insertEngProductBasis(engProductBasis);
    }

    /**
     * 修改产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    @Override
    public int updateEngProductBasis(EngProductBasis engProductBasis)
    {
        engProductBasis.setUpdateTime(DateUtils.getNowDate());
        int x=engProductBasisMapper.updateEngProductBasis(engProductBasis);
        return x;
    }

    /**
     * 批量删除产成品管理
     * 
     * @param ids 需要删除的产成品管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductBasisByIds(Long[] ids)
    {
        return engProductBasisMapper.deleteEngProductBasisByIds(ids);
    }

    /**
     * 删除产成品管理信息
     * 
     * @param id 产成品管理主键
     * @return 结果
     */
    @Override
    public int deleteEngProductBasisById(Long id)
    {
        return engProductBasisMapper.deleteEngProductBasisById(id);
    }


}
