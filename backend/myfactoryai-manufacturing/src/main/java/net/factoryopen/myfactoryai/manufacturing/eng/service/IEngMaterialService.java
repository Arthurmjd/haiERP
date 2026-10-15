package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;

/**
 * 外购料管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialService 
{
    /**
     * 查询外购料管理
     * 
     * @param id 外购料管理主键
     * @return 外购料管理
     */
    public EngMaterial selectEngMaterialById(Long id);

    /**
     * 查询前缀物料列表
     *
     * @param prefixCode 料名前缀
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngMaterialByPrefixCode(String prefixCode);

    /**
     * 查询外购料管理列表
     * 
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngProductList(EngMaterial engMaterial);

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngPureProductList(EngMaterial engMaterial);

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngMaterialList(EngMaterial engMaterial);

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngPureMaterialOrProductList(EngMaterial engMaterial);

    /**
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngUtilityList(EngMaterial engMaterial);

    /**
     * 查询存货列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngInventoryList(EngMaterial engMaterial);

    /**
     * 新增外购料管理
     * 
     * @param engMaterial 外购料管理
     * @return 结果
     */
    public int insertEngMaterial(EngMaterial engMaterial);

    /**
     * 修改外购料管理
     * 
     * @param engMaterial 外购料管理
     * @return 结果
     */
    public int updateEngMaterial(EngMaterial engMaterial);

    /**
     * 批量删除外购料管理
     * 
     * @param ids 需要删除的外购料管理主键集合
     * @return 结果
     */
    public int deleteEngMaterialByIds(Long[] ids);

    /**
     * 删除外购料管理信息
     * 
     * @param id 外购料管理主键
     * @return 结果
     */
    public int deleteEngMaterialById(Long id);


}
