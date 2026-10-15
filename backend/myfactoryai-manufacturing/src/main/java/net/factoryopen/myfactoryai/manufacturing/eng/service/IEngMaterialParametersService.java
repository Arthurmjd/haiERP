package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterialParameters;

/**
 * 物料参数Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
public interface IEngMaterialParametersService 
{
    /**
     * 查询物料参数
     * 
     * @param id 物料参数主键
     * @return 物料参数
     */
    public EngMaterialParameters selectEngMaterialParametersById(Long id);

    /**
     * 查询物料参数列表
     * 
     * @param engMaterialParameters 物料参数
     * @return 物料参数集合
     */
    public List<EngMaterialParameters> selectEngMaterialParametersList(EngMaterialParameters engMaterialParameters);

    /**
     * 新增物料参数
     * 
     * @param engMaterialParameters 物料参数
     * @return 结果
     */
    public int insertEngMaterialParameters(EngMaterialParameters engMaterialParameters);

    /**
     * 修改物料参数
     * 
     * @param engMaterialParameters 物料参数
     * @return 结果
     */
    public int updateEngMaterialParameters(EngMaterialParameters engMaterialParameters);

    /**
     * 批量删除物料参数
     * 
     * @param ids 需要删除的物料参数主键集合
     * @return 结果
     */
    public int deleteEngMaterialParametersByIds(Long[] ids);

    /**
     * 删除物料参数信息
     * 
     * @param id 物料参数主键
     * @return 结果
     */
    public int deleteEngMaterialParametersById(Long id);


}
