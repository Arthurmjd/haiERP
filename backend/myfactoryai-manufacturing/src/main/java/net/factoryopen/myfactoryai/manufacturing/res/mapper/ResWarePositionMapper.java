package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWarePosition;

/**
 * 储位Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
public interface ResWarePositionMapper 
{
    /**
     * 查询储位
     * 
     * @param id 储位主键
     * @return 储位
     */
    public ResWarePosition selectResWarePositionById(Long id);

    /**
     * 查询储位列表
     * 
     * @param resWarePosition 储位
     * @return 储位集合
     */
    public List<ResWarePosition> selectResWarePositionList(ResWarePosition resWarePosition);

    /**
     * 查询储位列表
     *
     * @param houseId 仓库
     * @return 储位集合
     */
    public List<ResWarePosition> selectResWarePositionListByHouseId(Long houseId);

    /**
     * 新增储位
     * 
     * @param resWarePosition 储位
     * @return 结果
     */
    public int insertResWarePosition(ResWarePosition resWarePosition);

    /**
     * 修改储位
     * 
     * @param resWarePosition 储位
     * @return 结果
     */
    public int updateResWarePosition(ResWarePosition resWarePosition);

    /**
     * 删除储位
     * 
     * @param id 储位主键
     * @return 结果
     */
    public int deleteResWarePositionById(Long id);

    /**
     * 批量删除储位
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResWarePositionByIds(Long[] ids);

    /**
     * 查询储位列表
     *
     * @param houseId 仓库
     * @return 储位集合
     */
    public int deleteResWarePositionByHouseId(Long houseId);
}
