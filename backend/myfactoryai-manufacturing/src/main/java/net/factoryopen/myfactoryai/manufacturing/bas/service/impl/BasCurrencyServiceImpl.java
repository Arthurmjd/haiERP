package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasCurrencyMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasCurrency;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasCurrencyService;

/**
 * 货币Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasCurrencyServiceImpl implements IBasCurrencyService 
{
    @Autowired
    private BasCurrencyMapper basCurrencyMapper;

    /**
     * 查询货币
     * 
     * @param id 货币主键
     * @return 货币
     */
    @Override
    public BasCurrency selectBasCurrencyById(Long id)
    {
        return basCurrencyMapper.selectBasCurrencyById(id);
    }

    /**
     * 查询货币列表
     * 
     * @param basCurrency 货币
     * @return 货币
     */
    @Override
    public List<BasCurrency> selectBasCurrencyList(BasCurrency basCurrency)
    {
        return basCurrencyMapper.selectBasCurrencyList(basCurrency);
    }

    /**
     * 新增货币
     * 
     * @param basCurrency 货币
     * @return 结果
     */
    @Override
    public int insertBasCurrency(BasCurrency basCurrency)
    {
        basCurrency.refreshCreatingStamp();
        return basCurrencyMapper.insertBasCurrency(basCurrency);
    }

    /**
     * 修改货币
     * 
     * @param basCurrency 货币
     * @return 结果
     */
    @Override
    public int updateBasCurrency(BasCurrency basCurrency)
    {
        basCurrency.setUpdateTime(DateUtils.getNowDate());
        basCurrency.setUpdateBy(SecurityUtils.getUsername());
        return basCurrencyMapper.updateBasCurrency(basCurrency);
    }

    /**
     * 批量删除货币
     * 
     * @param ids 需要删除的货币主键
     * @return 结果
     */
    @Override
    public int deleteBasCurrencyByIds(Long[] ids)
    {
        return basCurrencyMapper.deleteBasCurrencyByIds(ids);
    }

    /**
     * 删除货币信息
     * 
     * @param id 货币主键
     * @return 结果
     */
    @Override
    public int deleteBasCurrencyById(Long id)
    {
        return basCurrencyMapper.deleteBasCurrencyById(id);
    }
}
