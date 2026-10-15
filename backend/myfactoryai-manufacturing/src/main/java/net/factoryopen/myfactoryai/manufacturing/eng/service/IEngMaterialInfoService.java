package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialInfo;

/**
 * 物料信息Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-09-17
 */
public interface IEngMaterialInfoService 
{
    /**
     * 查询物料信息
     * 
     * @param id 物料信息主键
     * @return 物料信息
     */
    public EngMaterialInfo selectEngMaterialInfoById(Long id);

    /**
     * 查询物料信息列表
     * 
     * @param engMaterialInfo 物料信息
     * @return 物料信息集合
     */
    public List<EngMaterialInfo> selectEngMaterialInfoList(EngMaterialInfo engMaterialInfo);

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息集合
     */
    public List<EngMaterialInfo> selectPurchasingMaterialInfoList(EngMaterialInfo engMaterialInfo);

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息集合
     */
    public List<EngMaterialInfo> selectProductingMaterialInfoList(EngMaterialInfo engMaterialInfo);

    /**
     * 查询物料信息列表
     *
     * @param engMaterialInfo 物料信息
     * @return 物料信息集合
     */
    public List<EngMaterialInfo> filterMaterialList(EngMaterialInfo engMaterialInfo);

    /**
     * 新增物料信息
     * 
     * @param engMaterialInfo 物料信息
     * @return 结果
     */
    public int insertEngMaterialInfo(EngMaterialInfo engMaterialInfo);

    /**
     * 修改物料信息
     * 
     * @param engMaterialInfo 物料信息
     * @return 结果
     */
    public int updateEngMaterialInfo(EngMaterialInfo engMaterialInfo);

    /**
     * 批量删除物料信息
     * 
     * @param ids 需要删除的物料信息主键集合
     * @return 结果
     */
    public int deleteEngMaterialInfoByIds(Long[] ids);

    /**
     * 删除物料信息信息
     * 
     * @param id 物料信息主键
     * @return 结果
     */
    public int deleteEngMaterialInfoById(Long id);


}
