package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasUnitMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasUnit;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasUnitService;

/**
 * 单位Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasUnitServiceImpl implements IBasUnitService 
{
    @Autowired
    private BasUnitMapper basUnitMapper;

    /**
     * 查询单位
     * 
     * @param id 单位主键
     * @return 单位
     */
    @Override
    public BasUnit selectBasUnitById(Long id)
    {
        return basUnitMapper.selectBasUnitById(id);
    }

    /**
     * 查询单位列表
     * 
     * @param basUnit 单位
     * @return 单位
     */
    @Override
    public List<BasUnit> selectBasUnitList(BasUnit basUnit)
    {
        return basUnitMapper.selectBasUnitList(basUnit);
    }

    /**
     * 新增单位
     * 
     * @param basUnit 单位
     * @return 结果
     */
    @Override
    public int insertBasUnit(BasUnit basUnit)
    {
        //创建戳
        basUnit.setCreateBy(SecurityUtils.getUsername());
        basUnit.setCreateTime(DateUtils.getNowDate());
        basUnit.setUpdateBy(SecurityUtils.getUsername());
        basUnit.setUpdateTime(DateUtils.getNowDate());
        return basUnitMapper.insertBasUnit(basUnit);
    }

    /**
     * 修改单位
     * 
     * @param basUnit 单位
     * @return 结果
     */
    @Override
    public int updateBasUnit(BasUnit basUnit)
    {
        basUnit.setUpdateBy(SecurityUtils.getUsername());
        basUnit.setUpdateTime(DateUtils.getNowDate());
        return basUnitMapper.updateBasUnit(basUnit);
    }

    /**
     * 批量删除单位
     * 
     * @param ids 需要删除的单位主键
     * @return 结果
     */
    @Override
    public int deleteBasUnitByIds(Long[] ids)
    {
        return basUnitMapper.deleteBasUnitByIds(ids);
    }

    /**
     * 删除单位信息
     * 
     * @param id 单位主键
     * @return 结果
     */
    @Override
    public int deleteBasUnitById(Long id)
    {
        return basUnitMapper.deleteBasUnitById(id);
    }
}
