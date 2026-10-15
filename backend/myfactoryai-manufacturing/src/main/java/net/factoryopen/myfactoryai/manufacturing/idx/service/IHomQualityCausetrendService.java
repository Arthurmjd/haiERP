package net.factoryopen.myfactoryai.manufacturing.idx.service;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityCausetrend;

import java.util.List;

/**
 * 质异原因Service接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface IHomQualityCausetrendService {
    /**
     * 查询质异原因
     *
     * @param id 质异原因主键
     * @return 质异原因
     */
    public HomQualityCausetrend selectHomQualityCausetrendById(Long id);

    /**
     * 查询质异原因列表
     *
     * @param homQualityCausetrend 质异原因
     * @return 质异原因集合
     */
    public List<HomQualityCausetrend> selectHomQualityCausetrendList(HomQualityCausetrend homQualityCausetrend);

    /**
     * 新增质异原因
     *
     * @param homQualityCausetrend 质异原因
     * @return 结果
     */
    public int insertHomQualityCausetrend(HomQualityCausetrend homQualityCausetrend);

    /**
     * 修改质异原因
     *
     * @param homQualityCausetrend 质异原因
     * @return 结果
     */
    public int updateHomQualityCausetrend(HomQualityCausetrend homQualityCausetrend);

    /**
     * 批量删除质异原因
     *
     * @param ids 需要删除的质异原因主键集合
     * @return 结果
     */
    public int deleteHomQualityCausetrendByIds(Long[] ids);

    /**
     * 删除质异原因信息
     *
     * @param id 质异原因主键
     * @return 结果
     */
    public int deleteHomQualityCausetrendById(Long id);
}
