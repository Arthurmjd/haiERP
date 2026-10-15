package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.qua.mapper.QuaInspectRequestMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectRequest;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IQuaInspectRequestService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 质检请求通知Service业务层处理
 * 
 * @author admin
 * @date 2024-10-06
 */
@Service
public class QuaInspectRequestServiceImpl implements IQuaInspectRequestService 
{
    @Autowired(required = false)
    private QuaInspectRequestMapper quaInspectRequestMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询质检请求通知
     * 
     * @param id 质检请求通知主键
     * @return 质检请求通知
     */
    @Override
    public QuaInspectRequest selectQuaInspectRequestById(Long id)
    {
        return quaInspectRequestMapper.selectQuaInspectRequestById(id);
    }

    /**
     * 查询质检请求通知列表
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 质检请求通知
     */
    @Override
    public List<QuaInspectRequest> selectQuaInspectRequestList(QuaInspectRequest quaInspectRequest)
    {
        return quaInspectRequestMapper.selectQuaInspectRequestList(quaInspectRequest);
    }

    /**
     * 新增质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    @Override
    public int insertQuaInspectRequest(QuaInspectRequest quaInspectRequest)
    {
        quaInspectRequest.setCreateTime(DateUtils.getNowDate());
        return quaInspectRequestMapper.insertQuaInspectRequest(quaInspectRequest);
    }

    /**
     * 修改质检请求通知
     * 
     * @param quaInspectRequest 质检请求通知
     * @return 结果
     */
    @Override
    public int updateQuaInspectRequest(QuaInspectRequest quaInspectRequest)
    {
        quaInspectRequest.setUpdateTime(DateUtils.getNowDate());
        return quaInspectRequestMapper.updateQuaInspectRequest(quaInspectRequest);
    }

    /**
     * 批量删除质检请求通知
     * 
     * @param ids 需要删除的质检请求通知主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectRequestByIds(Long[] ids)
    {
        return quaInspectRequestMapper.deleteQuaInspectRequestByIds(ids);
    }

    /**
     * 删除质检请求通知信息
     * 
     * @param id 质检请求通知主键
     * @return 结果
     */
    @Override
    public int deleteQuaInspectRequestById(Long id)
    {
        return quaInspectRequestMapper.deleteQuaInspectRequestById(id);
    }


}
