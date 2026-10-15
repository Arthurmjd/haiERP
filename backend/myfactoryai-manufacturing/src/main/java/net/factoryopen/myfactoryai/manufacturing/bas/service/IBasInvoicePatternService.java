package net.factoryopen.myfactoryai.manufacturing.bas.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;

/**
 * 开票方式Service接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface IBasInvoicePatternService 
{
    /**
     * 查询开票方式
     * 
     * @param id 开票方式主键
     * @return 开票方式
     */
    public BasInvoicePattern selectBasInvoicePatternById(Long id);

    /**
     * 查询开票方式列表
     * 
     * @param basInvoicePattern 开票方式
     * @return 开票方式集合
     */
    public List<BasInvoicePattern> selectBasInvoicePatternList(BasInvoicePattern basInvoicePattern);

    /**
     * 新增开票方式
     * 
     * @param basInvoicePattern 开票方式
     * @return 结果
     */
    public int insertBasInvoicePattern(BasInvoicePattern basInvoicePattern);

    /**
     * 修改开票方式
     * 
     * @param basInvoicePattern 开票方式
     * @return 结果
     */
    public int updateBasInvoicePattern(BasInvoicePattern basInvoicePattern);

    /**
     * 批量删除开票方式
     * 
     * @param ids 需要删除的开票方式主键集合
     * @return 结果
     */
    public int deleteBasInvoicePatternByIds(Long[] ids);

    /**
     * 删除开票方式信息
     * 
     * @param id 开票方式主键
     * @return 结果
     */
    public int deleteBasInvoicePatternById(Long id);
}
