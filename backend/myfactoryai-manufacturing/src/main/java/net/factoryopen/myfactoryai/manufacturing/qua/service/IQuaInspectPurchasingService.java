package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectPurchasing;

/**
 * 外购检异登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IQuaInspectPurchasingService 
{
    /**
     * 查询外购检异登记
     * 
     * @param id 外购检异登记主键
     * @return 外购检异登记
     */
    public QuaInspectPurchasing selectQuaInspectPurchasingById(Long id);

    /**
     * 查询外购检异登记列表
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 外购检异登记集合
     */
    public List<QuaInspectPurchasing> selectQuaInspectPurchasingList(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 新增外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    public int insertQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 修改外购检异登记
     * 
     * @param quaInspectPurchasing 外购检异登记
     * @return 结果
     */
    public int updateQuaInspectPurchasing(QuaInspectPurchasing quaInspectPurchasing);

    /**
     * 批量删除外购检异登记
     * 
     * @param ids 需要删除的外购检异登记主键集合
     * @return 结果
     */
    public int deleteQuaInspectPurchasingByIds(Long[] ids);

    /**
     * 删除外购检异登记信息
     * 
     * @param id 外购检异登记主键
     * @return 结果
     */
    public int deleteQuaInspectPurchasingById(Long id);


}
