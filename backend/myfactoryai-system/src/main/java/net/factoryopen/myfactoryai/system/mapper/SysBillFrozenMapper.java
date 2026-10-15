package net.factoryopen.myfactoryai.system.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;

/**
 * 单对象冻结Mapper接口
 * 
 * @author admin
 * @date 2024-06-18
 */
public interface SysBillFrozenMapper 
{
    /**
     * 查询单对象冻结
     * 
     * @param id 单对象冻结主键
     * @return 单对象冻结
     */
    public SysBillFrozen selectSysBillFrozenById(Long id);

    /**
     * 查询单对象冻结列表
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 单对象冻结集合
     */
    public List<SysBillFrozen> selectSysBillFrozenList(SysBillFrozen sysBillFrozen);

    /**
     * 新增单对象冻结
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 结果
     */
    public int insertSysBillFrozen(SysBillFrozen sysBillFrozen);

    /**
     * 修改单对象冻结
     * 
     * @param sysBillFrozen 单对象冻结
     * @return 结果
     */
    public int updateSysBillFrozen(SysBillFrozen sysBillFrozen);

    /**
     * 删除单对象冻结
     * 
     * @param id 单对象冻结主键
     * @return 结果
     */
    public int deleteSysBillFrozenById(Long id);

    /**
     * 批量删除单对象冻结
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBillFrozenByIds(Long[] ids);
}
