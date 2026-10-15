package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWareHouseMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWareHouse;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWareHouseService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 仓库管理Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
@Service
public class ResWareHouseServiceImpl implements IResWareHouseService 
{
    @Autowired(required = false)
    private ResWareHouseMapper resWareHouseMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public ResWareHouse selectResWareHouseById(Long id)
    {
        return resWareHouseMapper.selectResWareHouseById(id);
    }

    /**
     * 查询仓库管理列表
     * 
     * @param resWareHouse 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<ResWareHouse> selectResWareHouseList(ResWareHouse resWareHouse)
    {
        return resWareHouseMapper.selectResWareHouseList(resWareHouse);
    }

    /**
     * 新增仓库管理
     * 
     * @param resWareHouse 仓库管理
     * @return 结果
     */
    @Override
    public int insertResWareHouse(ResWareHouse resWareHouse)
    {
        resWareHouse.setCreateTime(DateUtils.getNowDate());
        return resWareHouseMapper.insertResWareHouse(resWareHouse);
    }

    /**
     * 修改仓库管理
     * 
     * @param resWareHouse 仓库管理
     * @return 结果
     */
    @Override
    public int updateResWareHouse(ResWareHouse resWareHouse)
    {
        resWareHouse.setUpdateTime(DateUtils.getNowDate());
        return resWareHouseMapper.updateResWareHouse(resWareHouse);
    }

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteResWareHouseByIds(Long[] ids)
    {
        return resWareHouseMapper.deleteResWareHouseByIds(ids);
    }

    /**
     * 删除仓库管理信息
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteResWareHouseById(Long id)
    {
        return resWareHouseMapper.deleteResWareHouseById(id);
    }


}
