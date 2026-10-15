package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGr;

/**
 * 来料收货登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-26
 */
public interface SncGrMapper 
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
     * 查询来料收货登记列表
     *
     * @param supplierId 供应商ID
     * @return 来料收货登记集合
     */
    public List<SncGr> selectSncGrListBySupplierId(Long supplierId);

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
     * 删除来料收货登记
     * 
     * @param id 来料收货登记主键
     * @return 结果
     */
    public int deleteSncGrById(Long id);

    /**
     * 批量删除来料收货登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncGrByIds(Long[] ids);

    /**
     * 查询来料收货登记列表
     *
     * @param supplierId 供应商ID
     * @return 来料收货登记集合
     */
    public int deleteSncGrBySupplierId(Long supplierId);
}
