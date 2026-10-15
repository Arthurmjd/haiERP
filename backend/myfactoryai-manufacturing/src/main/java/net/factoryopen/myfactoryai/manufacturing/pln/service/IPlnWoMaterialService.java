package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoMaterial;

/**
 * 工单发料Service接口
 * 
 * @author admin
 * @date 2024-07-12
 */
public interface IPlnWoMaterialService 
{
    /**
     * 查询工单发料
     * 
     * @param id 工单发料主键
     * @return 工单发料
     */
    public PlnWoMaterial selectPlnWoMaterialById(Long id);

    /**
     * 查询工单发料列表
     * 
     * @param plnWoMaterial 工单发料
     * @return 工单发料集合
     */
    public List<PlnWoMaterial> selectPlnWoMaterialList(PlnWoMaterial plnWoMaterial);

    /**
     * 新增工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    public int insertPlnWoMaterial(PlnWoMaterial plnWoMaterial);

    /**
     * 修改工单发料
     * 
     * @param plnWoMaterial 工单发料
     * @return 结果
     */
    public int updatePlnWoMaterial(PlnWoMaterial plnWoMaterial);

    /**
     * 批量删除工单发料
     * 
     * @param ids 需要删除的工单发料主键集合
     * @return 结果
     */
    public int deletePlnWoMaterialByIds(Long[] ids);

    /**
     * 删除工单发料信息
     * 
     * @param id 工单发料主键
     * @return 结果
     */
    public int deletePlnWoMaterialById(Long id);


}
