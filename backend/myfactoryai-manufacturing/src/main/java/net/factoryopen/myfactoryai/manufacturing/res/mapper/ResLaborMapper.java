package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import org.springframework.stereotype.Service;

/**
 * 工人管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public interface ResLaborMapper 
{
    /**
     * 查询工人管理
     * 
     * @param id 工人管理主键
     * @return 工人管理
     */
    public ResLabor selectResLaborById(Long id);

    /**
     * 查询工人管理列表
     * 
     * @param resLabor 工人管理
     * @return 工人管理集合
     */
    public List<ResLabor> selectResLaborList(ResLabor resLabor);

    /**
     * 查询工人管理列表
     *
     * @param workgroupId 班组
     * @return 工人管理集合
     */
    public List<ResLabor> selectResLaborListByWorkgroupId(Long workgroupId);

    /**
     * 查询工人管理列表
     *
     * @param userId 用户
     * @return 工人管理集合
     */
    public List<ResLabor> selectResLaborListByUserId(Long userId);

    /**
     * 新增工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    public int insertResLabor(ResLabor resLabor);

    /**
     * 修改工人管理
     * 
     * @param resLabor 工人管理
     * @return 结果
     */
    public int updateResLabor(ResLabor resLabor);

    /**
     * 删除工人管理
     * 
     * @param id 工人管理主键
     * @return 结果
     */
    public int deleteResLaborById(Long id);

    /**
     * 批量删除工人管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResLaborByIds(Long[] ids);

    /**
     * 查询工人管理列表
     *
     * @param workgroupId 班组
     * @return 工人管理集合
     */
    public int deleteResLaborByWorkgroupId(Long workgroupId);

    /**
     * 查询工人管理列表
     *
     * @param userId 用户
     * @return 工人管理集合
     */
    public int deleteResLaborByUserId(Long userId);
}
