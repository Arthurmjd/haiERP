package net.factoryopen.myfactoryai.manufacturing.idx.service;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveDaytrend;

import java.util.List;

/**
 * 日现Service接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface IHomAchieveDaytrendService {
    /**
     * 查询日现
     *
     * @param id 日现主键
     * @return 日现
     */
    public HomAchieveDaytrend selectHomAchieveDaytrendById(Long id);

    /**
     * 查询日现列表
     *
     * @param homAchieveDaytrend 日现
     * @return 日现集合
     */
    public List<HomAchieveDaytrend> selectHomAchieveDaytrendList(HomAchieveDaytrend homAchieveDaytrend);

    /**
     * 新增日现
     *
     * @param homAchieveDaytrend 日现
     * @return 结果
     */
    public int insertHomAchieveDaytrend(HomAchieveDaytrend homAchieveDaytrend);

    /**
     * 修改日现
     *
     * @param homAchieveDaytrend 日现
     * @return 结果
     */
    public int updateHomAchieveDaytrend(HomAchieveDaytrend homAchieveDaytrend);

    /**
     * 批量删除日现
     *
     * @param ids 需要删除的日现主键集合
     * @return 结果
     */
    public int deleteHomAchieveDaytrendByIds(Long[] ids);

    /**
     * 删除日现信息
     *
     * @param id 日现主键
     * @return 结果
     */
    public int deleteHomAchieveDaytrendById(Long id);
}
