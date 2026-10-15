package net.factoryopen.myfactoryai.manufacturing.bas.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import org.springframework.stereotype.Service;

/**
 * 单位Mapper接口
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public interface BasUnitMapper 
{
    /**
     * 查询单位
     * 
     * @param id 单位主键
     * @return 单位
     */
    public BasUnit selectBasUnitById(Long id);

    /**
     * 查询单位列表
     * 
     * @param basUnit 单位
     * @return 单位集合
     */
    public List<BasUnit> selectBasUnitList(BasUnit basUnit);

    /**
     * 新增单位
     * 
     * @param basUnit 单位
     * @return 结果
     */
    public int insertBasUnit(BasUnit basUnit);

    /**
     * 修改单位
     * 
     * @param basUnit 单位
     * @return 结果
     */
    public int updateBasUnit(BasUnit basUnit);

    /**
     * 删除单位
     * 
     * @param id 单位主键
     * @return 结果
     */
    public int deleteBasUnitById(Long id);

    /**
     * 批量删除单位
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasUnitByIds(Long[] ids);
}
