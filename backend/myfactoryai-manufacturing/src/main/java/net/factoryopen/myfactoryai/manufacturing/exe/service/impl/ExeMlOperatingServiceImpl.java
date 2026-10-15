package net.factoryopen.myfactoryai.manufacturing.exe.service.impl;

import java.util.List;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeProcessMoves;
import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeProcessMovesMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.*;
import net.factoryopen.myfactoryai.manufacturing.pln.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLine;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLaborMapper;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLineMapper;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupMapper;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

     import net.factoryopen.core.myfactoryai.manufacturing.exe.ExeMlOperatingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.exe.mapper.ExeMlOperatingMapper;
import net.factoryopen.myfactoryai.manufacturing.exe.domain.ExeMlOperating;
import net.factoryopen.myfactoryai.manufacturing.exe.service.IExeMlOperatingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 车间班组作业Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-13
 */
@Service
public class ExeMlOperatingServiceImpl implements IExeMlOperatingService 
{
    @Autowired(required = false)
    private ExeMlOperatingMapper exeMlOperatingMapper;

    @Autowired
    private ExeProcessMovesMapper routingMapper;

    @Autowired
    private PlnMlSopMapper sopMapper;

    @Autowired
    private PlnMlDrawingMapper drawingMapper;

    @Autowired
    private PlnMlVideoMapper videoMapper;

    @Autowired
    private PlnMlRoutingsopMapper rsopMapper;

    @Autowired
    private PlnMlRoutingdrawingMapper rdrawingMapper;

    @Autowired
    private PlnMlRoutingvideoMapper rvideoMapper;

    @Autowired
    private ResLaborMapper laborMapper;

    @Autowired
    private ResWorkgroupMapper groupMapper;

    @Autowired(required = false)
    private ExeMlOperatingAlgorithm exeMlOperatingAlgorithm;

    /**
     * 查询车间班组作业
     * 
     * @param id 车间班组作业主键
     * @return 车间班组作业
     */
    @Override
    public ExeMlOperating selectExeMlOperatingById(Long id)
    {
        ExeMlOperating ml =exeMlOperatingMapper.selectExeMlOperatingById(id);

        //取制造批的SOP/Drawing/Video
        List<PlnMlSop> sopList = sopMapper.selectPlnMlSopListByMlId(ml.getId());
        List<PlnMlDrawing> drawingList = drawingMapper.selectPlnMlDrawingListByMlId(ml.getId());
        List<PlnMlVideo> videoList = videoMapper.selectPlnMlVideoListByMlId(ml.getId());
        ml.setSopUrl(sopList==null || sopList.size()==0? null:sopList.get(0).getSopUrl());
        ml.setVideoUrl(videoList==null || sopList.size()==0? null:videoList.get(0).getVideoUrl());
        ml.setDrawingUrl(drawingList==null || sopList.size()==0? null:drawingList.get(0).getDrawingUrl());

        ExeProcessMoves routing = new ExeProcessMoves();
        routing.setMlId(ml.getId());
        routing.setProcessSeq(ml.getCurrentProcessSeq());
        List<ExeProcessMoves> routingList = routingMapper.selectExeProcessMovesList(routing);
        Long routingId = routingList==null?null:routingList.get(0).getId();
        if (routingId!=null){
            List<PlnMlRoutingsop> rsopList = rsopMapper.selectPlnMlRoutingsopListByRoutingId(routingId);
            List<PlnMlRoutingvideo> rvideoList = rvideoMapper.selectPlnMlRoutingvideoListByRoutingId(routingId);
            List<PlnMlRoutingdrawing> rdrawingList = rdrawingMapper.selectPlnMlRoutingdrawingListByRoutingId(routingId);
            ml.setRoutingSopUrl(rsopList==null || rsopList.size()==0? null:rsopList.get(0).getSopUrl());
            ml.setRoutingVideoUrl(rvideoList==null || rvideoList.size()==0? null:rvideoList.get(0).getVideoUrl());
            ml.setRoutingDrawingUrl(rdrawingList==null || rdrawingList.size()==0? null:rdrawingList.get(0).getDrawingUrl());
        }

        return ml;
    }

    /**
     * 查询车间班组作业列表
     * 
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业
     */
    @Override
    public List<ExeMlOperating> selectExeMlOperatingList(ExeMlOperating exeMlOperating)
    {
        return exeMlOperatingMapper.selectExeMlOperatingList(exeMlOperating);
    }

    /**
     * 查询车间班组作业列表
     *
     * @param exeMlOperating 车间班组作业
     * @return 车间班组作业
     */
    @Override
    public List<ExeMlOperating> selectExeMlOperatingListByOperator(ExeMlOperating exeMlOperating)
    {
        Long userId = SecurityUtils.getUserId();
        Long currentLineId = null;
        Long currentGroupId = null;
        List<ResLabor> laborList = laborMapper.selectResLaborListByUserId(userId);
        if (laborList!=null && laborList.size()>0){
            currentGroupId = laborList.get(0).getWorkgroupId();

            ResWorkgroup group = groupMapper.selectResWorkgroupById(currentGroupId);
            if(group!=null) currentLineId = group.getLineId();
        }

        exeMlOperating.setLineId(currentLineId);
        exeMlOperating.setCurrentWorkgroupId(currentGroupId);

        return exeMlOperatingMapper.selectExeMlOperatingListByOperator(exeMlOperating);
    }

    /**
     * 新增车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    @Override
    public int insertExeMlOperating(ExeMlOperating exeMlOperating)
    {
        exeMlOperating.setCreateTime(DateUtils.getNowDate());
        return exeMlOperatingMapper.insertExeMlOperating(exeMlOperating);
    }

    /**
     * 修改车间班组作业
     * 
     * @param exeMlOperating 车间班组作业
     * @return 结果
     */
    @Override
    public int updateExeMlOperating(ExeMlOperating exeMlOperating)
    {
        exeMlOperating.refreshUpdatingStamp();
        return exeMlOperatingMapper.updateExeMlOperating(exeMlOperating);
    }

    /**
     * 批量删除车间班组作业
     * 
     * @param ids 需要删除的车间班组作业主键
     * @return 结果
     */
    @Override
    public int deleteExeMlOperatingByIds(Long[] ids)
    {
        return exeMlOperatingMapper.deleteExeMlOperatingByIds(ids);
    }

    /**
     * 删除车间班组作业信息
     * 
     * @param id 车间班组作业主键
     * @return 结果
     */
    @Override
    public int deleteExeMlOperatingById(Long id)
    {
        return exeMlOperatingMapper.deleteExeMlOperatingById(id);
    }

      /**
       * 接收
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int acceptMl(Long[] ids) throws Exception{
          try{
              return exeMlOperatingAlgorithm.acceptMl(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
      /**
       * 请料
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int requestMaterial(Long[] ids) throws Exception{
          try{
              return exeMlOperatingAlgorithm.requestMaterial(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
      /**
       * 转出
       *
       * @param ids id集合
       * @return 结果
       */
      @Override
      public int postMl(Long[] ids) throws Exception{
          try{
              return exeMlOperatingAlgorithm.postMl(ids);
          }catch (Exception ex){
              throw ex;
          }
      }
    /**
     * 请入库
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public int requestStockin(Long[] ids) throws Exception{
        try{
            return exeMlOperatingAlgorithm.requestStockin(ids);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 批次结案
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public int finishMl(Long[] ids) throws Exception{
        try{
            return exeMlOperatingAlgorithm.finishMl(ids);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 开工
     *
     * @param exeMlOperating 开工
     * @return 结果
     */
    @Override
    public int startWork(ExeMlOperating exeMlOperating) throws Exception{
        try{
            return exeMlOperatingAlgorithm.startWork(exeMlOperating);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 完工
     *
     * @param exeMlOperating 完工
     * @return 结果
     */
    @Override
    public int finishWork(ExeMlOperating exeMlOperating) throws Exception{
        try{
            return exeMlOperatingAlgorithm.finishWork(exeMlOperating);
        }catch (Exception ex){
            throw ex;
        }
    }

}
