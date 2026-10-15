package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpMp;

/**
 * 物料需求Mapper接口
 * 
 * @author admin
 * @date 2025-04-28
 */
public interface PlnMrpMpMapper 
{
    /**
     * 查询物料需求
     * 
     * @param id 物料需求主键
     * @return 物料需求
     */
    public PlnMrpMp selectPlnMrpMpById(Long id);

    /**
     * 查询物料需求列表
     * 
     * @param plnMrpMp 物料需求
     * @return 物料需求集合
     */
    public List<PlnMrpMp> selectPlnMrpMpList(PlnMrpMp plnMrpMp);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public List<PlnMrpMp> selectPlnMrpMpListByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public List<PlnMrpMp> selectPlnMrpMpListByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public List<PlnMrpMp> selectPlnMrpMpListByUnitId(Long unitId);

    /**
     * 新增物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    public int insertPlnMrpMp(PlnMrpMp plnMrpMp);

    /**
     * 修改物料需求
     * 
     * @param plnMrpMp 物料需求
     * @return 结果
     */
    public int updatePlnMrpMp(PlnMrpMp plnMrpMp);

    /**
     * 删除物料需求
     * 
     * @param id 物料需求主键
     * @return 结果
     */
    public int deletePlnMrpMpById(Long id);

    /**
     * 批量删除物料需求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMrpMpByIds(Long[] ids);

    /**
     * 查询物料需求列表
     *
     * @param mpsId 主计划
     * @return 物料需求集合
     */
    public int deletePlnMrpMpByMpsId(Long mpsId);

    /**
     * 查询物料需求列表
     *
     * @param materialId 物料编码
     * @return 物料需求集合
     */
    public int deletePlnMrpMpByMaterialId(Long materialId);

    /**
     * 查询物料需求列表
     *
     * @param unitId 单位
     * @return 物料需求集合
     */
    public int deletePlnMrpMpByUnitId(Long unitId);
}
