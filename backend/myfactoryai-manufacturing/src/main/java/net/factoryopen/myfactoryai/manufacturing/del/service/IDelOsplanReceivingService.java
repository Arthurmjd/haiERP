package net.factoryopen.myfactoryai.manufacturing.del.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplanReceiving;

/**
 * 外协收货Service接口
 * 
 * @author admin
 * @date 2024-07-09
 */
public interface IDelOsplanReceivingService 
{
    /**
     * 查询外协收货
     * 
     * @param id 外协收货主键
     * @return 外协收货
     */
    public DelOsplanReceiving selectDelOsplanReceivingById(Long id);

    /**
     * 查询外协收货列表
     * 
     * @param delOsplanReceiving 外协收货
     * @return 外协收货集合
     */
    public List<DelOsplanReceiving> selectDelOsplanReceivingList(DelOsplanReceiving delOsplanReceiving);

    /**
     * 新增外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    public int insertDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving);

    /**
     * 修改外协收货
     * 
     * @param delOsplanReceiving 外协收货
     * @return 结果
     */
    public int updateDelOsplanReceiving(DelOsplanReceiving delOsplanReceiving);

    /**
     * 批量删除外协收货
     * 
     * @param ids 需要删除的外协收货主键集合
     * @return 结果
     */
    public int deleteDelOsplanReceivingByIds(Long[] ids);

    /**
     * 删除外协收货信息
     * 
     * @param id 外协收货主键
     * @return 结果
     */
    public int deleteDelOsplanReceivingById(Long id);


    /**
     * 请检
     *
     * @param delOsplanReceiving 请检
     * @return 结果
     */
    public int requestInspection(DelOsplanReceiving delOsplanReceiving) throws Exception;

}
