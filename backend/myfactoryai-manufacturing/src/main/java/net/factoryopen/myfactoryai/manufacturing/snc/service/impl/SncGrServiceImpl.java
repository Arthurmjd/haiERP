package net.factoryopen.myfactoryai.manufacturing.snc.service.impl;

import java.util.List;

import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.snc.SncGrAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.snc.mapper.SncGrMapper;
import net.factoryopen.myfactoryai.manufacturing.snc.domain.SncGr;
import net.factoryopen.myfactoryai.manufacturing.snc.service.ISncGrService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 来料收货登记Service业务层处理
 * 
 * @author admin
 * @date 2024-07-26
 */
@Service
public class SncGrServiceImpl implements ISncGrService 
{
    @Autowired(required = false)
    private SncGrMapper sncGrMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private SncGrAlgorithm sncGrAlgorithm;

    /**
     * 查询来料收货登记
     * 
     * @param id 来料收货登记主键
     * @return 来料收货登记
     */
    @Override
    public SncGr selectSncGrById(Long id)
    {
        return sncGrMapper.selectSncGrById(id);
    }

    /**
     * 查询来料收货登记列表
     * 
     * @param sncGr 来料收货登记
     * @return 来料收货登记
     */
    @Override
    public List<SncGr> selectSncGrList(SncGr sncGr)
    {
        return sncGrMapper.selectSncGrList(sncGr);
    }

    /**
     * 新增来料收货登记
     * 
     * @param sncGr 来料收货登记
     * @return 结果
     */
    @Override
    public int insertSncGr(SncGr sncGr)
    {
        sncGr.setCreateTime(DateUtils.getNowDate());
        return sncGrMapper.insertSncGr(sncGr);
    }

    /**
     * 修改来料收货登记
     * 
     * @param sncGr 来料收货登记
     * @return 结果
     */
    @Override
    public int updateSncGr(SncGr sncGr)
    {
        sncGr.setUpdateTime(DateUtils.getNowDate());
        return sncGrMapper.updateSncGr(sncGr);
    }

    /**
     * 批量删除来料收货登记
     * 
     * @param ids 需要删除的来料收货登记主键
     * @return 结果
     */
    @Override
    public int deleteSncGrByIds(Long[] ids)
    {
        return sncGrMapper.deleteSncGrByIds(ids);
    }

    /**
     * 删除来料收货登记信息
     * 
     * @param id 来料收货登记主键
     * @return 结果
     */
    @Override
    public int deleteSncGrById(Long id)
    {
        return sncGrMapper.deleteSncGrById(id);
    }

     /**
      * 发放
      *
      * @param sncGr 发放
      * @return 结果
      */
     @Override
     public int enable(SncGr sncGr){
         Long id = sncGr.getId();
         SncGr obj = sncGrMapper.selectSncGrById(id);

         obj.setStatus(BillStatus.BASE_ENABLE);
         obj.refreshUpdatingStamp();
         return sncGrMapper.updateSncGr(obj);
     }
    /**
     * 变更
     *
     * @param sncGr 变更
     * @return 结果
     */
    @Override
    public int changeSncGr(SncGr sncGr){
        sncGr.setUpdateTime(DateUtils.getNowDate());
        return sncGrMapper.updateSncGr(sncGr);
    }
     /**
      * 冻结
      *
      * @param sncGr 冻结
      * @return 结果
      */
     @Override
     public int froze(SncGr sncGr){
         Long id = sncGr.getId();
         SncGr obj = sncGrMapper.selectSncGrById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("sncGr");
         frozen.setObjectId(id);
         frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
         sysBillFrozenMapper.insertSysBillFrozen(frozen);

         obj.setStatus(BillStatus.LIFE_FROZEN);
         obj.refreshUpdatingStamp();
         return sncGrMapper.updateSncGr(obj);
      }
     /**
      * 解冻
      *
      * @param sncGr 解冻
      * @return 结果
      */
     @Override
     public int unfroze(SncGr sncGr){
         Long id = sncGr.getId();
         SncGr obj = sncGrMapper.selectSncGrById(id);

         SysBillFrozen frozen= new SysBillFrozen();
         frozen.setClassName("sncGr");
         frozen.setObjectId(id);
         List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);

         if(list.size()>0){
             obj.setStatus(list.get(0).getFrozenStatus().intValue());
             obj.refreshUpdatingStamp();
             sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
             return sncGrMapper.updateSncGr(obj);
         }else{
             return 0;
         }
     }
     /**
      * 停用
      *
      * @param sncGr 停用
      * @return 结果
      */
     @Override
     public int disable(SncGr sncGr){
         Long id = sncGr.getId();
         SncGr obj = sncGrMapper.selectSncGrById(id);

         obj.setStatus(BillStatus.LIFE_DEAD);
         obj.refreshUpdatingStamp();
         return sncGrMapper.updateSncGr(obj);
     }
    /**
     * 入库
     *
     * @param sncGr 入库
     * @return 结果
     */
    @Override
    public int stockin(SncGr sncGr) throws Exception{
        try{
            return sncGrAlgorithm.stockin(sncGr);
        }catch (Exception ex){
            throw ex;
        }
    }
      /**
       * 请检
       *
       * @param sncGr 请检
       * @return 结果
       */
      @Override
      public int requestInspection(SncGr sncGr) throws Exception{
          try{
              return sncGrAlgorithm.requestInspection(sncGr);
          }catch (Exception ex){
              throw ex;
          }
      }

}
