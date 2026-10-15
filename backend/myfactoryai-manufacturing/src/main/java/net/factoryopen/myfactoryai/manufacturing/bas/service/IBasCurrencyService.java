package net.factoryopen.myfactoryai.manufacturing.bas.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasCurrency;

/**
 * 货币Service接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface IBasCurrencyService 
{
    /**
     * 查询货币
     * 
     * @param id 货币主键
     * @return 货币
     */
    public BasCurrency selectBasCurrencyById(Long id);

    /**
     * 查询货币列表
     * 
     * @param basCurrency 货币
     * @return 货币集合
     */
    public List<BasCurrency> selectBasCurrencyList(BasCurrency basCurrency);

    /**
     * 新增货币
     * 
     * @param basCurrency 货币
     * @return 结果
     */
    public int insertBasCurrency(BasCurrency basCurrency);

    /**
     * 修改货币
     * 
     * @param basCurrency 货币
     * @return 结果
     */
    public int updateBasCurrency(BasCurrency basCurrency);

    /**
     * 批量删除货币
     * 
     * @param ids 需要删除的货币主键集合
     * @return 结果
     */
    public int deleteBasCurrencyByIds(Long[] ids);

    /**
     * 删除货币信息
     * 
     * @param id 货币主键
     * @return 结果
     */
    public int deleteBasCurrencyById(Long id);
}
