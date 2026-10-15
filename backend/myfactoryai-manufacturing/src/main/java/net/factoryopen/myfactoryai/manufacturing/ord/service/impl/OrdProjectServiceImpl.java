package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdProjectMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdProject;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdProjectService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 客户项目预配Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class OrdProjectServiceImpl implements IOrdProjectService 
{
    @Autowired(required = false)
    private OrdProjectMapper ordProjectMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;


    /**
     * 查询客户项目预配
     * 
     * @param id 客户项目预配主键
     * @return 客户项目预配
     */
    @Override
    public OrdProject selectOrdProjectById(Long id)
    {
        return ordProjectMapper.selectOrdProjectById(id);
    }

    /**
     * 查询客户项目预配列表
     * 
     * @param ordProject 客户项目预配
     * @return 客户项目预配
     */
    @Override
    public List<OrdProject> selectOrdProjectList(OrdProject ordProject)
    {
        return ordProjectMapper.selectOrdProjectList(ordProject);
    }

    /**
     * 新增客户项目预配
     * 
     * @param ordProject 客户项目预配
     * @return 结果
     */
    @Override
    public int insertOrdProject(OrdProject ordProject)
    {
        ordProject.setCreateTime(DateUtils.getNowDate());
        return ordProjectMapper.insertOrdProject(ordProject);
    }

    /**
     * 修改客户项目预配
     * 
     * @param ordProject 客户项目预配
     * @return 结果
     */
    @Override
    public int updateOrdProject(OrdProject ordProject)
    {
        ordProject.refreshUpdatingStamp();
        return ordProjectMapper.updateOrdProject(ordProject);
    }

    /**
     * 批量删除客户项目预配
     * 
     * @param ids 需要删除的客户项目预配主键
     * @return 结果
     */
    @Override
    public int deleteOrdProjectByIds(Long[] ids)
    {
        return ordProjectMapper.deleteOrdProjectByIds(ids);
    }

    /**
     * 删除客户项目预配信息
     * 
     * @param id 客户项目预配主键
     * @return 结果
     */
    @Override
    public int deleteOrdProjectById(Long id)
    {
        return ordProjectMapper.deleteOrdProjectById(id);
    }


}
