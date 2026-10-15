package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;

import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryEngineering;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductEngineering;

/**
 * 产品工艺管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-11
 */
public interface IEngProductEngineeringService 
{
    /**
     * 查询产品工艺管理
     * 
     * @param id 产品工艺管理主键
     * @return 产品工艺管理
     */
    public EngProductEngineering selectEngProductEngineeringById(Long id);

    /**
     * 查询产品工艺管理列表
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 产品工艺管理集合
     */
    public List<EngProductEngineering> selectEngProductEngineeringList(EngProductEngineering engProductEngineering);

    /**
     * 新增产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    public int insertEngProductEngineering(EngProductEngineering engProductEngineering);

    /**
     * 修改产品工艺管理
     * 
     * @param engProductEngineering 产品工艺管理
     * @return 结果
     */
    public int updateEngProductEngineering(EngProductEngineering engProductEngineering);

    /**
     * 批量删除产品工艺管理
     * 
     * @param ids 需要删除的产品工艺管理主键集合
     * @return 结果
     */
    public int deleteEngProductEngineeringByIds(Long[] ids);

    /**
     * 删除产品工艺管理信息
     * 
     * @param id 产品工艺管理主键
     * @return 结果
     */
    public int deleteEngProductEngineeringById(Long id);

    /**
     * 继承工艺
     *
     * @param productId 产成品Id
     * @return 结果
     */
    public int inheritEngineering(Long productId) throws Exception;

    /**
     * 生成采购料号
     *
     * @param bom 物料清单项
     * @return 结果
     */
    public int generateCode(EngProductBom bom) throws Exception;


}
