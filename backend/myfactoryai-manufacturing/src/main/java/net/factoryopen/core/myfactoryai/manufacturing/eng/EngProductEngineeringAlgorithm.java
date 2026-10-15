package net.factoryopen.core.myfactoryai.manufacturing.eng;

import net.factoryopen.core.common.exception.CategoryUnavailableException;
import net.factoryopen.core.common.exception.MaterialUnavailableException;
import net.factoryopen.core.common.exception.TypeMismatchException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.core.common.type.ErType;
import net.factoryopen.core.common.type.MaterialBaseType;
import net.factoryopen.core.common.type.MaterialMrpCalculatingType;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.*;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.*;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContract;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdContractItem;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.sup.domain.SupPoItemReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品类工艺ServiceImpl业务逻辑实现
 * 
 * @author jitfactory-generator
 * @date 2024-11-09
 */
@Service
public class EngProductEngineeringAlgorithm
{
    @Autowired(required = false)
    private EngProductEngineeringMapper productMapper;

    @Autowired(required = false)
    private EngMaterialMapper materialMapper;

    @Autowired(required = false)
    private EngCategoryMapper categoryMapper;

    @Autowired(required = false)
    private EngCategoryBomMapper cbomMapper;

    @Autowired(required = false)
    private EngCategoryRoutingMapper croutingMapper;

    @Autowired(required = false)
    private EngCategorySopMapper csopMapper;

    @Autowired(required = false)
    private EngProductBomMapper pbomMapper;

    @Autowired(required = false)
    private EngProductRoutingMapper proutingMapper;

    @Autowired(required = false)
    private EngPruductSopMapper psopMapper;

  /**
   * 继承工艺
   *
   * @param productId 产品Id
   * @return 结果
   */
  public int inheritEngineering(Long productId) throws Exception{
      //获取产品原工艺
      EngProductEngineering product = productMapper.selectEngProductEngineeringById(productId);
      if (product==null){
          String msg = "当前物料并不真实存在！";
          throw new MaterialUnavailableException(msg);
      }
      //校验品类
      EngCategory category = categoryMapper.selectEngCategoryById(product.getCategoryId());
      if (category==null){
          String msg ="产品["+product.getMaterialCode()+"]的品类不存在！";
          throw new CategoryUnavailableException(msg);
      }

      //获取产品原工艺
      List<EngProductBom> bomList = pbomMapper.selectEngProductBomListByProductId(product.getId());
      List<EngProductRouting> routingList = proutingMapper.selectEngProductRoutingListByProductId(product.getId());
      List<EngPruductSop> sopList = psopMapper.selectEngPruductSopListByProductId(product.getId());
      //获取品类工艺
      List<EngCategoryBom> parentBomList = cbomMapper.selectEngCategoryBomListByCategoryId(category.getId());
      List<EngCategoryRouting> parentRoutingList = croutingMapper.selectEngCategoryRoutingListByCategoryId(category.getId());
      List<EngCategorySop> parentSopList = csopMapper.selectEngCategorySopListByCategoryId(category.getId());

      //产成品继承品类属性
      product.setBaseType(category.getMaterialType());
      product.setHouseId(null);
      product.setPositionId(null);
      product.setConsumeType(category.getConsumingType());
      product.setPurchaseType(category.getPurchasingType());
      product.setRateLoss(category.getLossRate());
      product.setDaysCycletime(category.getDaysCycletime());
      product.setDaysLeadtime(category.getDaysLeadtime());
      product.setDaysPosttime(category.getDaysPosttime());
      product.refreshUpdatingStamp();
      productMapper.updateEngProductEngineering(product);

      //产成品继承品类BOM
      if (parentBomList!=null){
          if (bomList!=null){for (EngProductBom item:bomList){pbomMapper.deleteEngProductBomById(item.getId());}}
          for (EngCategoryBom item:parentBomList){
              EngProductBom bom = new EngProductBom();
              bom.setProductId(product.getId());
              bom.setSeqNo(item.getSeqNo());
              bom.setCategoryId(item.getMaterialId());
              bom.setMaterialName(item.getMaterialName());
              bom.setMaterialSpec(item.getMaterialSpec());
              bom.setStandardQuantity(item.getStandardQuantity());
              bom.setLossRate(item.getLossRate());
              bom.setLineId(item.getLineId());
              bom.setConsumeType(item.getConsumeType());
              bom.setCalculateType(item.getCalculateType());
              bom.refreshCreatingStamp();
              pbomMapper.insertEngProductBom(bom);
          }
      }
      //覆盖Routing
      if (parentRoutingList!=null){
          if (routingList!=null){for (EngProductRouting item:routingList){proutingMapper.deleteEngProductRoutingById(item.getId());}}
          for (EngCategoryRouting item:parentRoutingList){
              EngProductRouting routing = new EngProductRouting();
              routing.setProductId(product.getId());
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
              proutingMapper.insertEngProductRouting(routing);
          }
      }
      //覆盖Sop
      if (parentSopList!=null){
          if (sopList!=null){for (EngPruductSop item:sopList){psopMapper.deleteEngPruductSopById(item.getId());}}
          for (EngCategorySop item:parentSopList){
              EngPruductSop sop = new EngPruductSop();
              sop.setProductId(product.getId());
              sop.setStepSeq(item.getStepSeq());
              sop.setStepTitle(item.getStepTitle());
              sop.setDescription(item.getDescription());
              sop.setSopUrl(item.getSopUrl());
              sop.refreshCreatingStamp();
              psopMapper.insertEngPruductSop(sop);
          }
      }

      return 1;
  }

    /**
     * 生成采购料号
     *
     * @param bom 物料清单项
     * @return 结果
     */
    public int generateCode(EngProductBom bom) throws Exception{
        Long unitId =Integer.toUnsignedLong (((Integer) bom.getParams().get("unitId")).intValue());

        //校验料号重复否
        EngMaterial material = new EngMaterial();
        material.setMaterialCode(bom.getMaterialCode());
        List<EngMaterial> materialList = materialMapper.selectEngMaterialList(material);
        if (materialList!=null && !materialList.isEmpty()){
            String msg = "料号[" + bom.getMaterialCode() + "]已存在！";
            throw new MaterialUnavailableException(msg);
        }

        //创建采购料
        material = new EngMaterial();
        material.setMaterialCode(bom.getMaterialCode());
        material.setCategoryId(bom.getCategoryId());
        material.setCompoundId(bom.getCompoundId());
        material.setMaterialSpec(bom.getMaterialSpec());
        material.setDrawNo(bom.getDrawNo());
        material.setBaseType(String.valueOf(MaterialBaseType.MATERIAL));
        material.setMaterialName(bom.getMaterialName());
        material.setUnitId(unitId);
        material.refreshUpdatingStamp();
        materialMapper.insertEngMaterial(material);

        //修改Bom项自身
        EngProductBom bomDatabase=pbomMapper.selectEngProductBomById(bom.getId());
        bomDatabase.setMaterialId(material.getId());
        bomDatabase.setMaterialCode(material.getMaterialCode());
        bomDatabase.setMaterialName(material.getMaterialName());
        bomDatabase.setMaterialSpec(material.getMaterialSpec());
        bomDatabase.setDrawNo(material.getDrawNo());
        bomDatabase.setCalculateType(String.valueOf(MaterialMrpCalculatingType.PURCHASING));
        bomDatabase.setCategoryId(material.getCategoryId());
        bomDatabase.setCompoundId(material.getCompoundId());
        bomDatabase.refreshUpdatingStamp();
        pbomMapper.updateEngProductBom(bomDatabase);

        return 1;
    }
}
