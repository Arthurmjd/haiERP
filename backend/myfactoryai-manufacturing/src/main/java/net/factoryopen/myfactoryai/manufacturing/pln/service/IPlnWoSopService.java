package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoSop;

/**
 * 工单SOPService接口
 * 
 * @author admin
 * @date 2024-07-12
 */
public interface IPlnWoSopService 
{
    /**
     * 查询工单SOP
     * 
     * @param id 工单SOP主键
     * @return 工单SOP
     */
    public PlnWoSop selectPlnWoSopById(Long id);

    /**
     * 查询工单SOP列表
     * 
     * @param plnWoSop 工单SOP
     * @return 工单SOP集合
     */
    public List<PlnWoSop> selectPlnWoSopList(PlnWoSop plnWoSop);

    /**
     * 新增工单SOP
     * 
     * @param plnWoSop 工单SOP
     * @return 结果
     */
    public int insertPlnWoSop(PlnWoSop plnWoSop);

    /**
     * 修改工单SOP
     * 
     * @param plnWoSop 工单SOP
     * @return 结果
     */
    public int updatePlnWoSop(PlnWoSop plnWoSop);

    /**
     * 批量删除工单SOP
     * 
     * @param ids 需要删除的工单SOP主键集合
     * @return 结果
     */
    public int deletePlnWoSopByIds(Long[] ids);

    /**
     * 删除工单SOP信息
     * 
     * @param id 工单SOP主键
     * @return 结果
     */
    public int deletePlnWoSopById(Long id);


}
