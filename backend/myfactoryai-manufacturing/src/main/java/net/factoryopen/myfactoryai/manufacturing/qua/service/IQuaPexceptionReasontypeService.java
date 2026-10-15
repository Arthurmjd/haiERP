package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionReasontype;

/**
 * 采购质量异常原因Service接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface IQuaPexceptionReasontypeService 
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
     * 批量删除采购质量异常原因
     * 
     * @param ids 需要删除的采购质量异常原因主键集合
     * @return 结果
     */
    public int deleteQuaPexceptionReasontypeByIds(Long[] ids);

    /**
     * 删除采购质量异常原因信息
     * 
     * @param id 采购质量异常原因主键
     * @return 结果
     */
    public int deleteQuaPexceptionReasontypeById(Long id);


}
