package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlSop;

/**
 * 批次SOPService接口
 * 
 * @author admin
 * @date 2024-07-13
 */
public interface IPlnMlSopService 
{
    /**
     * 查询批次SOP
     * 
     * @param id 批次SOP主键
     * @return 批次SOP
     */
    public PlnMlSop selectPlnMlSopById(Long id);

    /**
     * 查询批次SOP列表
     * 
     * @param plnMlSop 批次SOP
     * @return 批次SOP集合
     */
    public List<PlnMlSop> selectPlnMlSopList(PlnMlSop plnMlSop);

    /**
     * 新增批次SOP
     * 
     * @param plnMlSop 批次SOP
     * @return 结果
     */
    public int insertPlnMlSop(PlnMlSop plnMlSop);

    /**
     * 修改批次SOP
     * 
     * @param plnMlSop 批次SOP
     * @return 结果
     */
    public int updatePlnMlSop(PlnMlSop plnMlSop);

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的批次SOP主键集合
     * @return 结果
     */
    public int deletePlnMlSopByIds(Long[] ids);

    /**
     * 删除批次SOP信息
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    public int deletePlnMlSopById(Long id);


}
