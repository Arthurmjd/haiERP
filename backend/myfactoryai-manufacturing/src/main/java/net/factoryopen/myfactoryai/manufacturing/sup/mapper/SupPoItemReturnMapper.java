package net.factoryopen.myfactoryai.manufacturing.sup.mapper;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;

/**
 * 退货项跟踪Mapper接口
 * 
 * @author admin
 * @date 2024-10-11
 */
public interface SupPoItemReturnMapper 
{
    /**
     * 查询退货项跟踪
     * 
     * @param id 退货项跟踪主键
     * @return 退货项跟踪
     */
    public SupPoItemReturn selectSupPoItemReturnById(Long id);

    /**
     * 查询退货项跟踪列表
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 退货项跟踪集合
     */
    public List<SupPoItemReturn> selectSupPoItemReturnList(SupPoItemReturn supPoItemReturn);

    /**
     * 查询退货项跟踪列表
     *
     * @param poId 采购单ID
     * @return 退货项跟踪集合
     */
    public List<SupPoItemReturn> selectSupPoItemReturnListByPoId(Long poId);

    /**
     * 查询退货项跟踪列表
     *
     * @param supplierId 供应商ID
     * @return 退货项跟踪集合
     */
    public List<SupPoItemReturn> selectSupPoItemReturnListBySupplierId(Long supplierId);

    /**
     * 查询退货项跟踪列表
     *
     * @param materialId 物料ID
     * @return 退货项跟踪集合
     */
    public List<SupPoItemReturn> selectSupPoItemReturnListByMaterialId(Long materialId);

    /**
     * 新增退货项跟踪
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 结果
     */
    public int insertSupPoItemReturn(SupPoItemReturn supPoItemReturn);

    /**
     * 修改退货项跟踪
     * 
     * @param supPoItemReturn 退货项跟踪
     * @return 结果
     */
    public int updateSupPoItemReturn(SupPoItemReturn supPoItemReturn);

    /**
     * 删除退货项跟踪
     * 
     * @param id 退货项跟踪主键
     * @return 结果
     */
    public int deleteSupPoItemReturnById(Long id);

    /**
     * 批量删除退货项跟踪
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupPoItemReturnByIds(Long[] ids);

    /**
     * 查询退货项跟踪列表
     *
     * @param poId 采购单ID
     * @return 退货项跟踪集合
     */
    public int deleteSupPoItemReturnByPoId(Long poId);

    /**
     * 查询退货项跟踪列表
     *
     * @param supplierId 供应商ID
     * @return 退货项跟踪集合
     */
    public int deleteSupPoItemReturnBySupplierId(Long supplierId);

    /**
     * 查询退货项跟踪列表
     *
     * @param materialId 物料ID
     * @return 退货项跟踪集合
     */
    public int deleteSupPoItemReturnByMaterialId(Long materialId);
}
