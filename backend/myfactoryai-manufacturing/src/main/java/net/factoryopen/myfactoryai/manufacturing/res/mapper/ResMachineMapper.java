package net.factoryopen.myfactoryai.manufacturing.res.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResMachine;
import org.springframework.stereotype.Service;

/**
 * 机台管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2025-01-02
 */
@Service
public interface ResMachineMapper 
{
    /**
     * 查询机台管理
     * 
     * @param id 机台管理主键
     * @return 机台管理
     */
    public ResMachine selectResMachineById(Long id);
    /**
     * 查询机台管理
     *
     * @param code 机台编码
     * @return 机台管理
     */
    public ResMachine selectResMachineByCode(String code);

    /**
     * 查询机台管理列表
     * 
     * @param resMachine 机台管理
     * @return 机台管理集合
     */
    public List<ResMachine> selectResMachineList(ResMachine resMachine);

    /**
     * 查询机台管理列表
     *
     * @param typeId 机型ID
     * @return 机台管理集合
     */
    public List<ResMachine> selectResMachineListByTypeId(Long typeId);

    /**
     * 查询机台管理列表
     *
     * @param lineId 产线
     * @return 机台管理集合
     */
    public List<ResMachine> selectResMachineListByLineId(Long lineId);

    /**
     * 查询机台管理列表
     *
     * @param processId 工序ID
     * @return 机台管理集合
     */
    public List<ResMachine> selectResMachineListByProcessId(Long processId);

    /**
     * 新增机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    public int insertResMachine(ResMachine resMachine);

    /**
     * 修改机台管理
     * 
     * @param resMachine 机台管理
     * @return 结果
     */
    public int updateResMachine(ResMachine resMachine);

    /**
     * 删除机台管理
     * 
     * @param id 机台管理主键
     * @return 结果
     */
    public int deleteResMachineById(Long id);

    /**
     * 批量删除机台管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResMachineByIds(Long[] ids);

    /**
     * 查询机台管理列表
     *
     * @param typeId 机型ID
     * @return 机台管理集合
     */
    public int deleteResMachineByTypeId(Long typeId);

    /**
     * 查询机台管理列表
     *
     * @param lineId 产线
     * @return 机台管理集合
     */
    public int deleteResMachineByLineId(Long lineId);

    /**
     * 查询机台管理列表
     *
     * @param processId 工序ID
     * @return 机台管理集合
     */
    public int deleteResMachineByProcessId(Long processId);
}
