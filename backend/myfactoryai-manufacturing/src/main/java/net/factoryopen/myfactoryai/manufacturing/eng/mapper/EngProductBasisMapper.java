package net.factoryopen.myfactoryai.manufacturing.eng.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import org.springframework.stereotype.Service;

/**
 * 产成品管理Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-25
 */
@Service
public interface EngProductBasisMapper 
{
    /**
     * 查询产成品管理
     * 
     * @param id 产成品管理主键
     * @return 产成品管理
     */
    public EngProductBasis selectEngProductBasisById(Long id);

    /**
     * 查询产成品管理
     *
     * @param materialCode 产品编码
     * @return 产成品管理
     */
    public EngProductBasis selectEngProductBasisByCode(String materialCode);

    /**
     * 查询产成品管理列表
     * 
     * @param engProductBasis 产成品管理
     * @return 产成品管理集合
     */
    public List<EngProductBasis> selectEngProductBasisList(EngProductBasis engProductBasis);

    /**
     * 查询产成品管理列表
     *
     * @param unitId 单位
     * @return 产成品管理集合
     */
    public List<EngProductBasis> selectEngProductBasisListByUnitId(Long unitId);

    /**
     * 查询产成品管理列表
     *
     * @param categoryId 品类
     * @return 产成品管理集合
     */
    public List<EngProductBasis> selectEngProductBasisListByCategoryId(Long categoryId);

    /**
     * 查询产成品管理列表
     *
     * @param lineId 产线ID
     * @return 产成品管理集合
     */
    public List<EngProductBasis> selectEngProductBasisListByLineId(Long lineId);

    /**
     * 新增产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    public int insertEngProductBasis(EngProductBasis engProductBasis);

    /**
     * 修改产成品管理
     * 
     * @param engProductBasis 产成品管理
     * @return 结果
     */
    public int updateEngProductBasis(EngProductBasis engProductBasis);

    /**
     * 删除产成品管理
     * 
     * @param id 产成品管理主键
     * @return 结果
     */
    public int deleteEngProductBasisById(Long id);

    /**
     * 批量删除产成品管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEngProductBasisByIds(Long[] ids);

    /**
     * 查询产成品管理列表
     *
     * @param unitId 单位
     * @return 产成品管理集合
     */
    public int deleteEngProductBasisByUnitId(Long unitId);

    /**
     * 查询产成品管理列表
     *
     * @param categoryId 品类
     * @return 产成品管理集合
     */
    public int deleteEngProductBasisByCategoryId(Long categoryId);

    /**
     * 查询产成品管理列表
     *
     * @param lineId 产线ID
     * @return 产成品管理集合
     */
    public int deleteEngProductBasisByLineId(Long lineId);
}
