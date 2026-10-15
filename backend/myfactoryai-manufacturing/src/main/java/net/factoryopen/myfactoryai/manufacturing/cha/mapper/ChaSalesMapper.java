package net.factoryopen.myfactoryai.manufacturing.cha.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.cha.domain.ChaSales;

/**
 * 销售员预设Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-26
 */
public interface ChaSalesMapper 
{
    /**
     * 查询销售员预设
     * 
     * @param id 销售员预设主键
     * @return 销售员预设
     */
    public ChaSales selectChaSalesById(Long id);

    /**
     * 查询销售员预设列表
     * 
     * @param chaSales 销售员预设
     * @return 销售员预设集合
     */
    public List<ChaSales> selectChaSalesList(ChaSales chaSales);

    /**
     * 查询销售员预设列表
     *
     * @param userId 用户
     * @return 销售员预设集合
     */
    public List<ChaSales> selectChaSalesListByUserId(Long userId);

    /**
     * 新增销售员预设
     * 
     * @param chaSales 销售员预设
     * @return 结果
     */
    public int insertChaSales(ChaSales chaSales);

    /**
     * 修改销售员预设
     * 
     * @param chaSales 销售员预设
     * @return 结果
     */
    public int updateChaSales(ChaSales chaSales);

    /**
     * 删除销售员预设
     * 
     * @param id 销售员预设主键
     * @return 结果
     */
    public int deleteChaSalesById(Long id);

    /**
     * 批量删除销售员预设
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChaSalesByIds(Long[] ids);

    /**
     * 查询销售员预设列表
     *
     * @param userId 用户
     * @return 销售员预设集合
     */
    public int deleteChaSalesByUserId(Long userId);
}
