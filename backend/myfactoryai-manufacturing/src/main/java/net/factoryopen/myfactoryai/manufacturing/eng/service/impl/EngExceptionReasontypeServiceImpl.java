package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngExceptionReasontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionReasontype;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionReasontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常原因Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EngExceptionReasontypeServiceImpl implements IEngExceptionReasontypeService 
{
    @Autowired(required = false)
    private EngExceptionReasontypeMapper engExceptionReasontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工艺异常原因
     * 
     * @param id 工艺异常原因主键
     * @return 工艺异常原因
     */
    @Override
    public EngExceptionReasontype selectEngExceptionReasontypeById(Long id)
    {
        return engExceptionReasontypeMapper.selectEngExceptionReasontypeById(id);
    }

    /**
     * 查询工艺异常原因列表
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 工艺异常原因
     */
    @Override
    public List<EngExceptionReasontype> selectEngExceptionReasontypeList(EngExceptionReasontype engExceptionReasontype)
    {
        return engExceptionReasontypeMapper.selectEngExceptionReasontypeList(engExceptionReasontype);
    }

    /**
     * 新增工艺异常原因
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 结果
     */
    @Override
    public int insertEngExceptionReasontype(EngExceptionReasontype engExceptionReasontype)
    {
        engExceptionReasontype.setCreateTime(DateUtils.getNowDate());
        return engExceptionReasontypeMapper.insertEngExceptionReasontype(engExceptionReasontype);
    }

    /**
     * 修改工艺异常原因
     * 
     * @param engExceptionReasontype 工艺异常原因
     * @return 结果
     */
    @Override
    public int updateEngExceptionReasontype(EngExceptionReasontype engExceptionReasontype)
    {
        engExceptionReasontype.setUpdateTime(DateUtils.getNowDate());
        return engExceptionReasontypeMapper.updateEngExceptionReasontype(engExceptionReasontype);
    }

    /**
     * 批量删除工艺异常原因
     * 
     * @param ids 需要删除的工艺异常原因主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionReasontypeByIds(Long[] ids)
    {
        return engExceptionReasontypeMapper.deleteEngExceptionReasontypeByIds(ids);
    }

    /**
     * 删除工艺异常原因信息
     * 
     * @param id 工艺异常原因主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionReasontypeById(Long id)
    {
        return engExceptionReasontypeMapper.deleteEngExceptionReasontypeById(id);
    }


}
