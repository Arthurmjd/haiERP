package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlMovesService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 历史移转查询Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
@Service
public class ExeMlMovesServiceImpl implements IExeMlMovesService 
{
    @Autowired(required = false)
    private ExeMlMovesMapper exeMlMovesMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询历史移转查询
     * 
     * @param id 历史移转查询主键
     * @return 历史移转查询
     */
    @Override
    public ExeMlMoves selectExeMlMovesById(Long id)
    {
        return exeMlMovesMapper.selectExeMlMovesById(id);
    }

    /**
     * 查询历史移转查询列表
     * 
     * @param exeMlMoves 历史移转查询
     * @return 历史移转查询
     */
    @Override
    public List<ExeMlMoves> selectExeMlMovesList(ExeMlMoves exeMlMoves)
    {
        return exeMlMovesMapper.selectExeMlMovesList(exeMlMoves);
    }

    /**
     * 新增历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    @Override
    public int insertExeMlMoves(ExeMlMoves exeMlMoves)
    {
        exeMlMoves.setCreateTime(DateUtils.getNowDate());
        return exeMlMovesMapper.insertExeMlMoves(exeMlMoves);
    }

    /**
     * 修改历史移转查询
     * 
     * @param exeMlMoves 历史移转查询
     * @return 结果
     */
    @Override
    public int updateExeMlMoves(ExeMlMoves exeMlMoves)
    {
        exeMlMoves.setUpdateTime(DateUtils.getNowDate());
        return exeMlMovesMapper.updateExeMlMoves(exeMlMoves);
    }

    /**
     * 批量删除历史移转查询
     * 
     * @param ids 需要删除的历史移转查询主键
     * @return 结果
     */
    @Override
    public int deleteExeMlMovesByIds(Long[] ids)
    {
        return exeMlMovesMapper.deleteExeMlMovesByIds(ids);
    }

    /**
     * 删除历史移转查询信息
     * 
     * @param id 历史移转查询主键
     * @return 结果
     */
    @Override
    public int deleteExeMlMovesById(Long id)
    {
        return exeMlMovesMapper.deleteExeMlMovesById(id);
    }


}
