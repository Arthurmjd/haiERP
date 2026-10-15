package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsHazardRecord;

/**
 * 安全风险登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsHazardRecordMapper 
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
     * 查询安全风险登记列表
     *
     * @param deptId 部门ID
     * @return 安全风险登记集合
     */
    public List<EhsHazardRecord> selectEhsHazardRecordListByDeptId(Long deptId);

    /**
     * 查询安全风险登记列表
     *
     * @param employeeId 责任人ID
     * @return 安全风险登记集合
     */
    public List<EhsHazardRecord> selectEhsHazardRecordListByEmployeeId(Long employeeId);

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
     * 删除安全风险登记
     * 
     * @param id 安全风险登记主键
     * @return 结果
     */
    public int deleteEhsHazardRecordById(Long id);

    /**
     * 批量删除安全风险登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsHazardRecordByIds(Long[] ids);

    /**
     * 查询安全风险登记列表
     *
     * @param deptId 部门ID
     * @return 安全风险登记集合
     */
    public int deleteEhsHazardRecordByDeptId(Long deptId);

    /**
     * 查询安全风险登记列表
     *
     * @param employeeId 责任人ID
     * @return 安全风险登记集合
     */
    public int deleteEhsHazardRecordByEmployeeId(Long employeeId);
}
