package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategorySop;

/**
 * 品类SOPService接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface IEngCategorySopService 
{
    /**
     * 查询品类SOP
     * 
     * @param id 品类SOP主键
     * @return 品类SOP
     */
    public EngCategorySop selectEngCategorySopById(Long id);

    /**
     * 查询品类SOP列表
     * 
     * @param engCategorySop 品类SOP
     * @return 品类SOP集合
     */
    public List<EngCategorySop> selectEngCategorySopList(EngCategorySop engCategorySop);

    /**
     * 新增品类SOP
     * 
     * @param engCategorySop 品类SOP
     * @return 结果
     */
    public int insertEngCategorySop(EngCategorySop engCategorySop);

    /**
     * 修改品类SOP
     * 
     * @param engCategorySop 品类SOP
     * @return 结果
     */
    public int updateEngCategorySop(EngCategorySop engCategorySop);

    /**
     * 批量删除品类SOP
     * 
     * @param ids 需要删除的品类SOP主键集合
     * @return 结果
     */
    public int deleteEngCategorySopByIds(Long[] ids);

    /**
     * 删除品类SOP信息
     * 
     * @param id 品类SOP主键
     * @return 结果
     */
    public int deleteEngCategorySopById(Long id);


}
