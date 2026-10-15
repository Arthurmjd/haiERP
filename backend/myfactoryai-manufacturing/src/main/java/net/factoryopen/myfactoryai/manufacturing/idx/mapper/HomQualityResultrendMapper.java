package net.factoryopen.myfactoryai.manufacturing.idx.mapper;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityResultrend;

import java.util.List;

/**
 * 质异结果Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface HomQualityResultrendMapper {
    /**
     * 查询质异结果
     *
     * @param id 质异结果主键
     * @return 质异结果
     */
    public HomQualityResultrend selectHomQualityResultrendById(Long id);

    /**
     * 查询质异结果列表
     *
     * @param homQualityResultrend 质异结果
     * @return 质异结果集合
     */
    public List<HomQualityResultrend> selectHomQualityResultrendList(HomQualityResultrend homQualityResultrend);

    /**
     * 新增质异结果
     *
     * @param homQualityResultrend 质异结果
     * @return 结果
     */
    public int insertHomQualityResultrend(HomQualityResultrend homQualityResultrend);

    /**
     * 修改质异结果
     *
     * @param homQualityResultrend 质异结果
     * @return 结果
     */
    public int updateHomQualityResultrend(HomQualityResultrend homQualityResultrend);

    /**
     * 删除质异结果
     *
     * @param id 质异结果主键
     * @return 结果
     */
    public int deleteHomQualityResultrendById(Long id);

    /**
     * 批量删除质异结果
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomQualityResultrendByIds(Long[] ids);
}
