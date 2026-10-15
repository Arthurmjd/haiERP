package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;

/**
 * 工单管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
public interface IPlnWoService 
{
    /**
     * 查询工单管理
     * 
     * @param id 工单管理主键
     * @return 工单管理
     */
    public PlnWo selectPlnWoById(Long id);

    /**
     * 查询工单管理列表
     * 
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoDraftList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoPublishingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoProcessingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoFinishingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoHistoryList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoAllList(PlnWo plnWo);

    /**
     * 新增工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    public int insertPlnWo(PlnWo plnWo);

    /**
     * 修改工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    public int updatePlnWo(PlnWo plnWo);

    /**
     * 批量删除工单管理
     * 
     * @param ids 需要删除的工单管理主键集合
     * @return 结果
     */
    public int deletePlnWoByIds(Long[] ids);

    /**
     * 删除工单管理信息
     * 
     * @param id 工单管理主键
     * @return 结果
     */
    public int deletePlnWoById(Long id);

    /**
     * 发放
     *
     * @param plnWo 发放
     * @return 结果
     */
    public int enable(PlnWo plnWo);

    /**
     * 变更
     *
     * @param plnWo 变更
     * @return 结果
     */
    public int changeWo(PlnWo plnWo) throws Exception;

    /**
     * 冻结
     *
     * @param plnWo 冻结
     * @return 结果
     */
    public int frozeWo(PlnWo plnWo);

    /**
     * 停用
     *
     * @param plnWo 停用
     * @return 结果
     */
    public int disableWo(PlnWo plnWo);


}
