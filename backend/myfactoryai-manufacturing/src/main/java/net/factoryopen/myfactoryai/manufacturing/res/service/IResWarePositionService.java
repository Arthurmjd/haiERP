package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWarePosition;

/**
 * 储位Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-08-11
 */
public interface IResWarePositionService 
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
     * 批量删除储位
     * 
     * @param ids 需要删除的储位主键集合
     * @return 结果
     */
    public int deleteResWarePositionByIds(Long[] ids);

    /**
     * 删除储位信息
     * 
     * @param id 储位主键
     * @return 结果
     */
    public int deleteResWarePositionById(Long id);


}
