package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;

/**
 * 车间班组作业Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IExeMlOperatingService 
{
    /**
     * 查询车间班组作业
     * 
     * @param id 车间班组作业主键
     * @return 车间班组作业
     */
    public ExeMlOperating selectExeMlOperatingById(Long id);

    /**
     * 查询车间班组作业列表
     * 
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingList(ExeMlOperating exeMlOperating);

    /**
     * 查询车间班组作业列表
     *
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业集合
     */
    public List<ExeMlOperating> selectExeMlOperatingListByOperator(ExeMlOperating exeMlOperating);

    /**
     * 新增车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    public int insertExeMlOperating(ExeMlOperating exeMlOperating);

    /**
     * 修改车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    public int updateExeMlOperating(ExeMlOperating exeMlOperating);

    /**
     * 批量删除车间班组作业
     * 
     * @param ids 需要删除的车间班组作业主键集合
     * @return 结果
     */
    public int deleteExeMlOperatingByIds(Long[] ids);

    /**
     * 删除车间班组作业信息
     * 
     * @param id 车间班组作业主键
     * @return 结果
     */
    public int deleteExeMlOperatingById(Long id);

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
     * 请入库
     *
     * @param ids id集合
     * @return 结果
     */
    public int requestStockin(Long[] ids) throws Exception;

    /**
     * 请入库
     *
     * @param ids id集合
     * @return 结果
     */
    public int finishMl(Long[] ids) throws Exception;

    /**
     * 开工
     *
     * @param exeMlOperating 开工
     * @return 结果
     */
    public int startWork(ExeMlOperating exeMlOperating) throws Exception;

    /**
     * 完工
     *
     * @param exeMlOperating 完工
     * @return 结果
     */
    public int finishWork(ExeMlOperating exeMlOperating) throws Exception;


}
