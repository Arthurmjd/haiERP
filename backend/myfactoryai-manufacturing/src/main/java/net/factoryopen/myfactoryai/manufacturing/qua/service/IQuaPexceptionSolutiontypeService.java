package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaPexceptionSolutiontype;

/**
 * 采购质量异常处理类型Service接口
 * 
 * @author admin
 * @date 2024-11-07
 */
public interface IQuaPexceptionSolutiontypeService 
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
     * 批量删除采购质量异常处理类型
     * 
     * @param ids 需要删除的采购质量异常处理类型主键集合
     * @return 结果
     */
    public int deleteQuaPexceptionSolutiontypeByIds(Long[] ids);

    /**
     * 删除采购质量异常处理类型信息
     * 
     * @param id 采购质量异常处理类型主键
     * @return 结果
     */
    public int deleteQuaPexceptionSolutiontypeById(Long id);


}
