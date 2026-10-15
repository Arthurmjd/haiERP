package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGr;

/**
 * 来料收货登记Service接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface ISncGrService 
{
    /**
     * 查询来料收货登记
     * 
     * @param id 来料收货登记主键
     * @return 来料收货登记
     */
    public SncGr selectSncGrById(Long id);

    /**
     * 查询来料收货登记列表
     * 
     * @param sncGr 来料收货登记
     * @return 来料收货登记集合
     */
    public List<SncGr> selectSncGrList(SncGr sncGr);

    /**
     * 新增来料收货登记
     * 
     * @param sncGr 来料收货登记
     * @return 结果
     */
    public int insertSncGr(SncGr sncGr);

    /**
     * 修改来料收货登记
     * 
     * @param sncGr 来料收货登记
     * @return 结果
     */
    public int updateSncGr(SncGr sncGr);

    /**
     * 批量删除来料收货登记
     * 
     * @param ids 需要删除的来料收货登记主键集合
     * @return 结果
     */
    public int deleteSncGrByIds(Long[] ids);

    /**
     * 删除来料收货登记信息
     * 
     * @param id 来料收货登记主键
     * @return 结果
     */
    public int deleteSncGrById(Long id);

    /**
     * 发放
     *
     * @param sncGr 发放
     * @return 结果
     */
    public int enable(SncGr sncGr);

    /**
     * 变更
     *
     * @param sncGr 变更
     * @return 结果
     */
    public int changeSncGr(SncGr sncGr);

    /**
     * 冻结
     *
     * @param sncGr 冻结
     * @return 结果
     */
    public int froze(SncGr sncGr);

    /**
     * 解冻
     *
     * @param sncGr 解冻
     * @return 结果
     */
    public int unfroze(SncGr sncGr);

    /**
     * 停用
     *
     * @param sncGr 停用
     * @return 结果
     */
    public int disable(SncGr sncGr);

    /**
     * 入库
     *
     * @param sncGr 入库
     * @return 结果
     */
    public int stockin(SncGr sncGr) throws Exception;

    /**
     * 请检
     *
     * @param sncGr 请检
     * @return 结果
     */
    public int requestInspection(SncGr sncGr) throws Exception;


}
