package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlSop;
import org.springframework.stereotype.Service;

/**
 * 批次SOPMapper接口
 * 
 * @author admin
 * @date 2024-07-13
 */
@Service
public interface PlnMlSopMapper 
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
     * 查询批次SOP列表
     *
     * @param mlId 批次ID
     * @return 批次SOP集合
     */
    public List<PlnMlSop> selectPlnMlSopListByMlId(Long mlId);

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
     * 删除批次SOP
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    public int deletePlnMlSopById(Long id);

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlSopByIds(Long[] ids);

    /**
     * 查询批次SOP列表
     *
     * @param mlId 批次ID
     * @return 批次SOP集合
     */
    public int deletePlnMlSopByMlId(Long mlId);
}
