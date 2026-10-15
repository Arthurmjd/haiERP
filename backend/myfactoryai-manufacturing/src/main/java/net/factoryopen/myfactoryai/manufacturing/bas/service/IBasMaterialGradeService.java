package net.factoryopen.myfactoryai.manufacturing.bas.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.bas.domain.BasMaterialGrade;

/**
 * 物料等级Service接口
 * 
 * @author admin
 * @date 2024-03-08
 */
public interface IBasMaterialGradeService 
{
    /**
     * 查询物料等级
     * 
     * @param id 物料等级主键
     * @return 物料等级
     */
    public BasMaterialGrade selectBasMaterialGradeById(Long id);

    /**
     * 查询物料等级列表
     * 
     * @param basMaterialGrade 物料等级
     * @return 物料等级集合
     */
    public List<BasMaterialGrade> selectBasMaterialGradeList(BasMaterialGrade basMaterialGrade);

    /**
     * 新增物料等级
     * 
     * @param basMaterialGrade 物料等级
     * @return 结果
     */
    public int insertBasMaterialGrade(BasMaterialGrade basMaterialGrade);

    /**
     * 修改物料等级
     * 
     * @param basMaterialGrade 物料等级
     * @return 结果
     */
    public int updateBasMaterialGrade(BasMaterialGrade basMaterialGrade);

    /**
     * 批量删除物料等级
     * 
     * @param ids 需要删除的物料等级主键集合
     * @return 结果
     */
    public int deleteBasMaterialGradeByIds(Long[] ids);

    /**
     * 删除物料等级信息
     * 
     * @param id 物料等级主键
     * @return 结果
     */
    public int deleteBasMaterialGradeById(Long id);
}
