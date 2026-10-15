package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLine;

/**
 * 产线管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface ResLineMapper 
{
    /**
     * 查询产线管理
     * 
     * @param id 产线管理主键
     * @return 产线管理
     */
    public ResLine selectResLineById(Long id);

    /**
     * 查询产线管理列表
     * 
     * @param resLine 产线管理
     * @return 产线管理集合
     */
    public List<ResLine> selectResLineList(ResLine resLine);

    /**
     * 查询产线管理列表
     *
     * @param supplierId 外协厂商
     * @return 产线管理集合
     */
    public List<ResLine> selectResLineListBySupplierId(Long supplierId);

    /**
     * 新增产线管理
     * 
     * @param resLine 产线管理
     * @return 结果
     */
    public int insertResLine(ResLine resLine);

    /**
     * 修改产线管理
     * 
     * @param resLine 产线管理
     * @return 结果
     */
    public int updateResLine(ResLine resLine);

    /**
     * 删除产线管理
     * 
     * @param id 产线管理主键
     * @return 结果
     */
    public int deleteResLineById(Long id);

    /**
     * 批量删除产线管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResLineByIds(Long[] ids);

    /**
     * 查询产线管理列表
     *
     * @param supplierId 外协厂商
     * @return 产线管理集合
     */
    public int deleteResLineBySupplierId(Long supplierId);
}
