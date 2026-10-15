package net.factoryopen.myfactoryai.manufacturing.res.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWarePositionMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWarePosition;
import net.factoryopen.myfactoryai.manufacturing.res.service.IResWarePositionService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 储位Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
@Service
public class ResWarePositionServiceImpl implements IResWarePositionService 
{
    @Autowired(required = false)
    private ResWarePositionMapper resWarePositionMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询储位
     * 
     * @param id 储位主键
     * @return 储位
     */
    @Override
    public ResWarePosition selectResWarePositionById(Long id)
    {
        return resWarePositionMapper.selectResWarePositionById(id);
    }

    /**
     * 查询储位列表
     * 
     * @param resWarePosition 储位
     * @return 储位
     */
    @Override
    public List<ResWarePosition> selectResWarePositionList(ResWarePosition resWarePosition)
    {
        return resWarePositionMapper.selectResWarePositionList(resWarePosition);
    }

    /**
     * 新增储位
     * 
     * @param resWarePosition 储位
     * @return 结果
     */
    @Override
    public int insertResWarePosition(ResWarePosition resWarePosition)
    {
        resWarePosition.setCreateTime(DateUtils.getNowDate());
        return resWarePositionMapper.insertResWarePosition(resWarePosition);
    }

    /**
     * 修改储位
     * 
     * @param resWarePosition 储位
     * @return 结果
     */
    @Override
    public int updateResWarePosition(ResWarePosition resWarePosition)
    {
        resWarePosition.setUpdateTime(DateUtils.getNowDate());
        return resWarePositionMapper.updateResWarePosition(resWarePosition);
    }

    /**
     * 批量删除储位
     * 
     * @param ids 需要删除的储位主键
     * @return 结果
     */
    @Override
    public int deleteResWarePositionByIds(Long[] ids)
    {
        return resWarePositionMapper.deleteResWarePositionByIds(ids);
    }

    /**
     * 删除储位信息
     * 
     * @param id 储位主键
     * @return 结果
     */
    @Override
    public int deleteResWarePositionById(Long id)
    {
        return resWarePositionMapper.deleteResWarePositionById(id);
    }


}
