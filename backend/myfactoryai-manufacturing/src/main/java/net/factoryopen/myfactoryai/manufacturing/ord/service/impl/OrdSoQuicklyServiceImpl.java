package net.factoryopen.myfactoryai.manufacturing.ord.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.ord.OrdSoQuicklyAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoQuicklyMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoQuickly;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoQuicklyService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 急出订单Service业务层处理
 * 
 * @author admin
 * @date 2024-11-09
 */
@Service
public class OrdSoQuicklyServiceImpl implements IOrdSoQuicklyService 
{
    @Autowired(required = false)
    private OrdSoQuicklyMapper ordSoQuicklyMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private OrdSoQuicklyAlgorithm ordSoQuicklyAlgorithm;

    /**
     * 查询急出订单
     * 
     * @param id 急出订单主键
     * @return 急出订单
     */
    @Override
    public OrdSoQuickly selectOrdSoQuicklyById(Long id)
    {
        return ordSoQuicklyMapper.selectOrdSoQuicklyById(id);
    }

    /**
     * 查询急出订单列表
     * 
     * @param ordSoQuickly 急出订单
     * @return 急出订单
     */
    @Override
    public List<OrdSoQuickly> selectOrdSoQuicklyList(OrdSoQuickly ordSoQuickly)
    {
        return ordSoQuicklyMapper.selectOrdSoQuicklyList(ordSoQuickly);
    }

    /**
     * 新增急出订单
     * 
     * @param ordSoQuickly 急出订单
     * @return 结果
     */
    @Override
    public int insertOrdSoQuickly(OrdSoQuickly ordSoQuickly)
    {
        ordSoQuickly.setCreateTime(DateUtils.getNowDate());
        return ordSoQuicklyMapper.insertOrdSoQuickly(ordSoQuickly);
    }

    /**
     * 修改急出订单
     * 
     * @param ordSoQuickly 急出订单
     * @return 结果
     */
    @Override
    public int updateOrdSoQuickly(OrdSoQuickly ordSoQuickly)
    {
        ordSoQuickly.setUpdateTime(DateUtils.getNowDate());
        return ordSoQuicklyMapper.updateOrdSoQuickly(ordSoQuickly);
    }

    /**
     * 批量删除急出订单
     * 
     * @param ids 需要删除的急出订单主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoQuicklyByIds(Long[] ids)
    {
        return ordSoQuicklyMapper.deleteOrdSoQuicklyByIds(ids);
    }

    /**
     * 删除急出订单信息
     * 
     * @param id 急出订单主键
     * @return 结果
     */
    @Override
    public int deleteOrdSoQuicklyById(Long id)
    {
        return ordSoQuicklyMapper.deleteOrdSoQuicklyById(id);
    }

     /**
      * 发布
      *
      * @param ordSoQuickly 发布
      * @return 结果
      */
     @Override
     public int enable(OrdSoQuickly ordSoQuickly){
         Long id = ordSoQuickly.getId();
         OrdSoQuickly obj = ordSoQuicklyMapper.selectOrdSoQuicklyById(id);

         obj.setStatus(4);
         return ordSoQuicklyMapper.updateOrdSoQuickly(obj);
     }

    /**
     * 便捷订单生生单
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public int deliveryDirectly(Long[] ids) throws Exception{
        try{
            return ordSoQuicklyAlgorithm.deliveryDirectly(ids);
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 快速发货
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public int deliveryQuickly(Long[] ids) throws Exception{
        try{
            return ordSoQuicklyAlgorithm.deliveryQuickly(ids);
        }catch (Exception ex){
            throw ex;
        }
    }

}
