package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnWoInfo;

/**
 * 工单信息Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface PlnWoInfoMapper 
{
    /**
     * 查询工单信息
     * 
     * @param id 工单信息主键
     * @return 工单信息
     */
    public PlnWoInfo selectPlnWoInfoById(Long id);

    /**
     * 查询工单信息列表
     * 
     * @param plnWoInfo 工单信息
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoList(PlnWoInfo plnWoInfo);

    /**
     * 查询工单信息列表
     *
     * @param productId 产品编码
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoListByProductId(Long productId);

    /**
     * 查询工单信息列表
     *
     * @param lineId 产线
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoListByLineId(Long lineId);

    /**
     * 查询工单信息列表
     *
     * @param mpsId 主计划
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoListByMpsId(Long mpsId);

    /**
     * 查询工单信息列表
     *
     * @param projectId 项目
     * @return 工单信息集合
     */
    public List<PlnWoInfo> selectPlnWoInfoListByProjectId(Long projectId);

    /**
     * 新增工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    public int insertPlnWoInfo(PlnWoInfo plnWoInfo);

    /**
     * 修改工单信息
     * 
     * @param plnWoInfo 工单信息
     * @return 结果
     */
    public int updatePlnWoInfo(PlnWoInfo plnWoInfo);

    /**
     * 删除工单信息
     * 
     * @param id 工单信息主键
     * @return 结果
     */
    public int deletePlnWoInfoById(Long id);

    /**
     * 批量删除工单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnWoInfoByIds(Long[] ids);

    /**
     * 查询工单信息列表
     *
     * @param productId 产品编码
     * @return 工单信息集合
     */
    public int deletePlnWoInfoByProductId(Long productId);

    /**
     * 查询工单信息列表
     *
     * @param lineId 产线
     * @return 工单信息集合
     */
    public int deletePlnWoInfoByLineId(Long lineId);

    /**
     * 查询工单信息列表
     *
     * @param mpsId 主计划
     * @return 工单信息集合
     */
    public int deletePlnWoInfoByMpsId(Long mpsId);

    /**
     * 查询工单信息列表
     *
     * @param projectId 项目
     * @return 工单信息集合
     */
    public int deletePlnWoInfoByProjectId(Long projectId);
}
