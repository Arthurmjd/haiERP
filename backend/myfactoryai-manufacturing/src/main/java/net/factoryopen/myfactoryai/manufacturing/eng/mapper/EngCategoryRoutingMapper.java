package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngCategoryRouting;

/**
 * 品类工艺路线Mapper接口
 * 
 * @author admin
 * @date 2024-11-09
 */
public interface EngCategoryRoutingMapper 
{
    /**
     * 查询品类工艺路线
     * 
     * @param id 品类工艺路线主键
     * @return 品类工艺路线
     */
    public EngCategoryRouting selectEngCategoryRoutingById(Long id);

    /**
     * 查询品类工艺路线列表
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 品类工艺路线集合
     */
    public List<EngCategoryRouting> selectEngCategoryRoutingList(EngCategoryRouting engCategoryRouting);

    /**
     * 查询品类工艺路线列表
     *
     * @param categoryId 品类ID
     * @return 品类工艺路线集合
     */
    public List<EngCategoryRouting> selectEngCategoryRoutingListByCategoryId(Long categoryId);

    /**
     * 查询品类工艺路线列表
     *
     * @param processId 工序ID
     * @return 品类工艺路线集合
     */
    public List<EngCategoryRouting> selectEngCategoryRoutingListByProcessId(Long processId);

    /**
     * 新增品类工艺路线
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 结果
     */
    public int insertEngCategoryRouting(EngCategoryRouting engCategoryRouting);

    /**
     * 修改品类工艺路线
     * 
     * @param engCategoryRouting 品类工艺路线
     * @return 结果
     */
    public int updateEngCategoryRouting(EngCategoryRouting engCategoryRouting);

    /**
     * 删除品类工艺路线
     * 
     * @param id 品类工艺路线主键
     * @return 结果
     */
    public int deleteEngCategoryRoutingById(Long id);

    /**
     * 批量删除品类工艺路线
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngCategoryRoutingByIds(Long[] ids);

    /**
     * 查询品类工艺路线列表
     *
     * @param categoryId 品类ID
     * @return 品类工艺路线集合
     */
    public int deleteEngCategoryRoutingByCategoryId(Long categoryId);

    /**
     * 查询品类工艺路线列表
     *
     * @param processId 工序ID
     * @return 品类工艺路线集合
     */
    public int deleteEngCategoryRoutingByProcessId(Long processId);
}
