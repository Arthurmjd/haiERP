package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsGasFoodie;

/**
 * 气耗设备Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsGasFoodieService 
{
    /**
     * 查询气耗设备
     * 
     * @param id 气耗设备主键
     * @return 气耗设备
     */
    public EhsGasFoodie selectEhsGasFoodieById(Long id);

    /**
     * 查询气耗设备列表
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 气耗设备集合
     */
    public List<EhsGasFoodie> selectEhsGasFoodieList(EhsGasFoodie ehsGasFoodie);

    /**
     * 新增气耗设备
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 结果
     */
    public int insertEhsGasFoodie(EhsGasFoodie ehsGasFoodie);

    /**
     * 修改气耗设备
     * 
     * @param ehsGasFoodie 气耗设备
     * @return 结果
     */
    public int updateEhsGasFoodie(EhsGasFoodie ehsGasFoodie);

    /**
     * 批量删除气耗设备
     * 
     * @param ids 需要删除的气耗设备主键集合
     * @return 结果
     */
    public int deleteEhsGasFoodieByIds(Long[] ids);

    /**
     * 删除气耗设备信息
     * 
     * @param id 气耗设备主键
     * @return 结果
     */
    public int deleteEhsGasFoodieById(Long id);


}
