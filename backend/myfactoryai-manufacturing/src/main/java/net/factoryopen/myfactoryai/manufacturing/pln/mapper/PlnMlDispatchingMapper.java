package net.factoryopen.myfactoryai.manufacturing.pln.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDispatching;

/**
 * 上线派工Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface PlnMlDispatchingMapper 
{
    /**
     * 查询上线派工
     * 
     * @param id 上线派工主键
     * @return 上线派工
     */
    public PlnMlDispatching selectPlnMlDispatchingById(Long id);

    /**
     * 查询上线派工列表
     * 
     * @param plnMlDispatching 上线派工
     * @return 上线派工集合
     */
    public List<PlnMlDispatching> selectPlnMlDispatchingList(PlnMlDispatching plnMlDispatching);

    /**
     * 查询上线派工列表
     *
     * @param productId 产品编码
     * @return 上线派工集合
     */
    public List<PlnMlDispatching> selectPlnMlDispatchingListByProductId(Long productId);

    /**
     * 查询上线派工列表
     *
     * @param lineId 产线
     * @return 上线派工集合
     */
    public List<PlnMlDispatching> selectPlnMlDispatchingListByLineId(Long lineId);

    /**
     * 查询上线派工列表
     *
     * @param mpsId 主计划
     * @return 上线派工集合
     */
    public List<PlnMlDispatching> selectPlnMlDispatchingListByMpsId(Long mpsId);

    /**
     * 查询上线派工列表
     *
     * @param projectId 项目
     * @return 上线派工集合
     */
    public List<PlnMlDispatching> selectPlnMlDispatchingListByProjectId(Long projectId);

    /**
     * 新增上线派工
     * 
     * @param plnMlDispatching 上线派工
     * @return 结果
     */
    public int insertPlnMlDispatching(PlnMlDispatching plnMlDispatching);

    /**
     * 修改上线派工
     * 
     * @param plnMlDispatching 上线派工
     * @return 结果
     */
    public int updatePlnMlDispatching(PlnMlDispatching plnMlDispatching);

    /**
     * 删除上线派工
     * 
     * @param id 上线派工主键
     * @return 结果
     */
    public int deletePlnMlDispatchingById(Long id);

    /**
     * 批量删除上线派工
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlnMlDispatchingByIds(Long[] ids);

    /**
     * 查询上线派工列表
     *
     * @param productId 产品编码
     * @return 上线派工集合
     */
    public int deletePlnMlDispatchingByProductId(Long productId);

    /**
     * 查询上线派工列表
     *
     * @param lineId 产线
     * @return 上线派工集合
     */
    public int deletePlnMlDispatchingByLineId(Long lineId);

    /**
     * 查询上线派工列表
     *
     * @param mpsId 主计划
     * @return 上线派工集合
     */
    public int deletePlnMlDispatchingByMpsId(Long mpsId);

    /**
     * 查询上线派工列表
     *
     * @param projectId 项目
     * @return 上线派工集合
     */
    public int deletePlnMlDispatchingByProjectId(Long projectId);
}
