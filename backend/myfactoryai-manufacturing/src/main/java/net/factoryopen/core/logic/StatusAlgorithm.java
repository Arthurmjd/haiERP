package net.factoryopen.core.logic;

import net.factoryopen.core.common.codeManager.BillcodeManager;
import net.factoryopen.core.common.status.BillStatus;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelDeliveryDeal;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelMps;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelOsplan;
import net.factoryopen.myfactoryai.manufacturing.del.domain.DelPlanItem;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelDeliveryDealMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelMpsMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelOsplanMapper;
import net.factoryopen.myfactoryai.manufacturing.del.mapper.DelPlanItemMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngProductBasisMapper;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.mapper.OrdSoMapper;
import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

/**
 * 状态统一处理的业务逻辑实现
 *
 * @author jitfactory-generator
 * @date 2024-06-25
 */
@Service
public class StatusAlgorithm{

//    /**
//     * 新增外协计划维护
//     *
//     * @param delOsplan 外协计划维护
//     * @return 结果
//     */
//    @Override
//    public int insert(Object mapper, Class mapperClass, String entityBigName, Class entityClass, BaseEntity entity)
//    {
//        String methodName = "insert"+entityBigName;
//        Method method = mapperClass.getMethod(methodName, entityClass);
//        method.invoke(mapper, entity);
//    }
//
//    /**
//     * 修改外协计划维护
//     *
//     * @param delOsplan 外协计划维护
//     * @return 结果
//     */
//    @Override
//    public int updateDelOsplan(DelOsplan delOsplan)
//    {
//        delOsplan.setUpdateTime(DateUtils.getNowDate());
//        return delOsplanMapper.updateDelOsplan(delOsplan);
//    }
//
//    /**
//     * 批量删除外协计划维护
//     *
//     * @param ids 需要删除的外协计划维护主键
//     * @return 结果
//     */
//    @Override
//    public int deleteDelOsplanByIds(Long[] ids)
//    {
//        return delOsplanMapper.deleteDelOsplanByIds(ids);
//    }
//
//    /**
//     * 删除外协计划维护信息
//     *
//     * @param id 外协计划维护主键
//     * @return 结果
//     */
//    @Override
//    public int deleteDelOsplanById(Long id)
//    {
//        return delOsplanMapper.deleteDelOsplanById(id);
//    }
//
//    /**
//     * 变更
//     *
//     * @param delOsplan 变更
//     * @return 结果
//     */
//    @Override
//    public int changeDelOsplan(DelOsplan delOsplan){
//        delOsplan.setUpdateTime(DateUtils.getNowDate());
//        return delOsplanMapper.updateDelOsplan(delOsplan);
//    }
//    /**
//     * 冻结
//     *
//     * @param delOsplan 冻结
//     * @return 结果
//     */
//    @Override
//    public int froze(DelOsplan delOsplan){
//        Long id = delOsplan.getId();
//        DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);
//
//        SysBillFrozen frozen= new SysBillFrozen();
//        frozen.setClassName("delOsplan");
//        frozen.setObjectId(id);
//        frozen.setFrozenStatus((Long.valueOf(obj.getStatus().longValue())));
//        sysBillFrozenMapper.insertSysBillFrozen(frozen);
//
//        obj.setStatus(BillStatus.LIFE_FROZEN);
//        return delOsplanMapper.updateDelOsplan(obj);
//    }
//    /**
//     * 解冻
//     *
//     * @param delOsplan 解冻
//     * @return 结果
//     */
//    @Override
//    public int unfroze(DelOsplan delOsplan){
//        Long id = delOsplan.getId();
//        DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);
//
//        SysBillFrozen frozen= new SysBillFrozen();
//        frozen.setClassName("delOsplan");
//        frozen.setObjectId(id);
//        List<SysBillFrozen> list =sysBillFrozenMapper.selectSysBillFrozenList(frozen);
//
//        if(list.size()>0){
//            obj.setStatus(list.get(0).getFrozenStatus().intValue());
//            sysBillFrozenMapper.deleteSysBillFrozenById(list.get(0).getId());
//            return delOsplanMapper.updateDelOsplan(obj);
//        }else{
//            return 0;
//        }
//    }
//    /**
//     * 停用
//     *
//     * @param delOsplan 停用
//     * @return 结果
//     */
//    @Override
//    public int disable(DelOsplan delOsplan){
//        Long id = delOsplan.getId();
//        DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);
//
//        obj.setStatus(BillStatus.LIFE_DEAD);
//        return delOsplanMapper.updateDelOsplan(obj);
//    }
//    /**
//     * 强结
//     *
//     * @param delOsplan 强结
//     * @return 结果
//     */
//    @Override
//    public int finishforcely(DelOsplan delOsplan){
//        Long id = delOsplan.getId();
//        DelOsplan obj = delOsplanMapper.selectDelOsplanById(id);
//        obj.setStatus(BillStatus.FINISH_FORCELY);
//        return delOsplanMapper.updateDelOsplan(obj);
//    }

}
