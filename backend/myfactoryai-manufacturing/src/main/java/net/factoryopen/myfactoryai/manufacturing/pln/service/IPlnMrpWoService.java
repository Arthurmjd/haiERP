package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;

/**
 * MRP工单项Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IPlnMrpWoService 
{
    /**
     * 查询MRP工单项
     * 
     * @param id MRP工单项主键
     * @return MRP工单项
     */
    public PlnMrpWo selectPlnMrpWoById(Long id);

    /**
     * 查询MRP工单项列表
     * 
     * @param plnMrpWo MRP工单项
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoList(PlnMrpWo plnMrpWo);

    /**
     * 新增MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    public int insertPlnMrpWo(PlnMrpWo plnMrpWo);

    /**
     * 修改MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    public int updatePlnMrpWo(PlnMrpWo plnMrpWo);

    /**
     * 批量删除MRP工单项
     * 
     * @param ids 需要删除的MRP工单项主键集合
     * @return 结果
     */
    public int deletePlnMrpWoByIds(Long[] ids);

    /**
     * 删除MRP工单项信息
     * 
     * @param id MRP工单项主键
     * @return 结果
     */
    public int deletePlnMrpWoById(Long id);


    /**
     * 转工单
     *
     * @param ids id集合
     * @return 结果
     */
    public int toWo(Long[] ids) throws Exception;

}
