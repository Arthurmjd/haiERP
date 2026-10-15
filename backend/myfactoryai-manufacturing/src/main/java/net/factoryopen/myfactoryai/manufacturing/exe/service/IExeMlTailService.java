package net.factoryopen.myfactoryai.manufacturing.exe.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlTail;

/**
 * 制造批次尾情Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
public interface IExeMlTailService 
{
    /**
     * 查询制造批次尾情
     * 
     * @param id 制造批次尾情主键
     * @return 制造批次尾情
     */
    public ExeMlTail selectExeMlTailById(Long id);

    /**
     * 查询制造批次尾情列表
     * 
     * @param exeMlTail 制造批次尾情
     * @return 制造批次尾情集合
     */
    public List<ExeMlTail> selectExeMlTailList(ExeMlTail exeMlTail);

    /**
     * 新增制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    public int insertExeMlTail(ExeMlTail exeMlTail);

    /**
     * 修改制造批次尾情
     * 
     * @param exeMlTail 制造批次尾情
     * @return 结果
     */
    public int updateExeMlTail(ExeMlTail exeMlTail);

    /**
     * 批量删除制造批次尾情
     * 
     * @param ids 需要删除的制造批次尾情主键集合
     * @return 结果
     */
    public int deleteExeMlTailByIds(Long[] ids);

    /**
     * 删除制造批次尾情信息
     * 
     * @param id 制造批次尾情主键
     * @return 结果
     */
    public int deleteExeMlTailById(Long id);


}
