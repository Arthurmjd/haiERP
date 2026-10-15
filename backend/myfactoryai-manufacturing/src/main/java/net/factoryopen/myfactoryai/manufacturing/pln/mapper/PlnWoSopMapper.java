package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoSop;

/**
 * 工单SOPMapper接口
 * 
 * @author admin
 * @date 2024-07-12
 */
public interface PlnWoSopMapper 
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
     * 查询工单SOP列表
     *
     * @param woId 工单ID
     * @return 工单SOP集合
     */
    public List<PlnWoSop> selectPlnWoSopListByWoId(Long woId);

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
     * 删除工单SOP
     * 
     * @param id 工单SOP主键
     * @return 结果
     */
    public int deletePlnWoSopById(Long id);

    /**
     * 批量删除工单SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoSopByIds(Long[] ids);

    /**
     * 查询工单SOP列表
     *
     * @param woId 工单ID
     * @return 工单SOP集合
     */
    public int deletePlnWoSopByWoId(Long woId);
}
