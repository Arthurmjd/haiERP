package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasPayPatternMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasPayPattern;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasPayPatternService;

/**
 * 付款方式Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasPayPatternServiceImpl implements IBasPayPatternService 
{
    @Autowired
    private BasPayPatternMapper basPayPatternMapper;

    /**
     * 查询付款方式
     * 
     * @param id 付款方式主键
     * @return 付款方式
     */
    @Override
    public BasPayPattern selectBasPayPatternById(Long id)
    {
        return basPayPatternMapper.selectBasPayPatternById(id);
    }

    /**
     * 查询付款方式列表
     * 
     * @param basPayPattern 付款方式
     * @return 付款方式
     */
    @Override
    public List<BasPayPattern> selectBasPayPatternList(BasPayPattern basPayPattern)
    {
        return basPayPatternMapper.selectBasPayPatternList(basPayPattern);
    }

    /**
     * 新增付款方式
     * 
     * @param basPayPattern 付款方式
     * @return 结果
     */
    @Override
    public int insertBasPayPattern(BasPayPattern basPayPattern)
    {
        //创建戳
        basPayPattern.setCreateBy(SecurityUtils.getUsername());
        basPayPattern.setCreateTime(DateUtils.getNowDate());
        basPayPattern.setUpdateBy(SecurityUtils.getUsername());
        basPayPattern.setUpdateTime(DateUtils.getNowDate());
        return basPayPatternMapper.insertBasPayPattern(basPayPattern);
    }

    /**
     * 修改付款方式
     * 
     * @param basPayPattern 付款方式
     * @return 结果
     */
    @Override
    public int updateBasPayPattern(BasPayPattern basPayPattern)
    {
        basPayPattern.setUpdateBy(SecurityUtils.getUsername());
        basPayPattern.setUpdateTime(DateUtils.getNowDate());
        return basPayPatternMapper.updateBasPayPattern(basPayPattern);
    }

    /**
     * 批量删除付款方式
     * 
     * @param ids 需要删除的付款方式主键
     * @return 结果
     */
    @Override
    public int deleteBasPayPatternByIds(Long[] ids)
    {
        return basPayPatternMapper.deleteBasPayPatternByIds(ids);
    }

    /**
     * 删除付款方式信息
     * 
     * @param id 付款方式主键
     * @return 结果
     */
    @Override
    public int deleteBasPayPatternById(Long id)
    {
        return basPayPatternMapper.deleteBasPayPatternById(id);
    }
}
