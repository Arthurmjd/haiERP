package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMoves;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlMovesService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 批次移转记录Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class PlnMlMovesServiceImpl implements IPlnMlMovesService 
{
    @Autowired(required = false)
    private PlnMlMovesMapper plnMlMovesMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询批次移转记录
     * 
     * @param id 批次移转记录主键
     * @return 批次移转记录
     */
    @Override
    public PlnMlMoves selectPlnMlMovesById(Long id)
    {
        return plnMlMovesMapper.selectPlnMlMovesById(id);
    }

    /**
     * 查询批次移转记录列表
     * 
     * @param plnMlMoves 批次移转记录
     * @return 批次移转记录
     */
    @Override
    public List<PlnMlMoves> selectPlnMlMovesList(PlnMlMoves plnMlMoves)
    {
        return plnMlMovesMapper.selectPlnMlMovesList(plnMlMoves);
    }

    /**
     * 新增批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    @Override
    public int insertPlnMlMoves(PlnMlMoves plnMlMoves)
    {
        plnMlMoves.setCreateTime(DateUtils.getNowDate());
        return plnMlMovesMapper.insertPlnMlMoves(plnMlMoves);
    }

    /**
     * 修改批次移转记录
     * 
     * @param plnMlMoves 批次移转记录
     * @return 结果
     */
    @Override
    public int updatePlnMlMoves(PlnMlMoves plnMlMoves)
    {
        plnMlMoves.setUpdateTime(DateUtils.getNowDate());
        return plnMlMovesMapper.updatePlnMlMoves(plnMlMoves);
    }

    /**
     * 批量删除批次移转记录
     * 
     * @param ids 需要删除的批次移转记录主键
     * @return 结果
     */
    @Override
    public int deletePlnMlMovesByIds(Long[] ids)
    {
        return plnMlMovesMapper.deletePlnMlMovesByIds(ids);
    }

    /**
     * 删除批次移转记录信息
     * 
     * @param id 批次移转记录主键
     * @return 结果
     */
    @Override
    public int deletePlnMlMovesById(Long id)
    {
        return plnMlMovesMapper.deletePlnMlMovesById(id);
    }


}
