package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlMaterial;

/**
 * 批次备料单Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlMaterialService 
{
    /**
     * 查询批次备料单
     * 
     * @param id 批次备料单主键
     * @return 批次备料单
     */
    public PlnMlMaterial selectPlnMlMaterialById(Long id);

    /**
     * 查询批次备料单列表
     * 
     * @param plnMlMaterial 批次备料单
     * @return 批次备料单集合
     */
    public List<PlnMlMaterial> selectPlnMlMaterialList(PlnMlMaterial plnMlMaterial);

    /**
     * 新增批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    public int insertPlnMlMaterial(PlnMlMaterial plnMlMaterial);

    /**
     * 修改批次备料单
     * 
     * @param plnMlMaterial 批次备料单
     * @return 结果
     */
    public int updatePlnMlMaterial(PlnMlMaterial plnMlMaterial);

    /**
     * 批量删除批次备料单
     * 
     * @param ids 需要删除的批次备料单主键集合
     * @return 结果
     */
    public int deletePlnMlMaterialByIds(Long[] ids);

    /**
     * 删除批次备料单信息
     * 
     * @param id 批次备料单主键
     * @return 结果
     */
    public int deletePlnMlMaterialById(Long id);


}
