package net.factoryopen.myfactoryai.manufacturing.idx.mapper;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQcd;

import java.util.List;

/**
 * QCDMapper接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface HomQcdMapper {
    /**
     * 查询QCD
     *
     * @param id QCD主键
     * @return QCD
     */
    public HomQcd selectHomQcdById(Long id);

    /**
     * 查询QCD列表
     *
     * @param homQcd QCD
     * @return QCD集合
     */
    public List<HomQcd> selectHomQcdList(HomQcd homQcd);

    /**
     * 新增QCD
     *
     * @param homQcd QCD
     * @return 结果
     */
    public int insertHomQcd(HomQcd homQcd);

    /**
     * 修改QCD
     *
     * @param homQcd QCD
     * @return 结果
     */
    public int updateHomQcd(HomQcd homQcd);

    /**
     * 删除QCD
     *
     * @param id QCD主键
     * @return 结果
     */
    public int deleteHomQcdById(Long id);

    /**
     * 批量删除QCD
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomQcdByIds(Long[] ids);
}
