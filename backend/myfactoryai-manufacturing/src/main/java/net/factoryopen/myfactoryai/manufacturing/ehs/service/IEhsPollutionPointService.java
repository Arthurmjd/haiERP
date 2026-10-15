package net.factoryopen.myfactoryai.manufacturing.ehs.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionPoint;

/**
 * 污染物排放点Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IEhsPollutionPointService 
{
    /**
     * 查询污染物排放点
     * 
     * @param id 污染物排放点主键
     * @return 污染物排放点
     */
    public EhsPollutionPoint selectEhsPollutionPointById(Long id);

    /**
     * 查询污染物排放点列表
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointList(EhsPollutionPoint ehsPollutionPoint);

    /**
     * 新增污染物排放点
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 结果
     */
    public int insertEhsPollutionPoint(EhsPollutionPoint ehsPollutionPoint);

    /**
     * 修改污染物排放点
     * 
     * @param ehsPollutionPoint 污染物排放点
     * @return 结果
     */
    public int updateEhsPollutionPoint(EhsPollutionPoint ehsPollutionPoint);

    /**
     * 批量删除污染物排放点
     * 
     * @param ids 需要删除的污染物排放点主键集合
     * @return 结果
     */
    public int deleteEhsPollutionPointByIds(Long[] ids);

    /**
     * 删除污染物排放点信息
     * 
     * @param id 污染物排放点主键
     * @return 结果
     */
    public int deleteEhsPollutionPointById(Long id);


}
