package net.factoryopen.myfactoryai.manufacturing.eng.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;

/**
 * 工艺处理请求Service接口
 * 
 * @author admin
 * @date 2024-12-12
 */
public interface IEngErService 
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
     * 批量删除工艺处理请求
     * 
     * @param ids 需要删除的工艺处理请求主键集合
     * @return 结果
     */
    public int deleteEngErByIds(Long[] ids);

    /**
     * 删除工艺处理请求信息
     * 
     * @param id 工艺处理请求主键
     * @return 结果
     */
    public int deleteEngErById(Long id);

    /**
     * 完成
     *
     * @param engEr 完成
     * @return 结果
     */
    public int finish(EngEr engEr);

    /**
     * 生成产品料号
     *
     * @param engEr 生成料号
     * @return 结果
     */
    public int generateCode(EngEr engEr) throws Exception;

    /**
     * 维护BOM
     *
     * @param engEr 维护BOM
     * @return 结果
     */
    public int updateBom(EngEr engEr) throws Exception;

    /**
     * 维护路线
     *
     * @param engEr 维护路线
     * @return 结果
     */
    public int updateRouting(EngEr engEr) throws Exception;

    /**
     * 维护文档
     *
     * @param engEr 维护文档
     * @return 结果
     */
    public int updateDocs(EngEr engEr) throws Exception;


}
