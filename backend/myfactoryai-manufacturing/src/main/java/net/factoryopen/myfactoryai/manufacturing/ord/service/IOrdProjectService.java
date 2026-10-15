package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdProject;

/**
 * 客户项目预配Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdProjectService 
{
    /**
     * 查询客户项目预配
     * 
     * @param id 客户项目预配主键
     * @return 客户项目预配
     */
    public OrdProject selectOrdProjectById(Long id);

    /**
     * 查询客户项目预配列表
     * 
     * @param ordProject 客户项目预配
     * @return 客户项目预配集合
     */
    public List<OrdProject> selectOrdProjectList(OrdProject ordProject);

    /**
     * 新增客户项目预配
     * 
     * @param ordProject 客户项目预配
     * @return 结果
     */
    public int insertOrdProject(OrdProject ordProject);

    /**
     * 修改客户项目预配
     * 
     * @param ordProject 客户项目预配
     * @return 结果
     */
    public int updateOrdProject(OrdProject ordProject);

    /**
     * 批量删除客户项目预配
     * 
     * @param ids 需要删除的客户项目预配主键集合
     * @return 结果
     */
    public int deleteOrdProjectByIds(Long[] ids);

    /**
     * 删除客户项目预配信息
     * 
     * @param id 客户项目预配主键
     * @return 结果
     */
    public int deleteOrdProjectById(Long id);


}
