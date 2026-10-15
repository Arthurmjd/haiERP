package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;

/**
 * 工艺处理请求Mapper接口
 * 
 * @author admin
 * @date 2024-12-12
 */
public interface EngErMapper 
{
    /**
     * 查询工艺处理请求
     * 
     * @param id 工艺处理请求主键
     * @return 工艺处理请求
     */
    public EngEr selectEngErById(Long id);

    /**
     * 查询工艺处理请求列表
     * 
     * @param engEr 工艺处理请求
     * @return 工艺处理请求集合
     */
    public List<EngEr> selectEngErList(EngEr engEr);

    /**
     * 新增工艺处理请求
     * 
     * @param engEr 工艺处理请求
     * @return 结果
     */
    public int insertEngEr(EngEr engEr);

    /**
     * 修改工艺处理请求
     * 
     * @param engEr 工艺处理请求
     * @return 结果
     */
    public int updateEngEr(EngEr engEr);

    /**
     * 删除工艺处理请求
     * 
     * @param id 工艺处理请求主键
     * @return 结果
     */
    public int deleteEngErById(Long id);

    /**
     * 批量删除工艺处理请求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngErByIds(Long[] ids);
}
