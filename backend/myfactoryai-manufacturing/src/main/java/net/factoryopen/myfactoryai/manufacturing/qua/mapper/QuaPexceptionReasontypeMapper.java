package net.factoryopen.myfactoryai.manufacturing.qua.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionReasontype;

/**
 * 采购质量异常原因Mapper接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface QuaPexceptionReasontypeMapper 
{
    /**
     * 查询采购质量异常原因
     * 
     * @param id 采购质量异常原因主键
     * @return 采购质量异常原因
     */
    public QuaPexceptionReasontype selectQuaPexceptionReasontypeById(Long id);

    /**
     * 查询采购质量异常原因列表
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 采购质量异常原因集合
     */
    public List<QuaPexceptionReasontype> selectQuaPexceptionReasontypeList(QuaPexceptionReasontype quaPexceptionReasontype);

    /**
     * 查询采购质量异常原因列表
     *
     * @param categoryId 异常分类ID
     * @return 采购质量异常原因集合
     */
    public List<QuaPexceptionReasontype> selectQuaPexceptionReasontypeListByCategoryId(Long categoryId);

    /**
     * 新增采购质量异常原因
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 结果
     */
    public int insertQuaPexceptionReasontype(QuaPexceptionReasontype quaPexceptionReasontype);

    /**
     * 修改采购质量异常原因
     * 
     * @param quaPexceptionReasontype 采购质量异常原因
     * @return 结果
     */
    public int updateQuaPexceptionReasontype(QuaPexceptionReasontype quaPexceptionReasontype);

    /**
     * 删除采购质量异常原因
     * 
     * @param id 采购质量异常原因主键
     * @return 结果
     */
    public int deleteQuaPexceptionReasontypeById(Long id);

    /**
     * 批量删除采购质量异常原因
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuaPexceptionReasontypeByIds(Long[] ids);

    /**
     * 查询采购质量异常原因列表
     *
     * @param categoryId 异常分类ID
     * @return 采购质量异常原因集合
     */
    public int deleteQuaPexceptionReasontypeByCategoryId(Long categoryId);
}
