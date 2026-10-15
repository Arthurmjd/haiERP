package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngPruductSop;

/**
 * 产品SOPService接口
 * 
 * @author admin
 * @date 2024-07-11
 */
public interface IEngPruductSopService 
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
     * 批量删除产品SOP
     * 
     * @param ids 需要删除的产品SOP主键集合
     * @return 结果
     */
    public int deleteEngPruductSopByIds(Long[] ids);

    /**
     * 删除产品SOP信息
     * 
     * @param id 产品SOP主键
     * @return 结果
     */
    public int deleteEngPruductSopById(Long id);


}
