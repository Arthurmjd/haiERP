package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWo;
import org.springframework.stereotype.Service;

/**
 * 工单管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-12
 */
@Service
public interface PlnWoMapper 
{
    /**
     * 查询工单管理
     * 
     * @param id 工单管理主键
     * @return 工单管理
     */
    public PlnWo selectPlnWoById(Long id);

    /**
     * 查询工单管理列表
     * 
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoDraftList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoPublishingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoProcessingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoFinishingList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoHistoryList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param plnWo 工单管理
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoAllList(PlnWo plnWo);

    /**
     * 查询工单管理列表
     *
     * @param productId 产品编码
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListByProductId(Long productId);

    /**
     * 查询工单管理列表
     *
     * @param lineId 产线
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListByLineId(Long lineId);

    /**
     * 查询工单管理列表
     *
     * @param mpsId 主计划
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListByMpsId(Long mpsId);

    /**
     * 查询工单管理列表
     *
     * @param projectId 项目
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListByProjectId(Long projectId);

    /**
     * 查询工单管理列表
     *
     * @param soId 订单ID
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListBySoId(Long soId);

    /**
     * 查询工单管理列表
     *
     * @param soItemId 订单项ID
     * @return 工单管理集合
     */
    public List<PlnWo> selectPlnWoListBySoItemId(Long soItemId);

    /**
     * 新增工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    public int insertPlnWo(PlnWo plnWo);

    /**
     * 修改工单管理
     * 
     * @param plnWo 工单管理
     * @return 结果
     */
    public int updatePlnWo(PlnWo plnWo);

    /**
     * 删除工单管理
     * 
     * @param id 工单管理主键
     * @return 结果
     */
    public int deletePlnWoById(Long id);

    /**
     * 批量删除工单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoByIds(Long[] ids);

    /**
     * 查询工单管理列表
     *
     * @param productId 产品编码
     * @return 工单管理集合
     */
    public int deletePlnWoByProductId(Long productId);

    /**
     * 查询工单管理列表
     *
     * @param lineId 产线
     * @return 工单管理集合
     */
    public int deletePlnWoByLineId(Long lineId);

    /**
     * 查询工单管理列表
     *
     * @param mpsId 主计划
     * @return 工单管理集合
     */
    public int deletePlnWoByMpsId(Long mpsId);

    /**
     * 查询工单管理列表
     *
     * @param projectId 项目
     * @return 工单管理集合
     */
    public int deletePlnWoByProjectId(Long projectId);

    /**
     * 查询工单管理列表
     *
     * @param soId 订单ID
     * @return 工单管理集合
     */
    public int deletePlnWoBySoId(Long soId);

    /**
     * 查询工单管理列表
     *
     * @param soItemId 订单项ID
     * @return 工单管理集合
     */
    public int deletePlnWoBySoItemId(Long soItemId);
}
