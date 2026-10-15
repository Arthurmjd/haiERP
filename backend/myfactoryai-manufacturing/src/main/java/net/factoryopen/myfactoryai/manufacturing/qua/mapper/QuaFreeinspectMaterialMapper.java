package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaFreeinspectMaterial;

/**
 * 免检物料Mapper接口
 * 
 * @author admin
 * @date 2024-11-12
 */
public interface QuaFreeinspectMaterialMapper 
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
     * 查询免检物料列表
     *
     * @param policyId 免检策略ID
     * @return 免检物料集合
     */
    public List<QuaFreeinspectMaterial> selectQuaFreeinspectMaterialListByPolicyId(Long policyId);

    /**
     * 查询免检物料列表
     *
     * @param materialId 免检物料ID
     * @return 免检物料集合
     */
    public List<QuaFreeinspectMaterial> selectQuaFreeinspectMaterialListByMaterialId(Long materialId);

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
     * 删除免检物料
     * 
     * @param id 免检物料主键
     * @return 结果
     */
    public int deleteQuaFreeinspectMaterialById(Long id);

    /**
     * 批量删除免检物料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaFreeinspectMaterialByIds(Long[] ids);

    /**
     * 查询免检物料列表
     *
     * @param policyId 免检策略ID
     * @return 免检物料集合
     */
    public int deleteQuaFreeinspectMaterialByPolicyId(Long policyId);

    /**
     * 查询免检物料列表
     *
     * @param materialId 免检物料ID
     * @return 免检物料集合
     */
    public int deleteQuaFreeinspectMaterialByMaterialId(Long materialId);
}
