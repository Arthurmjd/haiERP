package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionIndex;

/**
 * 污染排放指标Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPollutionIndexService 
{
    /**
     * 查询污染排放指标
     * 
     * @param id 污染排放指标主键
     * @return 污染排放指标
     */
    public EhsPollutionIndex selectEhsPollutionIndexById(Long id);

    /**
     * 查询污染排放指标列表
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 污染排放指标集合
     */
    public List<EhsPollutionIndex> selectEhsPollutionIndexList(EhsPollutionIndex ehsPollutionIndex);

    /**
     * 新增污染排放指标
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 结果
     */
    public int insertEhsPollutionIndex(EhsPollutionIndex ehsPollutionIndex);

    /**
     * 修改污染排放指标
     * 
     * @param ehsPollutionIndex 污染排放指标
     * @return 结果
     */
    public int updateEhsPollutionIndex(EhsPollutionIndex ehsPollutionIndex);

    /**
     * 批量删除污染排放指标
     * 
     * @param ids 需要删除的污染排放指标主键集合
     * @return 结果
     */
    public int deleteEhsPollutionIndexByIds(Long[] ids);

    /**
     * 删除污染排放指标信息
     * 
     * @param id 污染排放指标主键
     * @return 结果
     */
    public int deleteEhsPollutionIndexById(Long id);


}
