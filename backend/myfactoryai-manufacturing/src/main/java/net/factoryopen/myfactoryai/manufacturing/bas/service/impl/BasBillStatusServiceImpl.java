package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasBillStatusMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasBillStatus;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasBillStatusService;

/**
 * 单据状态Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasBillStatusServiceImpl implements IBasBillStatusService 
{
    @Autowired
    private BasBillStatusMapper basBillStatusMapper;

    /**
     * 查询单据状态
     * 
     * @param id 单据状态主键
     * @return 单据状态
     */
    @Override
    public BasBillStatus selectBasBillStatusById(Long id)
    {
        return basBillStatusMapper.selectBasBillStatusById(id);
    }

    /**
     * 查询单据状态列表
     * 
     * @param basBillStatus 单据状态
     * @return 单据状态
     */
    @Override
    public List<BasBillStatus> selectBasBillStatusList(BasBillStatus basBillStatus)
    {
        return basBillStatusMapper.selectBasBillStatusList(basBillStatus);
    }

    /**
     * 新增单据状态
     * 
     * @param basBillStatus 单据状态
     * @return 结果
     */
    @Override
    public int insertBasBillStatus(BasBillStatus basBillStatus)
    {
        //创建戳
        basBillStatus.refreshCreatingStamp();
        return basBillStatusMapper.insertBasBillStatus(basBillStatus);
    }

    /**
     * 修改单据状态
     * 
     * @param basBillStatus 单据状态
     * @return 结果
     */
    @Override
    public int updateBasBillStatus(BasBillStatus basBillStatus)
    {
        basBillStatus.setUpdateTime(DateUtils.getNowDate());
        basBillStatus.setUpdateBy(SecurityUtils.getUsername());
        return basBillStatusMapper.updateBasBillStatus(basBillStatus);
    }

    /**
     * 批量删除单据状态
     * 
     * @param ids 需要删除的单据状态主键
     * @return 结果
     */
    @Override
    public int deleteBasBillStatusByIds(Long[] ids)
    {
        return basBillStatusMapper.deleteBasBillStatusByIds(ids);
    }

    /**
     * 删除单据状态信息
     * 
     * @param id 单据状态主键
     * @return 结果
     */
    @Override
    public int deleteBasBillStatusById(Long id)
    {
        return basBillStatusMapper.deleteBasBillStatusById(id);
    }
}
