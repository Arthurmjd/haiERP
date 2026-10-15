package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductSop;

/**
 * 产品SOPMapper接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface EngPruductSopMapper 
{
    /**
     * 查询产品SOP
     * 
     * @param id 产品SOP主键
     * @return 产品SOP
     */
    public EngPruductSop selectEngPruductSopById(Long id);

    /**
     * 查询产品SOP列表
     * 
     * @param engPruductSop 产品SOP
     * @return 产品SOP集合
     */
    public List<EngPruductSop> selectEngPruductSopList(EngPruductSop engPruductSop);

    /**
     * 查询产品SOP列表
     *
     * @param productId 产品ID
     * @return 产品SOP集合
     */
    public List<EngPruductSop> selectEngPruductSopListByProductId(Long productId);

    /**
     * 新增产品SOP
     * 
     * @param engPruductSop 产品SOP
     * @return 结果
     */
    public int insertEngPruductSop(EngPruductSop engPruductSop);

    /**
     * 修改产品SOP
     * 
     * @param engPruductSop 产品SOP
     * @return 结果
     */
    public int updateEngPruductSop(EngPruductSop engPruductSop);

    /**
     * 删除产品SOP
     * 
     * @param id 产品SOP主键
     * @return 结果
     */
    public int deleteEngPruductSopById(Long id);

    /**
     * 批量删除产品SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngPruductSopByIds(Long[] ids);

    /**
     * 查询产品SOP列表
     *
     * @param productId 产品ID
     * @return 产品SOP集合
     */
    public int deleteEngPruductSopByProductId(Long productId);
}
