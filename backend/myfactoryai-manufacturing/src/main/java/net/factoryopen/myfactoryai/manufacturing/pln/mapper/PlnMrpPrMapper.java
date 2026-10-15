package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpPr;

/**
 * 物料需求Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface PlnMrpPrMapper 
{
    /**
     * 查询物料需求
     * 
     * @param id 物料需求主键
     * @return 物料需求
     */
    public PlnMrpPr selectPlnMrpPrById(Long id);

    /**
     * 查询物料需求列表
     * 
     * @param plnMrpPr 物料需求
     * @return 物料需求集合
     */
    public List<PlnMrpPr> selectPlnMrpPrList(PlnMrpPr plnMrpPr);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public List<PlnMrpPr> selectPlnMrpPrListByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public List<PlnMrpPr> selectPlnMrpPrListByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public List<PlnMrpPr> selectPlnMrpPrListByUnitId(Long unitId);

    /**
     * 新增物料需求
     * 
     * @param plnMrpPr 物料需求
     * @return 结果
     */
    public int insertPlnMrpPr(PlnMrpPr plnMrpPr);

    /**
     * 修改物料需求
     * 
     * @param plnMrpPr 物料需求
     * @return 结果
     */
    public int updatePlnMrpPr(PlnMrpPr plnMrpPr);

    /**
     * 删除物料需求
     * 
     * @param id 物料需求主键
     * @return 结果
     */
    public int deletePlnMrpPrById(Long id);

    /**
     * 批量删除物料需求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMrpPrByIds(Long[] ids);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public int deletePlnMrpPrByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public int deletePlnMrpPrByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public int deletePlnMrpPrByUnitId(Long unitId);
}
