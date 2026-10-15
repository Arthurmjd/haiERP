package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasInvoicePatternMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasInvoicePattern;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasInvoicePatternService;

/**
 * 开票方式Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasInvoicePatternServiceImpl implements IBasInvoicePatternService 
{
    @Autowired
    private BasInvoicePatternMapper basInvoicePatternMapper;

    /**
     * 查询开票方式
     * 
     * @param id 开票方式主键
     * @return 开票方式
     */
    @Override
    public BasInvoicePattern selectBasInvoicePatternById(Long id)
    {
        return basInvoicePatternMapper.selectBasInvoicePatternById(id);
    }

    /**
     * 查询开票方式列表
     * 
     * @param basInvoicePattern 开票方式
     * @return 开票方式
     */
    @Override
    public List<BasInvoicePattern> selectBasInvoicePatternList(BasInvoicePattern basInvoicePattern)
    {
        return basInvoicePatternMapper.selectBasInvoicePatternList(basInvoicePattern);
    }

    /**
     * 新增开票方式
     * 
     * @param basInvoicePattern 开票方式
     * @return 结果
     */
    @Override
    public int insertBasInvoicePattern(BasInvoicePattern basInvoicePattern)
    {
        basInvoicePattern.refreshCreatingStamp();
        return basInvoicePatternMapper.insertBasInvoicePattern(basInvoicePattern);
    }

    /**
     * 修改开票方式
     * 
     * @param basInvoicePattern 开票方式
     * @return 结果
     */
    @Override
    public int updateBasInvoicePattern(BasInvoicePattern basInvoicePattern)
    {
        basInvoicePattern.setUpdateTime(DateUtils.getNowDate());
        basInvoicePattern.setUpdateBy(SecurityUtils.getUsername());
        return basInvoicePatternMapper.updateBasInvoicePattern(basInvoicePattern);
    }

    /**
     * 批量删除开票方式
     * 
     * @param ids 需要删除的开票方式主键
     * @return 结果
     */
    @Override
    public int deleteBasInvoicePatternByIds(Long[] ids)
    {
        return basInvoicePatternMapper.deleteBasInvoicePatternByIds(ids);
    }

    /**
     * 删除开票方式信息
     * 
     * @param id 开票方式主键
     * @return 结果
     */
    @Override
    public int deleteBasInvoicePatternById(Long id)
    {
        return basInvoicePatternMapper.deleteBasInvoicePatternById(id);
    }
}
