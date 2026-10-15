package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionRecord;

/**
 * 污染物排放登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsPollutionRecordMapper 
{
    /**
     * 查询污染物排放登记
     * 
     * @param id 污染物排放登记主键
     * @return 污染物排放登记
     */
    public EhsPollutionRecord selectEhsPollutionRecordById(Long id);

    /**
     * 查询污染物排放登记列表
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 污染物排放登记集合
     */
    public List<EhsPollutionRecord> selectEhsPollutionRecordList(EhsPollutionRecord ehsPollutionRecord);

    /**
     * 查询污染物排放登记列表
     *
     * @param foodieId 设备ID
     * @return 污染物排放登记集合
     */
    public List<EhsPollutionRecord> selectEhsPollutionRecordListByFoodieId(Long foodieId);

    /**
     * 新增污染物排放登记
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 结果
     */
    public int insertEhsPollutionRecord(EhsPollutionRecord ehsPollutionRecord);

    /**
     * 修改污染物排放登记
     * 
     * @param ehsPollutionRecord 污染物排放登记
     * @return 结果
     */
    public int updateEhsPollutionRecord(EhsPollutionRecord ehsPollutionRecord);

    /**
     * 删除污染物排放登记
     * 
     * @param id 污染物排放登记主键
     * @return 结果
     */
    public int deleteEhsPollutionRecordById(Long id);

    /**
     * 批量删除污染物排放登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsPollutionRecordByIds(Long[] ids);

    /**
     * 查询污染物排放登记列表
     *
     * @param foodieId 设备ID
     * @return 污染物排放登记集合
     */
    public int deleteEhsPollutionRecordByFoodieId(Long foodieId);
}
