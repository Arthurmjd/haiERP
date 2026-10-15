package net.factoryopen.core.myfactoryai.manufacturing.eng;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.factoryopen.core.common.codeManager.BillcodeManager;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.exception.CustomerUnavailableException;
import net.factoryopen.core.common.exception.DateUnavailableException;

/**
 * 品类工艺ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@Service
public class EngCategoryEngineeringAlgorithm
{
    @Autowired(required = false)
    private EngCategoryMapper categoryMapper;

    @Autowired(required = false)
    private EngCategoryBomMapper bomMapper;

    @Autowired(required = false)
    private EngCategoryRoutingMapper routingMapper;

    @Autowired(required = false)
    private EngCategorySopMapper sopMapper;

      /**
       * 填补子孙
       *
       * @param category 品类
       * @return 结果
       */
      public int fillChildren(EngCategoryEngineering category) throws Exception{
          EngCategory parentCategory = categoryMapper.selectEngCategoryById(category.getId());
          List<EngCategoryBom> parentBomList = bomMapper.selectEngCategoryBomListByCategoryId(parentCategory.getId());
          List<EngCategoryRouting> parentRoutingList = routingMapper.selectEngCategoryRoutingListByCategoryId(parentCategory.getId());
          List<EngCategorySop> parentSopList = sopMapper.selectEngCategorySopListByCategoryId(parentCategory.getId());

          //填补下级
          List<EngCategory> childList = categoryMapper.selectEngCategoryListByParentId(parentCategory.getId());
          if (childList!=null){
              for (EngCategory item:childList){
                  this.fillChild(item,false,parentCategory,parentBomList,parentRoutingList,parentSopList);
              }
          }

          return 1;
      }

      /**
       * 覆盖子孙
       *
       * @param categoryEngineering 品类
       * @return 结果
       */
      public int coverChildren(EngCategoryEngineering categoryEngineering) throws Exception{
          EngCategory parentCategory = categoryMapper.selectEngCategoryById(categoryEngineering.getId());
          List<EngCategoryBom> parentBomList = bomMapper.selectEngCategoryBomListByCategoryId(parentCategory.getId());
          List<EngCategoryRouting> parentRoutingList = routingMapper.selectEngCategoryRoutingListByCategoryId(parentCategory.getId());
          List<EngCategorySop> parentSopList = sopMapper.selectEngCategorySopListByCategoryId(parentCategory.getId());

          //覆盖下级
          List<EngCategory> childList = categoryMapper.selectEngCategoryListByParentId(parentCategory.getId());
          if (childList!=null){
              for (EngCategory item:childList){
                  this.fillChild(item,true,parentCategory,parentBomList,parentRoutingList,parentSopList);
              }
          }

          return 1;
      }

      private void fillChild(EngCategory category,
                             boolean coverEnable,
                             EngCategory root,
                             List<EngCategoryBom> rootBomList, List<EngCategoryRouting> rootRoutingList, List<EngCategorySop> rootSopList){
          //保证父子类型一致
          category.setMaterialType(root.getMaterialType());
          //若为覆盖则覆盖品类基本属性，否则填空
          if (coverEnable){
              category.setConsumingType(root.getConsumingType());
              category.setPurchasingType(root.getPurchasingType());
              category.setLossRate(root.getLossRate());
              category.setDaysCycletime(root.getDaysCycletime());
              category.setDaysLeadtime(root.getDaysLeadtime());
              category.setDaysPosttime(root.getDaysPosttime());
          }else{
              if (category.getConsumingType()==null) category.setConsumingType(root.getConsumingType());
              if (category.getPurchasingType()==null) category.setPurchasingType(root.getPurchasingType());
              if (category.getLossRate().doubleValue()<=0) category.setLossRate(root.getLossRate());
              if (category.getDaysCycletime().doubleValue()<=0) category.setDaysCycletime(root.getDaysCycletime());
              if (category.getDaysLeadtime().doubleValue()<=0) category.setDaysLeadtime(root.getDaysLeadtime());
              if (category.getDaysPosttime().doubleValue()<=0) category.setDaysPosttime(root.getDaysPosttime());
          }
          category.refreshUpdatingStamp();
          categoryMapper.updateEngCategory(category);

          //覆盖或增补下级工艺
          List<EngCategoryBom> bomList = bomMapper.selectEngCategoryBomListByCategoryId(category.getId());
          List<EngCategoryRouting> routingList = routingMapper.selectEngCategoryRoutingListByCategoryId(category.getId());
          List<EngCategorySop> sopList = sopMapper.selectEngCategorySopListByCategoryId(category.getId());
          if (coverEnable){
              //覆盖BOM
              if (rootBomList!=null){
                  if (bomList!=null){for (EngCategoryBom item:bomList){bomMapper.deleteEngCategoryBomById(item.getId());}}
                  for (EngCategoryBom item:rootBomList){
                      EngCategoryBom bom = new EngCategoryBom();
                      bom.setCategoryId(category.getId());
                      bom.setSeqNo(item.getSeqNo());
                      bom.setMaterialId(item.getMaterialId());
                      bom.setMaterialName(item.getMaterialName());
                      bom.setMaterialSpec(item.getMaterialSpec());
                      bom.setStandardQuantity(item.getStandardQuantity());
                      bom.setLossRate(item.getLossRate());
                      bom.setCalculateType(item.getCalculateType());
                      bom.setLineId(item.getLineId());
                      bom.setConsumeType(item.getConsumeType());
                      bom.refreshCreatingStamp();
                      bomMapper.insertEngCategoryBom(bom);
                  }
              }
              //覆盖Routing
              if (rootRoutingList!=null){
                  if (routingList!=null){for (EngCategoryRouting item:routingList){routingMapper.deleteEngCategoryRoutingById(item.getId());}}
                  for (EngCategoryRouting item:rootRoutingList){
                      EngCategoryRouting routing = new EngCategoryRouting();
                      routing.setCategoryId(category.getId());
                      routing.setSeqNo(item.getSeqNo());
                      routing.setLastSeqNo(item.getLastSeqNo());
                      routing.setNextSeqNo(item.getNextSeqNo());
                      routing.setProcessId(item.getProcessId());
                      routing.setProcessType(item.getProcessType());
                      routing.setLineId(item.getLineId());
                      routing.setLineType(item.getLineType());
                      routing.setSecondsFixed(item.getSecondsFixed());
                      routing.setSecondsVariable(item.getSecondsVariable());
                      routing.setSecondsRm(item.getSecondsRm());
                      routing.setSecondsSetup(item.getSecondsSetup());
                      routing.setSecondsStacking(item.getSecondsStacking());
                      routing.setSecondsTransfer(item.getSecondsTransfer());
                      routing.setSecondsBuffer(item.getSecondsBuffer());
                      routing.refreshCreatingStamp();
                      routingMapper.insertEngCategoryRouting(routing);
                  }
              }
              //覆盖Sop
              if (rootSopList!=null){
                  if (sopList!=null){for (EngCategorySop item:sopList){sopMapper.deleteEngCategorySopById(item.getId());}}
                  for (EngCategorySop item:rootSopList){
                      EngCategorySop sop = new EngCategorySop();
                      sop.setCategoryId(category.getId());
                      sop.setStepSeq(item.getStepSeq());
                      sop.setStepTitle(item.getStepTitle());
                      sop.setDescription(item.getDescription());
                      sop.setSopUrl(item.getSopUrl());
                      sop.refreshCreatingStamp();
                      sopMapper.insertEngCategorySop(sop);
                  }
              }
          }
          else{
              //填空BOM
              if (bomList==null || (bomList!=null && bomList.size()==0)){
                  if (rootBomList!=null){
                      for (EngCategoryBom item:rootBomList){
                          EngCategoryBom bom = new EngCategoryBom();
                          bom.setCategoryId(category.getId());
                          bom.setSeqNo(item.getSeqNo());
                          bom.setMaterialId(item.getMaterialId());
                          bom.setMaterialName(item.getMaterialName());
                          bom.setMaterialSpec(item.getMaterialSpec());
                          bom.setStandardQuantity(item.getStandardQuantity());
                          bom.setLossRate(item.getLossRate());
                          bom.setCalculateType(item.getCalculateType());
                          bom.setLineId(item.getLineId());
                          bom.setConsumeType(item.getConsumeType());
                          bom.refreshCreatingStamp();
                          bomMapper.insertEngCategoryBom(bom);
                      }
                  }
              }
              //填空Routing
              if (routingList==null || (routingList!=null && routingList.size()==0)){
                  if (rootRoutingList!=null){
                      for (EngCategoryRouting item:rootRoutingList){
                          EngCategoryRouting routing = new EngCategoryRouting();
                          routing.setCategoryId(category.getId());
                          routing.setSeqNo(item.getSeqNo());
                          routing.setLastSeqNo(item.getLastSeqNo());
                          routing.setNextSeqNo(item.getNextSeqNo());
                          routing.setProcessId(item.getProcessId());
                          routing.setProcessType(item.getProcessType());
                          routing.setLineId(item.getLineId());
                          routing.setLineType(item.getLineType());
                          routing.setSecondsFixed(item.getSecondsFixed());
                          routing.setSecondsVariable(item.getSecondsVariable());
                          routing.setSecondsRm(item.getSecondsRm());
                          routing.setSecondsSetup(item.getSecondsSetup());
                          routing.setSecondsStacking(item.getSecondsStacking());
                          routing.setSecondsTransfer(item.getSecondsTransfer());
                          routing.setSecondsBuffer(item.getSecondsBuffer());
                          routing.refreshCreatingStamp();
                          routingMapper.insertEngCategoryRouting(routing);
                      }
                  }
              }
              //填空Sop
              if (sopList==null || (sopList!=null && sopList.size()==0)){
                  if (rootSopList!=null){
                      for (EngCategorySop item:rootSopList){
                          EngCategorySop sop = new EngCategorySop();
                          sop.setCategoryId(category.getId());
                          sop.setStepSeq(item.getStepSeq());
                          sop.setStepTitle(item.getStepTitle());
                          sop.setDescription(item.getDescription());
                          sop.setSopUrl(item.getSopUrl());
                          sop.refreshCreatingStamp();
                          sopMapper.insertEngCategorySop(sop);
                      }
                  }
              }
          }

          //递归到下一级
          List<EngCategory> childList = categoryMapper.selectEngCategoryListByParentId(category.getId());
          if (childList!=null){
              for (EngCategory item:childList){
                  fillChild(item,coverEnable,root,rootBomList,rootRoutingList,rootSopList);
              }
          }
      }
}
