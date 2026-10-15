package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoBom;

/**
 * 工单BOMMapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface PlnWoBomMapper 
{
    /**
     * 查询工单BOM
     * 
     * @param id 工单BOM主键
     * @return 工单BOM
     */
    public PlnWoBom selectPlnWoBomById(Long id);

    /**
     * 查询工单BOM列表
     * 
     * @param plnWoBom 工单BOM
     * @return 工单BOM集合
     */
    public List<PlnWoBom> selectPlnWoBomList(PlnWoBom plnWoBom);

    /**
     * 查询工单BOM列表
     *
     * @param woId 工单
     * @return 工单BOM集合
     */
    public List<PlnWoBom> selectPlnWoBomListByWoId(Long woId);

    /**
     * 查询工单BOM列表
     *
     * @param materialId 物料编码
     * @return 工单BOM集合
     */
    public List<PlnWoBom> selectPlnWoBomListByMaterialId(Long materialId);

    /**
     * 新增工单BOM
     * 
     * @param plnWoBom 工单BOM
     * @return 结果
     */
    public int insertPlnWoBom(PlnWoBom plnWoBom);

    /**
     * 修改工单BOM
     * 
     * @param plnWoBom 工单BOM
     * @return 结果
     */
    public int updatePlnWoBom(PlnWoBom plnWoBom);

    /**
     * 删除工单BOM
     * 
     * @param id 工单BOM主键
     * @return 结果
     */
    public int deletePlnWoBomById(Long id);

    /**
     * 批量删除工单BOM
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoBomByIds(Long[] ids);

    /**
     * 查询工单BOM列表
     *
     * @param woId 工单
     * @return 工单BOM集合
     */
    public int deletePlnWoBomByWoId(Long woId);

    /**
     * 查询工单BOM列表
     *
     * @param materialId 物料编码
     * @return 工单BOM集合
     */
    public int deletePlnWoBomByMaterialId(Long materialId);
}
