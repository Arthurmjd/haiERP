package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngExceptionSolutiontypeMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngExceptionSolutiontype;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngExceptionSolutiontypeService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺异常处理类型Service业务层处理
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public class EngExceptionSolutiontypeServiceImpl implements IEngExceptionSolutiontypeService 
{
    @Autowired(required = false)
    private EngExceptionSolutiontypeMapper engExceptionSolutiontypeMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询工艺异常处理类型
     * 
     * @param id 工艺异常处理类型主键
     * @return 工艺异常处理类型
     */
    @Override
    public EngExceptionSolutiontype selectEngExceptionSolutiontypeById(Long id)
    {
        return engExceptionSolutiontypeMapper.selectEngExceptionSolutiontypeById(id);
    }

    /**
     * 查询工艺异常处理类型列表
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 工艺异常处理类型
     */
    @Override
    public List<EngExceptionSolutiontype> selectEngExceptionSolutiontypeList(EngExceptionSolutiontype engExceptionSolutiontype)
    {
        return engExceptionSolutiontypeMapper.selectEngExceptionSolutiontypeList(engExceptionSolutiontype);
    }

    /**
     * 新增工艺异常处理类型
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 结果
     */
    @Override
    public int insertEngExceptionSolutiontype(EngExceptionSolutiontype engExceptionSolutiontype)
    {
        engExceptionSolutiontype.setCreateTime(DateUtils.getNowDate());
        return engExceptionSolutiontypeMapper.insertEngExceptionSolutiontype(engExceptionSolutiontype);
    }

    /**
     * 修改工艺异常处理类型
     * 
     * @param engExceptionSolutiontype 工艺异常处理类型
     * @return 结果
     */
    @Override
    public int updateEngExceptionSolutiontype(EngExceptionSolutiontype engExceptionSolutiontype)
    {
        engExceptionSolutiontype.setUpdateTime(DateUtils.getNowDate());
        return engExceptionSolutiontypeMapper.updateEngExceptionSolutiontype(engExceptionSolutiontype);
    }

    /**
     * 批量删除工艺异常处理类型
     * 
     * @param ids 需要删除的工艺异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionSolutiontypeByIds(Long[] ids)
    {
        return engExceptionSolutiontypeMapper.deleteEngExceptionSolutiontypeByIds(ids);
    }

    /**
     * 删除工艺异常处理类型信息
     * 
     * @param id 工艺异常处理类型主键
     * @return 结果
     */
    @Override
    public int deleteEngExceptionSolutiontypeById(Long id)
    {
        return engExceptionSolutiontypeMapper.deleteEngExceptionSolutiontypeById(id);
    }


}
