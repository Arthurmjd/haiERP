package net.factoryopen.myfactoryai.system.mapper;


import net.factoryopen.myfactoryai.system.domain.SysBillcodeRule;

import java.util.List;

/**
 * 单据规则Mapper接口
 *
 * @author admin
 * @date 2022-08-30
 */
public interface SysBillcodeRuleMapper {
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
     * 删除单据规则
     *
     * @param id 单据规则主键
     * @return 结果
     */
    public int deleteSysBillcodeRuleById(Long id);

    /**
     * 批量删除单据规则
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBillcodeRuleByIds(Long[] ids);
}
