package net.factoryopen.myfactoryai.system.service;


import net.factoryopen.myfactoryai.system.domain.SysBillcodeRule;

import java.util.List;

/**
 * 单据规则Service接口
 *
 * @author admin
 * @date 2022-08-30
 */
public interface ISysBillcodeRuleService {
    /**
     * 查询单据规则
     *
     * @param id 单据规则主键
     * @return 单据规则
     */
    public SysBillcodeRule selectSysBillcodeRuleById(Long id);

    /**
     * 查询单据规则列表
     *
     * @param sysBillcodeRule 单据规则
     * @return 单据规则集合
     */
    public List<SysBillcodeRule> selectSysBillcodeRuleList(SysBillcodeRule sysBillcodeRule);

    /**
     * 新增单据规则
     *
     * @param sysBillcodeRule 单据规则
     * @return 结果
     */
    public int insertSysBillcodeRule(SysBillcodeRule sysBillcodeRule);

    /**
     * 修改单据规则
     *
     * @param sysBillcodeRule 单据规则
     * @return 结果
     */
    public int updateSysBillcodeRule(SysBillcodeRule sysBillcodeRule);

    /**
     * 批量删除单据规则
     *
     * @param ids 需要删除的单据规则主键集合
     * @return 结果
     */
    public int deleteSysBillcodeRuleByIds(Long[] ids);

    /**
     * 删除单据规则信息
     *
     * @param id 单据规则主键
     * @return 结果
     */
    public int deleteSysBillcodeRuleById(Long id);
}
