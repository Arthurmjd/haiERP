package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;

/**
 * 退货项跟踪Service接口
 * 
 * @author admin
 * @date 2024-10-11
 */
public interface ISupPoItemReturnService 
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
     * 批量删除退货项跟踪
     * 
     * @param ids 需要删除的退货项跟踪主键集合
     * @return 结果
     */
    public int deleteSupPoItemReturnByIds(Long[] ids);

    /**
     * 删除退货项跟踪信息
     * 
     * @param id 退货项跟踪主键
     * @return 结果
     */
    public int deleteSupPoItemReturnById(Long id);


}
