package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBom;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequest;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockRequestItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.snc.SncStockReqdealAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockReqdealMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqdeal;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockReqdealService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 请出入库处理Service业务层处理
 * 
 * @author admin
 * @date 2024-09-16
 */
@Service
public class SncStockReqdealServiceImpl implements ISncStockReqdealService 
{
    @Autowired(required = false)
    private SncStockReqdealMapper sncStockReqdealMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SncStockReqdealAlgorithm sncStockReqdealAlgorithm;

    /**
     * 查询请出入库处理
     * 
     * @param id 请出入库处理主键
     * @return 请出入库处理
     */
    @Override
    public SncStockReqdeal selectSncStockReqdealById(Long id)
    {
        return sncStockReqdealMapper.selectSncStockReqdealById(id);
    }

    /**
     * 查询请出入库处理列表
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 请出入库处理
     */
    @Override
    public List<SncStockReqdeal> selectSncStockReqdealList(SncStockReqdeal sncStockReqdeal)
    {
        return sncStockReqdealMapper.selectSncStockReqdealList(sncStockReqdeal);
    }

    /**
     * 新增请出入库处理
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 结果
     */
    @Override
    public int insertSncStockReqdeal(SncStockReqdeal sncStockReqdeal)
    {
        sncStockReqdeal.setCreateTime(DateUtils.getNowDate());
        return sncStockReqdealMapper.insertSncStockReqdeal(sncStockReqdeal);
    }

    /**
     * 修改请出入库处理
     * 
     * @param sncStockReqdeal 请出入库处理
     * @return 结果
     */
    @Override
    public int updateSncStockReqdeal(SncStockReqdeal sncStockReqdeal)
    {
        sncStockReqdeal.setUpdateTime(DateUtils.getNowDate());
        return sncStockReqdealMapper.updateSncStockReqdeal(sncStockReqdeal);
    }

    /**
     * 批量删除请出入库处理
     * 
     * @param ids 需要删除的请出入库处理主键
     * @return 结果
     */
    @Override
    public int deleteSncStockReqdealByIds(Long[] ids)
    {
        return sncStockReqdealMapper.deleteSncStockReqdealByIds(ids);
    }

    /**
     * 删除请出入库处理信息
     * 
     * @param id 请出入库处理主键
     * @return 结果
     */
    @Override
    public int deleteSncStockReqdealById(Long id)
    {
        return sncStockReqdealMapper.deleteSncStockReqdealById(id);
    }

    /**
     * 收货
     *
     * @param request 收货请求头
     * @param reqItems 收货请求明细
     * @return 结果
     */
    @Override
    public int receiveMaterial(SncStockRequest request, List<SncStockRequestItem> reqItems) throws Exception{
        try{
            return sncStockReqdealAlgorithm.receiveMaterial(request, reqItems);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 请检
     *
     * @param request 存货交易请求
     * @return 结果
     */
    public int requestInspection(SncStockRequest request) throws Exception{
        try{
            return sncStockReqdealAlgorithm.requestInspection(request);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 入库
     *
     * @param id 入库请求头
     * @param reqItems 入库请求明细
     * @return 结果
     */
    @Override
    public int stockIn(Long id, List<SncStockRequestItem> reqItems) throws Exception{
        try{
            return sncStockReqdealAlgorithm.stockIn(id, reqItems);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 出库
     *
     * @param id 出库请求头
     * @param reqItems 出库请求明细
     * @return 结果
     */
    @Override
    public int stockout(Long id, List<SncStockRequestItem> reqItems) throws Exception{
        try{
            return sncStockReqdealAlgorithm.stockout(id, reqItems);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 好物好库推荐
     *
     * @param reqItems 入库请求明细
     * @return 结果
     */
    @Override
    public List<SncStockRequestItem> gmgwSuggest(List<SncStockRequestItem> reqItems){
        return sncStockReqdealAlgorithm.gmgwSuggest(reqItems);
    }

    /**
     * 先进先出推荐
     *
     * @param reqItems 出库请求明细
     * @return 结果
     */
    @Override
    public List<SncStockRequestItem> fifoSuggest(List<SncStockRequestItem> reqItems){
        return sncStockReqdealAlgorithm.fifoSuggest(reqItems);
    }

    /**
     * 转正料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    @Override
    public int normalizeCode(SncStockRequestItem requestItem) throws Exception{
        try{
            return sncStockReqdealAlgorithm.normalizeCode(requestItem);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 生成料号
     *
     * @param requestItem 请求明细项
     * @return 结果
     */
    @Override
    public int generateMaterialCode(SncStockRequestItem requestItem) throws Exception{
        try{
            return sncStockReqdealAlgorithm.generateMaterialCode(requestItem);
        }catch (Exception ex){
            throw ex;
        }
    }

}
