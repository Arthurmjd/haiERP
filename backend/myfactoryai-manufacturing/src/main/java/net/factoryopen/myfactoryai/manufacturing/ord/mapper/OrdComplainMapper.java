package net.factoryopen.myfactoryai.manufacturing.ord.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdComplain;

/**
 * 客诉登记Mapper接口
 * 
 * @author admin
 * @date 2024-07-04
 */
public interface OrdComplainMapper 
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
     * 查询客诉登记列表
     *
     * @param soId 订单ID
     * @return 客诉登记集合
     */
    public List<OrdComplain> selectOrdComplainListBySoId(Long soId);

    /**
     * 查询客诉登记列表
     *
     * @param soItemId 订单项ID
     * @return 客诉登记集合
     */
    public List<OrdComplain> selectOrdComplainListBySoItemId(Long soItemId);

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
     * 删除客诉登记
     * 
     * @param id 客诉登记主键
     * @return 结果
     */
    public int deleteOrdComplainById(Long id);

    /**
     * 批量删除客诉登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrdComplainByIds(Long[] ids);

    /**
     * 查询客诉登记列表
     *
     * @param soId 订单ID
     * @return 客诉登记集合
     */
    public int deleteOrdComplainBySoId(Long soId);

    /**
     * 查询客诉登记列表
     *
     * @param soItemId 订单项ID
     * @return 客诉登记集合
     */
    public int deleteOrdComplainBySoItemId(Long soItemId);
}
