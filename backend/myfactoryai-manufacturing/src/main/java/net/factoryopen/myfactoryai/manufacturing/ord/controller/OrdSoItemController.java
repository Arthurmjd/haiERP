package net.factoryopen.myfactoryai.manufacturing.ord.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import net.factoryopen.core.common.exception.StatusUnavailableException;
import net.factoryopen.core.common.status.BillStatus;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngMaterial;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngProductBasis;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngMaterialInfoService;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngProductBasisService;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSo;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import net.factoryopen.myfactoryai.manufacturing.ord.domain.OrdSoItem;
import net.factoryopen.myfactoryai.manufacturing.ord.service.IOrdSoItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 *
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@RestController
@RequestMapping("/ord/soItem")
public class OrdSoItemController extends BaseController {
    @Autowired
    private IOrdSoItemService ordSoItemService;
    @Autowired
    private IEngProductBasisService productService;

    @Autowired
    private IOrdSoService soService;

    /**
     * 查询订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrdSoItem ordSoItem) {
        if(ordSoItem!=null && ordSoItem.getPageEnable()!=null && ordSoItem.getPageEnable()) startPage();
        List<OrdSoItem> list = ordSoItemService.selectOrdSoItemList(ordSoItem);
        return getDataTable(list);
    }

    /**
     * 导出订单明细列表
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:export')")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrdSoItem ordSoItem) {
        List<OrdSoItem> list = ordSoItemService.selectOrdSoItemList(ordSoItem);
        ExcelUtil<OrdSoItem> util = new ExcelUtil<OrdSoItem>(OrdSoItem.class);
        util.exportExcel(response, list, "订单明细数据");
    }

    /**
     * 获取订单明细详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(ordSoItemService.selectOrdSoItemById(id));
    }

    /**
     * 新增订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:add')")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrdSoItem ordSoItem) {
        AjaxResult ar = null;
        try {
            EngProductBasis product = productService.selectEngProductBasisById(ordSoItem.getProductId());
            ordSoItem.setProductCode(product.getMaterialCode());
            ordSoItem.setDrawNo(product.getDrawNo());
            ordSoItem.setCustomerProductNo(product.getCustomerProductNo());

            int ret = ordSoItemService.insertOrdSoItem(ordSoItem);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[新增]1条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[新增]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

    /**
     * 修改订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:edit')")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrdSoItem ordSoItem) {
        AjaxResult ar = null;
        try {
            OrdSo so = soService.selectOrdSoById(ordSoItem.getSoId());
            if(so.getStatus()!= BillStatus.BASE_DRAFT && so.getStatus()!=BillStatus.BASE_AUDIT_REJECT && so.getStatus()!=BillStatus.BASE_AUDIT_PASS){
                String msg = "订单[" + so.getBillNo() + "]在当前状态下不能修改！";
                throw new StatusUnavailableException(msg);
            }

            int ret = ordSoItemService.updateOrdSoItem(ordSoItem);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[修改]" + ret + "条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[修改]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

    /**
     * 删除订单明细
     */
    //@PreAuthorize("@ss.hasPermi('ord:soItem:remove')")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        AjaxResult ar = null;
        try {
            if (ids!=null){
                for (Long id:ids){
                    OrdSoItem soitem = ordSoItemService.selectOrdSoItemById(id);
                    OrdSo so = soService.selectOrdSoById(soitem.getSoId());
                    if(so.getStatus()!= BillStatus.BASE_DRAFT && so.getStatus()!=BillStatus.BASE_AUDIT_REJECT){
                        String msg = "订单[" + so.getBillNo() + "]在当前状态下不能删除！]";
                        throw new StatusUnavailableException(msg);
                    }
                }
            }

            int ret = ordSoItemService.deleteOrdSoItemByIds(ids);
            if (ret > 0) {
                ar = AjaxResult.success("操作成功：[删除]" + ret + "条数据！");
            } else {
                ar = AjaxResult.error(ret, "操作失败：[删除]0条数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar = AjaxResult.error("操作异常：" + e.getMessage());
        }
        return ar;
    }

}
