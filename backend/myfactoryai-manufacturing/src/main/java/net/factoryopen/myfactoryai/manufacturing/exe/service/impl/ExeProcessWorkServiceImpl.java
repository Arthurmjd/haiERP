package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessWorkMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessWork;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessWorkService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工序出工Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
@Service
public class ExeProcessWorkServiceImpl implements IExeProcessWorkService 
{
    @Autowired(required = false)
    private ExeProcessWorkMapper exeProcessWorkMapper;

    @Autowired(required = false)
    private ExeProcessMovesMapper exeProcessMovesMapper;

    @Autowired(required = false)
    private PlnMlMapper mlMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工序出工
     * 
     * @param id 工序出工主键
     * @return 工序出工
     */
    @Override
    public ExeProcessWork selectExeProcessWorkById(Long id)
    {
        return exeProcessWorkMapper.selectExeProcessWorkById(id);
    }

    /**
     * 查询工序出工列表
     * 
     * @param exeProcessWork 工序出工
     * @return 工序出工
     */
    @Override
    public List<ExeProcessWork> selectExeProcessWorkList(ExeProcessWork exeProcessWork)
    {
        return exeProcessWorkMapper.selectExeProcessWorkList(exeProcessWork);
    }

    /**
     * 查询工序出工列表
     *
     * @param ml 制造批
     * @return 工序出工
     */
    @Override
    public List<ExeProcessWork> listByMl(PlnMl ml)
    {
        ml = mlMapper.selectPlnMlById(ml.getId());
        ExeProcessMoves move = new ExeProcessMoves();
        move.setMlId(ml.getId());
        move.setProcessSeq(ml.getCurrentProcessSeq());
        List<ExeProcessMoves> moves = exeProcessMovesMapper.selectExeProcessMovesList(move);

        Long moveId =0L;
        if (moves!=null && moves.size()>0) moveId = moves.get(moves.size()-1).getId();
        return exeProcessWorkMapper.selectExeProcessWorkListByMoveId(moveId);
    }

    /**
     * 新增工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    @Override
    public int insertExeProcessWork(ExeProcessWork exeProcessWork)
    {
        exeProcessWork.setCreateTime(DateUtils.getNowDate());
        return exeProcessWorkMapper.insertExeProcessWork(exeProcessWork);
    }

    /**
     * 修改工序出工
     * 
     * @param exeProcessWork 工序出工
     * @return 结果
     */
    @Override
    public int updateExeProcessWork(ExeProcessWork exeProcessWork)
    {
        exeProcessWork.setUpdateTime(DateUtils.getNowDate());
        return exeProcessWorkMapper.updateExeProcessWork(exeProcessWork);
    }

    /**
     * 批量删除工序出工
     * 
     * @param ids 需要删除的工序出工主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessWorkByIds(Long[] ids)
    {
        return exeProcessWorkMapper.deleteExeProcessWorkByIds(ids);
    }

    /**
     * 删除工序出工信息
     * 
     * @param id 工序出工主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessWorkById(Long id)
    {
        return exeProcessWorkMapper.deleteExeProcessWorkById(id);
    }


}
