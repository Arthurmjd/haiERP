package net.factoryopen.myfactoryai.manufacturing.pln.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDispatching;

/**
 * 上线派工Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IPlnMlDispatchingService 
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
     * 批量删除上线派工
     * 
     * @param ids 需要删除的上线派工主键集合
     * @return 结果
     */
    public int deletePlnMlDispatchingByIds(Long[] ids);

    /**
     * 删除上线派工信息
     * 
     * @param id 上线派工主键
     * @return 结果
     */
    public int deletePlnMlDispatchingById(Long id);

    /**
     * 批派
     *
     * @param plnMlDispatching 批派
     * @return 结果
     */
    public int dispatchMultipleMl(Long[] ids, PlnMlDispatching plnMlDispatching) throws Exception;

    /**
     * 单派
     *
     * @param plnMlDispatching 单派
     * @return 结果
     */
    public int dispatchSingleMl(PlnMlDispatching plnMlDispatching) throws Exception;


}
