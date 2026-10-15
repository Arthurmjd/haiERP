package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardRecord;

/**
 * 安全风险登记Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsHazardRecordService 
{
    /**
     * 查询安全风险登记
     * 
     * @param id 安全风险登记主键
     * @return 安全风险登记
     */
    public EhsHazardRecord selectEhsHazardRecordById(Long id);

    /**
     * 查询安全风险登记列表
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 安全风险登记集合
     */
    public List<EhsHazardRecord> selectEhsHazardRecordList(EhsHazardRecord ehsHazardRecord);

    /**
     * 新增安全风险登记
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 结果
     */
    public int insertEhsHazardRecord(EhsHazardRecord ehsHazardRecord);

    /**
     * 修改安全风险登记
     * 
     * @param ehsHazardRecord 安全风险登记
     * @return 结果
     */
    public int updateEhsHazardRecord(EhsHazardRecord ehsHazardRecord);

    /**
     * 批量删除安全风险登记
     * 
     * @param ids 需要删除的安全风险登记主键集合
     * @return 结果
     */
    public int deleteEhsHazardRecordByIds(Long[] ids);

    /**
     * 删除安全风险登记信息
     * 
     * @param id 安全风险登记主键
     * @return 结果
     */
    public int deleteEhsHazardRecordById(Long id);


}
