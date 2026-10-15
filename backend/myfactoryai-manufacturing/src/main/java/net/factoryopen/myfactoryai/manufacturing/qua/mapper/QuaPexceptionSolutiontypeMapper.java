package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionSolutiontype;

/**
 * 采购质量异常处理类型Mapper接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface QuaPexceptionSolutiontypeMapper 
{
    /**
     * 查询采购质量异常处理类型
     * 
     * @param id 采购质量异常处理类型主键
     * @return 采购质量异常处理类型
     */
    public QuaPexceptionSolutiontype selectQuaPexceptionSolutiontypeById(Long id);

    /**
     * 查询采购质量异常处理类型列表
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 采购质量异常处理类型集合
     */
    public List<QuaPexceptionSolutiontype> selectQuaPexceptionSolutiontypeList(QuaPexceptionSolutiontype quaPexceptionSolutiontype);

    /**
     * 查询采购质量异常处理类型列表
     *
     * @param categoryId 异常分类
     * @return 采购质量异常处理类型集合
     */
    public List<QuaPexceptionSolutiontype> selectQuaPexceptionSolutiontypeListByCategoryId(Long categoryId);

    /**
     * 新增采购质量异常处理类型
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 结果
     */
    public int insertQuaPexceptionSolutiontype(QuaPexceptionSolutiontype quaPexceptionSolutiontype);

    /**
     * 修改采购质量异常处理类型
     * 
     * @param quaPexceptionSolutiontype 采购质量异常处理类型
     * @return 结果
     */
    public int updateQuaPexceptionSolutiontype(QuaPexceptionSolutiontype quaPexceptionSolutiontype);

    /**
     * 删除采购质量异常处理类型
     * 
     * @param id 采购质量异常处理类型主键
     * @return 结果
     */
    public int deleteQuaPexceptionSolutiontypeById(Long id);

    /**
     * 批量删除采购质量异常处理类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaPexceptionSolutiontypeByIds(Long[] ids);

    /**
     * 查询采购质量异常处理类型列表
     *
     * @param categoryId 异常分类
     * @return 采购质量异常处理类型集合
     */
    public int deleteQuaPexceptionSolutiontypeByCategoryId(Long categoryId);
}
