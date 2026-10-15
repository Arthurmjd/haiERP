package net.factoryopen.myfactoryai.manufacturing.sup.service;

import java.util.List;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPo;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItem;

/**
 * 采购单管理Service接口
 * 
 * @author admin
 * @date 2024-09-23
 */
public interface ISupPoService 
{
    /**
     * 查询采购单管理
     * 
     * @param id 采购单管理主键
     * @return 采购单管理
     */
    public SupPo selectSupPoById(Long id);

    /**
     * 查询采购单管理列表
     * 
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoDraftList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoPublishingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoProcessingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoFinishingList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoHistoryList(SupPo supPo);

    /**
     * 查询采购单管理列表
     *
     * @param supPo 采购单管理
     * @return 采购单管理集合
     */
    public List<SupPo> selectSupPoAllList(SupPo supPo);

    /**
     * 新增采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    public int insertSupPo(SupPo supPo);

    /**
     * 修改采购单管理
     * 
     * @param supPo 采购单管理
     * @return 结果
     */
    public int updateSupPo(SupPo supPo);

    /**
     * 批量删除采购单管理
     * 
     * @param ids 需要删除的采购单管理主键集合
     * @return 结果
     */
    public int deleteSupPoByIds(Long[] ids);

    /**
     * 删除采购单管理信息
     * 
     * @param id 采购单管理主键
     * @return 结果
     */
    public int deleteSupPoById(Long id);

    /**
     * 发放
     *
     * @param supPo 发放
     * @return 结果
     */
    public int enable(SupPo supPo);

    /**
     * 变更
     *
     * @param supPo 变更
     * @return 结果
     */
    public int changeSupPo(SupPo supPo);

    /**
     * 冻结
     *
     * @param supPo 冻结
     * @return 结果
     */
    public int froze(SupPo supPo);

    /**
     * 解冻
     *
     * @param supPo 解冻
     * @return 结果
     */
    public int unfroze(SupPo supPo);

    /**
     * 停用
     *
     * @param supPo 停用
     * @return 结果
     */
    public int disable(SupPo supPo);

    /**
     * 通知收货
     *
     * @param supPo 通知收货
     * @return 结果
     */
    public int noticeReceiving(SupPo supPo) throws Exception;

    /**
     * 通知退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    public int noticeReturning(SupPoItem poitem) throws Exception;

    /**
     * 放弃退货
     *
     * @param poitem 采购明细
     * @return 结果
     */
    public int ignoreReturning(SupPoItem poitem) throws Exception;


}
