package net.factoryopen.myfactoryai.manufacturing.idx.mapper;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveLotrend;

import java.util.List;

/**
 * 批次现Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface HomAchieveLotrendMapper {
    /**
     * 查询批次现
     *
     * @param id 批次现主键
     * @return 批次现
     */
    public HomAchieveLotrend selectHomAchieveLotrendById(Long id);

    /**
     * 查询批次现列表
     *
     * @param homAchieveLotrend 批次现
     * @return 批次现集合
     */
    public List<HomAchieveLotrend> selectHomAchieveLotrendList(HomAchieveLotrend homAchieveLotrend);

    /**
     * 新增批次现
     *
     * @param homAchieveLotrend 批次现
     * @return 结果
     */
    public int insertHomAchieveLotrend(HomAchieveLotrend homAchieveLotrend);

    /**
     * 修改批次现
     *
     * @param homAchieveLotrend 批次现
     * @return 结果
     */
    public int updateHomAchieveLotrend(HomAchieveLotrend homAchieveLotrend);

    /**
     * 删除批次现
     *
     * @param id 批次现主键
     * @return 结果
     */
    public int deleteHomAchieveLotrendById(Long id);

    /**
     * 批量删除批次现
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomAchieveLotrendByIds(Long[] ids);
}
