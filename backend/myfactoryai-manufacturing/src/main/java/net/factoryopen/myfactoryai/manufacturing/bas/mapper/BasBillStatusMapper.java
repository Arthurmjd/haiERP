package net.factoryopen.myfactoryai.manufacturing.bas.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasBillStatus;

/**
 * 单据状态Mapper接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface BasBillStatusMapper 
{
    /**
     * 查询单据状态
     * 
     * @param id 单据状态主键
     * @return 单据状态
     */
    public BasBillStatus selectBasBillStatusById(Long id);

    /**
     * 查询单据状态列表
     * 
     * @param basBillStatus 单据状态
     * @return 单据状态集合
     */
    public List<BasBillStatus> selectBasBillStatusList(BasBillStatus basBillStatus);

    /**
     * 新增单据状态
     * 
     * @param basBillStatus 单据状态
     * @return 结果
     */
    public int insertBasBillStatus(BasBillStatus basBillStatus);

    /**
     * 修改单据状态
     * 
     * @param basBillStatus 单据状态
     * @return 结果
     */
    public int updateBasBillStatus(BasBillStatus basBillStatus);

    /**
     * 删除单据状态
     * 
     * @param id 单据状态主键
     * @return 结果
     */
    public int deleteBasBillStatusById(Long id);

    /**
     * 批量删除单据状态
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasBillStatusByIds(Long[] ids);
}
