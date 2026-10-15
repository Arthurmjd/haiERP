package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMr;

/**
 * MRP物料需求Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface PlnMrpMrMapper 
{
    /**
     * 查询MRP物料需求
     * 
     * @param id MRP物料需求主键
     * @return MRP物料需求
     */
    public PlnMrpMr selectPlnMrpMrById(Long id);

    /**
     * 查询MRP物料需求列表
     * 
     * @param plnMrpMr MRP物料需求
     * @return MRP物料需求集合
     */
    public List<PlnMrpMr> selectPlnMrpMrList(PlnMrpMr plnMrpMr);

    /**
     * 查询MRP物料需求列表
     *
     * @param mpsId 主计划
     * @return MRP物料需求集合
     */
    public List<PlnMrpMr> selectPlnMrpMrListByMpsId(Long mpsId);

    /**
     * 查询MRP物料需求列表
     *
     * @param materialId 物料编码
     * @return MRP物料需求集合
     */
    public List<PlnMrpMr> selectPlnMrpMrListByMaterialId(Long materialId);

    /**
     * 查询MRP物料需求列表
     *
     * @param unitId 单位
     * @return MRP物料需求集合
     */
    public List<PlnMrpMr> selectPlnMrpMrListByUnitId(Long unitId);

    /**
     * 新增MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    public int insertPlnMrpMr(PlnMrpMr plnMrpMr);

    /**
     * 修改MRP物料需求
     * 
     * @param plnMrpMr MRP物料需求
     * @return 结果
     */
    public int updatePlnMrpMr(PlnMrpMr plnMrpMr);

    /**
     * 删除MRP物料需求
     * 
     * @param id MRP物料需求主键
     * @return 结果
     */
    public int deletePlnMrpMrById(Long id);

    /**
     * 批量删除MRP物料需求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMrpMrByIds(Long[] ids);

    /**
     * 查询MRP物料需求列表
     *
     * @param mpsId 主计划
     * @return MRP物料需求集合
     */
    public int deletePlnMrpMrByMpsId(Long mpsId);

    /**
     * 查询MRP物料需求列表
     *
     * @param materialId 物料编码
     * @return MRP物料需求集合
     */
    public int deletePlnMrpMrByMaterialId(Long materialId);

    /**
     * 查询MRP物料需求列表
     *
     * @param unitId 单位
     * @return MRP物料需求集合
     */
    public int deletePlnMrpMrByUnitId(Long unitId);
}
