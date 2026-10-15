package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;

/**
 * 工人管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface IResLaborService 
{
    /**
     * 查询工人管理
     * 
     * @param id 工人管理主键
     * @return 工人管理
     */
    public ResLabor selectResLaborById(Long id);

    /**
     * 查询工人管理列表
     * 
     * @param resLabor 工人管理
     * @return 工人管理集合
     */
    public List<ResLabor> selectResLaborList(ResLabor resLabor);

    /**
     * 新增工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    public int insertResLabor(ResLabor resLabor);

    /**
     * 修改工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    public int updateResLabor(ResLabor resLabor);

    /**
     * 批量删除工人管理
     * 
     * @param ids 需要删除的工人管理主键集合
     * @return 结果
     */
    public int deleteResLaborByIds(Long[] ids);

    /**
     * 删除工人管理信息
     * 
     * @param id 工人管理主键
     * @return 结果
     */
    public int deleteResLaborById(Long id);


}
