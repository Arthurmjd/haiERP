package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;

/**
 * 物料需求Service接口
 * 
 * @author admin
 * @date 2025-04-28
 */
public interface IPlnMrpMpService 
{
    /**
     * 查询物料需求
     * 
     * @param id 物料需求主键
     * @return 物料需求
     */
    public PlnMrpMp selectPlnMrpMpById(Long id);

    /**
     * 查询物料需求列表
     * 
     * @param plnMrpMp 物料需求
     * @return 物料需求集合
     */
    public List<PlnMrpMp> selectPlnMrpMpList(PlnMrpMp plnMrpMp);

    /**
     * 新增物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    public int insertPlnMrpMp(PlnMrpMp plnMrpMp);

    /**
     * 修改物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    public int updatePlnMrpMp(PlnMrpMp plnMrpMp);

    /**
     * 批量删除物料需求
     * 
     * @param ids 需要删除的物料需求主键集合
     * @return 结果
     */
    public int deletePlnMrpMpByIds(Long[] ids);

    /**
     * 删除物料需求信息
     * 
     * @param id 物料需求主键
     * @return 结果
     */
    public int deletePlnMrpMpById(Long id);

    /**
     * 启用
     *
     * @param plnMrpMp 启用
     * @return 结果
     */
    public int enable(PlnMrpMp plnMrpMp);

    /**
     * 请购
     *
     * @param ids id集合
     * @return 结果
     */
    public int requestPurchase(Long[] ids) throws Exception;


}
