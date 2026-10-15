package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlCreated;

/**
 * 新派批次Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IPlnMlCreatedService 
{
    /**
     * 查询新派批次
     * 
     * @param id 新派批次主键
     * @return 新派批次
     */
    public PlnMlCreated selectPlnMlCreatedById(Long id);

    /**
     * 查询新派批次列表
     * 
     * @param plnMlCreated 新派批次
     * @return 新派批次集合
     */
    public List<PlnMlCreated> selectPlnMlCreatedList(PlnMlCreated plnMlCreated);

    /**
     * 新增新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    public int insertPlnMlCreated(PlnMlCreated plnMlCreated);

    /**
     * 修改新派批次
     * 
     * @param plnMlCreated 新派批次
     * @return 结果
     */
    public int updatePlnMlCreated(PlnMlCreated plnMlCreated);

    /**
     * 批量删除新派批次
     * 
     * @param ids 需要删除的新派批次主键集合
     * @return 结果
     */
    public int deletePlnMlCreatedByIds(Long[] ids);

    /**
     * 删除新派批次信息
     * 
     * @param id 新派批次主键
     * @return 结果
     */
    public int deletePlnMlCreatedById(Long id);


}
