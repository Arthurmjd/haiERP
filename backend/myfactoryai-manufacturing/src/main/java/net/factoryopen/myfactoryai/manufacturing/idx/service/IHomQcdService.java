package net.factoryopen.myfactoryai.manufacturing.idx.service;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQcd;

import java.util.List;

/**
 * QCDService接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface IHomQcdService {
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
     * 批量删除QCD
     *
     * @param ids 需要删除的QCD主键集合
     * @return 结果
     */
    public int deleteHomQcdByIds(Long[] ids);

    /**
     * 删除QCD信息
     *
     * @param id QCD主键
     * @return 结果
     */
    public int deleteHomQcdById(Long id);
}
