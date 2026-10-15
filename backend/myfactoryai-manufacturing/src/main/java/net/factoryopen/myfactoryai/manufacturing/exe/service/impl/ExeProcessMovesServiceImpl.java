package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessMovesService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工序移转记录Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class ExeProcessMovesServiceImpl implements IExeProcessMovesService 
{
    @Autowired(required = false)
    private ExeProcessMovesMapper exeProcessMovesMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工序移转记录
     * 
     * @param id 工序移转记录主键
     * @return 工序移转记录
     */
    @Override
    public ExeProcessMoves selectExeProcessMovesById(Long id)
    {
        return exeProcessMovesMapper.selectExeProcessMovesById(id);
    }

    /**
     * 查询工序移转记录列表
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 工序移转记录
     */
    @Override
    public List<ExeProcessMoves> selectExeProcessMovesList(ExeProcessMoves exeProcessMoves)
    {
        return exeProcessMovesMapper.selectExeProcessMovesList(exeProcessMoves);
    }

    /**
     * 新增工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    @Override
    public int insertExeProcessMoves(ExeProcessMoves exeProcessMoves)
    {
        exeProcessMoves.setCreateTime(DateUtils.getNowDate());
        return exeProcessMovesMapper.insertExeProcessMoves(exeProcessMoves);
    }

    /**
     * 修改工序移转记录
     * 
     * @param exeProcessMoves 工序移转记录
     * @return 结果
     */
    @Override
    public int updateExeProcessMoves(ExeProcessMoves exeProcessMoves)
    {
        exeProcessMoves.setUpdateTime(DateUtils.getNowDate());
        return exeProcessMovesMapper.updateExeProcessMoves(exeProcessMoves);
    }

    /**
     * 批量删除工序移转记录
     * 
     * @param ids 需要删除的工序移转记录主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessMovesByIds(Long[] ids)
    {
        return exeProcessMovesMapper.deleteExeProcessMovesByIds(ids);
    }

    /**
     * 删除工序移转记录信息
     * 
     * @param id 工序移转记录主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessMovesById(Long id)
    {
        return exeProcessMovesMapper.deleteExeProcessMovesById(id);
    }


}
