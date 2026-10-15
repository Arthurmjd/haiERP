package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesTailMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMovesTail;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeProcessMovesTailService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工序移转记尾情Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-15
 */
@Service
public class ExeProcessMovesTailServiceImpl implements IExeProcessMovesTailService 
{
    @Autowired(required = false)
    private ExeProcessMovesTailMapper exeProcessMovesTailMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工序移转记尾情
     * 
     * @param id 工序移转记尾情主键
     * @return 工序移转记尾情
     */
    @Override
    public ExeProcessMovesTail selectExeProcessMovesTailById(Long id)
    {
        return exeProcessMovesTailMapper.selectExeProcessMovesTailById(id);
    }

    /**
     * 查询工序移转记尾情列表
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 工序移转记尾情
     */
    @Override
    public List<ExeProcessMovesTail> selectExeProcessMovesTailList(ExeProcessMovesTail exeProcessMovesTail)
    {
        return exeProcessMovesTailMapper.selectExeProcessMovesTailList(exeProcessMovesTail);
    }

    /**
     * 新增工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    @Override
    public int insertExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail)
    {
        exeProcessMovesTail.setCreateTime(DateUtils.getNowDate());
        return exeProcessMovesTailMapper.insertExeProcessMovesTail(exeProcessMovesTail);
    }

    /**
     * 修改工序移转记尾情
     * 
     * @param exeProcessMovesTail 工序移转记尾情
     * @return 结果
     */
    @Override
    public int updateExeProcessMovesTail(ExeProcessMovesTail exeProcessMovesTail)
    {
        exeProcessMovesTail.setUpdateTime(DateUtils.getNowDate());
        return exeProcessMovesTailMapper.updateExeProcessMovesTail(exeProcessMovesTail);
    }

    /**
     * 批量删除工序移转记尾情
     * 
     * @param ids 需要删除的工序移转记尾情主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessMovesTailByIds(Long[] ids)
    {
        return exeProcessMovesTailMapper.deleteExeProcessMovesTailByIds(ids);
    }

    /**
     * 删除工序移转记尾情信息
     * 
     * @param id 工序移转记尾情主键
     * @return 结果
     */
    @Override
    public int deleteExeProcessMovesTailById(Long id)
    {
        return exeProcessMovesTailMapper.deleteExeProcessMovesTailById(id);
    }


}
