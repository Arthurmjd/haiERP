package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;

/**
 * 合同登记发布Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
public interface IOrdContractService 
{
    /**
     * 查询合同发布
     * 
     * @param id 合同发布主键
     * @return 合同发布
     */
    public OrdContract selectOrdContractById(Long id);

    /**
     * 查询合同发布列表
     * 
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractDraftList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractPublishingList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractProcessingList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractFinishingList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractHistoryList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractAllList(OrdContract ordContract);

    /**
     * 查询合同发布列表
     *
     * @param ordContract 合同发布
     * @return 合同发布集合
     */
    public List<OrdContract> selectOrdContractList4Adult(OrdContract ordContract);

    /**
     * 新增合同发布
     * 
     * @param ordContract 合同发布
     * @return 结果
     */
    public int insertOrdContract(OrdContract ordContract);

    /**
     * 修改合同发布
     * 
     * @param ordContract 合同发布
     * @return 结果
     */
    public int updateOrdContract(OrdContract ordContract);

    /**
     * 批量删除合同发布
     * 
     * @param ids 需要删除的合同发布主键集合
     * @return 结果
     */
    public int deleteOrdContractByIds(Long[] ids);

    /**
     * 删除合同发布信息
     * 
     * @param id 合同发布主键
     * @return 结果
     */
    public int deleteOrdContractById(Long id);

    /**
     * 发布
     *
     * @param ordContract 发布
     * @return 结果
     */
    public int enable(OrdContract ordContract);

    /**
     * 变更
     *
     * @param ordContract 变更
     * @return 结果
     */
    public int changeOrdContract(OrdContract ordContract);

    /**
     * 冻结
     *
     * @param ordContract 冻结
     * @return 结果
     */
    public int froze(OrdContract ordContract);

    /**
     * 解冻
     *
     * @param ordContract 解冻
     * @return 结果
     */
    public int unfroze(OrdContract ordContract);

    /**
     * 停用
     *
     * @param ordContract 停用
     * @return 结果
     */
    public int disable(OrdContract ordContract);

    /**
     * 强结
     *
     * @param ordContract 强结
     * @return 结果
     */
    public int finishforcely(OrdContract ordContract);

    /**
     *  转单
     *
     * @param ids 订单ID集合
     * @return 结果
     */
    public int toSo(Long[] ids) throws Exception;

    /**
     * 请求临时料号
     *
     * @param ordContract 合同
     * @return 结果
     */
    public int requestTempProduct(OrdContract ordContract) throws Exception;


}
