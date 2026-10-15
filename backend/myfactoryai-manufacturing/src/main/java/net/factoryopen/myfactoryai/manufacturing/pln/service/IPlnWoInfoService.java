package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoInfo;

/**
 * 工单信息Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IPlnWoInfoService 
{
    /**
     * 查询工单信息
     * 
     * @param id 工单信息主键
     * @return 工单信息
     */
    public PlnWoInfo selectPlnWoInfoById(Long id);

    /**
     * 查询工单信息列表
     * 
     * @param plnWoInfo 工单信息
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoList(PlnWoInfo plnWoInfo);

    /**
     * 新增工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    public int insertPlnWoInfo(PlnWoInfo plnWoInfo);

    /**
     * 修改工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    public int updatePlnWoInfo(PlnWoInfo plnWoInfo);

    /**
     * 批量删除工单信息
     * 
     * @param ids 需要删除的工单信息主键集合
     * @return 结果
     */
    public int deletePlnWoInfoByIds(Long[] ids);

    /**
     * 删除工单信息信息
     * 
     * @param id 工单信息主键
     * @return 结果
     */
    public int deletePlnWoInfoById(Long id);


}
