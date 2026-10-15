package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasShipmentTypeMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasShipmentType;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasShipmentTypeService;

/**
 * 交货方式Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasShipmentTypeServiceImpl implements IBasShipmentTypeService 
{
    @Autowired
    private BasShipmentTypeMapper basShipmentTypeMapper;

    /**
     * 查询交货方式
     * 
     * @param id 交货方式主键
     * @return 交货方式
     */
    @Override
    public BasShipmentType selectBasShipmentTypeById(Long id)
    {
        return basShipmentTypeMapper.selectBasShipmentTypeById(id);
    }

    /**
     * 查询交货方式列表
     * 
     * @param basShipmentType 交货方式
     * @return 交货方式
     */
    @Override
    public List<BasShipmentType> selectBasShipmentTypeList(BasShipmentType basShipmentType)
    {
        return basShipmentTypeMapper.selectBasShipmentTypeList(basShipmentType);
    }

    /**
     * 新增交货方式
     * 
     * @param basShipmentType 交货方式
     * @return 结果
     */
    @Override
    public int insertBasShipmentType(BasShipmentType basShipmentType)
    {
        //创建戳
        basShipmentType.setCreateBy(SecurityUtils.getUsername());
        basShipmentType.setCreateTime(DateUtils.getNowDate());
        basShipmentType.setUpdateBy(SecurityUtils.getUsername());
        basShipmentType.setUpdateTime(DateUtils.getNowDate());
        return basShipmentTypeMapper.insertBasShipmentType(basShipmentType);
    }

    /**
     * 修改交货方式
     * 
     * @param basShipmentType 交货方式
     * @return 结果
     */
    @Override
    public int updateBasShipmentType(BasShipmentType basShipmentType)
    {
        basShipmentType.setUpdateBy(SecurityUtils.getUsername());
        basShipmentType.setUpdateTime(DateUtils.getNowDate());
        return basShipmentTypeMapper.updateBasShipmentType(basShipmentType);
    }

    /**
     * 批量删除交货方式
     * 
     * @param ids 需要删除的交货方式主键
     * @return 结果
     */
    @Override
    public int deleteBasShipmentTypeByIds(Long[] ids)
    {
        return basShipmentTypeMapper.deleteBasShipmentTypeByIds(ids);
    }

    /**
     * 删除交货方式信息
     * 
     * @param id 交货方式主键
     * @return 结果
     */
    @Override
    public int deleteBasShipmentTypeById(Long id)
    {
        return basShipmentTypeMapper.deleteBasShipmentTypeById(id);
    }
}
