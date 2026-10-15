package net.factoryopen.myfactoryai.manufacturing.bas.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.factoryopen.myfactoryai.manufacturing.bas.mapper.BasMaterialGradeMapper;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasMaterialGrade;
import net.factoryopen.myfactoryai.manufacturing.bas.service.IBasMaterialGradeService;

/**
 * 物料等级Service业务层处理
 * 
 * @author admin
 * @date 2024-03-08
 */
@Service
public class BasMaterialGradeServiceImpl implements IBasMaterialGradeService 
{
    @Autowired
    private BasMaterialGradeMapper basMaterialGradeMapper;

    /**
     * 查询物料等级
     * 
     * @param id 物料等级主键
     * @return 物料等级
     */
    @Override
    public BasMaterialGrade selectBasMaterialGradeById(Long id)
    {
        return basMaterialGradeMapper.selectBasMaterialGradeById(id);
    }

    /**
     * 查询物料等级列表
     * 
     * @param basMaterialGrade 物料等级
     * @return 物料等级
     */
    @Override
    public List<BasMaterialGrade> selectBasMaterialGradeList(BasMaterialGrade basMaterialGrade)
    {
        return basMaterialGradeMapper.selectBasMaterialGradeList(basMaterialGrade);
    }

    /**
     * 新增物料等级
     * 
     * @param basMaterialGrade 物料等级
     * @return 结果
     */
    @Override
    public int insertBasMaterialGrade(BasMaterialGrade basMaterialGrade)
    {
        //创建戳
        basMaterialGrade.setCreateBy(SecurityUtils.getUsername());
        basMaterialGrade.setCreateTime(DateUtils.getNowDate());
        basMaterialGrade.setUpdateBy(SecurityUtils.getUsername());
        basMaterialGrade.setUpdateTime(DateUtils.getNowDate());
        return basMaterialGradeMapper.insertBasMaterialGrade(basMaterialGrade);
    }

    /**
     * 修改物料等级
     * 
     * @param basMaterialGrade 物料等级
     * @return 结果
     */
    @Override
    public int updateBasMaterialGrade(BasMaterialGrade basMaterialGrade)
    {
        basMaterialGrade.setUpdateTime(DateUtils.getNowDate());
        basMaterialGrade.setUpdateBy(SecurityUtils.getUsername());
        return basMaterialGradeMapper.updateBasMaterialGrade(basMaterialGrade);
    }

    /**
     * 批量删除物料等级
     * 
     * @param ids 需要删除的物料等级主键
     * @return 结果
     */
    @Override
    public int deleteBasMaterialGradeByIds(Long[] ids)
    {
        return basMaterialGradeMapper.deleteBasMaterialGradeByIds(ids);
    }

    /**
     * 删除物料等级信息
     * 
     * @param id 物料等级主键
     * @return 结果
     */
    @Override
    public int deleteBasMaterialGradeById(Long id)
    {
        return basMaterialGradeMapper.deleteBasMaterialGradeById(id);
    }
}
