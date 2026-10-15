package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMl;

/**
 * 批次管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlService 
{
    /**
     * 查询批次管理
     * 
     * @param id 批次管理主键
     * @return 批次管理
     */
    public PlnMl selectPlnMlById(Long id);

    /**
     * 查询批次管理列表
     * 
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlDraftList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlPublishingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlProcessingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlFinishingList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlHistoryList(PlnMl plnMl);

    /**
     * 查询批次管理列表
     *
     * @param plnMl 批次管理
     * @return 批次管理集合
     */
    public List<PlnMl> selectPlnMlAllList(PlnMl plnMl);

    /**
     * 新增批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    public int insertPlnMl(PlnMl plnMl);

    /**
     * 修改批次管理
     * 
     * @param plnMl 批次管理
     * @return 结果
     */
    public int updatePlnMl(PlnMl plnMl);

    /**
     * 批量删除批次管理
     * 
     * @param ids 需要删除的批次管理主键集合
     * @return 结果
     */
    public int deletePlnMlByIds(Long[] ids);

    /**
     * 删除批次管理信息
     * 
     * @param id 批次管理主键
     * @return 结果
     */
    public int deletePlnMlById(Long id);

    /**
     * 变更
     *
     * @param plnMl 变更
     * @return 结果
     */
    public int changeMl(PlnMl plnMl) throws Exception;

    /**
     * 冻结
     *
     * @param plnMl 冻结
     * @return 结果
     */
    public int frozeMl(PlnMl plnMl);

    /**
     * 停用
     *
     * @param plnMl 停用
     * @return 结果
     */
    public int disableMl(PlnMl plnMl);


    /**
     * 变更
     *
     * @param plnMl 变更
     * @return 结果
     */
    public int change(PlnMl plnMl) throws Exception;

    /**
     * 冻结
     *
     * @param plnMl 冻结
     * @return 结果
     */
    public int froze(PlnMl plnMl);

    /**
     * 停用
     *
     * @param plnMl 停用
     * @return 结果
     */
    public int disable(PlnMl plnMl);

}
