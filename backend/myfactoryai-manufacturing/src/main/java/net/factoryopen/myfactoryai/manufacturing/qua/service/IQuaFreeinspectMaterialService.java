package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectMaterial;

/**
 * 免检物料Service接口
 * 
 * @author admin
 * @date 2024-11-12
 */
public interface IQuaFreeinspectMaterialService 
{
    /**
     * 查询免检物料
     * 
     * @param id 免检物料主键
     * @return 免检物料
     */
    public QuaFreeinspectMaterial selectQuaFreeinspectMaterialById(Long id);

    /**
     * 查询免检物料列表
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 免检物料集合
     */
    public List<QuaFreeinspectMaterial> selectQuaFreeinspectMaterialList(QuaFreeinspectMaterial quaFreeinspectMaterial);

    /**
     * 新增免检物料
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 结果
     */
    public int insertQuaFreeinspectMaterial(QuaFreeinspectMaterial quaFreeinspectMaterial);

    /**
     * 修改免检物料
     * 
     * @param quaFreeinspectMaterial 免检物料
     * @return 结果
     */
    public int updateQuaFreeinspectMaterial(QuaFreeinspectMaterial quaFreeinspectMaterial);

    /**
     * 批量删除免检物料
     * 
     * @param ids 需要删除的免检物料主键集合
     * @return 结果
     */
    public int deleteQuaFreeinspectMaterialByIds(Long[] ids);

    /**
     * 删除免检物料信息
     * 
     * @param id 免检物料主键
     * @return 结果
     */
    public int deleteQuaFreeinspectMaterialById(Long id);


}
