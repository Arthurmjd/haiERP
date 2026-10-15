package net.factoryopen.myfactoryai.manufacturing.res.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLine;

/**
 * 产线管理Service接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
public interface IResLineService 
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
     * 批量删除产线管理
     * 
     * @param ids 需要删除的产线管理主键集合
     * @return 结果
     */
    public int deleteResLineByIds(Long[] ids);

    /**
     * 删除产线管理信息
     * 
     * @param id 产线管理主键
     * @return 结果
     */
    public int deleteResLineById(Long id);


}
