package net.factoryopen.myfactoryai.manufacturing.bas.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasShipmentType;

/**
 * 交货方式Service接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface IBasShipmentTypeService 
{
    /**
     * 查询交货方式
     * 
     * @param id 交货方式主键
     * @return 交货方式
     */
    public BasShipmentType selectBasShipmentTypeById(Long id);

    /**
     * 查询交货方式列表
     * 
     * @param basShipmentType 交货方式
     * @return 交货方式集合
     */
    public List<BasShipmentType> selectBasShipmentTypeList(BasShipmentType basShipmentType);

    /**
     * 新增交货方式
     * 
     * @param basShipmentType 交货方式
     * @return 结果
     */
    public int insertBasShipmentType(BasShipmentType basShipmentType);

    /**
     * 修改交货方式
     * 
     * @param basShipmentType 交货方式
     * @return 结果
     */
    public int updateBasShipmentType(BasShipmentType basShipmentType);

    /**
     * 批量删除交货方式
     * 
     * @param ids 需要删除的交货方式主键集合
     * @return 结果
     */
    public int deleteBasShipmentTypeByIds(Long[] ids);

    /**
     * 删除交货方式信息
     * 
     * @param id 交货方式主键
     * @return 结果
     */
    public int deleteBasShipmentTypeById(Long id);
}
