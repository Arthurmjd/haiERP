package net.factoryopen.myfactoryai.manufacturing.qua.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.QuaInspectReturn;

/**
 * 退货检异登记Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
public interface IQuaInspectReturnService 
{
    /**
     * 查询退货检异登记
     * 
     * @param id 退货检异登记主键
     * @return 退货检异登记
     */
    public QuaInspectReturn selectQuaInspectReturnById(Long id);

    /**
     * 查询退货检异登记列表
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 退货检异登记集合
     */
    public List<QuaInspectReturn> selectQuaInspectReturnList(QuaInspectReturn quaInspectReturn);

    /**
     * 新增退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    public int insertQuaInspectReturn(QuaInspectReturn quaInspectReturn);

    /**
     * 修改退货检异登记
     * 
     * @param quaInspectReturn 退货检异登记
     * @return 结果
     */
    public int updateQuaInspectReturn(QuaInspectReturn quaInspectReturn);

    /**
     * 批量删除退货检异登记
     * 
     * @param ids 需要删除的退货检异登记主键集合
     * @return 结果
     */
    public int deleteQuaInspectReturnByIds(Long[] ids);

    /**
     * 删除退货检异登记信息
     * 
     * @param id 退货检异登记主键
     * @return 结果
     */
    public int deleteQuaInspectReturnById(Long id);

    /**
     * 请判
     *
     * @param quaInspectReturn 请判
     * @return 结果
     */
    public int requestDeal(QuaInspectReturn quaInspectReturn) throws Exception;


}
