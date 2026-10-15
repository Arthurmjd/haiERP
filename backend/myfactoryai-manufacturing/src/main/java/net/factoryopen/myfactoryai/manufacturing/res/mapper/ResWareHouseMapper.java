package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWareHouse;

/**
 * 仓库管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
public interface ResWareHouseMapper 
{
    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    public ResWareHouse selectResWareHouseById(Long id);

    /**
     * 查询仓库管理列表
     * 
     * @param resWareHouse 仓库管理
     * @return 仓库管理集合
     */
    public List<ResWareHouse> selectResWareHouseList(ResWareHouse resWareHouse);

    /**
     * 新增仓库管理
     * 
     * @param resWareHouse 仓库管理
     * @return 结果
     */
    public int insertResWareHouse(ResWareHouse resWareHouse);

    /**
     * 修改仓库管理
     * 
     * @param resWareHouse 仓库管理
     * @return 结果
     */
    public int updateResWareHouse(ResWareHouse resWareHouse);

    /**
     * 删除仓库管理
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    public int deleteResWareHouseById(Long id);

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResWareHouseByIds(Long[] ids);
}
