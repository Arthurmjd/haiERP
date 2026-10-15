package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.snc.SncStockReqhistoryAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncStockReqhistoryMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncStockReqhistory;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncStockReqhistoryService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 出入库历史Service业务层处理
 * 
 * @author liqiang
 * @date 2025-05-17
 */
@Service
public class SncStockReqhistoryServiceImpl implements ISncStockReqhistoryService 
{
    @Autowired(required = false)
    private SncStockReqhistoryMapper sncStockReqhistoryMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SncStockReqhistoryAlgorithm sncStockReqhistoryAlgorithm;

    /**
     * 查询出入库历史
     * 
     * @param id 出入库历史主键
     * @return 出入库历史
     */
    @Override
    public SncStockReqhistory selectSncStockReqhistoryById(Long id)
    {
        return sncStockReqhistoryMapper.selectSncStockReqhistoryById(id);
    }

    /**
     * 查询出入库历史列表
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 出入库历史
     */
    @Override
    public List<SncStockReqhistory> selectSncStockReqhistoryList(SncStockReqhistory sncStockReqhistory)
    {
        return sncStockReqhistoryMapper.selectSncStockReqhistoryList(sncStockReqhistory);
    }

    /**
     * 新增出入库历史
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 结果
     */
    @Override
    public int insertSncStockReqhistory(SncStockReqhistory sncStockReqhistory)
    {
        sncStockReqhistory.setCreateTime(DateUtils.getNowDate());
        return sncStockReqhistoryMapper.insertSncStockReqhistory(sncStockReqhistory);
    }

    /**
     * 修改出入库历史
     * 
     * @param sncStockReqhistory 出入库历史
     * @return 结果
     */
    @Override
    public int updateSncStockReqhistory(SncStockReqhistory sncStockReqhistory)
    {
        sncStockReqhistory.setUpdateTime(DateUtils.getNowDate());
        return sncStockReqhistoryMapper.updateSncStockReqhistory(sncStockReqhistory);
    }

    /**
     * 批量删除出入库历史
     * 
     * @param ids 需要删除的出入库历史主键
     * @return 结果
     */
    @Override
    public int deleteSncStockReqhistoryByIds(Long[] ids)
    {
        return sncStockReqhistoryMapper.deleteSncStockReqhistoryByIds(ids);
    }

    /**
     * 删除出入库历史信息
     * 
     * @param id 出入库历史主键
     * @return 结果
     */
    @Override
    public int deleteSncStockReqhistoryById(Long id)
    {
        return sncStockReqhistoryMapper.deleteSncStockReqhistoryById(id);
    }

    /**
     * 打印
     *
     * @param sncStockReqhistory 打印
     * @return 结果
     */
    @Override
    public int printRequest(SncStockReqhistory sncStockReqhistory) throws Exception{
        try{
            return sncStockReqhistoryAlgorithm.printRequest(sncStockReqhistory);
        }catch (Exception ex){
            throw ex;
        }
    }

}
