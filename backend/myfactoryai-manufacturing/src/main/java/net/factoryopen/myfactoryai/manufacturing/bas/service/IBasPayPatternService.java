package net.factoryopen.myfactoryai.manufacturing.bas.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasPayPattern;

/**
 * 付款方式Service接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface IBasPayPatternService 
{
    /**
     * 查询付款方式
     * 
     * @param id 付款方式主键
     * @return 付款方式
     */
    public BasPayPattern selectBasPayPatternById(Long id);

    /**
     * 查询付款方式列表
     * 
     * @param basPayPattern 付款方式
     * @return 付款方式集合
     */
    public List<BasPayPattern> selectBasPayPatternList(BasPayPattern basPayPattern);

    /**
     * 新增付款方式
     * 
     * @param basPayPattern 付款方式
     * @return 结果
     */
    public int insertBasPayPattern(BasPayPattern basPayPattern);

    /**
     * 修改付款方式
     * 
     * @param basPayPattern 付款方式
     * @return 结果
     */
    public int updateBasPayPattern(BasPayPattern basPayPattern);

    /**
     * 批量删除付款方式
     * 
     * @param ids 需要删除的付款方式主键集合
     * @return 结果
     */
    public int deleteBasPayPatternByIds(Long[] ids);

    /**
     * 删除付款方式信息
     * 
     * @param id 付款方式主键
     * @return 结果
     */
    public int deleteBasPayPatternById(Long id);
}
