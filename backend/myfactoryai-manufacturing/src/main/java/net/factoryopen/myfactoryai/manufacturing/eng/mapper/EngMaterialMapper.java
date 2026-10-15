package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import org.springframework.stereotype.Service;

/**
 * 外购料管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public interface EngMaterialMapper 
{
    /**
     * 查询外购料管理
     * 
     * @param id 外购料管理主键
     * @return 外购料管理
     */
    public EngMaterial selectEngMaterialById(Long id);

    /**
     * 查询所有可出货的货物
     * 含外贸件0、自制件1、虚拟件2，生产性采购料3，非生产性采购料4，不含临时料5
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectAllGoodsList(EngMaterial engMaterial);

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
     * 查询外购料管理列表
     *
     * @param engMaterial 外购料管理
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngInventoryList(EngMaterial engMaterial);

    /**
     * 查询外购料管理列表
     *
     * @param unitId 单位
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngMaterialListByUnitId(Long unitId);

    /**
     * 查询外购料管理列表
     *
     * @param categoryId 品类
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngMaterialListByCategoryId(Long categoryId);

    /**
     * 查询前缀物料列表
     *
     * @param prefixCode 料名前缀
     * @return 外购料管理集合
     */
    public List<EngMaterial> selectEngMaterialByPrefixCode(String prefixCode);

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
     * 删除外购料管理
     * 
     * @param id 外购料管理主键
     * @return 结果
     */
    public int deleteEngMaterialById(Long id);

    /**
     * 批量删除外购料管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngMaterialByIds(Long[] ids);

    /**
     * 查询外购料管理列表
     *
     * @param unitId 单位
     * @return 外购料管理集合
     */
    public int deleteEngMaterialByUnitId(Long unitId);

    /**
     * 查询外购料管理列表
     *
     * @param categoryId 品类
     * @return 外购料管理集合
     */
    public int deleteEngMaterialByCategoryId(Long categoryId);
}
