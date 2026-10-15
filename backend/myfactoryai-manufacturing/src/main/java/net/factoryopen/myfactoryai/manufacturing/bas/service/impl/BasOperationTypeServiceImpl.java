package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasOperationTypeMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasOperationType;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasOperationTypeService;

/**
 * 作业类型Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasOperationTypeServiceImpl implements IBasOperationTypeService 
{
    @Autowired
    private BasOperationTypeMapper basOperationTypeMapper;

    /**
     * 查询作业类型
     * 
     * @param id 作业类型主键
     * @return 作业类型
     */
    @Override
    public BasOperationType selectBasOperationTypeById(Long id)
    {
        return basOperationTypeMapper.selectBasOperationTypeById(id);
    }

    /**
     * 查询作业类型列表
     * 
     * @param basOperationType 作业类型
     * @return 作业类型
     */
    @Override
    public List<BasOperationType> selectBasOperationTypeList(BasOperationType basOperationType)
    {
        return basOperationTypeMapper.selectBasOperationTypeList(basOperationType);
    }

    /**
     * 新增作业类型
     * 
     * @param basOperationType 作业类型
     * @return 结果
     */
    @Override
    public int insertBasOperationType(BasOperationType basOperationType)
    {
        //创建戳
        basOperationType.setCreateBy(SecurityUtils.getUsername());
        basOperationType.setCreateTime(DateUtils.getNowDate());
        basOperationType.setUpdateBy(SecurityUtils.getUsername());
        basOperationType.setUpdateTime(DateUtils.getNowDate());
        return basOperationTypeMapper.insertBasOperationType(basOperationType);
    }

    /**
     * 修改作业类型
     * 
     * @param basOperationType 作业类型
     * @return 结果
     */
    @Override
    public int updateBasOperationType(BasOperationType basOperationType)
    {
        basOperationType.setUpdateBy(SecurityUtils.getUsername());
        basOperationType.setUpdateTime(DateUtils.getNowDate());
        return basOperationTypeMapper.updateBasOperationType(basOperationType);
    }

    /**
     * 批量删除作业类型
     * 
     * @param ids 需要删除的作业类型主键
     * @return 结果
     */
    @Override
    public int deleteBasOperationTypeByIds(Long[] ids)
    {
        return basOperationTypeMapper.deleteBasOperationTypeByIds(ids);
    }

    /**
     * 删除作业类型信息
     * 
     * @param id 作业类型主键
     * @return 结果
     */
    @Override
    public int deleteBasOperationTypeById(Long id)
    {
        return basOperationTypeMapper.deleteBasOperationTypeById(id);
    }
}
