package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoRoutingsop;

/**
 * 工单SOPMapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface PlnWoRoutingsopMapper 
{
    /**
     * 查询工单SOP
     * 
     * @param id 工单SOP主键
     * @return 工单SOP
     */
    public PlnWoRoutingsop selectPlnWoRoutingsopById(Long id);

    /**
     * 查询工单SOP列表
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 工单SOP集合
     */
    public List<PlnWoRoutingsop> selectPlnWoRoutingsopList(PlnWoRoutingsop plnWoRoutingsop);

    /**
     * 查询工单SOP列表
     *
     * @param routingId 工序
     * @return 工单SOP集合
     */
    public List<PlnWoRoutingsop> selectPlnWoRoutingsopListByRoutingId(Long routingId);

    /**
     * 新增工单SOP
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 结果
     */
    public int insertPlnWoRoutingsop(PlnWoRoutingsop plnWoRoutingsop);

    /**
     * 修改工单SOP
     * 
     * @param plnWoRoutingsop 工单SOP
     * @return 结果
     */
    public int updatePlnWoRoutingsop(PlnWoRoutingsop plnWoRoutingsop);

    /**
     * 删除工单SOP
     * 
     * @param id 工单SOP主键
     * @return 结果
     */
    public int deletePlnWoRoutingsopById(Long id);

    /**
     * 批量删除工单SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoRoutingsopByIds(Long[] ids);

    /**
     * 查询工单SOP列表
     *
     * @param routingId 工序
     * @return 工单SOP集合
     */
    public int deletePlnWoRoutingsopByRoutingId(Long routingId);
}
