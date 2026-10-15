package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialReplacable;

/**
 * 物料替代Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface EngMaterialReplacableMapper 
{
    /**
     * 查询物料替代
     * 
     * @param id 物料替代主键
     * @return 物料替代
     */
    public EngMaterialReplacable selectEngMaterialReplacableById(Long id);

    /**
     * 查询物料替代列表
     * 
     * @param engMaterialReplacable 物料替代
     * @return 物料替代集合
     */
    public List<EngMaterialReplacable> selectEngMaterialReplacableList(EngMaterialReplacable engMaterialReplacable);

    /**
     * 查询物料替代列表
     *
     * @param materialId 物料编码
     * @return 物料替代集合
     */
    public List<EngMaterialReplacable> selectEngMaterialReplacableListByMaterialId(Long materialId);

    /**
     * 查询物料替代列表
     *
     * @param replacableMaterialId 替代物料
     * @return 物料替代集合
     */
    public List<EngMaterialReplacable> selectEngMaterialReplacableListByReplacableMaterialId(Long replacableMaterialId);

    /**
     * 新增物料替代
     * 
     * @param engMaterialReplacable 物料替代
     * @return 结果
     */
    public int insertEngMaterialReplacable(EngMaterialReplacable engMaterialReplacable);

    /**
     * 修改物料替代
     * 
     * @param engMaterialReplacable 物料替代
     * @return 结果
     */
    public int updateEngMaterialReplacable(EngMaterialReplacable engMaterialReplacable);

    /**
     * 删除物料替代
     * 
     * @param id 物料替代主键
     * @return 结果
     */
    public int deleteEngMaterialReplacableById(Long id);

    /**
     * 批量删除物料替代
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialReplacableByIds(Long[] ids);

    /**
     * 查询物料替代列表
     *
     * @param materialId 物料编码
     * @return 物料替代集合
     */
    public int deleteEngMaterialReplacableByMaterialId(Long materialId);

    /**
     * 查询物料替代列表
     *
     * @param replacableMaterialId 替代物料
     * @return 物料替代集合
     */
    public int deleteEngMaterialReplacableByReplacableMaterialId(Long replacableMaterialId);
}
