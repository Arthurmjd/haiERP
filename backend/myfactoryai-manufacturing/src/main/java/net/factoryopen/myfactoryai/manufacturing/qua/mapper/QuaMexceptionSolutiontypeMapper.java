package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaMexceptionSolutiontype;

/**
 * 质量异常处理类型Mapper接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface QuaMexceptionSolutiontypeMapper 
{
    /**
     * 查询质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 质量异常处理类型
     */
    public QuaMexceptionSolutiontype selectQuaMexceptionSolutiontypeById(Long id);

    /**
     * 查询质量异常处理类型列表
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 质量异常处理类型集合
     */
    public List<QuaMexceptionSolutiontype> selectQuaMexceptionSolutiontypeList(QuaMexceptionSolutiontype quaMexceptionSolutiontype);

    /**
     * 查询质量异常处理类型列表
     *
     * @param categoryId 分类ID
     * @return 质量异常处理类型集合
     */
    public List<QuaMexceptionSolutiontype> selectQuaMexceptionSolutiontypeListByCategoryId(Long categoryId);

    /**
     * 新增质量异常处理类型
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    public int insertQuaMexceptionSolutiontype(QuaMexceptionSolutiontype quaMexceptionSolutiontype);

    /**
     * 修改质量异常处理类型
     * 
     * @param quaMexceptionSolutiontype 质量异常处理类型
     * @return 结果
     */
    public int updateQuaMexceptionSolutiontype(QuaMexceptionSolutiontype quaMexceptionSolutiontype);

    /**
     * 删除质量异常处理类型
     * 
     * @param id 质量异常处理类型主键
     * @return 结果
     */
    public int deleteQuaMexceptionSolutiontypeById(Long id);

    /**
     * 批量删除质量异常处理类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaMexceptionSolutiontypeByIds(Long[] ids);

    /**
     * 查询质量异常处理类型列表
     *
     * @param categoryId 分类ID
     * @return 质量异常处理类型集合
     */
    public int deleteQuaMexceptionSolutiontypeByCategoryId(Long categoryId);
}
