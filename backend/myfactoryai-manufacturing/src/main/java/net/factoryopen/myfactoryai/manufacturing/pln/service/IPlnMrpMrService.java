package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;

/**
 * MRP物料需求Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IPlnMrpMrService 
{
    /**
     * 查询MRP物料需求
     * 
     * @param id MRP物料需求主键
     * @return MRP物料需求
     */
    public PlnMrpMr selectPlnMrpMrById(Long id);

    /**
     * 查询MRP物料需求列表
     * 
     * @param plnMrpMr MRP物料需求
     * @return MRP物料需求集合
     */
    public List<PlnMrpMr> selectPlnMrpMrList(PlnMrpMr plnMrpMr);

    /**
     * 新增MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    public int insertPlnMrpMr(PlnMrpMr plnMrpMr);

    /**
     * 修改MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    public int updatePlnMrpMr(PlnMrpMr plnMrpMr);

    /**
     * 批量删除MRP物料需求
     * 
     * @param ids 需要删除的MRP物料需求主键集合
     * @return 结果
     */
    public int deletePlnMrpMrByIds(Long[] ids);

    /**
     * 删除MRP物料需求信息
     * 
     * @param id MRP物料需求主键
     * @return 结果
     */
    public int deletePlnMrpMrById(Long id);


    /**
     * 转需求
     *
     * @param ids id集合
     * @return 结果
     */
    public int toMp(Long[] ids) throws Exception;

}
