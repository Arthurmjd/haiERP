package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessOperating;

/**
 * 产线班组作业Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IExeProcessOperatingService 
{
    /**
     * 查询产线班组作业
     * 
     * @param id 产线班组作业主键
     * @return 产线班组作业
     */
    public ExeProcessOperating selectExeProcessOperatingById(Long id);

    /**
     * 查询产线班组作业列表
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 产线班组作业集合
     */
    public List<ExeProcessOperating> selectExeProcessOperatingList(ExeProcessOperating exeProcessOperating);

    /**
     * 新增产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    public int insertExeProcessOperating(ExeProcessOperating exeProcessOperating);

    /**
     * 修改产线班组作业
     * 
     * @param exeProcessOperating 产线班组作业
     * @return 结果
     */
    public int updateExeProcessOperating(ExeProcessOperating exeProcessOperating);

    /**
     * 批量删除产线班组作业
     * 
     * @param ids 需要删除的产线班组作业主键集合
     * @return 结果
     */
    public int deleteExeProcessOperatingByIds(Long[] ids);

    /**
     * 删除产线班组作业信息
     * 
     * @param id 产线班组作业主键
     * @return 结果
     */
    public int deleteExeProcessOperatingById(Long id);

    /**
     * 接收
     *
     * @param ids id集合
     * @return 结果
     */
    public int acceptMl(Long[] ids) throws Exception;

    /**
     * 请料
     *
     * @param ids id集合
     * @return 结果
     */
    public int requestMaterial(Long[] ids) throws Exception;

    /**
     * 转出
     *
     * @param ids id集合
     * @return 结果
     */
    public int postMl(Long[] ids) throws Exception;

    /**
     * 开工
     *
     * @param exeProcessOperating 开工
     * @return 结果
     */
    public int startWork(ExeProcessOperating exeProcessOperating) throws Exception;

    /**
     * 完工
     *
     * @param exeProcessOperating 完工
     * @return 结果
     */
    public int finishWork(ExeProcessOperating exeProcessOperating) throws Exception;


}
