package net.factoryopen.myfactoryai.manufacturing.snc.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncInventoryIo;

/**
 * 仓库存货流水Mapper接口
 * 
 * @author jitfactory-generator
 * @date 2024-07-20
 */
public interface SncInventoryIoMapper 
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
     * 查询仓库存货流水列表
     *
     * @param houseId 现仓库
     * @return 仓库存货流水集合
     */
    public List<SncInventoryIo> selectSncInventoryIoListByHouseId(Long houseId);

    /**
     * 查询仓库存货流水列表
     *
     * @param positionId 现储位
     * @return 仓库存货流水集合
     */
    public List<SncInventoryIo> selectSncInventoryIoListByPositionId(Long positionId);

    /**
     * 查询仓库存货流水列表
     *
     * @param materialId 物料编码
     * @return 仓库存货流水集合
     */
    public List<SncInventoryIo> selectSncInventoryIoListByMaterialId(Long materialId);

    /**
     * 查询仓库存货流水列表
     *
     * @param mlId 制造批
     * @return 仓库存货流水集合
     */
    public List<SncInventoryIo> selectSncInventoryIoListByMlId(Long mlId);

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
     * 删除仓库存货流水
     * 
     * @param id 仓库存货流水主键
     * @return 结果
     */
    public int deleteSncInventoryIoById(Long id);

    /**
     * 批量删除仓库存货流水
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSncInventoryIoByIds(Long[] ids);

    /**
     * 查询仓库存货流水列表
     *
     * @param houseId 现仓库
     * @return 仓库存货流水集合
     */
    public int deleteSncInventoryIoByHouseId(Long houseId);

    /**
     * 查询仓库存货流水列表
     *
     * @param positionId 现储位
     * @return 仓库存货流水集合
     */
    public int deleteSncInventoryIoByPositionId(Long positionId);

    /**
     * 查询仓库存货流水列表
     *
     * @param materialId 物料编码
     * @return 仓库存货流水集合
     */
    public int deleteSncInventoryIoByMaterialId(Long materialId);

    /**
     * 查询仓库存货流水列表
     *
     * @param mlId 制造批
     * @return 仓库存货流水集合
     */
    public int deleteSncInventoryIoByMlId(Long mlId);
}
