package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlStockin;

/**
 * 产成品入库Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IExeMlStockinService 
{
    /**
     * 查询产成品入库
     * 
     * @param id 产成品入库主键
     * @return 产成品入库
     */
    public ExeMlStockin selectExeMlStockinById(Long id);

    /**
     * 查询产成品入库列表
     * 
     * @param exeMlStockin 产成品入库
     * @return 产成品入库集合
     */
    public List<ExeMlStockin> selectExeMlStockinList(ExeMlStockin exeMlStockin);

    /**
     * 新增产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    public int insertExeMlStockin(ExeMlStockin exeMlStockin);

    /**
     * 修改产成品入库
     * 
     * @param exeMlStockin 产成品入库
     * @return 结果
     */
    public int updateExeMlStockin(ExeMlStockin exeMlStockin);

    /**
     * 批量删除产成品入库
     * 
     * @param ids 需要删除的产成品入库主键集合
     * @return 结果
     */
    public int deleteExeMlStockinByIds(Long[] ids);

    /**
     * 删除产成品入库信息
     * 
     * @param id 产成品入库主键
     * @return 结果
     */
    public int deleteExeMlStockinById(Long id);

    /**
     * 入库
     *
     * @param ids id集合
     * @return 结果
     */
    public int productStockin(Long[] ids) throws Exception;


}
