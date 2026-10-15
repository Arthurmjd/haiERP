package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductRoutingsop;

/**
 * 产品SOPMapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface EngPruductRoutingsopMapper 
{
    /**
     * 查询产品SOP
     * 
     * @param id 产品SOP主键
     * @return 产品SOP
     */
    public EngPruductRoutingsop selectEngPruductRoutingsopById(Long id);

    /**
     * 查询产品SOP列表
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 产品SOP集合
     */
    public List<EngPruductRoutingsop> selectEngPruductRoutingsopList(EngPruductRoutingsop engPruductRoutingsop);

    /**
     * 查询产品SOP列表
     *
     * @param routingId 产品路线
     * @return 产品SOP集合
     */
    public List<EngPruductRoutingsop> selectEngPruductRoutingsopListByRoutingId(Long routingId);

    /**
     * 新增产品SOP
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 结果
     */
    public int insertEngPruductRoutingsop(EngPruductRoutingsop engPruductRoutingsop);

    /**
     * 修改产品SOP
     * 
     * @param engPruductRoutingsop 产品SOP
     * @return 结果
     */
    public int updateEngPruductRoutingsop(EngPruductRoutingsop engPruductRoutingsop);

    /**
     * 删除产品SOP
     * 
     * @param id 产品SOP主键
     * @return 结果
     */
    public int deleteEngPruductRoutingsopById(Long id);

    /**
     * 批量删除产品SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngPruductRoutingsopByIds(Long[] ids);

    /**
     * 查询产品SOP列表
     *
     * @param routingId 产品路线
     * @return 产品SOP集合
     */
    public int deleteEngPruductRoutingsopByRoutingId(Long routingId);
}
