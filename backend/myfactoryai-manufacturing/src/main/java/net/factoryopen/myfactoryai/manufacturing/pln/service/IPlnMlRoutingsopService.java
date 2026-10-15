package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingsop;

/**
 * 批次SOPService接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IPlnMlRoutingsopService 
{
    /**
     * 查询批次SOP
     * 
     * @param id 批次SOP主键
     * @return 批次SOP
     */
    public PlnMlRoutingsop selectPlnMlRoutingsopById(Long id);

    /**
     * 查询批次SOP列表
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 批次SOP集合
     */
    public List<PlnMlRoutingsop> selectPlnMlRoutingsopList(PlnMlRoutingsop plnMlRoutingsop);

    /**
     * 新增批次SOP
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 结果
     */
    public int insertPlnMlRoutingsop(PlnMlRoutingsop plnMlRoutingsop);

    /**
     * 修改批次SOP
     * 
     * @param plnMlRoutingsop 批次SOP
     * @return 结果
     */
    public int updatePlnMlRoutingsop(PlnMlRoutingsop plnMlRoutingsop);

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的批次SOP主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingsopByIds(Long[] ids);

    /**
     * 删除批次SOP信息
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    public int deletePlnMlRoutingsopById(Long id);


}
