package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMrpWo;
import org.springframework.stereotype.Service;

/**
 * MRP工单项Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
@Service
public interface PlnMrpWoMapper 
{
    /**
     * 查询MRP工单项
     * 
     * @param id MRP工单项主键
     * @return MRP工单项
     */
    public PlnMrpWo selectPlnMrpWoById(Long id);

    /**
     * 查询MRP工单项列表
     * 
     * @param plnMrpWo MRP工单项
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoList(PlnMrpWo plnMrpWo);

    /**
     * 查询MRP工单项列表
     *
     * @param mpsId 主计划
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoListByMpsId(Long mpsId);

    /**
     * 查询MRP工单项列表
     *
     * @param productId 产品编码
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoListByProductId(Long productId);

    /**
     * 查询MRP工单项列表
     *
     * @param lineId 产线ID
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoListByLineId(Long lineId);

    /**
     * 查询MRP工单项列表
     *
     * @param soId 销售订单
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoListBySoId(Long soId);

    /**
     * 查询MRP工单项列表
     *
     * @param soItemId 订单项
     * @return MRP工单项集合
     */
    public List<PlnMrpWo> selectPlnMrpWoListBySoItemId(Long soItemId);

    /**
     * 新增MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    public int insertPlnMrpWo(PlnMrpWo plnMrpWo);

    /**
     * 修改MRP工单项
     * 
     * @param plnMrpWo MRP工单项
     * @return 结果
     */
    public int updatePlnMrpWo(PlnMrpWo plnMrpWo);

    /**
     * 删除MRP工单项
     * 
     * @param id MRP工单项主键
     * @return 结果
     */
    public int deletePlnMrpWoById(Long id);

    /**
     * 批量删除MRP工单项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMrpWoByIds(Long[] ids);

    /**
     * 查询MRP工单项列表
     *
     * @param mpsId 主计划
     * @return MRP工单项集合
     */
    public int deletePlnMrpWoByMpsId(Long mpsId);

    /**
     * 查询MRP工单项列表
     *
     * @param productId 产品编码
     * @return MRP工单项集合
     */
    public int deletePlnMrpWoByProductId(Long productId);

    /**
     * 查询MRP工单项列表
     *
     * @param lineId 产线ID
     * @return MRP工单项集合
     */
    public int deletePlnMrpWoByLineId(Long lineId);

    /**
     * 查询MRP工单项列表
     *
     * @param soId 销售订单
     * @return MRP工单项集合
     */
    public int deletePlnMrpWoBySoId(Long soId);

    /**
     * 查询MRP工单项列表
     *
     * @param soItemId 订单项
     * @return MRP工单项集合
     */
    public int deletePlnMrpWoBySoItemId(Long soItemId);
}
