package net.factoryopen.myfactoryai.manufacturing.qua.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngMaterialMapper;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResLabor;
import net.factoryopen.myfactoryai.manufacturing.res.domain.ResWorkgroup;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResLaborMapper;
import net.factoryopen.myfactoryai.manufacturing.res.mapper.ResWorkgroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import net.factoryopen.core.myfactoryai.manufacturing.qua.ExeInspectSelfAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.qua.mapper.ExeInspectSelfMapper;
import net.factoryopen.myfactoryai.manufacturing.qua.domain.ExeInspectSelf;
import net.factoryopen.myfactoryai.manufacturing.qua.service.IExeInspectSelfService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 自检异常登记Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-09
 */
@Service
public class ExeInspectSelfServiceImpl implements IExeInspectSelfService 
{
    @Autowired(required = false)
    private ExeInspectSelfMapper exeInspectSelfMapper;

    @Autowired
    private ResLaborMapper laborMapper;

    @Autowired
    private ResWorkgroupMapper groupMapper;

    @Autowired
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private ExeInspectSelfAlgorithm exeInspectSelfAlgorithm;

    /**
     * 查询自检异常登记
     * 
     * @param id 自检异常登记主键
     * @return 自检异常登记
     */
    @Override
    public ExeInspectSelf selectExeInspectSelfById(Long id)
    {
        return exeInspectSelfMapper.selectExeInspectSelfById(id);
    }

    /**
     * 查询自检异常登记列表
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 自检异常登记
     */
    @Override
    public List<ExeInspectSelf> selectExeInspectSelfList(ExeInspectSelf exeInspectSelf)
    {
        return exeInspectSelfMapper.selectExeInspectSelfList(exeInspectSelf);
    }

    /**
     * 查询自检异常登记列表
     *
     * @param exeInspectSelf 自检异常登记
     * @return 自检异常登记
     */
    @Override
    public List<ExeInspectSelf> selectExeInspectSelfListByOperator(ExeInspectSelf exeInspectSelf)
    {
        //过滤产线、班组
        Long userId = SecurityUtils.getUserId();
        Long currentLineId = null;
        Long currentGroupId = null;
        List<ResLabor> laborList = laborMapper.selectResLaborListByUserId(userId);
        if (laborList!=null && laborList.size()>0){
            currentGroupId = laborList.get(0).getWorkgroupId();

            ResWorkgroup group = groupMapper.selectResWorkgroupById(currentGroupId);
            if(group!=null) currentLineId = group.getLineId();
        }

        return exeInspectSelfMapper.selectExeInspectSelfList(exeInspectSelf);
    }

    /**
     * 新增自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    @Override
    public int insertExeInspectSelf(ExeInspectSelf exeInspectSelf)
    {
        //取产品信息
        EngMaterial material = materialMapper.selectEngMaterialById(exeInspectSelf.getMaterialId());
        //补基本信息
        exeInspectSelf.setMaterialCode(material.getMaterialCode());
        exeInspectSelf.setMaterialName(material.getMaterialName());
        exeInspectSelf.setMaterialSpec(material.getMaterialSpec());
        //保存
        exeInspectSelf.refreshUpdatingStamp();
        return exeInspectSelfMapper.insertExeInspectSelf(exeInspectSelf);
    }

    /**
     * 修改自检异常登记
     * 
     * @param exeInspectSelf 自检异常登记
     * @return 结果
     */
    @Override
    public int updateExeInspectSelf(ExeInspectSelf exeInspectSelf)
    {
        exeInspectSelf.setUpdateTime(DateUtils.getNowDate());
        return exeInspectSelfMapper.updateExeInspectSelf(exeInspectSelf);
    }

    /**
     * 批量删除自检异常登记
     * 
     * @param ids 需要删除的自检异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeInspectSelfByIds(Long[] ids)
    {
        return exeInspectSelfMapper.deleteExeInspectSelfByIds(ids);
    }

    /**
     * 删除自检异常登记信息
     * 
     * @param id 自检异常登记主键
     * @return 结果
     */
    @Override
    public int deleteExeInspectSelfById(Long id)
    {
        return exeInspectSelfMapper.deleteExeInspectSelfById(id);
    }

      /**
       * 请判
       *
       * @param exeInspectSelf 请判
       * @return 结果
       */
      @Override
      public int requestDeal(ExeInspectSelf exeInspectSelf) throws Exception{
          try{
              return exeInspectSelfAlgorithm.requestDeal(exeInspectSelf);
          }catch (Exception ex){
              throw ex;
          }
      }

}
