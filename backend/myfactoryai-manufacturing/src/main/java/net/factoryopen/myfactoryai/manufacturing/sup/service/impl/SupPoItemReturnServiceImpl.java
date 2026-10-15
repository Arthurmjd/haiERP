package net.factoryopen.myfactoryai.manufacturing.sup.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.sup.mapper.SupPoItemReturnMapper;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;
import net.factoryopen.myfactoryai.manufacturing.sup.service.ISupPoItemReturnService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 退货项跟踪Service业务层处理
 * 
 * @author admin
 * @date 2024-10-11
 */
@Service
public class SupPoItemReturnServiceImpl implements ISupPoItemReturnService 
{
    @Autowired(required = false)
    private SupPoItemReturnMapper supPoItemReturnMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询退货项跟踪
     * 
     * @param id 退货项跟踪主键
     * @return 退货项跟踪
     */
    @Override
    public SupPoItemReturn selectSupPoItemReturnById(Long id)
    {
        return supPoItemReturnMapper.selectSupPoItemReturnById(id);
    }

    /**
     * 查询退货项跟踪列表
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 退货项跟踪
     */
    @Override
    public List<SupPoItemReturn> selectSupPoItemReturnList(SupPoItemReturn supPoItemReturn)
    {
        return supPoItemReturnMapper.selectSupPoItemReturnList(supPoItemReturn);
    }

    /**
     * 新增退货项跟踪
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 结果
     */
    @Override
    public int insertSupPoItemReturn(SupPoItemReturn supPoItemReturn)
    {
        supPoItemReturn.setCreateTime(DateUtils.getNowDate());
        return supPoItemReturnMapper.insertSupPoItemReturn(supPoItemReturn);
    }

    /**
     * 修改退货项跟踪
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 结果
     */
    @Override
    public int updateSupPoItemReturn(SupPoItemReturn supPoItemReturn)
    {
        supPoItemReturn.setUpdateTime(DateUtils.getNowDate());
        return supPoItemReturnMapper.updateSupPoItemReturn(supPoItemReturn);
    }

    /**
     * 批量删除退货项跟踪
     * 
     * @param ids 需要删除的退货项跟踪主键
     * @return 结果
     */
    @Override
    public int deleteSupPoItemReturnByIds(Long[] ids)
    {
        return supPoItemReturnMapper.deleteSupPoItemReturnByIds(ids);
    }

    /**
     * 删除退货项跟踪信息
     * 
     * @param id 退货项跟踪主键
     * @return 结果
     */
    @Override
    public int deleteSupPoItemReturnById(Long id)
    {
        return supPoItemReturnMapper.deleteSupPoItemReturnById(id);
    }


}
