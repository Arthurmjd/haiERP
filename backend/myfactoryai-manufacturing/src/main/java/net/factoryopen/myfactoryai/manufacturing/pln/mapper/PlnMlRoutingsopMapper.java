package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlRoutingsop;
import org.springframework.stereotype.Service;

/**
 * 批次SOPMapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public interface PlnMlRoutingsopMapper 
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
     * 查询批次SOP列表
     *
     * @param routingId 工序
     * @return 批次SOP集合
     */
    public List<PlnMlRoutingsop> selectPlnMlRoutingsopListByRoutingId(Long routingId);

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
     * 删除批次SOP
     * 
     * @param id 批次SOP主键
     * @return 结果
     */
    public int deletePlnMlRoutingsopById(Long id);

    /**
     * 批量删除批次SOP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlRoutingsopByIds(Long[] ids);

    /**
     * 查询批次SOP列表
     *
     * @param routingId 工序
     * @return 批次SOP集合
     */
    public int deletePlnMlRoutingsopByRoutingId(Long routingId);
}
