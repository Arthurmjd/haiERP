package net.factoryopen.myfactoryai.manufacturing.ord.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;

/**
 * 客诉登记Service接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface IOrdComplainService 
{
    /**
     * 查询客诉登记
     * 
     * @param id 客诉登记主键
     * @return 客诉登记
     */
    public OrdComplain selectOrdComplainById(Long id);

    /**
     * 查询客诉登记列表
     * 
     * @param ordComplain 客诉登记
     * @return 客诉登记集合
     */
    public List<OrdComplain> selectOrdComplainList(OrdComplain ordComplain);

    /**
     * 新增客诉登记
     * 
     * @param ordComplain 客诉登记
     * @return 结果
     */
    public int insertOrdComplain(OrdComplain ordComplain);

    /**
     * 修改客诉登记
     * 
     * @param ordComplain 客诉登记
     * @return 结果
     */
    public int updateOrdComplain(OrdComplain ordComplain);

    /**
     * 批量删除客诉登记
     * 
     * @param ids 需要删除的客诉登记主键集合
     * @return 结果
     */
    public int deleteOrdComplainByIds(Long[] ids);

    /**
     * 删除客诉登记信息
     * 
     * @param id 客诉登记主键
     * @return 结果
     */
    public int deleteOrdComplainById(Long id);


    /**
     * 发布
     *
     * @param ordComplain 发布
     * @return 结果
     */
    public int enableOrdComplain(OrdComplain ordComplain);

    /**
     * 请检
     *
     * @param ordComplain 请检
     * @return 结果
     */
    public int requestInspect(OrdComplain ordComplain) throws Exception;

}
