package net.factoryopen.myfactoryai.system.service.impl;


import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.system.domain.SysBillcodeRule;
import net.factoryopen.myfactoryai.system.mapper.SysBillcodeRuleMapper;
import net.factoryopen.myfactoryai.system.service.ISysBillcodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单据规则Service业务层处理
 *
 * @author admin
 * @date 2022-08-30
 */
@Service
public class SysBillcodeRuleServiceImpl implements ISysBillcodeRuleService {
    @Autowired
    private SysBillcodeRuleMapper sysBillcodeRuleMapper;

    /**
     * 查询单据规则
     *
     * @param id 单据规则主键
     * @return 单据规则
     */
    @Override
    public SysBillcodeRule selectSysBillcodeRuleById(Long id) {
        return sysBillcodeRuleMapper.selectSysBillcodeRuleById(id);
    }

    /**
     * 查询单据规则列表
     *
     * @param sysBillcodeRule 单据规则
     * @return 单据规则
     */
    @Override
    public List<SysBillcodeRule> selectSysBillcodeRuleList(SysBillcodeRule sysBillcodeRule) {
        return sysBillcodeRuleMapper.selectSysBillcodeRuleList(sysBillcodeRule);
    }

    /**
     * 新增单据规则
     *
     * @param sysBillcodeRule 单据规则
     * @return 结果
     */
    @Override
    public int insertSysBillcodeRule(SysBillcodeRule sysBillcodeRule) {
        sysBillcodeRule.setCreateTime(DateUtils.getNowDate());
        return sysBillcodeRuleMapper.insertSysBillcodeRule(sysBillcodeRule);
    }

    /**
     * 修改单据规则
     *
     * @param sysBillcodeRule 单据规则
     * @return 结果
     */
    @Override
    public int updateSysBillcodeRule(SysBillcodeRule sysBillcodeRule) {
        sysBillcodeRule.setUpdateTime(DateUtils.getNowDate());
        return sysBillcodeRuleMapper.updateSysBillcodeRule(sysBillcodeRule);
    }

    /**
     * 批量删除单据规则
     *
     * @param ids 需要删除的单据规则主键
     * @return 结果
     */
    @Override
    public int deleteSysBillcodeRuleByIds(Long[] ids) {
        return sysBillcodeRuleMapper.deleteSysBillcodeRuleByIds(ids);
    }

    /**
     * 删除单据规则信息
     *
     * @param id 单据规则主键
     * @return 结果
     */
    @Override
    public int deleteSysBillcodeRuleById(Long id) {
        return sysBillcodeRuleMapper.deleteSysBillcodeRuleById(id);
    }
}
