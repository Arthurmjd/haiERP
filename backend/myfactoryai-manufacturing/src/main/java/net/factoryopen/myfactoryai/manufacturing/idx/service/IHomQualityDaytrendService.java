package net.factoryopen.myfactoryai.manufacturing.idx.service;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityDaytrend;

import java.util.List;

/**
 * 质异日势Service接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface IHomQualityDaytrendService {
    /**
     * 查询质异日势
     *
     * @param id 质异日势主键
     * @return 质异日势
     */
    public HomQualityDaytrend selectHomQualityDaytrendById(Long id);

    /**
     * 查询质异日势列表
     *
     * @param homQualityDaytrend 质异日势
     * @return 质异日势集合
     */
    public List<HomQualityDaytrend> selectHomQualityDaytrendList(HomQualityDaytrend homQualityDaytrend);

    /**
     * 新增质异日势
     *
     * @param homQualityDaytrend 质异日势
     * @return 结果
     */
    public int insertHomQualityDaytrend(HomQualityDaytrend homQualityDaytrend);

    /**
     * 修改质异日势
     *
     * @param homQualityDaytrend 质异日势
     * @return 结果
     */
    public int updateHomQualityDaytrend(HomQualityDaytrend homQualityDaytrend);

    /**
     * 批量删除质异日势
     *
     * @param ids 需要删除的质异日势主键集合
     * @return 结果
     */
    public int deleteHomQualityDaytrendByIds(Long[] ids);

    /**
     * 删除质异日势信息
     *
     * @param id 质异日势主键
     * @return 结果
     */
    public int deleteHomQualityDaytrendById(Long id);
}
