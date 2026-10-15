package net.factoryopen.myfactoryai.manufacturing.ehs.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ehs.domain.EhsPollutionPoint;

/**
 * 污染物排放点Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface EhsPollutionPointMapper 
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
     * 查询污染物排放点列表
     *
     * @param categoryId 排放分类
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByCategoryId(Long categoryId);

    /**
     * 查询污染物排放点列表
     *
     * @param index1Id 排放指标1
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByIndex1Id(Long index1Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index2Id 排放指标2
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByIndex2Id(Long index2Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index3Id 排放指标3
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByIndex3Id(Long index3Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index4Id 排放指标4
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByIndex4Id(Long index4Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index5Id 排放指标5
     * @return 污染物排放点集合
     */
    public List<EhsPollutionPoint> selectEhsPollutionPointListByIndex5Id(Long index5Id);

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
     * 删除污染物排放点
     * 
     * @param id 污染物排放点主键
     * @return 结果
     */
    public int deleteEhsPollutionPointById(Long id);

    /**
     * 批量删除污染物排放点
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEhsPollutionPointByIds(Long[] ids);

    /**
     * 查询污染物排放点列表
     *
     * @param categoryId 排放分类
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByCategoryId(Long categoryId);

    /**
     * 查询污染物排放点列表
     *
     * @param index1Id 排放指标1
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByIndex1Id(Long index1Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index2Id 排放指标2
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByIndex2Id(Long index2Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index3Id 排放指标3
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByIndex3Id(Long index3Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index4Id 排放指标4
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByIndex4Id(Long index4Id);

    /**
     * 查询污染物排放点列表
     *
     * @param index5Id 排放指标5
     * @return 污染物排放点集合
     */
    public int deleteEhsPollutionPointByIndex5Id(Long index5Id);
}
