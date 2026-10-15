package net.factoryopen.myfactoryai.manufacturing.snc.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventoryIo;

/**
 * 仓库存货流水Service接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
public interface ISncInventoryIoService 
{
    /**
     * 查询仓库存货流水
     * 
     * @param id 仓库存货流水主键
     * @return 仓库存货流水
     */
    public SncInventoryIo selectSncInventoryIoById(Long id);

    /**
     * 查询仓库存货流水列表
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 仓库存货流水集合
     */
    public List<SncInventoryIo> selectSncInventoryIoList(SncInventoryIo sncInventoryIo);

    /**
     * 新增仓库存货流水
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 结果
     */
    public int insertSncInventoryIo(SncInventoryIo sncInventoryIo);

    /**
     * 修改仓库存货流水
     * 
     * @param sncInventoryIo 仓库存货流水
     * @return 结果
     */
    public int updateSncInventoryIo(SncInventoryIo sncInventoryIo);

    /**
     * 批量删除仓库存货流水
     * 
     * @param ids 需要删除的仓库存货流水主键集合
     * @return 结果
     */
    public int deleteSncInventoryIoByIds(Long[] ids);

    /**
     * 删除仓库存货流水信息
     * 
     * @param id 仓库存货流水主键
     * @return 结果
     */
    public int deleteSncInventoryIoById(Long id);


}
